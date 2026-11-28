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

/* Formats every number shown to the user: always 2 decimals, a dot separator and no currency symbol. */
public final class OutputFormat {

    /* Fixed locale so the decimal separator is always "." (never ","). */
    private static final Locale DISPLAY_LOCALE = Locale.ROOT;

    /* Private constructor to prevent instantiation of a utility class. */
    private OutputFormat() {
    }

    /* Formats a percentage with 2 decimals, e.g. 66.67%. Returns N/A for negative or NaN values. */
    public static String percentage(double percent) {
        if (Double.isNaN(percent) || percent < 0) {
            return "N/A";
        }
        return String.format(DISPLAY_LOCALE, "%.2f%%", percent);
    }

    /* Computes and formats part / whole as a percentage with 2 decimals. Returns the formatted percentage, or "N/A" when whole is 0 (no division by zero) */
    public static String percentage(int part, int whole) {
        if (whole <= 0) {
            return "N/A";
        }
        return percentage(part * 100.0 / whole);
    }

    /* Formats a number with 2 decimals and no currency symbol, e.g. 1.50. */
    public static String number(double value) {
        return String.format(DISPLAY_LOCALE, "%.2f", value);
    }
}
