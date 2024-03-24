package issues;

import java.util.Objects;

public class Subtask extends Task {
    public int epicId;

    public Subtask (String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        if (this.id != epicId) {
            this.epicId = epicId;
        }

    }


    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description + "\nID Эпика: " + epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status,epicId);
    }


}
