package duke.task;

import duke.util.DukeDateTime;
import duke.exception.DukeException;

/**
 * Represents a <code>Task</code> that occurs on a specific date and time.
 */
public class Event extends Task implements Timestampable {
    private final DukeDateTime timestamp;

    public Event(String name, DukeDateTime timestamp) {
        super(name);
        this.timestamp = timestamp;
    }

    public Event(String name, boolean isDone, DukeDateTime timestamp) {
        super(name, isDone);
        this.timestamp = timestamp;
    }

    @Override
    public String toText() {
        String[] props = new String[]{"E", super.getStatusIcon(), super.getName(), timestamp.toISO()};
        return String.join(" | ", props);
    }

    @Override
    public boolean onSameDayAs(DukeDateTime date) {
        return DukeDateTime.onSameDay(timestamp, date);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (at: %s)", super.toString(), timestamp);
    }
}
