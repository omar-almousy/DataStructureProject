/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

import java.util.Scanner;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/* Main class: runs the menu, validates every input with Validator, and calls the manager. */
public class SmartStudyPlannerApplication {

    /* Sentinel returned by the int readers when the input is missing or invalid. */
    private static final int INVALID_INT = Integer.MIN_VALUE;

    /* Sentinel returned by the double readers when the input is missing or invalid. */
    private static final double INVALID_DOUBLE = Double.NaN;

    private static SmartStudyPlannerManager planner;
    private static Scanner scanner;

    /* Entry point: runs the menu loop until the user chooses 0 or input ends. */
    public static void main(String[] args) {
        planner = new SmartStudyPlannerManager();
        scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            if (!scanner.hasNextLine()) {
                break; // input closed (e.g. Ctrl+Z / end of piped input)
            }
            int choice = parseInt(clean(scanner.nextLine()));
            if (choice == INVALID_INT) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            running = handleChoice(choice);
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    /* Prints the main menu. */
   /* private static void printMenu() {
        System.out.println("\n=== Smart Study Planner Menu ===");
        System.out.println("0. Exit");
        System.out.println("1. Add Course");
        System.out.println("2. Display All Courses");
        System.out.println("3. Delete Course");
        System.out.println("4. Update Task Status");
        System.out.println("5. Search Task by ID");
        System.out.println("6. Add Study Task");
        System.out.println("7. Display All Study Tasks");
        System.out.println("8. Delete Study Task");
        System.out.println("9. Log Study Session");
        System.out.println("10. Display All Sessions");
        System.out.println("11. Delete Study Session");
        System.out.println("12. Display High-Priority Incomplete Tasks");
        System.out.print("------------------------------\n");
    }*/

    private static void printMenu() {
    System.out.println("\n=== Smart Study Planner Menu ===");
    System.out.println("0. Exit");
    System.out.println("1. Add Course");
    System.out.println("2. Delete Course");
    System.out.println("3. Update Task Status");
    System.out.println("4. Search Task by ID");
    System.out.println("5. Add Study Task");
    System.out.println("6. Delete Study Task");
    System.out.println("7. Log Study Session");
    System.out.println("8. Display All Courses");
    System.out.println("9. Display All Study Tasks");
    System.out.println("10. Display High-Priority Incomplete Tasks");
    System.out.println("11. Display All Sessions");
    System.out.println("12. Delete Study Session");
    System.out.println("13. Show Sessions for a Task");
    System.out.println("14. Show Completion Percentage");
    System.out.println("------------------------------");
}

    // ----- Raw input -----

    /* Prompts for and reads one full line of input, trimmed. Returns the trimmed line, or an empty string if input has ended */
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return "";
        }
        return clean(scanner.nextLine());
    }

    /* Trims a line and removes an invisible byte-order mark that PowerShell can add to piped input. */
    private static String clean(String line) {
        return line.replace("﻿", "").trim();
    }

    /* Parses a whole number, catching bad input. Returns the number, or INVALID_INT if the text is not a whole number */
    private static int parseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return INVALID_INT;
        }
    }

    /* Parses a decimal number, catching bad input. Returns the number, or INVALID_DOUBLE (NaN) if the text is not a number */
    private static double parseDouble(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return INVALID_DOUBLE;
        }
    }

    // ----- Validated input (the only place user input is checked) -----

    /* Reads a whole number and checks it against a Validator rule. Returns INVALID_INT if rejected. */
    private static int readValidInt(String prompt, IntPredicate rule, String error) {
        int value = parseInt(readLine(prompt));
        if (value == INVALID_INT || !rule.test(value)) {
            System.out.println(error);
            return INVALID_INT;
        }
        return value;
    }

    /* Prompts for a decimal number and checks it against a Validator rule. Returns the valid value, or INVALID_DOUBLE (NaN) if it was rejected */
    private static double readValidDouble(String prompt, DoublePredicate rule, String error) {
        double value = parseDouble(readLine(prompt));
        if (Double.isNaN(value) || !rule.test(value)) {
            System.out.println(error);
            return INVALID_DOUBLE;
        }
        return value;
    }

    /* Prompts for text and checks it against a Validator rule. Returns the valid text, or null if it was rejected */
    private static String readValidLine(String prompt, Predicate<String> rule, String error) {
        String value = readLine(prompt);
        if (!rule.test(value)) {
            System.out.println(error);
            return null;
        }
        return value;
    }

    /* Prompts for a six-digit task ID (shared by every option that asks for one). Returns the valid task ID, or INVALID_INT if it was rejected */
    private static int readTaskID() {
        return readValidInt("Enter task ID (6 digits): ", Validator::isValidTaskID, Validator.MSG_TASK_ID);
    }

    /* Prompts for a five-digit course ID (shared by every option that asks for one). Returns the valid course ID, or INVALID_INT if it was rejected */
    private static int readCourseID() {
        return readValidInt("Enter course ID (5 digits): ", Validator::isValidCourseID, Validator.MSG_COURSE_ID);
    }

    /* Prompts for a six-digit session ID (shared by every option that asks for one). Returns the valid session ID, or INVALID_INT if it was rejected */
    private static int readSessionID() {
        return readValidInt("Enter session ID (6 digits): ", Validator::isValidSessionID,
                Validator.MSG_SESSION_ID);
    }

    /* Prompts for a status from the whitelist (shared by Add Task and Update Status). Returns the valid status, or null if it was rejected */
    private static String readStatus() {
        return readValidLine("Enter status (Pending / In Progress / Completed): ",
                Validator::isValidStatus, Validator.MSG_STATUS);
    }

    // ----- Menu dispatch -----

    /* Dispatches a menu choice. Returns false if the user chose to exit, true otherwise */
    private static boolean handleChoice(int choice) {
    switch (choice) {
        case 0:
            return false;
        case 1:
            handleAddCourse();
            break;
        case 2:
            handleDeleteCourse();
            break;
        case 3:
            handleUpdateTaskStatus();
            break;
        case 4:
            handleSearchTaskByID();
            break;
        case 5:
            handleAddStudyTask();
            break;
        case 6:
            handleDeleteStudyTask();
            break;
        case 7:
            handleLogStudySession();
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
        case 13:
            handleShowSessionsForTask();
            break;
        case 14:
            handleShowCompletionPercentage();
            break;
        default:
            System.out.println("Invalid choice. Please select 0-14.");
            break;
    }
    return true;
}

    // ----- Options -----

    /* Option 1: prompts for all course fields, validates each one, and adds the course. */
    private static void handleAddCourse() {
        int id = readCourseID();
        if (id == INVALID_INT) return;
        String name = readValidLine("Enter course name: ", Validator::isNonEmpty,
                "Invalid course name: " + Validator.MSG_NON_EMPTY);
        if (name == null) return;
        String instructor = readValidLine("Enter instructor name: ", Validator::isNonEmpty,
                "Invalid instructor name: " + Validator.MSG_NON_EMPTY);
        if (instructor == null) return;
        int credits = readValidInt("Enter credit hours (1-6): ", Validator::isValidCreditHours,
                Validator.MSG_CREDIT_HOURS);
        if (credits == INVALID_INT) return;

        boolean success = planner.addCourse(new Course(id, name, instructor, credits));
        if (success) {
            System.out.println("Course " + id + " added successfully.");
        } else {
            System.out.println("Failed to add course: duplicate ID " + id + " already exists.");
        }
    }

    /* Option 2: deletes a course if no tasks reference it. */
    private static void handleDeleteCourse() {
        int courseID = readCourseID();
        if (courseID == INVALID_INT) return;
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
                break;
        }
    }

    /* Option 3: updates a task's status after checking the task and validating the status. */
    private static void handleUpdateTaskStatus() {
        int id = readTaskID();
        if (id == INVALID_INT) return;
        if (planner.searchTaskByID(id) == null) {
            System.out.println("Task not found.");
            return;
        }
        String newStatus = readStatus();
        if (newStatus == null) return;

        planner.updateTaskStatus(id, newStatus);
        System.out.println("Status updated successfully.");
    }

    /* Option 4: searches for a task by ID. */
    private static void handleSearchTaskByID() {
        int id = readTaskID();
        if (id == INVALID_INT) return;

        StudyTask task = planner.searchTaskByID(id);
        if (task == null) {
            System.out.println("no matching task");
        } else {
            System.out.println(task);
        }
    }

    /* Option 5: prompts for all task fields, validates each one, and adds the task. */
    private static void handleAddStudyTask() {
        int id = readTaskID();
        if (id == INVALID_INT) return;
        String title = readValidLine("Enter task title: ", Validator::isNonEmpty,
                "Invalid title: " + Validator.MSG_NON_EMPTY);
        if (title == null) return;
        String description = readLine("Enter task description: ");
        int courseID = readCourseID();
        if (courseID == INVALID_INT) return;
        Course course = planner.getCourseByID(courseID);
        if (course == null) {
            System.out.println("Unknown course: no course exists with ID " + courseID + ".");
            return;
        }
        String deadline = readValidLine("Enter deadline (DD/MM/YYYY): ", Validator::isValidDeadline,
                Validator.MSG_DATE);
        if (deadline == null) return;
        int priority = readValidInt("Enter priority (1 = High, 2 = Medium, 3 = Low): ",
                Validator::isValidPriority, Validator.MSG_PRIORITY);
        if (priority == INVALID_INT) return;
        String status = readStatus();
        if (status == null) return;
        double hours = readValidDouble("Enter estimated hours: ", Validator::isPositive,
                "Invalid estimated hours: " + Validator.MSG_POSITIVE);
        if (Double.isNaN(hours)) return;

        StudyTask t = new StudyTask(id, title, description, course, deadline, priority, hours);
        t.setStatus(status);
        boolean success = planner.addTask(t);
        if (success) {
            System.out.println("Task " + id + " added successfully to course " + courseID + ".");
        } else {
            System.out.println("Failed to add task: duplicate ID " + id + " already exists.");
        }
    }

    /* Option 6: deletes a task if no sessions reference it. */
    private static void handleDeleteStudyTask() {
        int taskID = readTaskID();
        if (taskID == INVALID_INT) return;
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
                break;
        }
    }

    /* Option 7: logs a study session of a given duration against an existing task. */
    private static void handleLogStudySession() {
        int sessionID = readSessionID();
        if (sessionID == INVALID_INT) return;
        int taskID = readTaskID();
        if (taskID == INVALID_INT) return;
        double hours = readValidDouble("Enter duration in hours: ", Validator::isValidDuration,
                "Failure: duration " + Validator.MSG_POSITIVE);
        if (Double.isNaN(hours)) return;

        int result = planner.logStudySession(sessionID, taskID, hours);
        switch (result) {
            case 0:
                StudyTask task = planner.searchTaskByID(taskID);
                System.out.println("Session " + sessionID + " logged successfully: "
                        + OutputFormat.number(hours) + " hour(s) on task " + taskID
                        + " (" + task.getTitle() + ").");
                break;
            case 1:
                System.out.println("Failure: duplicate session ID " + sessionID + ".");
                break;
            case 2:
                System.out.println("Failure: task not found.");
                break;
            default:
                System.out.println("Unexpected error.");
                break;
        }
    }

    /* Option 8: displays all courses. */
    private static void handleDisplayAllCourses() {
        planner.displayAllCourses();
    }

    /* Option 9: displays all study tasks. */
    private static void handleDisplayAllStudyTasks() {
        planner.displayAllStudyTasks();
    }

    /* Option 10: displays all high-priority incomplete tasks. */
    private static void handleDisplayHighPriorityIncompleteTasks() {
        planner.displayHighPriorityIncompleteTasks();
    }

    /* Option 11: displays all sessions. */
    private static void handleDisplayAllSessions() {
        KWLinkedList<StudySession> sessions = planner.getAllSessions();
        if (sessions.isEmpty()) {
            System.out.println("No sessions available.");
        } else {
            for (int i = 0; i < sessions.size(); i++) {
                System.out.println("  " + sessions.get(i));
            }
        }
    }

    /* Option 12: deletes a session by ID. */
    private static void handleDeleteStudySession() {
        int sessionID = readSessionID();
        if (sessionID == INVALID_INT) return;
        boolean success = planner.removeSession(String.valueOf(sessionID));
        System.out.println(success ? "Session deleted successfully." : "Session not found.");
    }

    /* Option 13: shows the sessions logged to one task. Distinguishes "task not found" from "task exists but has no sessions". */
    private static void handleShowSessionsForTask() {
        int taskID = readTaskID();
        if (taskID == INVALID_INT) return;
        StudyTask task = planner.searchTaskByID(taskID);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        KWLinkedList<StudySession> found = planner.getSessionsForTask(taskID);
        if (found.isEmpty()) {
            System.out.println("No sessions recorded for this task.");
            return;
        }
        System.out.println("Sessions recorded for task '" + task.getTitle() + "' (ID: " + taskID + "):");
        for (int i = 0; i < found.size(); i++) {
            System.out.println("  " + found.get(i));
        }
    }

    /* Option 14: shows the percentage of tasks that are Completed, to 2 decimals. */
    private static void handleShowCompletionPercentage() {
        double percent = planner.calculateCompletionPercentage();
        if (percent < 0) {
            System.out.println("No tasks exist, so there is no completion percentage yet.");
        } else {
            System.out.println("Completion percentage: " + OutputFormat.percentage(percent));
        }
    }
}
