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
import java.util.List;

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
     * Deletes a course by its courseID.
     * Blocks deletion if any StudyTask references this courseID (no cascade delete).
     *
     * @param courseID the ID of the course to delete
     * @return 0 = success, 1 = course not found, 2 = blocked because tasks reference it
     */
    public int deleteCourse(int courseID) {
        // Find the course
        int courseIndex = -1;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID() == courseID) {
                courseIndex = i;
                break;
            }
        }
        if (courseIndex == -1) {
            return 1; // not found
        }

        // Check if any task references this course
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getCourse() != null && t.getCourse().getCourseID() == courseID) {
                return 2; // blocked by dependent tasks
            }
        }

        courses.remove(courseIndex);
        return 0; // success
    }

    /**
     * Deletes a study task by its taskID.
     * Blocks deletion if any StudySession references this taskID (no cascade delete).
     *
     * @param taskID the ID of the task to delete
     * @return 0 = success, 1 = task not found, 2 = blocked because sessions reference it
     */
    public int deleteStudyTask(int taskID) {
        // Find the task
        int taskIndex = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == taskID) {
                taskIndex = i;
                break;
            }
        }
        if (taskIndex == -1) {
            return 1; // not found
        }

        // Check if any session references this task
        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);
            List<StudyTask> sessionTasks = s.getTasks();
            for (int j = 0; j < sessionTasks.size(); j++) {
                if (sessionTasks.get(j).getTaskId() == taskID) {
                    return 2; // blocked by dependent sessions
                }
            }
        }

        tasks.remove(taskIndex);
        return 0; // success
    }

    // ----- Task management -----

    /**
     * Adds a study task to the list.
     * Rejects if task is null, has no course, taskID is duplicate,
     * or the associated courseID does not exist.
     *
     * @param t the task to add
     * @return true if added, false otherwise
     */
    public boolean addTask(StudyTask t) {
        if (t == null || t.getCourse() == null) {
            return false;
        }
        // Reject if taskID is duplicate
        if (findTaskByID(t.getTaskId()) != null) {
            return false;
        }
        // Reject if associated courseID does not exist
        if (findCourseByID(t.getCourse().getCourseID()) == null) {
            return false;
        }
        return tasks.add(t);
    }

    /**
     * Helper: finds a task by its ID using an indexed for loop.
     *
     * @param id the task ID to search for
     * @return the StudyTask if found, null otherwise
     */
    private StudyTask findTaskByID(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getTaskId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Searches for a task by its ID.
     *
     * @param id the task ID to search for
     * @return the StudyTask if found, null otherwise
     */
    public StudyTask searchTaskByID(int id) {
        return findTaskByID(id);
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
            // Insertion sort: priority (1 = High first), then earliest deadline
            while (insertPos < sorted.size() && sorted.get(insertPos).compareTo(t) <= 0) {
                insertPos++;
            }
            sorted.add(insertPos, t);
        }
        return sorted;
    }

    public boolean markTaskComplete(int id) {
        StudyTask t = findTaskByID(id);
        if (t != null) {
            t.markComplete();
            return true;
        }
        return false;
    }

    public boolean removeTask(int id) {
        return deleteStudyTask(id) == 0;
    }

    /**
     * Displays all study tasks with course info using an indexed for loop.
     * Prints an appropriate message if the list is empty.
     */
    public void displayAllStudyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No study tasks available.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            Course c = t.getCourse();
            System.out.println("  " + t + " | course=" + (c != null ? c.getCourseName() : "N/A"));
        }
    }

    /**
     * Updates the status of a task by its ID.
     * Valid status values: "Pending", "In Progress", "Completed".
     *
     * @param id the task ID
     * @param newStatus the new status to set
     * @return true if updated successfully, false if task not found or invalid status
     */
    public boolean updateTaskStatus(int id, String newStatus) {
        if (!Validator.isValidStatus(newStatus)) {
            return false;
        }
        StudyTask t = findTaskByID(id);
        if (t != null) {
            t.setStatus(newStatus);
            return true;
        }
        return false;
    }

    /**
     * Displays all high-priority incomplete tasks using indexed loops.
     * Prints a message if none are found.
     */
    public void displayHighPriorityIncompleteTasks() {
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.isHighPriority() && !t.isCompleted()) {
                System.out.println("  " + t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No high-priority incomplete tasks found.");
        }
    }

    // ----- Session management -----

    public boolean addSession(StudySession session) {
        if (session == null) {
            return false;
        }
        return sessions.add(session);
    }

    /**
     * Logs a study session of the given duration against an existing task.
     * Checks are applied in order: duration, duplicate session ID, task existence.
     *
     * @param sessionID the six-digit session ID
     * @param taskID the ID of the task the session is logged to
     * @param durationHours the session length in hours (must be greater than 0)
     * @return 0 = success, 1 = duplicate session ID, 2 = task not found,
     *         3 = invalid duration, 4 = invalid session ID
     */
    public int logStudySession(int sessionID, int taskID, double durationHours) {
        if (!Validator.isValidSessionID(sessionID)) {
            return 4;
        }
        if (!Validator.isValidDuration(durationHours)) {
            return 3;
        }
        if (findSessionByID(sessionID) != null) {
            return 1;
        }
        StudyTask task = findTaskByID(taskID);
        if (task == null) {
            return 2;
        }
        sessions.add(new StudySession(sessionID, task, durationHours));
        return 0;
    }

    /**
     * Helper: finds a session by its integer ID using an indexed for loop.
     *
     * @param id the session ID to search for
     * @return the StudySession if found, null otherwise
     */
    private StudySession findSessionByID(int id) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == id) {
                return sessions.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a copy of the sessions list.
     *
     * @return a LinkedList containing all sessions
     */
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