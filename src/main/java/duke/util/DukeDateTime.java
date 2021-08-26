package duke.util;

import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a wrapper around a <code>LocalDateTime</code>.
 */
public class DukeDateTime {
    private final static DateTimeFormatter USER_INPUT_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private final static DateTimeFormatter USER_INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh.mma");

    private final LocalDateTime dateTime;

    public DukeDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DukeDateTime(LocalDate date) {
        this.dateTime = date.atStartOfDay();
    }

    /**
     * Parses datetime string entered by the user.
     * @param str the string entered by the user
     * @return the corresponding <code>DukeDateTime</code> object
     * @throws DukeException if the datetime cannot be parsed with the given format
     */
    public static DukeDateTime parseUserInputDateTime(String str) throws DukeException {
        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(str, USER_INPUT_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DukeException("Unable to parse datetime. Please use the `YYYY-MM-DD HHMM` format " +
                    "(e.g. 2021-08-24 2130).");
        }
        return new DukeDateTime(dt);
    }

    /**
     * Parses date string entered by the user.
     * @param str the string entered by the user
     * @return the corresponding <code>DukeDateTime</code> object
     * @throws DukeException if the date cannot be parsed with the given format
     */
    public static DukeDateTime parseUserInputDate(String str) throws DukeException {
        LocalDate dt;
        try {
            dt = LocalDate.parse(str, USER_INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DukeException("Unable to parse date. Please use the `YYYY-MM-DD` format " +
                    "(e.g. 2021-08-24).");
        }
        return new DukeDateTime(dt);
    }

    /**
     * Parses a datetime string in ISO format.
     * @param str the datetime string
     * @return the corresponding <code>DukeDateTime</code> object
     * @throws DukeException if the date cannot be parsed with the ISO format
     */
    public static DukeDateTime parseISO(String str) throws DukeException {
        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new DukeException("Unable to parse datetime. Please use the ISO datetime format.");
        }
        return new DukeDateTime(dt);
    }

    /**
     * Returns true if the two <code>DukeDateTime</code> objects fall on the same date.
     */
    public static boolean onSameDay(DukeDateTime d1, DukeDateTime d2) {
        return d1.dateTime.toLocalDate().equals(d2.dateTime.toLocalDate());
    }

    /**
     * Returns the datetime formatted in ISO.
     */
    public String toISO() {
        return this.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String toString() {
        return this.dateTime.format(DISPLAY_FORMATTER);
    }
}
