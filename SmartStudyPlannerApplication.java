
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

import java.util.LinkedList;
import java.util.Scanner;

public class SmartStudyPlannerApplication {

    private static final int INVALID_INT = Integer.MIN_VALUE;
    private static final double INVALID_DOUBLE = Double.NaN;

    private static SmartStudyPlannerManager planner;
    private static Scanner scanner;

    public static void main(String[] args) {
        planner = new SmartStudyPlannerManager();
        scanner = new Scanner(System.in);

        boolean running = true;

        while (running && scanner.hasNextLine()) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            if (choice == INVALID_INT) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            running = handleChoice(choice);
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Smart Study Planner Menu ===");
        System.out.println("0. Exit");
        System.out.println("1. Add Course");
        System.out.println("2. Add Study Task");
        System.out.println("3. Log Study Session");
        System.out.println("4. Update Task Status");
        System.out.println("5. Search Task by ID");
        System.out.println("6. Delete Study Task");
        System.out.println("7. Delete Course");
        System.out.println("8. Display All Courses");
        System.out.println("9. Display All Study Tasks");
        System.out.println("10. Display High-Priority Incomplete Tasks");
        System.out.println("11. Display All Sessions");
        System.out.println("12. Delete Study Session");
        System.out.println("------------------------------");
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);

