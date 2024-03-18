package Issues;

import Issues.Task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskList;


    public ArrayList<Integer> getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(ArrayList<Integer> subtaskList) {
        this.subtaskList = subtaskList;
    }



    public Epic(String name, String description) {
        super(name,description);
        ArrayList<Integer> subtaskList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description + "\nСписок подзадач: " + subtaskList;
    }


}

