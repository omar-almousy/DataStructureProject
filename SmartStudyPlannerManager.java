/*
 * Course: [COURSE_CODE]
 * Section: [SECTION]
 * Project: Smart Study Planner
 * Group Members:
 *   [Member 1 Name] - [Member 1 ID]
 *   [Member 2 Name] - [Member 2 ID]
 *   [Member 3 Name] - [Member 3 ID]
 *   [Member 4 Name] - [Member 4 ID]
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Manages courses, study tasks, and study sessions for the Smart Study Planner.
 * Uses ArrayList, HashMap, and PriorityQueue data structures for efficient storage and retrieval.
 */
public class SmartStudyPlannerManager {
    private List<Course> courses;
    private List<StudyTask> tasks;
    private List<StudySession> sessions;
    private Map<String, Course> courseMap;
    private Map<String, List<StudyTask>> tasksByCourse;

    public SmartStudyPlannerManager() {
        this.courses = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.courseMap = new HashMap<>();
        this.tasksByCourse = new HashMap<>();
    }

    // ----- Course management -----
    public boolean addCourse(Course course) {
        if (course == null || courseMap.containsKey(course.getCourseCode())) {
            return false;
        }
        courses.add(course);
        courseMap.put(course.getCourseCode(), course);
        return true;
    }

    public Course getCourseByCode(String courseCode) {
        return courseMap.get(courseCode);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public boolean removeCourse(String courseCode) {
        Course c = courseMap.remove(courseCode);
        if (c == null) return false;
        courses.remove(c);
        tasksByCourse.remove(courseCode);
        tasks.removeIf(t -> courseCode.equals(t.getCourse() != null ? t.getCourse().getCourseCode() : null));
        return true;
    }

    // ----- Task management -----
    public boolean addTask(StudyTask task) {
        if (task == null || task.getCourse() == null) {
            return false;
        }
        tasks.add(task);
        tasksByCourse.computeIfAbsent(task.getCourse().getCourseCode(), k -> new ArrayList<>()).add(task);
        return true;
    }

    public StudyTask getTaskById(String taskId) {
        for (StudyTask t : tasks) {
            if (taskId.equals(t.getTaskId())) {
                return t;
            }
        }
        return null;
    }

    public List<StudyTask> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<StudyTask> getTasksByCourse(String courseCode) {
        List<StudyTask> list = tasksByCourse.get(courseCode);
        return list != null ? new ArrayList<>(list) : new ArrayList<>();
    }

    public List<StudyTask> getPendingTasks() {
        return tasks.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }

    public List<StudyTask> getCompletedTasks() {
        return tasks.stream().filter(StudyTask::isCompleted).collect(Collectors.toList());
    }

    /**
     * Returns pending tasks ordered by priority (High first) then due date.
     */
    public List<StudyTask> getPrioritizedPendingTasks() {
        PriorityQueue<StudyTask> pq = new PriorityQueue<>();
        for (StudyTask t : tasks) {
            if (!t.isCompleted()) {
                pq.offer(t);
            }
        }
        List<StudyTask> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll());
        }
        return result;
    }

    public boolean markTaskComplete(String taskId) {
        StudyTask t = getTaskById(taskId);
        if (t != null) {
            t.markComplete();
            return true;
        }
        return false;
    }

    public boolean removeTask(String taskId) {
        StudyTask t = getTaskById(taskId);
        if (t == null) return false;
        tasks.remove(t);
        if (t.getCourse() != null) {
            List<StudyTask> courseTasks = tasksByCourse.get(t.getCourse().getCourseCode());
            if (courseTasks != null) {
                courseTasks.remove(t);
            }
        }
        return true;
    }

    // ----- Session management -----
    public boolean addSession(StudySession session) {
        if (session == null) {
            return false;
        }
        sessions.add(session);
        return true;
    }

    public List<StudySession> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    public List<StudySession> getSessionsByCourse(String courseCode) {
        return sessions.stream()
                .filter(s -> s.getCourse() != null && courseCode.equals(s.getCourse().getCourseCode()))
                .collect(Collectors.toList());
    }

    public StudySession getSessionById(String sessionId) {
        for (StudySession s : sessions) {
            if (sessionId.equals(s.getSessionId())) {
                return s;
            }
        }
        return null;
    }

    public boolean removeSession(String sessionId) {
        return sessions.removeIf(s -> sessionId.equals(s.getSessionId()));
    }

    // ----- Schedule generation -----
    /**
     * Generates a simple schedule by assigning pending high-priority tasks
     * to sessions, distributed across courses.
     */
    public List<StudySession> generateSchedule(List<StudySession> availableSessions) {
        List<StudyTask> prioritized = getPrioritizedPendingTasks();
        List<StudySession> scheduled = new ArrayList<>();

        int taskIndex = 0;
        for (StudySession session : availableSessions) {
            if (taskIndex >= prioritized.size()) break;
            StudySession copy = new StudySession(
                    session.getSessionId(), session.getDate(),
                    session.getStartTime(), session.getEndTime(),
                    session.getCourse(), session.getNotes());
            double remaining = copy.getDurationHours();
            while (taskIndex < prioritized.size() && remaining > 0) {
                StudyTask task = prioritized.get(taskIndex);
                if (task.getEstimatedHours() <= remaining) {
                    copy.addTask(task);
                    remaining -= task.getEstimatedHours();
                    taskIndex++;
                } else {
                    break;
                }
            }
            if (!copy.getTasks().isEmpty()) {
                scheduled.add(copy);
            }
        }
        return scheduled;
    }

    // ----- Summary -----
    public String getSummary() {
        int pending = getPendingTasks().size();
        int completed = getCompletedTasks().size();
        return String.format("Smart Study Planner Summary%n" +
                "  Courses: %d%n" +
                "  Tasks: %d (pending: %d, completed: %d)%n" +
                "  Sessions: %d%n",
                courses.size(), tasks.size(), pending, completed, sessions.size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSummary()).append("%n");
        sb.append("Courses:%n");
        for (Course c : courses) {
            sb.append("  ").append(c).append("%n");
        }
        sb.append("Pending Tasks (prioritized):%n");
        for (StudyTask t : getPrioritizedPendingTasks()) {
            sb.append("  ").append(t).append("%n");
        }
        sb.append("Sessions:%n");
        for (StudySession s : sessions) {
            sb.append("  ").append(s).append("%n");
        }
        return sb.toString();
    }
}