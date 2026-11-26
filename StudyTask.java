
public class StudyTask implements Comparable<StudyTask> {

    private int taskId;
    private String title;
    private String description;
    private Course course;
    private String dueDate;
    private String priority;
    private double estimatedHours;
    private boolean completed;
    private String status;

    /**
     * Constructs a study task using an integer ID.
     */
    public StudyTask(int taskId, String title, String description,
                     Course course, String dueDate, String priority,
                     double estimatedHours) {

        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.completed = false;
        this.status = "Pending";
    }

    /**
     * Constructs a study task using a String ID.
     */
    public StudyTask(String taskId, String title, String description,
                     Course course, String dueDate, String priority,
                     double estimatedHours) {

        this(Integer.parseInt(taskId), title, description,
                course, dueDate, priority, estimatedHours);
    }

    /**
     * Returns the task ID.
     */
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = Integer.parseInt(taskId);
    }

    /**
     * Returns the task title.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the task description.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the associated course.
     */
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Returns the due date.
     */
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns the task priority.
     */
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Returns the estimated hours to complete.
     */
    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    /**
     * Returns true if the task is completed.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns the task status.
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.completed = "Completed".equals(status);
    }

    /**
     * Returns true if this task has high priority.
     */
    public boolean isHighPriority() {
        return "High".equals(priority);
    }

    /**
     * Marks the task as completed.
     */
    public void markComplete() {
        this.completed = true;
        this.status = "Completed";
    }

    /**
     * Marks the task as incomplete.
     */
    public void markIncomplete() {
        this.completed = false;
        this.status = "Pending";
    }

    /**
     * Compares tasks by priority and then due date.
     */
    @Override
    public int compareTo(StudyTask other) {

        int thisRank = priorityRank(this.priority);
        int otherRank = priorityRank(other.priority);

        if (thisRank != otherRank) {
            return Integer.compare(otherRank, thisRank);
        }

        return this.dueDate.compareTo(other.dueDate);
    }

    /**
     * Computes a numeric rank for a priority.
     */
    private int priorityRank(String p) {
        switch (p) {
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

    /**
     * Returns a string representation of this task.
     */
    @Override
    public String toString() {
        return String.format(
                "Task[id=%d, title=%s, course=%s, due=%s, priority=%s, hours=%.1f, completed=%b, status=%s]",
                taskId,
                title,
                (course != null ? course.getCourseCode() : "N/A"),
                dueDate,
                priority,
                estimatedHours,
                completed,
                status
        );
    }
}
