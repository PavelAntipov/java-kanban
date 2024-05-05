package issues;

import java.util.ArrayList;
import java.util.Objects;


public class Epic extends Task {
    private ArrayList<Integer> subtaskList;
    public Epic(String name, String description) {
        super(name,description);
        this.subtaskList = new ArrayList<>();

    }

    public ArrayList<Integer> getSubtaskList() {
        return subtaskList;
    }


    public void setSubtaskList(ArrayList<Integer> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public void ammendSubtaskList(int issId) {
        if (this.id != issId) {
            subtaskList.add(issId);
        }
    }

    public void deleteSubtaskFromList(int issId) {
        subtaskList.remove(issId);
    }
    @Override
    public String toString() {
        return "id: " + id + " Название: " + name + " Статус: " + status +
                " Описание: " + description + " Список подзадач: " + subtaskList + "\n";
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status,subtaskList);
    }


}

