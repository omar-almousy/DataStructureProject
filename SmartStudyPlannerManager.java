/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

/**
 * Manages courses, study tasks, and study sessions for the Smart Study Planner.
 * Stores them in the course's KWLinkedList class (not java.util.LinkedList).
 * All iterations use indexed for loops (no enhanced for-each).
 */
public class SmartStudyPlannerManager {
    private KWLinkedList<Course> courses;
    private KWLinkedList<StudyTask> tasks;
    private KWLinkedList<StudySession> sessions;

    /**
     * Constructor initializing all three KWLinkedLists.
     */
    public SmartStudyPlannerManager() {
        this.courses = new KWLinkedList<>();
        this.tasks = new KWLinkedList<>();
        this.sessions = new KWLinkedList<>();
    }

    /**
     * Returns a new KWLinkedList holding the same elements, so callers
     * cannot change the manager's own lists.
     *
     * @param source the list to copy
     * @param <T> the element type
     * @return a copy of source
     */
    private static <T> KWLinkedList<T> copyOf(KWLinkedList<T> source) {
        KWLinkedList<T> copy = new KWLinkedList<>();
        for (int i = 0; i < source.size(); i++) {
            copy.add(source.get(i));
        }
        return copy;
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
    public KWLinkedList<Course> getAllCourses() {
        return copyOf(courses);
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
            KWArrayList<StudyTask> sessionTasks = s.getTasks();
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

    public KWLinkedList<StudyTask> getAllTasks() {
        return copyOf(tasks);
    }

    public KWLinkedList<StudyTask> getTasksByCourse(int courseID) {
        KWLinkedList<StudyTask> result = new KWLinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getCourse() != null && t.getCourse().getCourseID() == courseID) {
                result.add(t);
            }
        }
        return result;
    }

    public KWLinkedList<StudyTask> getPendingTasks() {
        KWLinkedList<StudyTask> result = new KWLinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (!t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public KWLinkedList<StudyTask> getCompletedTasks() {
        KWLinkedList<StudyTask> result = new KWLinkedList<>();
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public KWLinkedList<StudyTask> getPrioritizedPendingTasks() {
        KWLinkedList<StudyTask> pending = getPendingTasks();
        KWLinkedList<StudyTask> sorted = new KWLinkedList<>();
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
            System.out.println("  " + tasks.get(i));
        }
    }

    /**
     * Updates the status of a task by its ID.
     * The status value itself is validated by the caller with
     * {@link Validator#isValidStatus(String)}; this method only checks that the task exists.
     *
     * @param id the task ID
     * @param newStatus the new status to set (already validated)
     * @return true if updated successfully, false if the task was not found
     */
    public boolean updateTaskStatus(int id, String newStatus) {
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
     * Field formats (six-digit session ID, positive duration) are validated by the
     * caller with {@link Validator}; this method checks only the rules that need
     * the stored data: duplicate session ID, then task existence.
     *
     * @param sessionID the six-digit session ID (already validated)
     * @param taskID the ID of the task the session is logged to
     * @param durationHours the session length in hours (already validated)
     * @return 0 = success, 1 = duplicate session ID, 2 = task not found
     */
    public int logStudySession(int sessionID, int taskID, double durationHours) {
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
     * Returns every session that was logged to the given task.
     * The caller checks first that the task exists (see {@link #searchTaskByID(int)}),
     * so an empty list means "the task exists but has no sessions".
     *
     * @param taskID the task ID
     * @return a new list of matching sessions (empty if none)
     */
    public KWLinkedList<StudySession> getSessionsForTask(int taskID) {
        KWLinkedList<StudySession> result = new KWLinkedList<>();
        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);
            KWArrayList<StudyTask> sessionTasks = s.getTasks();
            for (int j = 0; j < sessionTasks.size(); j++) {
                if (sessionTasks.get(j).getTaskId() == taskID) {
                    result.add(s);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Calculates the percentage of study tasks whose status is Completed.
     * Formula: (completed tasks / total tasks) * 100.
     * Display formatting is done with {@link OutputFormat#percentage(double)}.
     *
     * @return the percentage in [0, 100], or -1 when there are no tasks
     *         (avoids division by zero)
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
        return completed * 100.0 / tasks.size();
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
    public KWLinkedList<StudySession> getAllSessions() {
        return copyOf(sessions);
    }

    public KWLinkedList<StudySession> getSessionsByCourse(int courseID) {
        KWLinkedList<StudySession> result = new KWLinkedList<>();
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

    public KWLinkedList<StudySession> generateSchedule(KWLinkedList<StudySession> availableSessions) {
        KWLinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();
        KWLinkedList<StudySession> scheduled = new KWLinkedList<>();

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
        KWLinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();
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