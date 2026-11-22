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

import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Smart Study Planner.
 * Demonstrates the planner by creating sample courses, tasks, and sessions,
 * then running the schedule generator and printing results.
 */
public class SmartStudyPlannerApplication {

    public static void main(String[] args) {
        SmartStudyPlannerManager planner = new SmartStudyPlannerManager();

        System.out.println("=== Smart Study Planner ===\n");

        // ---- Create courses ----
        Course math = new Course("CS101", "Introduction to Programming", 4, "Dr. Smith");
        Course phys = new Course("PHYS201", "Physics I", 3, "Prof. Johnson");
        Course chem = new Course("CHEM101", "General Chemistry", 3, "Dr. Lee");

        planner.addCourse(math);
        planner.addCourse(phys);
        planner.addCourse(chem);

        System.out.println("Courses added:");
        for (Course c : planner.getAllCourses()) {
            System.out.println("  " + c);
        }

        // ---- Create study tasks ----
        StudyTask t1 = new StudyTask("T001", "Finish Chapter 3 exercises",
                "Complete exercises 3.1 through 3.10", math, "2026-12-01", "High", 2.5);
        StudyTask t2 = new StudyTask("T002", "Lab report",
                "Write and submit physics lab report", phys, "2026-12-02", "High", 3.0);
        StudyTask t3 = new StudyTask("T003", "Review periodic table",
                "Memorize elements and groups", chem, "2026-12-05", "Medium", 1.5);
        StudyTask t4 = new StudyTask("T004", "Debug assignment",
                "Fix bugs in sorting algorithm", math, "2026-11-30", "High", 1.0);
        StudyTask t5 = new StudyTask("T005", "Read chapter 5",
                "Read and summarize chemistry chapter 5", chem, "2026-12-07", "Low", 2.0);

        planner.addTask(t1);
        planner.addTask(t2);
        planner.addTask(t3);
        planner.addTask(t4);
        planner.addTask(t5);

        System.out.println("\nTasks added:");
        for (StudyTask t : planner.getAllTasks()) {
            System.out.println("  " + t);
        }

        // ---- Mark one task complete ----
        planner.markTaskComplete("T004");
        System.out.println("\nMarked T004 as complete.");

        // ---- Create study sessions ----
        StudySession s1 = new StudySession("S001", "2026-11-25", "10:00", "12:00", math, "Morning study block");
        StudySession s2 = new StudySession("S002", "2026-11-25", "14:00", "16:00", phys, "Afternoon lab prep");
        StudySession s3 = new StudySession("S003", "2026-11-26", "09:00", "11:00", chem, "Review session");

        planner.addSession(s1);
        planner.addSession(s2);
        planner.addSession(s3);

        System.out.println("\nSessions added:");
        for (StudySession s : planner.getAllSessions()) {
            System.out.println("  " + s);
        }

        // ---- Generate schedule ----
        System.out.println("\n=== Generated Schedule ===");
        List<StudySession> schedule = planner.generateSchedule(planner.getAllSessions());
        for (StudySession sess : schedule) {
            System.out.println("  " + sess);
        }

        // ---- Print summary ----
        System.out.println(planner.getSummary());

        // ---- Interactive menu (Options 11 & 12) ----
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Smart Study Planner Menu ===");
            System.out.println("0. Exit");
            System.out.println("11. Check task sessions");
            System.out.println("12. Show completion percentage");
            System.out.print("Select an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                case "11": {
                    // Option 11: prompt taskID; distinguish "task not found" from "no sessions recorded"
                    System.out.print("Enter task ID: ");
                    String taskId = sc.nextLine();
                    StudyTask task = planner.getTaskById(taskId);
                    if (task == null) {
                        System.out.println("Task not found.");
                    } else if (!planner.hasSessionsForTask(taskId)) {
                        System.out.println("No sessions recorded for this task.");
                    } else {
                        System.out.println("Sessions recorded for task '" + task.getTitle()
                                + "' (ID: " + taskId + ").");
                    }
                    break;
                }

                case "12": {
                    // Option 12: call calculateCompletionPercentage(); handle no tasks; format to 2 decimals
                    double pct = planner.calculateCompletionPercentage();
                    if (pct < 0) {
                        System.out.println("No tasks exist.");
                    } else {
                        System.out.printf("Completion percentage: %.2f%%%n", pct);
                    }
                    break;
                }

                default:
                    System.out.println("Invalid option.");
            }
        }
        sc.close();
    }
}