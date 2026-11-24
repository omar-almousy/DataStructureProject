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
import java.util.List;

/**
 * Represents a scheduled study session.
 * A session has a date, time range, associated course, and a list of tasks to work on.
 */
public class StudySession {
    private String sessionId;
    private String date;
    private String startTime;
    private String endTime;
    private Course course;
    private List<StudyTask> tasks;
    private String notes;

    /**
     * Constructs a study session with the given details.
     *
     * @param sessionId the session ID
     * @param date      the session date
     * @param startTime the start time
     * @param endTime   the end time
     * @param course    the associated course
     * @param notes     session notes
     */
    public StudySession(String sessionId, String date, String startTime,
            String endTime, Course course, String notes) {
        this.sessionId = sessionId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.notes = notes;
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns the session ID.
     *
     * @return the session ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID.
     *
     * @param sessionId the session ID to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Returns the session date.
     *
     * @return the session date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the session date.
     *
     * @param date the session date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the start time.
     *
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time.
     *
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the associated course.
     *
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the associated course.
     *
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Returns the list of tasks in this session.
     *
     * @return the list of tasks
     */
    public List<StudyTask> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to this session.
     *
     * @param task the task to add
     */
    public void addTask(StudyTask task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    /**
     * Removes a task from this session.
     *
     * @param task the task to remove
     */
    public void removeTask(StudyTask task) {
        tasks.remove(task);
    }

    /**
     * Returns the session notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the session notes.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Returns a string representation of this session.
     *
     * @return a formatted string with session details
     */
    @Override
    public String toString() {
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Session[id=%s, date=%s, time=%s-%s, course=%s, duration=%.1fh, notes='%s']%n",
                sessionId, date, startTime, endTime,
                (course != null ? course.getCourseCode() : "N/A"), getDurationHours(), notes));
        if (tasks.isEmpty()) {
            sb.append("  (no tasks assigned)");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("  ").append(tasks.get(i)).append("%n");
            }
        }
        return sb.toString();
    }
}