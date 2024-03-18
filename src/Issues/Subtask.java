package Issues;

import Issues.Task;

public class Subtask extends Task {
    public int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Subtask (String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description + "\nID Эпика: " + epicId;
    }




}