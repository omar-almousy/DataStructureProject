/*
 * Course: ITCS214
 * Section: 7
 * Project: Smart Study Planner
 * Group Members:
 *   Omar - 202400777
 *   AbdulNasser ali mohameed - 202302839
 *   oakba - 202504174
 */

import java.util.Locale;

/**
 * Single place for how numbers are shown to the user.
 * Every percentage and every decimal number (hours, durations) printed by the
 * application goes through here, so they all use the same format:
 * exactly 2 decimal places, a dot as the decimal separator on every computer,
 * and no currency symbol or thousands separator.
 */
public final class OutputFormat {

    /** Fixed locale so the decimal separator is always "." (never ","). */
    private static final Locale DISPLAY_LOCALE = Locale.ROOT;

    /** Private constructor to prevent instantiation of a utility class. */
    private OutputFormat() {
    }

    /**
     * Formats a percentage with exactly 2 decimals and a % sign.
     * Examples: 0 becomes "0.00%", 66.666 becomes "66.67%", 100 becomes "100.00%".
     *
     * @param percent the percentage value (0-100)
     * @return the formatted percentage, or "N/A" if the value is negative or NaN
     *         (used by callers as the "no data" sentinel)
     */
    public static String percentage(double percent) {
        if (Double.isNaN(percent) || percent < 0) {
            return "N/A";
        }
        return String.format(DISPLAY_LOCALE, "%.2f%%", percent);
    }

    /**
     * Computes and formats part / whole as a percentage with 2 decimals.
     *
     * @param part the count of matching items
     * @param whole the total count
     * @return the formatted percentage, or "N/A" when whole is 0 (no division by zero)
     */
    public static String percentage(int part, int whole) {
        if (whole <= 0) {
            return "N/A";
        }
        return percentage(part * 100.0 / whole);
    }

    /**
     * Formats a plain decimal number with exactly 2 decimals and no currency
     * symbol or grouping (e.g. 1.5 becomes "1.50", 1234.5 becomes "1234.50").
     *
     * @param value the number
     * @return the formatted number
     */
    public static String number(double value) {
        return String.format(DISPLAY_LOCALE, "%.2f", value);
    }
}
