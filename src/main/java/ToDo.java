public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    public ToDo(String name, boolean isDone) {
        super(name, isDone);
    }

    public static ToDo fromText(String text) throws DukeException {
        String[] toDoDetails = text.split(" | ", 3);
        if (toDoDetails.length < 3) {
            throw new DukeException("Cannot parse ToDo from text - not enough arguments supplied.");
        }
        boolean isDone = toDoDetails[1] == "X";
        String name = toDoDetails[2];
        return new ToDo(name, isDone);
    }

    @Override
    public String toText() {
        String[] props = new String[]{"T", super.getStatusIcon(), super.getName()};
        return String.join(" | ", props);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
