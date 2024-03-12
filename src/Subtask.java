public class Subtask extends Task {
    public int epicId;

    public Subtask (String name, String description, int epicId) {
        this.name = name;
        this.description = description;
        status = Statuses.NEW;
        id = TaskManager.getNewTaskID();
        this.epicId = epicId;
    }

    public boolean checkEpicExists(int epicId) {
       TaskManager.epics.containsKey(epicId);
       if (TaskManager.epics.containsKey(epicId)) {
           return true;
       } else {
           return false;
       }

    }
    private void addSubtaskToEpic (int epicId, int id) {
        Epic epic = TaskManager.epics.get(epicId);
        epic.subtaskList.add(id);

    }
}
