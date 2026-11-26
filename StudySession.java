
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scheduled study session.
 * A session has an ID, date, time range, associated course, and a list of tasks.
 */
public class StudySession {

    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private Course course;
    private List<StudyTask> tasks;
    private String notes;
    private double loggedHours; // used when the session is logged with a duration instead of times

    /**
     * Constructs a logged study session of a fixed duration for one task.
     *
     * @param id the six-digit session ID
     * @param task the task this session is logged to
     * @param durationHours the session length in hours
     */
    public StudySession(int id, StudyTask task, double durationHours) {
        // Last-line guard only: the application validates input before calling this.
        if (!Validator.isValidSessionID(id)) {
            throw new IllegalArgumentException(Validator.MSG_SESSION_ID);
        }
        if (!Validator.isValidDuration(durationHours)) {
            throw new IllegalArgumentException("Duration " + Validator.MSG_POSITIVE);
        }
        this.id = id;
        this.course = (task != null ? task.getCourse() : null);
        this.notes = "";
        this.loggedHours = durationHours;
        this.tasks = new ArrayList<>();
        if (task != null) {
            this.tasks.add(task);
        }
    }

    /**
     * Constructs a study session using an integer ID.
     */
    public StudySession(int id, String date, String startTime,
                        String endTime, Course course, String notes) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.notes = notes;
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a study session using a String ID.
     */
    public StudySession(String sessionId, String date, String startTime,
                        String endTime, Course course, String notes) {
        this(Integer.parseInt(sessionId), date, startTime,
                endTime, course, notes);
    }

    /**
     * Returns the session ID.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return String.valueOf(id);
    }

    public void setSessionId(String sessionId) {
        this.id = Integer.parseInt(sessionId);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<StudyTask> getTasks() {
        return tasks;
    }

    public void addTask(StudyTask task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    public void removeTask(StudyTask task) {
        tasks.remove(task);
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Calculates the session duration in hours.
     *
     * @return duration in hours
     */
    public double getDurationHours() {
        if (startTime == null || endTime == null) {
            return loggedHours;
        }
        try {
            String[] startParts = startTime.split(":");
            String[] endParts = endTime.split(":");

            int startMin = Integer.parseInt(startParts[0]) * 60
                    + Integer.parseInt(startParts[1]);

            int endMin = Integer.parseInt(endParts[0]) * 60
                    + Integer.parseInt(endParts[1]);

            return (endMin - startMin) / 60.0;

        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Returns a string representation of this session.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (startTime == null || endTime == null) {
            sb.append(String.format("Session[id=%d, course=%s, duration=%sh]%n",
                    id,
                    (course != null ? course.getCourseCode() : "N/A"),
                    OutputFormat.number(getDurationHours())));
        } else {
            sb.append(String.format(
                "Session[id=%d, date=%s, time=%s-%s, course=%s, duration=%sh, notes='%s']%n",
                id,
                date,
                startTime,
                endTime,
                (course != null ? course.getCourseCode() : "N/A"),
                OutputFormat.number(getDurationHours()),
                notes
            ));
        }

        if (tasks.isEmpty()) {
            sb.append("  (no tasks assigned)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("  ")
                        .append(tasks.get(i))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
