import java.util.Scanner;

public class Duke {
    // User-facing error messages
    private static final String INVALID_COMMAND_MESSAGE = "Invalid command.";
    private static final String INVALID_TASK_NUMBER_MESSAGE = "Invalid task number.";

    private final Scanner scanner = new Scanner(System.in);
    private final Storage storage;
    private TaskManager taskManager;
    private final Ui ui;

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

    public void run() {
        ui.greet(taskManager);
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine().trim();
            String firstWord = inputLine.split("\\s+")[0];
            try {
                Command command;
                try {
                    command = Command.valueOf(firstWord.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new DukeException(INVALID_COMMAND_MESSAGE);
                }
                String inputLineWithoutCommand = inputLine.replace(firstWord, "").trim();
                command.validateArguments(inputLineWithoutCommand);
                switch (command) {
                case LIST:
                    if (inputLineWithoutCommand.isEmpty()) {
                        ui.print(taskManager.list());
                    } else {
                        DukeDateTime date = DukeDateTime.parseUserInputDate(inputLineWithoutCommand);
                        ui.print(taskManager.list(date));
                    }
                    break;
                case TODO:
                    String toDoName = inputLineWithoutCommand;
                    ToDo toDo = new ToDo(toDoName);
                    ui.print(taskManager.addTask(toDo));
                    storage.saveTasks(taskManager.toText());
                    break;
                case DEADLINE:
                    String[] deadlineDetails = inputLineWithoutCommand.split("\\s+/by\\s+", 2);
                    String deadlineName = deadlineDetails[0];
                    DukeDateTime deadlineDueDate=  DukeDateTime.parseUserInputDateTime(deadlineDetails[1]);
                    Deadline deadline = new Deadline(deadlineName, deadlineDueDate);
                    ui.print(taskManager.addTask(deadline));
                    storage.saveTasks(taskManager.toText());
                    break;
                case EVENT:
                    String[] eventDetails = inputLineWithoutCommand.split("\\s+/at\\s+", 2);
                    String eventName = eventDetails[0];
                    DukeDateTime eventTimestamp = DukeDateTime.parseUserInputDateTime(eventDetails[1]);
                    Event event = new Event(eventName, eventTimestamp);
                    ui.print(taskManager.addTask(event));
                    storage.saveTasks(taskManager.toText());
                    break;
                case DONE:
                    try {
                        int taskNumber = Integer.parseInt(inputLineWithoutCommand);
                        ui.print(taskManager.markTaskAsDone(taskNumber));
                        storage.saveTasks(taskManager.toText());
                    } catch (NumberFormatException e) {
                        // User provided an argument that is not parsable.
                        throw new DukeException(INVALID_TASK_NUMBER_MESSAGE);
                    }
                    break;
                case DELETE:
                    try {
                        int taskNumber = Integer.parseInt(inputLineWithoutCommand);
                        ui.print(taskManager.deleteTask(taskNumber));
                        storage.saveTasks(taskManager.toText());
                    } catch (NumberFormatException e) {
                        // User provided an argument that is not parsable.
                        throw new DukeException(INVALID_TASK_NUMBER_MESSAGE);
                    }
                    break;
                case BYE:
                    ui.farewell();
                    scanner.close();
                    return;
                }
            } catch (DukeException e) {
                ui.print(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Duke("./data/tasks.txt").run();
    }
}
