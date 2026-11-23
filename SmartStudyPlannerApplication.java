
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
        Course math = new Course(101, "Introduction to Programming", "Dr. Smith", 4);
        Course phys = new Course(102, "Physics I", "Prof. Johnson", 3);
        Course chem = new Course(103, "General Chemistry", "Dr. Lee", 3);

        planner.addCourse(math);
        planner.addCourse(phys);
        planner.addCourse(chem);

        System.out.println("Courses added:");
        for (Course c : planner.getAllCourses()) {
            System.out.println("  " + c);
        }

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
        planner.markTaskComplete(4);
        System.out.println("\nMarked task 4 as complete.");

        // ---- Create study sessions ----
        StudySession s1 = new StudySession(1, "2026-11-25", "10:00", "12:00", math, "Morning study block");
        StudySession s2 = new StudySession(2, "2026-11-25", "14:00", "16:00", phys, "Afternoon lab prep");
        StudySession s3 = new StudySession(3, "2026-11-26", "09:00", "11:00", chem, "Review session");

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

        System.out.println("=== Smart Study Planner finished ===");
    }
}
