package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskManager;
import duke.ui.Ui;
import duke.util.DukeDateTime;

/**
 * Represents a command for listing tasks.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String USAGE_MESSAGE =
            "To list all tasks, use 'list'.\nTo list all tasks on a certain date, use 'list <date>'.";

    private final String commandArguments;

    public ListCommand(String commandArguments) {
        this.commandArguments = commandArguments;
    }

    @Override
    public void execute(TaskManager taskManager, Ui ui, Storage storage) throws DukeException {
        if (commandArguments.isEmpty()) {
            ui.print(taskManager.list());
        } else {
            DukeDateTime date = DukeDateTime.parseUserInputDate(commandArguments);
            ui.print(taskManager.list(date));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
