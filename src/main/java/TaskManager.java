import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    // Success Messages
    private static final String TASKS_COUNT_MESSAGE = "Now you have %d %s in the list.";
    private static final String UNDONE_TASKS_COUNT_MESSAGE = "You have %d incomplete %s remaining.";
    private static final String TASK_ADDED_MESSAGE = "Got it. I've added this task:\n  %s\n\n" + TASKS_COUNT_MESSAGE;
    private static final String MARKED_TASK_AS_DONE_MESSAGE = "Nice! I've marked this task as done:\n  %s\n\n" +
            UNDONE_TASKS_COUNT_MESSAGE;
    private static final String DELETED_TASK_MESSAGE = "Noted. I've removed this task:\n  %s\n\n" + TASKS_COUNT_MESSAGE;

    // Error Messages
    private static final String TASK_NOT_FOUND_MESSAGE =
            "You don't have a task with that number.";

    private final List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public String addTask(Task task) {
        taskList.add(task);
        int taskCount = getTaskCount();
        String pluralised = taskCount > 1 ? "tasks" : "task";
        return String.format(TASK_ADDED_MESSAGE, task, taskCount, pluralised);
    }

    public String markTaskAsDone(int taskNumber) throws DukeException {
        try {
            // User input is 1-indexed
            int taskIndex = taskNumber - 1;
            Task task = taskList.get(taskIndex);
            task.markAsDone();
            int undoneTaskCount = getUndoneTaskCount();
            String pluralised = undoneTaskCount > 1 || undoneTaskCount == 0 ? "tasks" : "task";
            return String.format(MARKED_TASK_AS_DONE_MESSAGE, task, undoneTaskCount, pluralised);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(TASK_NOT_FOUND_MESSAGE);
        }
    }

    public String deleteTask(int taskNumber) throws DukeException {
        try {
            // User input is 1-indexed
            int taskIndex = taskNumber - 1;
            Task task = taskList.remove(taskIndex);
            int taskCount = getTaskCount();
            String pluralised = taskCount > 1 || taskCount == 0 ? "tasks" : "task";
            return String.format(DELETED_TASK_MESSAGE, task, taskCount, pluralised);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(TASK_NOT_FOUND_MESSAGE);
        }
    }

    private int getTaskCount() {
        return taskList.size();
    }

    private int getUndoneTaskCount() {
        int count = 0;
        for (Task t : taskList) {
            if (!t.checkTaskDone()) {
                count++;
            }
        }
        return count;
    }

    private String toText() {
        String[] tasks = new String[taskList.size()];
        for (int i = 0; i < taskList.size(); i++) {
            tasks[i] = taskList.get(i).toText();
        }
        return String.join("\n", tasks);
    }

    @Override
    public String toString() {
        String[] allTasks = new String[taskList.size()];
        for (int i = 0; i < allTasks.length; i++) {
            Task task = taskList.get(i);
            // Display numbers are 1-indexed
            int taskNumber = i + 1;
            allTasks[i] = String.format("%d. %s", taskNumber, task.toString());
        }
        return String.join("\n", allTasks);
    }
}
