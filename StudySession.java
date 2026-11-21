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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Session[id=%s, date=%s, time=%s-%s, course=%s, duration=%.1fh, notes='%s']%n",
                sessionId, date, startTime, endTime,
                (course != null ? course.getCourseCode() : "N/A"), getDurationHours(), notes));
        if (tasks.isEmpty()) {
            sb.append("  (no tasks assigned)");
        } else {
            for (StudyTask t : tasks) {
                sb.append("  ").append(t).append("%n");
            }
        }
        return sb.toString();
    }
}