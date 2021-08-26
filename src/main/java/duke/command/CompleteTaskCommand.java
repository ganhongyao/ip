package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskManager;
import duke.ui.Ui;

/**
 * Represents a command for marking a <code>Task</code> as completed.
 */
public class CompleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "done";
    public static final String USAGE_MESSAGE = "To mark a task as done, use 'done <task-number>'.";

    private final String commandArguments;

    public CompleteTaskCommand(String commandArguments) {
        this.commandArguments = commandArguments;
    }

    @Override
    public void execute(TaskManager taskManager, Ui ui, Storage storage) throws DukeException {
        if (commandArguments.isEmpty()) {
            throw new DukeException("Invalid use of the 'done' command.\n\n" + USAGE_MESSAGE);
        }
        try {
            int taskNumber = Integer.parseInt(commandArguments);
            ui.print(taskManager.markTaskAsDone(taskNumber));
            storage.saveTasks(taskManager);
        } catch (NumberFormatException e) {
            // User provided an argument that is not parsable.
            throw new DukeException("Invalid task number.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
