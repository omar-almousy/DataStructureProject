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

    /**
     * Constructs a new SmartStudyPlannerManager with empty collections.
     */
    public SmartStudyPlannerManager() {
        this.courses = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.courseMap = new HashMap<>();
        this.tasksByCourse = new HashMap<>();
    }

    // ----- Course management -----
    /**
     * Adds a course if it is not null and its code is not already present.
     *
     * @param course the course to add
     * @return true if added; false if null or duplicate code
     */
    public boolean addCourse(Course course) {
        if (course == null || courseMap.containsKey(course.getCourseCode())) {
            return false;
        }
        courses.add(course);
        courseMap.put(course.getCourseCode(), course);
        return true;
    }

    /**
     * Returns the course with the given code.
     *
     * @param courseCode the course code
     * @return the course, or null if not found
     */
    public Course getCourseByCode(String courseCode) {
        return courseMap.get(courseCode);
    }

    /**
     * Returns a copy of all courses.
     *
     * @return a list of all courses
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Removes a course and its associated tasks by code.
     *
     * @param courseCode the course code to remove
     * @return true if removed; false if not found
     */
    public boolean removeCourse(String courseCode) {
        Course c = courseMap.remove(courseCode);
        if (c == null) return false;
        courses.remove(c);
        tasksByCourse.remove(courseCode);
        tasks.removeIf(t -> courseCode.equals(t.getCourse() != null ? t.getCourse().getCourseCode() : null));
        return true;
    }

    // ----- Task management -----
    /**
     * Adds a task and indexes it by its course code.
     *
     * @param task the task to add
     * @return true if added; false if null or no course
     */
    public boolean addTask(StudyTask task) {
        if (task == null || task.getCourse() == null) {
            return false;
        }
        tasks.add(task);
        tasksByCourse.computeIfAbsent(task.getCourse().getCourseCode(), k -> new ArrayList<>()).add(task);
        return true;
    }

    /**
     * Finds a task by its ID using an indexed loop.
     *
     * @param taskId the task ID
     * @return the matching task, or null if not found
     */
    public StudyTask getTaskById(String taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (taskId.equals(t.getTaskId())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Returns a copy of all tasks.
     *
     * @return a list of all tasks
     */
    public List<StudyTask> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns all tasks for a given course code.
     *
     * @param courseCode the course code
     * @return a list of tasks for the course
     */
    public List<StudyTask> getTasksByCourse(String courseCode) {
        List<StudyTask> list = tasksByCourse.get(courseCode);
        return list != null ? new ArrayList<>(list) : new ArrayList<>();
    }

    /**
     * Returns all pending (not completed) tasks.
     *
     * @return a list of pending tasks
     */
    public List<StudyTask> getPendingTasks() {
        return tasks.stream().filter(t -> !t.isCompleted()).collect(Collectors.toList());
    }

    /**
     * Returns all completed tasks.
     *
     * @return a list of completed tasks
     */
    public List<StudyTask> getCompletedTasks() {
        return tasks.stream().filter(StudyTask::isCompleted).collect(Collectors.toList());
    }

    /**
     * Returns pending tasks ordered by priority (High first) then due date.
     */
    public List<StudyTask> getPrioritizedPendingTasks() {
        PriorityQueue<StudyTask> pq = new PriorityQueue<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
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

    /**
     * Marks a task as completed by ID.
     *
     * @param taskId the task ID
     * @return true if found and marked; false if not found
     */
    public boolean markTaskComplete(String taskId) {
        StudyTask t = getTaskById(taskId);
        if (t != null) {
            t.markComplete();
            return true;
        }
        return false;
    }

    /**
     * Removes a task by ID.
     *
     * @param taskId the task ID
     * @return true if removed; false if not found
     */
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
    /**
     * Adds a session if it is not null.
     *
     * @param session the session to add
     * @return true if added; false if null
     */
    public boolean addSession(StudySession session) {
        if (session == null) {
            return false;
        }
        sessions.add(session);
        return true;
    }

    /**
     * Returns a copy of all sessions.
     *
     * @return a list of all sessions
     */
    public List<StudySession> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    /**
     * Returns all sessions for a given course code.
     *
     * @param courseCode the course code
     * @return a list of sessions for the course
     */
    public List<StudySession> getSessionsByCourse(String courseCode) {
        return sessions.stream()
                .filter(s -> s.getCourse() != null && courseCode.equals(s.getCourse().getCourseCode()))
                .collect(Collectors.toList());
    }

    /**
     * Finds a session by its ID using an indexed loop.
     *
     * @param sessionId the session ID
     * @return the matching session, or null if not found
     */
    public StudySession getSessionById(String sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);
            if (sessionId.equals(s.getSessionId())) {
                return s;
            }
        }
        return null;
    }

    /**
     * Removes a session by ID.
     *
     * @param sessionId the session ID
     * @return true if removed
     */
    public boolean removeSession(String sessionId) {
        return sessions.removeIf(s -> sessionId.equals(s.getSessionId()));
    }

    /**
     * Logs a study session for a given task with validation.
     * <p>
     * Validates that the session ID is exactly six digits, that no session with
     * that ID already exists, that the task exists (via {@link #getTaskById}),
     * and that the duration is greater than zero. If a date is supplied it must
     * match DD/MM/YYYY.
     *
     * @param sessionId    six-digit session identifier
     * @param taskId       ID of the task to log the session against
     * @param durationHours session duration in hours (must be &gt; 0)
     * @param date         optional date in DD/MM/YYYY format (may be null or empty)
     * @return success or specific failure message
     */
    public String logStudySession(String sessionId, String taskId,
                                  double durationHours, String date) {
        // Validate session ID: exactly six digits
        if (sessionId == null || !sessionId.matches("\\d{6}")) {
            return "Failure: session ID must be a six-digit number.";
        }

        // Reject duplicate session ID
        if (getSessionById(sessionId) != null) {
            return "Failure: duplicate session ID.";
        }

        // Validate task exists via manager
        StudyTask task = getTaskById(taskId);
        if (task == null) {
            return "Failure: task not found.";
        }

        // Validate duration
        if (durationHours <= 0) {
            return "Failure: duration must be greater than 0.";
        }

        // Validate date format if supplied
        if (date != null && !date.isEmpty() && !date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return "Failure: date must be in DD/MM/YYYY format.";
        }

        // Derive start/end times from durationHours (start at 00:00)
        int totalMinutes = (int) Math.round(durationHours * 60);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        String startTime = "00:00";
        String endTime = String.format("%02d:%02d", hours, minutes);

        StudySession session = new StudySession(
                sessionId,
                date != null ? date : "",
                startTime,
                endTime,
                task.getCourse(),
                "Logged session for task '" + task.getTitle() + "'");
        session.addTask(task);

        if (addSession(session)) {
            return "Success: session logged for task '" + task.getTitle()
                    + "' (ID: " + taskId + ").";
        } else {
            return "Failure: could not add session.";
        }
    }

    // ----- Session lookup -----
    /**
     * Returns true if any recorded session contains a task with the given taskId.
     * Uses indexed loops only (no enhanced for-loops).
     *
     * @param taskId the task ID to look for
     * @return true if at least one session has this task; false otherwise
     */
    public boolean hasSessionsForTask(String taskId) {
        if (taskId == null) {
            return false;
        }
        for (int i = 0; i < sessions.size(); i++) {
            List<StudyTask> sessionTasks = sessions.get(i).getTasks();
            for (int j = 0; j < sessionTasks.size(); j++) {
                if (taskId.equals(sessionTasks.get(j).getTaskId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the completion percentage of all tracked tasks.
     * <p>
     * Formula: {@code (completedTasks / totalTasks) * 100.0}. Uses the Part B
     * helper {@link StudyTask#isCompleted()} to count completed tasks via an
     * indexed loop. Returns {@code -1} when there are no tasks (guards against
     * division by zero / NaN).
     *
     * @return completion percentage in [0.0, 100.0]; {@code -1} when empty
     */
    public double calculateCompletionPercentage() {
        if (tasks.isEmpty()) {
            return -1;
        }
        int completed = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isCompleted()) {
                completed++;
            }
        }
        return (completed / (double) tasks.size()) * 100.0;
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
        for (int i = 0; i < availableSessions.size(); i++) {
            StudySession session = availableSessions.get(i);
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
    /**
     * Returns a summary string with counts of courses, tasks, and sessions.
     *
     * @return a formatted summary string
     */
    public String getSummary() {
        int pending = getPendingTasks().size();
        int completed = getCompletedTasks().size();
        return String.format("Smart Study Planner Summary%n" +
                "  Courses: %d%n" +
                "  Tasks: %d (pending: %d, completed: %d)%n" +
                "  Sessions: %d%n",
                courses.size(), tasks.size(), pending, completed, sessions.size());
    }

    /**
     * Returns a string representation of the planner including summary,
     * courses, pending tasks, and sessions.
     *
     * @return a formatted string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSummary()).append("%n");
        sb.append("Courses:%n");
        for (int i = 0; i < courses.size(); i++) {
            sb.append("  ").append(courses.get(i)).append("%n");
        }
        sb.append("Pending Tasks (prioritized):%n");
        List<StudyTask> pendingTasks = getPrioritizedPendingTasks();
        for (int i = 0; i < pendingTasks.size(); i++) {
            sb.append("  ").append(pendingTasks.get(i)).append("%n");
        }
        sb.append("Sessions:%n");
        for (int i = 0; i < sessions.size(); i++) {
            sb.append("  ").append(sessions.get(i)).append("%n");
        }
        return sb.toString();
    }
}