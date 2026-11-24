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

    /**
     * Constructs a study task with the given details.
     *
     * @param taskId          the task ID
     * @param title           the task title
     * @param description     the task description
     * @param course          the associated course
     * @param dueDate         the due date
     * @param priority        the priority (High, Medium, Low)
     * @param estimatedHours  the estimated hours to complete
     */
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

    /**
     * Returns the task ID.
     *
     * @return the task ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the task ID.
     *
     * @param taskId the task ID to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Returns the task title.
     *
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the task title.
     *
     * @param title the task title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the task description.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description the task description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Returns the due date.
     *
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     *
     * @param dueDate the due date to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns the task priority.
     *
     * @return the priority (High, Medium, Low)
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the task priority.
     *
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Returns the estimated hours to complete.
     *
     * @return the estimated hours
     */
    public double getEstimatedHours() {
        return estimatedHours;
    }

    /**
     * Sets the estimated hours to complete.
     *
     * @param estimatedHours the estimated hours to set
     */
    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    /**
     * Returns true if the task is completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marks the task as completed.
     */
    public void markComplete() {
        this.completed = true;
    }

    /**
     * Marks the task as incomplete.
     */
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

    /**
     * Computes a numeric rank for a priority string (High=3, Medium=2, Low=1).
     *
     * @param p the priority string
     * @return the numeric rank
     */
    private int priorityRank(String p) {
        switch (p) {
            case "High": return 3;
            case "Medium": return 2;
            case "Low": return 1;
            default: return 0;
        }
    }

    /**
     * Returns a string representation of this task.
     *
     * @return a formatted string with task details
     */
    @Override
    public String toString() {
        return String.format("Task[id=%s, title='%s', course=%s, due=%s, priority=%s, hours=%.1f, completed=%b]",
                taskId, title, (course != null ? course.getCourseCode() : "N/A"),
                dueDate, priority, estimatedHours, completed);
    }
}