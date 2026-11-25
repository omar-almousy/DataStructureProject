
import java.util.LinkedList;
import java.util.List;

/**
 * Manages courses, study tasks, and study sessions for the Smart Study Planner.
 * Uses LinkedList data structures for storage.
 * All iterations use indexed for loops.
 */
public class SmartStudyPlannerManager {

    private LinkedList<Course> courses;
    private LinkedList<StudyTask> tasks;
    private LinkedList<StudySession> sessions;

    /**
     * Constructs a new SmartStudyPlannerManager with empty collections.
     */
    public SmartStudyPlannerManager() {
        this.courses = new LinkedList<>();
        this.tasks = new LinkedList<>();
        this.sessions = new LinkedList<>();
    }

    // ----- Course management -----

    public boolean addCourse(Course course) {
        if (course == null || findCourseByID(course.getCourseID()) != null) {
            return false;
        }
        return courses.add(course);
    }

    public Course findCourseByID(int id) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseID() == id) {
                return c;
            }
        }
        return null;
    }

    public Course getCourseByID(int id) {
        return findCourseByID(id);
    }

    public Course getCourseByCode(String courseCode) {
        if (courseCode == null) {
            return null;
        }

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (courseCode.equals(c.getCourseCode())) {
                return c;
            }
        }
        return null;
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println("  " + courses.get(i));
        }
    }

    public List<Course> getAllCourses() {
        return new LinkedList<>(courses);
    }

    public boolean removeCourse(int courseID) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID() == courseID) {
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

    public boolean removeCourse(String courseCode) {
        if (courseCode == null) {
            return false;
        }

        Course course = getCourseByCode(courseCode);
        if (course == null) {
            return false;
        }

        return removeCourse(course.getCourseID());
    }

    // ----- Task management -----

    public boolean addTask(StudyTask task) {
        if (task == null || task.getCourse() == null) {
            return false;
        }

        if (findTaskByID(task.getTaskId()) != null) {
            return false;
        }

        if (findCourseByID(task.getCourse().getCourseID()) == null) {
            return false;
        }

        return tasks.add(task);
    }

    public StudyTask getTaskById(int taskId) {
        return findTaskByID(taskId);
    }

    public StudyTask getTaskById(String taskId) {
        if (taskId == null) {
            return null;
        }

        try {
            return findTaskByID(Integer.parseInt(taskId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public StudyTask searchTaskByID(int id) {
        return findTaskByID(id);
    }

    private StudyTask findTaskByID(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            if (t.getTaskId() == id) {
                return t;
            }
        }
        return null;
    }

    public List<StudyTask> getAllTasks() {
        return new LinkedList<>(tasks);
    }

    public List<StudyTask> getTasksByCourse(int courseID) {
        LinkedList<StudyTask> result = new LinkedList<>();

        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);

            if (t.getCourse() != null
                    && t.getCourse().getCourseID() == courseID) {
                result.add(t);
            }
        }
        return result;
    }

    public List<StudyTask> getTasksByCourse(String courseCode) {
        LinkedList<StudyTask> result = new LinkedList<>();

        if (courseCode == null) {
            return result;
        }

        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);

            if (t.getCourse() != null
                    && courseCode.equals(t.getCourse().getCourseCode())) {
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

    public List<StudyTask> getCompletedTasks() {
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

            while (insertPos < sorted.size()
                    && shouldComeBefore(sorted.get(insertPos), t)) {
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

        return a.getDueDate().compareTo(b.getDueDate()) <= 0;
    }

    private int priorityRank(String priority) {
        switch (priority) {
            case "High":
                return 3;
            case "Medium":
                return 2;
            case "Low":
                return 1;
            default:
                return 0;
        }
    }

    public boolean markTaskComplete(int id) {
        StudyTask t = findTaskByID(id);

        if (t != null) {
            t.markComplete();
            return true;
        }
        return false;
    }

    public boolean markTaskComplete(String taskId) {
        StudyTask t = getTaskById(taskId);

        if (t != null) {
            t.markComplete();
            return true;
        }
        return false;
    }

    public boolean removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeTask(String taskId) {
        StudyTask t = getTaskById(taskId);

        if (t == null) {
            return false;
        }

        return removeTask(t.getTaskId());
    }

    public void displayAllStudyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No study tasks available.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            StudyTask t = tasks.get(i);
            Course c = t.getCourse();

            System.out.println("  " + t + " | course="
                    + (c != null ? c.getCourseName() : "N/A"));
        }
    }

    public boolean updateTaskStatus(int id, String newStatus) {
        if (newStatus == null) {
            return false;
        }

        if (!"Pending".equals(newStatus)
                && !"In Progress".equals(newStatus)
                && !"Completed".equals(newStatus)) {
            return false;
        }

        StudyTask t = findTaskByID(id);

        if (t != null) {
            t.setStatus(newStatus);
            return true;
        }

        return false;
    }

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

        // Reject duplicate session IDs.
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == session.getId()) {
                return false;
            }
        }

        // Reject sessions linked to nonexistent tasks.
        for (int i = 0; i < session.getTasks().size(); i++) {
            StudyTask task = session.getTasks().get(i);

            if (task == null || getTaskById(task.getTaskId()) == null) {
                return false;
            }
        }

        // Reject invalid duration.
        if (session.getDurationHours() <= 0) {
            return false;
        }

        sessions.add(session);
        return true;
    }

    public LinkedList<StudySession> getAllSessions() {
        return new LinkedList<>(sessions);
    }

    public List<StudySession> getSessionsByCourse(int courseID) {
        LinkedList<StudySession> result = new LinkedList<>();

        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);

            if (s.getCourse() != null
                    && s.getCourse().getCourseID() == courseID) {
                result.add(s);
            }
        }
        return result;
    }

    public List<StudySession> getSessionsByCourse(String courseCode) {
        LinkedList<StudySession> result = new LinkedList<>();

        if (courseCode == null) {
            return result;
        }

        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);

            if (s.getCourse() != null
                    && courseCode.equals(s.getCourse().getCourseCode())) {
                result.add(s);
            }
        }
        return result;
    }

    public StudySession getSessionById(int sessionId) {
        return findSessionByID(sessionId);
    }

    public StudySession getSessionById(String sessionId) {
        if (sessionId == null) {
            return null;
        }

        try {
            return findSessionByID(Integer.parseInt(sessionId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public StudySession findSessionByID(int id) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == id) {
                return sessions.get(i);
            }
        }
        return null;
    }

    public boolean removeSession(int sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId() == sessionId) {
                sessions.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeSession(String sessionId) {
        StudySession s = getSessionById(sessionId);

        if (s == null) {
            return false;
        }

        return removeSession(s.getId());
    }

    public void displaySessionsForTask(int taskID) {
        StudyTask task = getTaskById(taskID);

        if (task == null) {
            System.out.println("Task with ID " + taskID + " does not exist.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);

            for (int j = 0; j < s.getTasks().size(); j++) {
                if (s.getTasks().get(j).getTaskId() == taskID) {
                    if (!found) {
                        System.out.println("Sessions for task ID " + taskID + ":");
                    }

                    System.out.println("  " + s);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No sessions found for task ID " + taskID + ".");
        }
    }

    // ----- Study session logging -----

    public String logStudySession(String sessionId, String taskId,
                                  double durationHours, String date) {

        if (sessionId == null || !sessionId.matches("\\d{6}")) {
            return "Failure: session ID must be a six-digit number.";
        }

        if (getSessionById(sessionId) != null) {
            return "Failure: duplicate session ID.";
        }

        StudyTask task = getTaskById(taskId);

        if (task == null) {
            return "Failure: task not found.";
        }

        if (durationHours <= 0) {
            return "Failure: duration must be greater than 0.";
        }

        if (date != null && !date.isEmpty()
                && !date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return "Failure: date must be in DD/MM/YYYY format.";
        }

        int totalMinutes = (int) Math.round(durationHours * 60);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        String startTime = "00:00";
        String endTime = String.format("%02d:%02d", hours, minutes);

        int numericSessionId = Integer.parseInt(sessionId);

        StudySession session = new StudySession(
                numericSessionId,
                date != null ? date : "",
                startTime,
                endTime,
                task.getCourse(),
                "Logged session for task '" + task.getTitle() + "'"
        );

        session.addTask(task);

        if (addSession(session)) {
            return "Success: session logged for task '" + task.getTitle()
                    + "' (ID: " + taskId + ").";
        }

        return "Failure: could not add session.";
    }

    // ----- Session lookup -----

    public boolean hasSessionsForTask(String taskId) {
        if (taskId == null) {
            return false;
        }

        int id;

        try {
            id = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            return false;
        }

        for (int i = 0; i < sessions.size(); i++) {
            List<StudyTask> sessionTasks = sessions.get(i).getTasks();

            for (int j = 0; j < sessionTasks.size(); j++) {
                if (sessionTasks.get(j).getTaskId() == id) {
                    return true;
                }
            }
        }

        return false;
    }

    // ----- Completion percentage -----

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

    public LinkedList<StudySession> generateSchedule(List<StudySession> availableSessions) {
        LinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();
        LinkedList<StudySession> scheduled = new LinkedList<>();

        if (availableSessions == null) {
            return scheduled;
        }

        int taskIndex = 0;

        for (int i = 0; i < availableSessions.size(); i++) {
            if (taskIndex >= prioritized.size()) {
                break;
            }

            StudySession session = availableSessions.get(i);

            StudySession copy = new StudySession(
                    session.getId(),
                    session.getDate(),
                    session.getStartTime(),
                    session.getEndTime(),
                    session.getCourse(),
                    session.getNotes()
            );

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

        return String.format(
                "Smart Study Planner Summary%n"
                + "  Courses: %d%n"
                + "  Tasks: %d (pending: %d, completed: %d)%n"
                + "  Sessions: %d%n",
                courses.size(), tasks.size(), pending, completed, sessions.size()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getSummary()).append(System.lineSeparator());
        sb.append("Courses:").append(System.lineSeparator());

        for (int i = 0; i < courses.size(); i++) {
            sb.append("  ").append(courses.get(i))
                    .append(System.lineSeparator());
        }

        sb.append("Pending Tasks (prioritized):")
                .append(System.lineSeparator());

        LinkedList<StudyTask> prioritized = getPrioritizedPendingTasks();

        for (int i = 0; i < prioritized.size(); i++) {
            sb.append("  ").append(prioritized.get(i))
                    .append(System.lineSeparator());
        }

        sb.append("Sessions:").append(System.lineSeparator());

        for (int i = 0; i < sessions.size(); i++) {
            sb.append("  ").append(sessions.get(i))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
