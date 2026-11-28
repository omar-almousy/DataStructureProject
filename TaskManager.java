/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

/* Part B manager for Task objects, stored in the course's KWArrayList class. */
public class TaskManager {
    private KWArrayList<Task> tasks;
    private KWArrayList<Integer> validCourseIDs;

    /* Constructs a task manager with the given set of valid (existing) course IDs. The IDs are copied, so later changes to the caller's list have no effect. */
    public TaskManager(KWArrayList<Integer> validCourseIDs) {
        this.tasks = new KWArrayList<>();
        this.validCourseIDs = new KWArrayList<>();
        if (validCourseIDs != null) {
            for (int i = 0; i < validCourseIDs.size(); i++) {
                this.validCourseIDs.add(validCourseIDs.get(i));
            }
        }
    }

    /* Adds a task if its ID is new and its course ID exists. Returns true if added. */
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

    /* Displays all tasks with their course info, or an empty-list message if none. */
    public void displayAllStudyTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No study tasks to display.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i));
        }
    }

    /* Searches for a task by its ID. Returns the matching Task, or null if not found */
    public Task searchTaskByID(int id) {
        return findTaskByID(id);
    }

    /* Updates the status of an existing task. Returns true if the status was updated; false if the task ID is unknown or the status is invalid */
    public boolean updateTaskStatus(int id, String newStatus) {
        if (!Validator.isValidStatus(newStatus)) {
            return false;
        }
        Task task = findTaskByID(id);
        if (task == null) {
            return false;
        }
        task.setStatus(newStatus);
        return true;
    }

    /* Displays all high-priority incomplete tasks, using the Part B helper methods Task.isHighPriority() and Task.isCompleted(). Prints a message if none match. */
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

    /* Returns the percentage of tasks that are Completed, or -1 if there are no tasks. */
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

    /* Finds a task by its ID using an indexed loop. Returns the matching Task, or null if not found */
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

    /* Returns true if the given course ID exists in the set of valid course IDs. Returns true if the course ID exists */
    private boolean courseExists(int courseID) {
        for (int i = 0; i < validCourseIDs.size(); i++) {
            if (validCourseIDs.get(i) == courseID) {
                return true;
            }
        }
        return false;
    }
}
