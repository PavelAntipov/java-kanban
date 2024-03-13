import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> subtaskList;


    public Epic(String name, String description) {
        this.name = name;
        this.description = description;
        status = Statuses.NEW;
        id = TaskManager.getNewTaskID();
        subtaskList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description + "\nСписок подзадач: " + subtaskList;
    }


}

