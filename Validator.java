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

/**
 * Centralized validation helpers for the Smart Study Planner.
 * All field validation logic lives here to ensure consistency across the application.
 */
public class Validator {

    /** Private constructor to prevent instantiation of a utility class. */
    private Validator() {
    }

    /**
     * Validates that a course ID is exactly 5 digits (10000-99999).
     *
     * @param id the course ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCourseID(int id) {
        return id >= 10000 && id <= 99999;
    }

    /**
     * Validates that a task ID is exactly 6 digits (100000-999999).
     *
     * @param id the task ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidTaskID(int id) {
        return id >= 100000 && id <= 999999;
    }

    /**
     * Validates that a session ID is exactly 6 digits (100000-999999).
     *
     * @param id the session ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidSessionID(int id) {
        return id >= 100000 && id <= 999999;
    }

    /**
     * Validates that credit hours are between 1 and 6 inclusive.
     *
     * @param ch the credit hours to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCreditHours(int ch) {
        return ch >= 1 && ch <= 6;
    }

    /**
     * Validates that priority is 1, 2, or 3.
     *
     * @param p the priority to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPriority(int p) {
        return p == 1 || p == 2 || p == 3;
    }

    /**
     * Validates that status is one of: "Pending", "In Progress", "Completed".
     *
     * @param s the status string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidStatus(String s) {
        return "Pending".equals(s) || "In Progress".equals(s) || "Completed".equals(s);
    }

    /**
     * Validates that a date string matches DD/MM/YYYY format and is a real
     * calendar date (e.g. 31/04 and 29/02 in a non-leap year are rejected).
     *
     * @param d the date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDeadline(String d) {
        if (d == null || !d.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        String[] parts = d.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12 || year < 1000) {
            return false;
        }
        return day >= 1 && day <= daysInMonth(month, year);
    }

    /**
     * Returns the number of days in the given month, accounting for leap years.
     *
     * @param month the month (1-12)
     * @param year the year
     * @return the number of days in that month
     */
    private static int daysInMonth(int month, int year) {
        switch (month) {
            case 2:
                boolean leap = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
                return leap ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Converts a valid DD/MM/YYYY date into a sortable integer YYYYMMDD.
     *
     * @param d a date already validated with isValidDeadline
     * @return the date as YYYYMMDD, or Integer.MAX_VALUE if the date is invalid
     */
    public static int deadlineSortKey(String d) {
        if (!isValidDeadline(d)) {
            return Integer.MAX_VALUE;
        }
        String[] parts = d.split("/");
        return Integer.parseInt(parts[2]) * 10000
                + Integer.parseInt(parts[1]) * 100
                + Integer.parseInt(parts[0]);
    }

    /**
     * Validates that a string is non-null and non-empty after trimming.
     *
     * @param s the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * Validates that duration hours is positive.
     *
     * @param hours the duration to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDuration(double hours) {
        return hours > 0;
    }
}