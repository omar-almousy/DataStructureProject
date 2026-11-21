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

/**
 * Represents a study task in the Smart Study Planner.
 * Each task belongs to a course and has a priority, due date, and completion status.
 * Tasks implement Comparable so they can be ordered in a priority queue.
 */
public class StudyTask implements Comparable<StudyTask> {
    private String taskId;
    private String title;
    private String description;
    private Course course;
    private String dueDate;
    private String priority; // "High", "Medium", "Low"
    private double estimatedHours;
    private boolean completed;

    public StudyTask(String taskId, String title, String description,
            Course course, String dueDate, String priority, double estimatedHours) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.completed = false;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markComplete() {
        this.completed = true;
    }

    public void markIncomplete() {
        this.completed = false;
    }

    @Override
    public int compareTo(StudyTask other) {
        // Higher priority tasks come first
        int thisRank = priorityRank(this.priority);
        int otherRank = priorityRank(other.priority);
        if (thisRank != otherRank) {
            return Integer.compare(otherRank, thisRank); // descending: High first
        }
        // Then by due date (earlier first)
        return this.dueDate.compareTo(other.dueDate);
    }

    private int priorityRank(String p) {
        switch (p) {
            case "High": return 3;
            case "Medium": return 2;
            case "Low": return 1;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("Task[id=%s, title='%s', course=%s, due=%s, priority=%s, hours=%.1f, completed=%b]",
                taskId, title, (course != null ? course.getCourseCode() : "N/A"),
                dueDate, priority, estimatedHours, completed);
    }
}