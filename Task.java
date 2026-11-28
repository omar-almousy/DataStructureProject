/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

import java.util.Objects;

/* Part B task: six-digit ID, title, course ID, deadline, priority 1-3 and status. Equal when IDs match. */
public class Task {
    private int taskID;      // unique six-digit task identifier
    private String taskTitle;// non-empty task title
    private int courseID;    // associated course identifier
    private String deadline; // deadline in DD/MM/YYYY format
    private int priority;    // 1 = High, 2 = Medium, 3 = Low
    private String status;   // Pending / In Progress / Completed

    /* Constructs a task initializing all fields. */
    public Task(int taskID, String taskTitle, int courseID,
                String deadline, int priority, String status) {
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.courseID = courseID;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
    }

    // ----- Getters -----

    public int getTaskID() {
        return taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    // ----- Setters -----

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /* Two tasks are equal if and only if they have the same taskID. Returns true if the other object is a Task with the same taskID */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return this.taskID == task.taskID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskID);
    }

    /* Returns true if the task status is Completed (case-safe comparison). Returns true only when status represents "Completed" */
    public boolean isCompleted() {
        return status != null && status.equalsIgnoreCase("Completed");
    }

    /* Returns true if the task priority is High (priority == 1). Returns true only when priority is 1 */
    public boolean isHighPriority() {
        return priority == 1;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, title='%s', courseID=%d, deadline=%s, priority=%d, status=%s]",
                taskID, taskTitle, courseID, deadline, priority, status);
    }
}
