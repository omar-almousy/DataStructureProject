import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scheduled study session.
 * A session has an ID, date, time range, associated course, and a list of tasks to work on.
 */
public class StudySession {
    private int id;
    private String date;
    private String startTime;
    private String endTime;
    private Course course;
    private List<StudyTask> tasks;
    private String notes;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * Returns the session duration in hours (e.g. "14:00" - "10:00" = 4.0).
     */
    public double getDurationHours() {
        try {
            String[] startParts = startTime.split(":");
            String[] endParts = endTime.split(":");
            int startMin = Integer.parseInt(startParts[0]) * 60 + Integer.parseInt(startParts[1]);
            int endMin = Integer.parseInt(endParts[0]) * 60 + Integer.parseInt(endParts[1]);
            return (endMin - startMin) / 60.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return String.format("Session[id=%d, date=%s, time=%s-%s, course=%s, duration=%.1fh, notes=%s]",
                id, date, startTime, endTime,
                (course != null ? course.getCourseCode() : "N/A"), getDurationHours(), notes);
    }
}