        if (!scanner.hasNextLine()) {
            return "";
        }

        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        try {
            return Integer.parseInt(readLine(prompt));
        } catch (NumberFormatException e) {
            return INVALID_INT;
        }
    }

    private static double readDouble(String prompt) {
        try {
            return Double.parseDouble(readLine(prompt));
        } catch (NumberFormatException e) {
            return INVALID_DOUBLE;
        }
    }

    private static boolean handleChoice(int choice) {
        switch (choice) {
            case 0:
                return false;
            case 1:
                handleAddCourse();
                break;
            case 2:
                handleAddStudyTask();
                break;
            case 3:
                handleLogStudySession();
                break;
            case 4:
                handleUpdateTaskStatus();
                break;
            case 5:
                handleSearchTaskByID();
                break;
            case 6:
                handleDeleteStudyTask();
                break;
            case 7:
                handleDeleteCourse();
                break;
            case 8:
                handleDisplayAllCourses();
                break;
            case 9:
                handleDisplayAllStudyTasks();
                break;
            case 10:
                handleDisplayHighPriorityIncompleteTasks();
                break;
            case 11:
                handleDisplayAllSessions();
                break;
            case 12:
                handleDeleteStudySession();
                break;
            default:
                System.out.println("Invalid choice. Please select 0-12.");
        }

        return true;
    }

    private static void handleAddCourse() {
        int id = readInt("Enter course ID (5 digits): ");

        if (!Validator.isValidCourseID(id)) {
            System.out.println("Invalid course ID: must be exactly 5 digits (10000-99999).");
            return;
        }

        String name = readLine("Enter course name: ");

        if (!Validator.isNonEmpty(name)) {
            System.out.println("Invalid course name: cannot be empty.");
            return;
        }

        String instructor = readLine("Enter instructor name: ");

        if (!Validator.isNonEmpty(instructor)) {
            System.out.println("Invalid instructor name: cannot be empty.");
            return;
        }

        int credits = readInt("Enter credit hours (1-6): ");

        if (!Validator.isValidCreditHours(credits)) {
            System.out.println("Invalid credit hours: must be a whole number between 1 and 6.");
            return;
        }

        boolean success = planner.addCourse(
                new Course(id, name, instructor, credits));

        if (success) {
            System.out.println("Course " + id + " added successfully.");
        } else {
            System.out.println("Failed to add course: duplicate ID " + id + " already exists.");
        }
    }

    private static void handleAddStudyTask() {
        int id = readInt("Enter task ID (6 digits): ");

        if (!Validator.isValidTaskID(id)) {
            System.out.println("Invalid task ID: must be exactly 6 digits (100000-999999).");
            return;
        }

        String title = readLine("Enter task title: ");

        if (!Validator.isNonEmpty(title)) {
            System.out.println("Invalid title: cannot be empty.");
            return;
        }

        String description = readLine("Enter task description: ");
        int courseID = readInt("Enter course ID: ");
        Course course = planner.getCourseByID(courseID);

        if (course == null) {
            System.out.println("Unknown course: no course exists with that ID.");
            return;
        }

        String deadline = readLine("Enter deadline (DD/MM/YYYY): ");

        if (!Validator.isValidDeadline(deadline)) {
            System.out.println("Invalid deadline: must be a real date in DD/MM/YYYY format.");
            return;
        }

        int priority = readInt("Enter priority (1 = High, 2 = Medium, 3 = Low): ");

        if (!Validator.isValidPriority(priority)) {
            System.out.println("Invalid priority: must be 1, 2, or 3.");
            return;
        }

        String status = readLine("Enter status (Pending / In Progress / Completed): ");

        if (!Validator.isValidStatus(status)) {
            System.out.println("Invalid status: must be Pending, In Progress, or Completed.");
            return;
        }

        double hours = readDouble("Enter estimated hours: ");

        if (!Validator.isValidDuration(hours)) {
            System.out.println("Invalid estimated hours: must be a number greater than 0.");
            return;
        }

        StudyTask task = new StudyTask(
                id, title, description, course, deadline, priority, hours);

        task.setStatus(status);

        boolean success = planner.addTask(task);

        if (success) {
            System.out.println("Task " + id + " added successfully to course " + courseID + ".");
        } else {
            System.out.println("Failed to add task: duplicate ID " + id + " already exists.");
        }
    }

    private static void handleLogStudySession() {
        int sessionID = readInt("Enter session ID (6 digits): ");

        if (!Validator.isValidSessionID(sessionID)) {
            System.out.println("Invalid session ID: must be exactly 6 digits (100000-999999).");
            return;
        }

        int taskID = readInt("Enter task ID: ");

        if (taskID == INVALID_INT) {
            System.out.println("Failure: task not found.");
            return;
        }

        double hours = readDouble("Enter duration in hours: ");

        if (!Validator.isValidDuration(hours)) {
            System.out.println("Failure: duration must be a number greater than 0.");
            return;
        }

        int result = planner.logStudySession(sessionID, taskID, hours);

        switch (result) {
            case 0:
                StudyTask task = planner.searchTaskByID(taskID);
                System.out.printf(
                        "Session %d logged successfully: %.2f hour(s) on task %d (%s).%n",
                        sessionID, hours, taskID, task.getTitle());
                break;

            case 1:
                System.out.println("Failure: duplicate session ID " + sessionID + ".");
                break;

            case 2:
                System.out.println("Failure: task not found.");
                break;

            case 3:
                System.out.println("Failure: duration must be a number greater than 0.");
                break;

            default:
                System.out.println("Failure: invalid session ID.");
        }
    }

    private static void handleUpdateTaskStatus() {
        int id = readInt("Enter task ID: ");

        if (id == INVALID_INT) {
            System.out.println("Invalid task ID.");
            return;
        }

        StudyTask task = planner.searchTaskByID(id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        String newStatus = readLine(
                "Enter new status (Pending / In Progress / Completed): ");

        if (!Validator.isValidStatus(newStatus)) {
            System.out.println("Invalid status. Allowed values: Pending, In Progress, Completed.");
            return;
        }

        boolean updated = planner.updateTaskStatus(id, newStatus);

        if (updated) {
            System.out.println("Status updated successfully.");
        } else {
            System.out.println("Failed to update task status.");
        }
    }

    private static void handleSearchTaskByID() {
        int id = readInt("Enter task ID: ");

        if (id == INVALID_INT) {
            System.out.println("Invalid task ID.");
            return;
        }

        StudyTask task = planner.searchTaskByID(id);

        if (task == null) {
            System.out.println("no matching task");
        } else {
            System.out.println(task);
        }
    }

    private static void handleDeleteStudyTask() {
        int taskID = readInt("Enter task ID to delete: ");

        if (taskID == INVALID_INT) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        int result = planner.deleteStudyTask(taskID);

        switch (result) {
            case 0:
                System.out.println("Task deleted successfully.");
                break;

            case 1:
                System.out.println("Task not found.");
                break;

            case 2:
                System.out.println("Cannot delete task - has sessions.");
                break;

            default:
                System.out.println("Unexpected error.");
        }
    }

    private static void handleDeleteCourse() {
        int courseID = readInt("Enter course ID to delete: ");

        if (courseID == INVALID_INT) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        int result = planner.deleteCourse(courseID);

        switch (result) {
            case 0:
                System.out.println("Course deleted successfully.");
                break;

            case 1:
                System.out.println("Course not found.");
                break;

            case 2:
                System.out.println("Cannot delete course - has tasks.");
                break;

            default:
                System.out.println("Unexpected error.");
        }
    }

    private static void handleDisplayAllCourses() {
        planner.displayAllCourses();
    }

    private static void handleDisplayAllStudyTasks() {
        planner.displayAllStudyTasks();
    }

    private static void handleDisplayHighPriorityIncompleteTasks() {
        planner.displayHighPriorityIncompleteTasks();
    }

    private static void handleDisplayAllSessions() {
        LinkedList<StudySession> sessions = planner.getAllSessions();

        if (sessions.isEmpty()) {
            System.out.println("No sessions available.");
        } else {
            for (int i = 0; i < sessions.size(); i++) {
                System.out.println("  " + sessions.get(i));
            }
        }
    }

    private static void handleDeleteStudySession() {
        String sessionId = readLine("Enter session ID to delete: ");
        boolean success = planner.removeSession(sessionId);

        System.out.println(success
                ? "Session deleted successfully."
                : "Session not found.");
    }
}