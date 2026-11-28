/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

/* Manages courses, tasks and sessions, stored in the course's KWLinkedList class. */
public class SmartStudyPlannerManager {
    private KWLinkedList<Course> courses;
    private KWLinkedList<StudyTask> tasks;
    private KWLinkedList<StudySession> sessions;

    /* Constructor initializing all three KWLinkedLists. */
    public SmartStudyPlannerManager() {
        this.courses = new KWLinkedList<>();
        this.tasks = new KWLinkedList<>();
        this.sessions = new KWLinkedList<>();
    }

    /* Returns a new KWLinkedList holding the same elements, so callers cannot change the manager's own lists. Returns a copy of source */
    private static <T> KWLinkedList<T> copyOf(KWLinkedList<T> source) {
        KWLinkedList<T> copy = new KWLinkedList<>();
        for (int i = 0; i < source.size(); i++) {
            copy.add(source.get(i));
        }
        return copy;
    }

    // ----- Course management -----

    /* Adds a course only if its courseID is not already present. Returns true if the course was added, false if it is null or a duplicate courseID */
    public boolean addCourse(Course c) {
        if (c == null) {
            return false;
        }
        if (findCourseByID(c.getCourseID()) != null) {
            return false;
        }
        return courses.add(c);
    }

    /* Finds a course by its ID using an indexed for loop. Returns the Course if found, null otherwise */
    public Course findCourseByID(int id) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseID() == id) {
                return c;
            }
        }
        return null;
    }

    /* Returns the course with the given ID (alias for findCourseByID). Returns the Course if found, null otherwise */
    public Course getCourseByID(int id) {
        return findCourseByID(id);
    }

    /* Displays all courses using an indexed for loop. Prints an appropriate message if the list is empty. */
    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("  " + courses.get(i));
        }
    }

    /* Returns a copy of the courses list. Returns a LinkedList containing all courses */
    public KWLinkedList<Course> getAllCourses() {
        return copyOf(courses);
    }

    /* Deletes a course unless tasks use it. Returns 0 = deleted, 1 = not found, 2 = blocked by tasks. */
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

    /* Deletes a task unless sessions use it. Returns 0 = deleted, 1 = not found, 2 = blocked by sessions. */
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

    /* Adds a task if its ID is new and its course exists. Returns true if added. */
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

    /* Helper: finds a task by its ID using an indexed for loop. Returns the StudyTask if found, null otherwise */
    private StudyTask findTaskByID(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getTaskId() == id) {
                return t;
            }
        }
        return null;
    }

    /* Searches for a task by its ID. Returns the StudyTask if found, null otherwise */
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

    /* Displays all study tasks with course info using an indexed for loop. Prints an appropriate message if the list is empty. */
    public void displayAllStudyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No study tasks available.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + tasks.get(i));
        }
    }

    /* Sets a task's status (already validated by the caller). Returns false if the task was not found. */
    public boolean updateTaskStatus(int id, String newStatus) {
        StudyTask t = findTaskByID(id);
        if (t != null) {
            t.setStatus(newStatus);
            return true;
        }
        return false;
    }

    /* Displays all high-priority incomplete tasks using indexed loops. Prints a message if none are found. */
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

    /* Logs a session to a task. Returns 0 = logged, 1 = duplicate session ID, 2 = task not found. */
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

    /* Returns every session logged to the given task (empty list if none). */
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

    /* Returns the percentage of tasks that are Completed, or -1 if there are no tasks. */
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

    /* Helper: finds a session by its integer ID using an indexed for loop. Returns the StudySession if found, null otherwise */
    private StudySession findSessionByID(int id) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == id) {
                return sessions.get(i);
            }
        }
        return null;
    }

    /* Returns a copy of the sessions list. Returns a LinkedList containing all sessions */
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