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
 * Tasks implement Comparable so they can be ordered by priority.
 */
public class StudyTask implements Comparable<StudyTask> {
    private int taskId;
    private String title;
    private String description;
    private Course course;
    private String dueDate;
    private String priority; // "High", "Medium", "Low"
    private double estimatedHours;
    private String status; // "Pending", "In Progress", "Completed"

    public StudyTask(int taskId, String title, String description,
            Course course, String dueDate, String priority, double estimatedHours) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.status = "Pending";
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns true if this task has high priority.
     */
    public boolean isHighPriority() {
        return "High".equals(priority);
    }

    /**
     * Returns true if this task is completed.
     */
    public boolean isCompleted() {
        return "Completed".equals(status);
    }

    public void markComplete() {
        this.status = "Completed";
    }

    public void markIncomplete() {
        this.status = "Pending";
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
        return String.format("Task[id=%d, title='%s', course=%s, due=%s, priority=%s, hours=%.1f, status=%s]",
                taskId, title, (course != null ? course.getCourseID() : "N/A"),
                dueDate, priority, estimatedHours, status);
    }
}