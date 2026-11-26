/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

public class StudyTask implements Comparable<StudyTask> {

    private int taskId;
    private String title;
    private String description;
    private Course course;
    private String deadline;
    private int priority; // 1 = High, 2 = Medium, 3 = Low
    private double estimatedHours;
    private boolean completed;
    private String status;

    /**
     * Constructs a study task using an integer ID.
     */
    public StudyTask(int taskId, String title, String description,
                     Course course, String deadline, int priority,
                     double estimatedHours) {

        // Last-line guard only: the application validates input before calling this.
        if (!Validator.isValidTaskID(taskId)) {
            throw new IllegalArgumentException(Validator.MSG_TASK_ID);
        }
        if (!Validator.isNonEmpty(title)) {
            throw new IllegalArgumentException("Task title " + Validator.MSG_NON_EMPTY);
        }
        if (!Validator.isValidDeadline(deadline)) {
            throw new IllegalArgumentException(Validator.MSG_DATE);
        }
        if (!Validator.isValidPriority(priority)) {
            throw new IllegalArgumentException(Validator.MSG_PRIORITY);
        }

        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.deadline = deadline;
        this.priority = priority;
        this.estimatedHours = estimatedHours;
        this.completed = false;
        this.status = "Pending";
    }

    /**
     * Constructs a study task using a String ID.
     */
    public StudyTask(String taskId, String title, String description,
                     Course course, String deadline, int priority,
                     double estimatedHours) {
        this(Integer.parseInt(taskId), title, description,
                course, deadline, priority, estimatedHours);
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
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the task priority.
     */
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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
        // Last-line guard only: callers validate the status with Validator first.
        if (!Validator.isValidStatus(status)) {
            throw new IllegalArgumentException(Validator.MSG_STATUS);
        }
        this.status = status;
        this.completed = "Completed".equals(status);
    }

    /**
     * Returns true if this task has high priority.
     */
    public boolean isHighPriority() {
        return priority == 1;
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

        // 1 = High comes first, so a lower number sorts earlier
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }

        return Integer.compare(Validator.dateSortKey(this.deadline),
                Validator.dateSortKey(other.deadline));
    }

    /**
     * Returns a string representation of this task.
     */
    @Override
    public String toString() {
        String courseText = (course != null)
                ? course.getCourseCode() + " " + course.getCourseName()
                : "N/A";
        String descriptionText = (description != null && !description.trim().isEmpty())
                ? description
                : "(none)";
        return String.format(
                "Task[id=%d, title=%s, description=%s, course=%s, deadline=%s, priority=%d (%s), "
                        + "hours=%s, status=%s]",
                taskId,
                title,
                descriptionText,
                courseText,
                deadline,
                priority,
                priorityLabel(),
                OutputFormat.number(estimatedHours),
                status
        );
    }

    /**
     * Returns the word for this task's priority number.
     *
     * @return "High" for 1, "Medium" for 2, "Low" for 3
     */
    private String priorityLabel() {
        switch (priority) {
            case 1:
                return "High";
            case 2:
                return "Medium";
            default:
                return "Low";
        }
    }
}
