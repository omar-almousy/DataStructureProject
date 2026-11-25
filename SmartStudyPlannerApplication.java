
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

    /**
     * Main entry point for the Smart Study Planner application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SmartStudyPlannerManager planner = new SmartStudyPlannerManager();

        System.out.println("=== Smart Study Planner ===\n");

        // ---- Create courses ----
        Course math = new Course(101, "Introduction to Programming", "Dr. Smith", 4);
        Course phys = new Course(102, "Physics I", "Prof. Johnson", 3);
        Course chem = new Course(103, "General Chemistry", "Dr. Lee", 3);

        System.out.println("Adding courses:");
        System.out.println("  Add CS101 (101): " + planner.addCourse(math));
        System.out.println("  Add PHYS201 (102): " + planner.addCourse(phys));
        System.out.println("  Add CHEM101 (103): " + planner.addCourse(chem));

        System.out.println("Courses added:");
        List<Course> allCourses = planner.getAllCourses();
        for (int i = 0; i < allCourses.size(); i++) {
            System.out.println("  " + allCourses.get(i));
        }

        // Test duplicate courseID rejection
        Course mathDup = new Course(101, "Duplicate Programming", "Dr. Smith", 4);
        System.out.println("  Add duplicate (101): " + planner.addCourse(mathDup));

        System.out.println("\n=== All Courses ===");
        planner.displayAllCourses();

        // ---- Create study tasks ----
        StudyTask t1 = new StudyTask(1, "Finish Chapter 3 exercises",
                "Complete exercises 3.1 through 3.10", math, "2026-12-01", "High", 2.5);
        StudyTask t2 = new StudyTask(2, "Lab report",
                "Write and submit physics lab report", phys, "2026-12-02", "High", 3.0);
        StudyTask t3 = new StudyTask(3, "Review periodic table",
                "Memorize elements and groups", chem, "2026-12-05", "Medium", 1.5);
        StudyTask t4 = new StudyTask(4, "Debug assignment",
                "Fix bugs in sorting algorithm", math, "2026-11-30", "High", 1.0);
        StudyTask t5 = new StudyTask(5, "Read chapter 5",
                "Read and summarize chemistry chapter 5", chem, "2026-12-07", "Low", 2.0);

        System.out.println("Adding tasks:");
        System.out.println("  Add task 1: " + planner.addTask(t1));
        System.out.println("  Add task 2: " + planner.addTask(t2));
        System.out.println("  Add task 3: " + planner.addTask(t3));
        System.out.println("  Add task 4: " + planner.addTask(t4));
        System.out.println("  Add task 5: " + planner.addTask(t5));

        System.out.println("\nTasks added:");
        List<StudyTask> allTasks = planner.getAllTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            System.out.println("  " + allTasks.get(i));
        }

        // Test duplicate taskID rejection
        StudyTask tDup = new StudyTask(1, "Duplicate task", "Should be rejected",
                math, "2026-12-10", "Low", 1.0);
        System.out.println("  Add duplicate taskID (1): " + planner.addTask(tDup));

        // Test task with non-existent course ID
        Course noCourse = new Course(999, "Nonexistent", "N/A", 1);
        StudyTask tBadCourse = new StudyTask(6, "Bad course task", "Should be rejected",
                noCourse, "2026-12-10", "Low", 1.0);
        System.out.println("  Add task with bad courseID (999): " + planner.addTask(tBadCourse));

        System.out.println("\n=== All Tasks ===");
        planner.displayAllStudyTasks();

        // ---- Search task by ID ----
        System.out.println("\n=== Search Task by ID ===");
        StudyTask found = planner.searchTaskByID(3);
        System.out.println("  Search task 3: " + (found != null ? found.getTitle() : "not found"));
        StudyTask notFound = planner.searchTaskByID(99);
        System.out.println("  Search task 99: " + (notFound != null ? notFound.getTitle() : "not found"));

        // ---- Update task status ----
        System.out.println("\n=== Update Task Status ===");
        System.out.println("  Update task 2 to 'In Progress': " + planner.updateTaskStatus(2, "In Progress"));
        System.out.println("  Update task 99 to 'Completed' (unknown ID): " + planner.updateTaskStatus(99, "Completed"));
        System.out.println("  Update task 1 to 'InvalidStatus' (invalid): " + planner.updateTaskStatus(1, "InvalidStatus"));
        System.out.println("  Update task 4 to 'Completed': " + planner.updateTaskStatus(4, "Completed"));

        // ---- Mark one task complete ----
        planner.markTaskComplete(4);
        System.out.println("\nMarked task 4 as complete.");

        // ---- Display high-priority incomplete tasks ----
        System.out.println("\n=== High-Priority Incomplete Tasks ===");
        planner.displayHighPriorityIncompleteTasks();

        // ---- Create study sessions ----
        StudySession s1 = new StudySession(1, "2026-11-25", "10:00", "12:00", math, "Morning study block");
        StudySession s2 = new StudySession(2, "2026-11-25", "14:00", "16:00", phys, "Afternoon lab prep");
        StudySession s3 = new StudySession(3, "2026-11-26", "09:00", "11:00", chem, "Review session");

        planner.addSession(s1);
        planner.addSession(s2);
        planner.addSession(s3);

        System.out.println("\nSessions added:");
        List<StudySession> allSessions = planner.getAllSessions();
        for (int i = 0; i < allSessions.size(); i++) {
            System.out.println("  " + allSessions.get(i));
        }

        // ---- Generate schedule ----
        System.out.println("\n=== Generated Schedule ===");
        List<StudySession> schedule = planner.generateSchedule(planner.getAllSessions());

        for (int i = 0; i < schedule.size(); i++) {
            System.out.println("  " + schedule.get(i));
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
