package duke.parser;

import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.AddToDoCommand;
import duke.command.Command;
import duke.command.CompleteTaskCommand;
import duke.command.DeleteTaskCommand;
import duke.command.ExitCommand;
import duke.command.HelpCommand;
import duke.command.ListCommand;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Parses the command as well as any arguments entered by the user.
     * @param fullCommand the full command entered by the user
     * @return the corresponding <code>Command</code>, or the <code>HelpCommand</code> if the command is not recognised
     */
    public static Command parse(String fullCommand) {
        final String trimmedCommand = fullCommand.trim();
        final String commandWord = trimmedCommand.split("\\s+")[0];
        final String commandArguments = trimmedCommand.replace(commandWord, "").trim();

        switch (commandWord) {
        case ListCommand.COMMAND_WORD:
            return new ListCommand(commandArguments);
        case AddToDoCommand.COMMAND_WORD:
            return new AddToDoCommand(commandArguments);
        case AddDeadlineCommand.COMMAND_WORD:
            return new AddDeadlineCommand(commandArguments);
        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommand(commandArguments);
        case CompleteTaskCommand.COMMAND_WORD:
            return new CompleteTaskCommand(commandArguments);
        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommand(commandArguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        default:
            return new HelpCommand();
        }
    }
}
