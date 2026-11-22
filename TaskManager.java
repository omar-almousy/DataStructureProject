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
 * Manages a collection of Part B {@link Task} objects.
 * Provides add, display, search, update, high-priority incomplete listing, and
 * completion-percentage calculation operations.
 * <p>
 * All iterations use indexed loops only (no enhanced for-loops).
 */
public class TaskManager {
    private List<Task> tasks;
    private List<Integer> validCourseIDs;

    /**
     * Constructs a task manager with the given set of valid (existing) course IDs.
     *
     * @param validCourseIDs course IDs that are considered valid/existing
     */
    public TaskManager(List<Integer> validCourseIDs) {
        this.tasks = new ArrayList<>();
        this.validCourseIDs =
                validCourseIDs != null ? new ArrayList<>(validCourseIDs) : new ArrayList<>();
    }

    /**
     * Adds a task if its taskID is not a duplicate and its courseID exists.
     *
     * @param task the task to add
     * @return true if the task was added; false if rejected (null task, duplicate
     *         taskID, or unknown courseID)
     */
    public boolean addTask(Task task) {
        if (task == null) {
            return false;
        }
        if (findTaskByID(task.getTaskID()) != null) {
            return false;
        }
        if (!courseExists(task.getCourseID())) {
            return false;
        }
        tasks.add(task);
        return true;
    }

    /**
     * Displays all tasks with their course info, or an empty-list message if none.
     */
    public void displayAllStudyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No study tasks to display.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i));
        }
    }

    /**
     * Searches for a task by its ID.
     *
     * @param id the task ID to search for
     * @return the matching {@link Task}, or null if not found
     */
    public Task searchTaskByID(int id) {
        return findTaskByID(id);
    }

    /**
     * Updates the status of an existing task.
     *
     * @param id        the task ID
     * @param newStatus the new status (Pending / In Progress / Completed)
     * @return true if the status was updated; false if the task ID is unknown or
     *         the status is invalid
     */
    public boolean updateTaskStatus(int id, String newStatus) {
        if (!isValidStatus(newStatus)) {
            return false;
        }
        Task task = findTaskByID(id);
        if (task == null) {
            return false;
        }
        task.setStatus(newStatus);
        return true;
    }

    /**
     * Displays all high-priority incomplete tasks, using the Part B helper methods
     * {@link Task#isHighPriority()} and {@link Task#isCompleted()}. Prints a
     * message if none match.
     */
    public void displayHighPriorityIncompleteTasks() {
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.isHighPriority() && !t.isCompleted()) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No high-priority incomplete tasks.");
        }
    }

    /**
     * Calculates the completion percentage of all tracked tasks.
     * <p>
     * Formula: {@code (completedTasks / totalTasks) * 100.0}. Uses the Part B
     * helper {@link Task#isCompleted()} to count completed tasks via an indexed
     * loop. Result formatting (e.g. 2 decimals) is left to the app layer.
     *
     * @return completion percentage in [0.0, 100.0]; returns {@code -1} when
     *         there are no tasks (guards against division by zero / NaN)
     */
    public double calculateCompletionPercentage() {
        if (tasks.isEmpty()) {
            return -1;
        }
        int completed = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isCompleted()) {
                completed++;
            }
        }
        return (completed / (double) tasks.size()) * 100.0;
    }

    // ----- Helper -----

    /**
     * Finds a task by its ID using an indexed loop.
     *
     * @param id the task ID
     * @return the matching {@link Task}, or null if not found
     */
    private Task findTaskByID(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getTaskID() == id) {
                return t;
            }
        }
        return null;
    }

    // ----- Validation helpers -----

    /**
     * Returns true if the given course ID exists in the set of valid course IDs.
     *
     * @param courseID the course ID to validate
     * @return true if the course ID exists
     */
    private boolean courseExists(int courseID) {
        for (int i = 0; i < validCourseIDs.size(); i++) {
            if (validCourseIDs.get(i) == courseID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the given status is one of Pending, In Progress, or
     * Completed (case-insensitive).
     *
     * @param status the status to validate
     * @return true if valid
     */
    private boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }
        return status.equalsIgnoreCase("Pending")
                || status.equalsIgnoreCase("In Progress")
                || status.equalsIgnoreCase("Completed");
    }
}
