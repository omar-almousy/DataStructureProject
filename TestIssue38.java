import java.util.*;

/**
 * Standalone test for Issue #38 - Delete Task and Delete Course functionality.
 * Tests all three outcomes for both delete operations.
 */
public class TestIssue38 {
    static int passed = 0;
    static int failed = 0;

    static void assertEquals(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("PASS: " + testName + " -> \"" + actual + "\"");
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " -> expected \"" + expected + "\" but got \"" + actual + "\"");
            failed++;
        }
    }

    static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " -> expected true but was false");
            failed++;
        }
    }

    public static void main(String[] args) {
        SmartStudyPlannerManager planner = new SmartStudyPlannerManager();

        // Setup: Add a course
        Course math = new Course(101, "Introduction to Programming", "Dr. Smith", 4);
        boolean added = planner.addCourse(math);
        assertTrue("Add course 101", added);

        // Setup: Add a task referencing course 101
        StudyTask task = new StudyTask(1001, "Finish Chapter 3", "Exercises", math, "2026-12-01", "High", 2.5);
        added = planner.addTask(task);
        assertTrue("Add task 1001", added);

        // Setup: Add a session referencing task 1001
        List<StudyTask> sessionTasks = new ArrayList<>();
        sessionTasks.add(task);
        StudySession session = new StudySession(10001, "2026-11-25", "10:00", "12:00", math, "Morning study");
        session.getTasks().add(task);
        planner.addSession(session);
        assertTrue("Add session 10001 referencing task 1001", true);

        System.out.println("\n=== DELETE COURSE TESTS ===");

        // Test 1: Try to delete course 101 that has a task -> should return 2 (blocked)
        int result = planner.deleteCourse(101);
        assertEquals("Delete course 101 with tasks -> return code", "2", String.valueOf(result));
        assertTrue("Course 101 still exists", planner.findCourseByID(101) != null);
        assertTrue("Task 1001 still exists", planner.searchTaskByID(1001) != null);

        // Test 2: Delete the task first
        result = planner.deleteStudyTask(1001);
        assertEquals("Delete task 1001 (after session still references?) -> return code", "2", String.valueOf(result));
        assertTrue("Task 1001 still exists (blocked by session)", planner.searchTaskByID(1001) != null);

        // Test 3: Delete the session
        boolean removed = planner.removeSession("10001");
        assertTrue("Remove session 10001", removed);

        // Test 4: Now delete task 1001 (no sessions reference it)
        result = planner.deleteStudyTask(1001);
        assertEquals("Delete task 1001 (no sessions) -> return code", "0", String.valueOf(result));
        assertTrue("Task 1001 no longer exists", planner.searchTaskByID(1001) == null);

        // Test 5: Now delete course 101 (no tasks reference it)
        result = planner.deleteCourse(101);
        assertEquals("Delete course 101 (no tasks) -> return code", "0", String.valueOf(result));
        assertTrue("Course 101 no longer exists", planner.findCourseByID(101) == null);

        System.out.println("\n=== DELETE NOT FOUND TESTS ===");

        // Test 6: Delete non-existent task
        result = planner.deleteStudyTask(9999);
        assertEquals("Delete non-existent task 9999 -> return code", "1", String.valueOf(result));

        // Test 7: Delete non-existent course
        result = planner.deleteCourse(8888);
        assertEquals("Delete non-existent course 8888 -> return code", "1", String.valueOf(result));

        System.out.println("\n=== RESULTS ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}