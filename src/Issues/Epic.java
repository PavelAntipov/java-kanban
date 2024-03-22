package Issues;

import Issues.Task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskList;
    public Epic(String name, String description) {
        super(name,description);
        ArrayList<Integer> subtaskList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskList() {
        return subtaskList;
    }


    public void setSubtaskList(ArrayList<Integer> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public void ammendSubtaskList(int issId) {
        subtaskList.add(issId);
    }

    public void deleteSubtaskFromList(int issId) {
        subtaskList.remove(issId);
    }
    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description + "\nСписок подзадач: " + subtaskList;
    }


}

