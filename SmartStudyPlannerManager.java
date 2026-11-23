/*
 * Course: [COURSE_CODE]
 * Section: [SECTION]
 * Project: Smart Study Planner
 * Group Members:
 *   [Member 1 Name] - [Member 1 ID]
 *   [Member 3 Name] - [Member 3 ID]
 *   [Member 4 Name] - [Member 4 ID]
 */

import java.util.LinkedList;

/**
 * Manages courses, study tasks, and study sessions for the Smart Study Planner.
 * Uses LinkedList data structures for course, task, and session storage.
 * All iterations use indexed for loops (no enhanced for-each).
 */
public class SmartStudyPlannerManager {
    private LinkedList<Course> courses;
    private LinkedList<StudyTask> tasks;
    private LinkedList<StudySession> sessions;

    /**
     * Constructor initializing all three LinkedLists.
     */
    public SmartStudyPlannerManager() {
        this.courses = new LinkedList<>();
        this.tasks = new LinkedList<>();
        this.sessions = new LinkedList<>();
    }

    // ----- Course management -----

    /**
     * Adds a course only if its courseID is not already present.
     *
     * @param c the course to add
     * @return true if the course was added, false if it is null or a duplicate courseID
     */
    public boolean addCourse(Course c) {
        if (c == null) {
            return false;
        }
        if (findCourseByID(c.getCourseID()) != null) {
            return false;
        }
        return courses.add(c);
    }

    /**
     * Finds a course by its ID using an indexed for loop.
     *
     * @param id the course ID to search for
     * @return the Course if found, null otherwise
     */
    public Course findCourseByID(int id) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseID() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns the course with the given ID (alias for findCourseByID).
     *
     * @param id the course ID to search for
     * @return the Course if found, null otherwise
     */
    public Course getCourseByID(int id) {
        return findCourseByID(id);
    }

    /**
     * Displays all courses using an indexed for loop.
     * Prints an appropriate message if the list is empty.
     */
    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("  " + courses.get(i));
        }
    }

    /**
     * Returns a copy of the courses list.
     *
     * @return a LinkedList containing all courses
     */
    public LinkedList<Course> getAllCourses() {
        return new LinkedList<>(courses);
    }

    /**
     * Removes a course by its courseID. Also removes any tasks belonging to that course.
     *
     * @param courseID the ID of the course to remove
     * @return true if removed, false if not found
     */
    public boolean removeCourse(int courseID) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseID() == courseID) {
                courses.remove(i);
                for (int j = tasks.size() - 1; j >= 0; j--) {
                    if (tasks.get(j).getCourse() != null
                            && tasks.get(j).getCourse().getCourseID() == courseID) {
                        tasks.remove(j);
                    }
                }
                return true;
            }
        }
        return false;
    }

    // ----- Task management -----

    public boolean addTask(StudyTask task) {
        if (task == null || task.getCourse() == null) {
            return false;
        }
        return tasks.add(task);
    }

    public StudyTask getTaskById(String taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (taskId.equals(t.getTaskId())) {
                return t;
            }
        }
        return null;
    }

    public LinkedList<StudyTask> getAllTasks() {
        return new LinkedList<>(tasks);
    }

    public LinkedList<StudyTask> getTasksByCourse(int courseID) {
        LinkedList<StudyTask> result = new LinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getCourse() != null && t.getCourse().getCourseID() == courseID) {
                result.add(t);
            }
        }
        return result;
    }

    public LinkedList<StudyTask> getPendingTasks() {
        LinkedList<StudyTask> result = new LinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (!t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public LinkedList<StudyTask> getCompletedTasks() {
        LinkedList<StudyTask> result = new LinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public LinkedList<StudyTask> getPrioritizedPendingTasks() {
        LinkedList<StudyTask> pending = getPendingTasks();
        LinkedList<StudyTask> sorted = new LinkedList<>();
        for (int i = 0; i < pending.size(); i++) {
            StudyTask t = pending.get(i);
            int insertPos = 0;
            while (insertPos < sorted.size() && shouldComeBefore(sorted.get(insertPos), t)) {
                insertPos++;
            }
            sorted.add(insertPos, t);
        }
        return sorted;
    }

    private boolean shouldComeBefore(StudyTask a, StudyTask b) {
        int rankA = priorityRank(a.getPriority());
        int rankB = priorityRank(b.getPriority());
        if (rankA != rankB) {
            return rankA > rankB;
        }
        return a.getDueDate().compareTo(b.getDueDate()) < 0;
    }

    private int priorityRank(String p) {
        switch (p) {
            case "High": return 3;
            case "Medium": return 2;
            case "Low": return 1;
            default: return 0;
        }
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
        for (int i = 0; i < tasks.size(); i++) {
            if (taskId.equals(tasks.get(i).getTaskId())) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    // ----- Session management -----

    public boolean addSession(StudySession session) {
        if (session == null) {
            return false;
        }
        return sessions.add(session);
    }

    public LinkedList<StudySession> getAllSessions() {
        return new LinkedList<>(sessions);
    }

    public LinkedList<StudySession> getSessionsByCourse(int courseID) {
        LinkedList<StudySession> result = new LinkedList<>();
        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);
            if (s.getCourse() != null && s.getCourse().getCourseID() == courseID) {
                result.add(s);
            }
        }
        return result;
    }

    public StudySession getSessionById(String sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessionId.equals(sessions.get(i).getSessionId())) {
                return sessions.get(i);
            }
        }
        return null;
    }

    public boolean removeSession(String sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessionId.equals(sessions.get(i).getSessionId())) {
                sessions.remove(i);
                return true;
            }
        }
        return false;
    }

    // ----- Schedule generation -----

    public LinkedList<StudySession> generateSchedule(LinkedList<StudySession> availableSessions) {
        LinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();
        LinkedList<StudySession> scheduled = new LinkedList<>();

        int taskIndex = 0;
        for (int i = 0; i < availableSessions.size(); i++) {
            if (taskIndex >= prioritized.size()) {
                break;
            }
            StudySession session = availableSessions.get(i);
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
            if (copy.getTaskCount() > 0) {
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
        for (int i = 0; i < courses.size(); i++) {
            sb.append("  ").append(courses.get(i)).append("%n");
        }
        sb.append("Pending Tasks (prioritized):%n");
        LinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();
        for (int i = 0; i < prioritized.size(); i++) {
            sb.append("  ").append(prioritized.get(i)).append("%n");
        }
        sb.append("Sessions:%n");
        for (int i = 0; i < sessions.size(); i++) {
            sb.append("  ").append(sessions.get(i)).append("%n");
        }
        return sb.toString();
    }
}