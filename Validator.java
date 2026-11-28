/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

import java.util.regex.Pattern;

/* Every field rule and its error message, in one place. The app checks user input with these; the manager checks only duplicates, existence and delete rules. */
public final class Validator {

    // ----- Messages shown to the user when a rule fails -----

    /* Message for an invalid five-digit course ID. */
    public static final String MSG_COURSE_ID = "Invalid course ID: must be exactly 5 digits (10000-99999).";
    /* Message for an invalid six-digit task ID. */
    public static final String MSG_TASK_ID = "Invalid task ID: must be exactly 6 digits (100000-999999).";
    /* Message for an invalid six-digit session ID. */
    public static final String MSG_SESSION_ID = "Invalid session ID: must be exactly 6 digits (100000-999999).";
    /* Message for credit hours outside 1-6. */
    public static final String MSG_CREDIT_HOURS = "Invalid credit hours: must be a whole number between 1 and 6.";
    /* Message for a priority other than 1, 2 or 3. */
    public static final String MSG_PRIORITY = "Invalid priority: must be 1 (High), 2 (Medium), or 3 (Low).";
    /* Message for a status outside the whitelist. */
    public static final String MSG_STATUS = "Invalid status: must be Pending, In Progress, or Completed.";
    /* Message for a date that is not a real DD/MM/YYYY date. */
    public static final String MSG_DATE = "Invalid date: must be a real date in DD/MM/YYYY format (e.g. 05/03/2026).";
    /* Message for a number that is not strictly positive. */
    public static final String MSG_POSITIVE = "must be a number greater than 0.";
    /* Message suffix for an empty text field. */
    public static final String MSG_NON_EMPTY = "cannot be empty.";

    // ----- Rule constants -----

    /* Smallest and largest five-digit IDs (course IDs). */
    private static final int FIVE_DIGIT_MIN = 10000;
    private static final int FIVE_DIGIT_MAX = 99999;

    /* Smallest and largest six-digit IDs (task and session IDs). */
    private static final int SIX_DIGIT_MIN = 100000;
    private static final int SIX_DIGIT_MAX = 999999;

    /* Allowed credit-hour range. */
    private static final int MIN_CREDIT_HOURS = 1;
    private static final int MAX_CREDIT_HOURS = 6;

    /* Allowed priorities: 1 = High, 2 = Medium, 3 = Low. */
    private static final int MIN_PRIORITY = 1;
    private static final int MAX_PRIORITY = 3;

    /* The only status values accepted anywhere in the application (case-sensitive). */
    private static final String[] STATUS_WHITELIST = {"Pending", "In Progress", "Completed"};

    /* Exact DD/MM/YYYY shape: two-digit day, two-digit month, four-digit year. */
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    /* Private constructor to prevent instantiation of a utility class. */
    private Validator() {
    }

    // ----- ID rules -----

    /* Checks that a value is a five-digit ID (10000-99999). Returns true if valid */
    public static boolean isFiveDigitID(int id) {
        return id >= FIVE_DIGIT_MIN && id <= FIVE_DIGIT_MAX;
    }

    /* Checks that a value is a six-digit ID (100000-999999). Returns true if valid */
    public static boolean isSixDigitID(int id) {
        return id >= SIX_DIGIT_MIN && id <= SIX_DIGIT_MAX;
    }

    /* Validates a course ID (five digits). Returns true if valid */
    public static boolean isValidCourseID(int id) {
        return isFiveDigitID(id);
    }

    /* Validates a task ID (six digits). Returns true if valid */
    public static boolean isValidTaskID(int id) {
        return isSixDigitID(id);
    }

    /* Validates a session ID (six digits). Returns true if valid */
    public static boolean isValidSessionID(int id) {
        return isSixDigitID(id);
    }

    // ----- Range and whitelist rules -----

    /* Validates that credit hours are between 1 and 6 inclusive. Returns true if valid */
    public static boolean isValidCreditHours(int ch) {
        return ch >= MIN_CREDIT_HOURS && ch <= MAX_CREDIT_HOURS;
    }

    /* Validates that priority is 1, 2 or 3. Returns true if valid */
    public static boolean isValidPriority(int p) {
        return p >= MIN_PRIORITY && p <= MAX_PRIORITY;
    }

    /* Validates that a status is exactly one of: "Pending", "In Progress", "Completed". Returns true if valid */
    public static boolean isValidStatus(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < STATUS_WHITELIST.length; i++) {
            if (STATUS_WHITELIST[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    /* Validates that a number is strictly positive (rejects 0, negatives and NaN). Returns true if value is greater than 0 */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /* Validates a duration or estimate in hours (must be positive). Returns true if valid */
    public static boolean isValidDuration(double hours) {
        return isPositive(hours);
    }

    /* Validates that a string is non-null and not blank after trimming. Returns true if valid */
    public static boolean isNonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // ----- Date rule -----

    /* Checks a real DD/MM/YYYY date. Rejects 1/1/2026, 31/13/2026, 31/04/2026 and 29/02/2025. */
    public static boolean isValidDate(String d) {
        if (d == null || !DATE_PATTERN.matcher(d).matches()) {
            return false;
        }
        String[] parts = d.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12 || year < 1) {
            return false;
        }
        return day >= 1 && day <= daysInMonth(month, year);
    }

    /* Validates a task deadline (same rule as .isValidDate(String)). Returns true if valid */
    public static boolean isValidDeadline(String d) {
        return isValidDate(d);
    }

    /* Returns the number of days in the given month, accounting for leap years. Returns the number of days in that month */
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

    /* Converts a valid DD/MM/YYYY date into a sortable integer YYYYMMDD. Returns the date as YYYYMMDD, or Integer.MAX_VALUE if the date is invalid */
    public static int dateSortKey(String d) {
        if (!isValidDate(d)) {
            return Integer.MAX_VALUE;
        }
        String[] parts = d.split("/");
        return Integer.parseInt(parts[2]) * 10000
                + Integer.parseInt(parts[1]) * 100
                + Integer.parseInt(parts[0]);
    }
}
