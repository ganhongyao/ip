package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.CommandParser;
import duke.storage.Storage;
import duke.task.TaskManager;
import duke.ui.Ui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Represents the main Duke application.
 */
public class Duke {
    private final Storage storage;
    private TaskManager taskManager;
    private final Ui ui;

    /**
     * Constructor for a Duke object.
     * @param filePath the path to the file where tasks should be stored.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskManager = new TaskManager(storage.loadTasks());
        } catch (DukeException e) {
            ui.print(e.getMessage());
            taskManager = new TaskManager();
        }
    }

    /**
     * Runs the program until termination.
     */
    public void run() {
        ui.greet(taskManager);
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = CommandParser.parse(fullCommand);
                command.execute(taskManager, ui, storage);
                isExit = command.isExit();
            } catch (DukeException e) {
                ui.print(e.getMessage());
            }
        }
    }


    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    public static void main(String[] args) {
        new Duke("./data/tasks.txt").run();
    }
}
