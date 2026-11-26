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

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main application class for the Smart Study Planner.
 * Provides a resilient menu shell with 12 options plus exit (0).
 * Uses a switch statement for dispatch and handles bad input gracefully.
 */
public class SmartStudyPlannerApplication {

    private static SmartStudyPlannerManager planner;
    private static Scanner scanner;

    public static void main(String[] args) {
        planner = new SmartStudyPlannerManager();
        scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            if (choice == Integer.MIN_VALUE) {
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
        System.out.println("2. Display All Courses");
        System.out.println("3. Delete Course");
        System.out.println("4. Add Study Task");
        System.out.println("5. Display All Study Tasks");
        System.out.println("6. Delete Study Task");
        System.out.println("7. Search Task by ID");
        System.out.println("8. Update Task Status");
        System.out.println("9. Add Study Session");
        System.out.println("10. Display All Sessions");
        System.out.println("11. Delete Study Session");
        System.out.println("12. Display High-Priority Incomplete Tasks");
        System.out.print("------------------------------\n");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a number.");
            return Integer.MIN_VALUE;
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a number.");
            return Double.MIN_VALUE;
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
                handleDisplayAllCourses();
                break;
            case 3:
                handleDeleteCourse();
                break;
            case 4:
                handleAddStudyTask();
                break;
            case 5:
                handleDisplayAllStudyTasks();
                break;
            case 6:
                handleDeleteStudyTask();
                break;
            case 7:
                handleSearchTaskByID();
                break;
            case 8:
                handleUpdateTaskStatus();
                break;
            case 9:
                handleAddStudySession();
                break;
            case 10:
                handleDisplayAllSessions();
                break;
            case 11:
                handleDeleteStudySession();
                break;
            case 12:
                handleDisplayHighPriorityIncompleteTasks();
                break;
            default:
                System.out.println("Invalid choice. Please select 0-12.");
                break;
        }
        return true;
    }

    private static void handleAddCourse() {
        int id = readInt("Enter course ID: ");
        if (id == Integer.MIN_VALUE) return;
        scanner.nextLine();
        String name = readLine("Enter course name: ");
        String instructor = readLine("Enter instructor name: ");
        int credits = readInt("Enter credit hours: ");
        if (credits == Integer.MIN_VALUE) return;

        Course c = new Course(id, name, instructor, credits);
        boolean success = planner.addCourse(c);
        System.out.println(success ? "Course added successfully."
                : "Failed to add course (duplicate ID or invalid data).");
    }

    private static void handleDisplayAllCourses() {
        planner.displayAllCourses();
    }

    private static void handleDeleteCourse() {
        int courseID = readInt("Enter course ID to delete: ");
        if (courseID == Integer.MIN_VALUE) return;
        int result = planner.deleteCourse(courseID);
        switch (result) {
            case 0:
                System.out.println("Course deleted successfully.");
                break;
            case 1:
                System.out.println("Course not found.");
                break;
            case 2:
                System.out.println("Cannot delete course: one or more study tasks reference this course.");
                break;
            default:
                System.out.println("Unexpected error.");
                break;
        }
    }

    private static void handleAddStudyTask() {
        int id = readInt("Enter task ID: ");
        if (id == Integer.MIN_VALUE) return;
        scanner.nextLine();
        String title = readLine("Enter task title: ");
        String description = readLine("Enter task description: ");
        int courseID = readInt("Enter course ID: ");
        if (courseID == Integer.MIN_VALUE) return;
        scanner.nextLine();
        String dueDate = readLine("Enter due date (YYYY-MM-DD): ");
        String priority = readLine("Enter priority (High/Medium/Low): ");
        double hours = readDouble("Enter estimated hours: ");
        if (hours == Double.MIN_VALUE) return;

        Course course = planner.getCourseByID(courseID);
        if (course == null) {
            System.out.println("Failed to add task: course ID " + courseID + " does not exist.");
            return;
        }

        StudyTask t = new StudyTask(id, title, description, course, dueDate, priority, hours);
        boolean success = planner.addTask(t);
        System.out.println(success ? "Task added successfully."
                : "Failed to add task (duplicate ID or invalid data).");
    }

    private static void handleDisplayAllStudyTasks() {
        planner.displayAllStudyTasks();
    }

    private static void handleDeleteStudyTask() {
        int taskID = readInt("Enter task ID to delete: ");
        if (taskID == Integer.MIN_VALUE) return;
        int result = planner.deleteStudyTask(taskID);
        switch (result) {
            case 0:
                System.out.println("Task deleted successfully.");
                break;
            case 1:
                System.out.println("Task not found.");
                break;
            case 2:
                System.out.println("Cannot delete task: one or more study sessions reference this task.");
                break;
            default:
                System.out.println("Unexpected error.");
                break;
        }
    }

    private static void handleSearchTaskByID() {
        int id = readInt("Enter task ID to search: ");
        if (id == Integer.MIN_VALUE) return;
        StudyTask t = planner.searchTaskByID(id);
        if (t != null) {
            System.out.println("Found: " + t);
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void handleUpdateTaskStatus() {
        int id = readInt("Enter task ID: ");
        if (id == Integer.MIN_VALUE) return;
        scanner.nextLine();
        String newStatus = readLine("Enter new status (Pending / In Progress / Completed): ");
        boolean success = planner.updateTaskStatus(id, newStatus);
        if (success) {
            System.out.println("Task status updated successfully.");
        } else {
            System.out.println("Failed to update status (task not found or invalid status).");
        }
    }

    private static void handleAddStudySession() {
        scanner.nextLine();
        String sessionId = readLine("Enter session ID: ");
        String date = readLine("Enter date (YYYY-MM-DD): ");
        String startTime = readLine("Enter start time (HH:MM): ");
        String endTime = readLine("Enter end time (HH:MM): ");
        int courseID = readInt("Enter course ID: ");
        if (courseID == Integer.MIN_VALUE) return;
        scanner.nextLine();
        String notes = readLine("Enter notes: ");

        Course course = planner.getCourseByID(courseID);
        if (course == null) {
            System.out.println("Failed to add session: course ID " + courseID + " does not exist.");
            return;
        }

        StudySession s = new StudySession(sessionId, date, startTime, endTime, course, notes);
        boolean success = planner.addSession(s);
        System.out.println(success ? "Session added successfully." : "Failed to add session.");
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
        scanner.nextLine();
        String sessionId = readLine("Enter session ID to delete: ");
        boolean success = planner.removeSession(sessionId);
        System.out.println(success ? "Session deleted successfully." : "Session not found.");
    }

    private static void handleDisplayHighPriorityIncompleteTasks() {
        planner.displayHighPriorityIncompleteTasks();
    }
}