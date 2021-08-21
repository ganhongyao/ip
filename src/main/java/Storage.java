import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private boolean shouldSave;
    private final String filePath;
    private final File file;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
        if (!this.file.isFile()) {
            this.file.getParentFile().mkdirs();
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                System.out.printf("An error occurred when trying to access file %s." +
                        "Changes to your task list will not be saved locally.\n", filePath);
                shouldSave = false;
                return;
            }
        }
        shouldSave = true;
    }

    public List<Task> loadTasks() {
        List<Task> taskList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String nextLine = bufferedReader.readLine();
            while (nextLine != null) {
                try {
                    taskList.add(Task.fromText(nextLine));
                } catch (DukeException e) {
                    System.out.printf("Bad task format found in %s: %s\nSkipping over...\n", filePath, e.getMessage());
                }
                nextLine = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            return taskList;
        } catch (IOException e) {
            System.out.printf("An error occurred when trying to load %s.\n", filePath);
        }
        return taskList;
    }

    public void saveTasks(String tasks) throws DukeException {
        if (!shouldSave) {
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(tasks);
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeException("An error occurred when trying to save tasks locally: " + e.getMessage());
        }
    }
}
