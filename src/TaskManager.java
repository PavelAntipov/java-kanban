import Issues.Epic;
import Issues.Statuses;
import Issues.Subtask;
import Issues.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private int idCounter = 1;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();

    //Метод для получения ID новой задачи
    public int getNewTaskID() {
        idCounter++;
        return idCounter;
    }

    //Создание задач по типам
    public void taskCreate(Task task) {
        int taskId = getNewTaskID();
        task.setId(taskId);
        tasks.put(taskId, task);
    }

    public void subtaskCreate(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            return;
        } else {
            int subtaskId = getNewTaskID();
            subtask.setId(subtaskId);
            subtasks.put(subtaskId, subtask);
            Epic epic = epics.get(epicId);
            ArrayList<Integer> subtaskList = epic.getSubtaskList();
            subtaskList.add(subtaskId);
            epic.setSubtaskList(subtaskList);
            epics.put(epicId, epic);
        }
    }

    public void epicCreate(Epic epic) {
        int epicId = getNewTaskID();
        epics.put(epicId, epic);
    }


    //Обновление информации о задаче
    public void taskUpdate(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        int issId = task.getId();
        tasks.put(issId, task);
    }

    public void subtaskUpdate(Subtask subtask) {
        int issId = subtask.getId();
        if (!subtasks.containsKey(subtask.getId()) || !epics.containsKey(subtask.getEpicId())) {
            return;
        }

        Subtask oldSubtask = subtasks.get(issId);
        if (subtask.getEpicId() != oldSubtask.getEpicId()) {
            Epic oldEpic = epics.get(oldSubtask.getEpicId());
            ArrayList<Integer> oldEpicSList = oldEpic.getSubtaskList();
            oldEpicSList.remove(issId);
            oldEpic.setSubtaskList(oldEpicSList);
            epics.put(oldSubtask.getEpicId(), oldEpic);
            checkEpicStatus(oldSubtask.getEpicId());
            Epic newEpic = epics.get(subtask.getEpicId());
            ArrayList<Integer> newEpicSList = newEpic.getSubtaskList();
            newEpicSList.add(issId);
            newEpic.setSubtaskList(newEpicSList);
            epics.put(subtask.getEpicId(), newEpic);

        }
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(subtask.getId());


    }

    public void epicUpdate(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }
        epics.put(epic.getId(), epic);
    }


    //Возвращение всех задач в зависимости от типа задачи

    public ArrayList<Task> TaskList() {
        ArrayList<Task> TaskList = new ArrayList<>();
        for (Task task : tasks.values()) {
            TaskList.add(task);
        }
        return TaskList;
    }

    public ArrayList<Subtask> SubtaskList() {
        ArrayList<Subtask> SubtaskList = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            SubtaskList.add(subtask);
        }
        return SubtaskList;
    }

    public ArrayList<Epic> EpicList() {
        ArrayList<Epic> EpicList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            EpicList.add(epic);
        }
        return EpicList;
    }

    //Получение списка сабтасков эпика
    public ArrayList<Integer> EpicSubtaskList(int epicId) {
        return epics.get(epicId).getSubtaskList();
    }

    // Удаление всех задач по типу

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            ArrayList<Integer> subtaskList = epic.getSubtaskList();
            subtaskList.clear();
            epic.setSubtaskList(subtaskList);
            epic.setStatus(Statuses.NEW);
            epics.put(epic.getId(), epic);
        }
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }


    public Task searchTaskById(int issId) {
        if (tasks.containsKey(issId)) {
            return tasks.get(issId);
        } else {
            return null;
        }

    }

    public Subtask searchSubtaskById(int issId) {
        if (subtasks.containsKey(issId)) {
            return subtasks.get(issId);
        } else {
            return null;
        }


    }

    public Epic searchEpicById(int issId) {
        if (epics.containsKey(issId)) {
            return epics.get(issId);
        } else {
            return null;
        }
    }

    public void removeTaskById(int issId) {
        if (tasks.containsKey(issId)) {
            tasks.remove(issId);
        }
    }

    public void removeSubtaskById(int issId) {
        if (subtasks.containsKey(issId)) {
            int epicId = subtasks.get(issId).getEpicId();
            epics.get(epicId).getSubtaskList().remove(issId);
            checkEpicStatus(epicId);
            subtasks.remove(issId);
        }
    }

    public void removeEpicById(int issId) {
        if (epics.containsKey(issId)) {
            epics.remove(issId);
        }
    }

    // Проверка наличия эпика
    public boolean checkEpicExists(int epicId) {
        epics.containsKey(epicId);
        if (epics.containsKey(epicId)) {
            return true;
        } else {
            return false;
        }

    }


    public void checkEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtaskList = epics.get(epicId).getSubtaskList();
        if (subtaskList.size() == 0) {
            epics.get(epicId).setStatus(Statuses.NEW);
        } else {
            int stsDone = 0;
            int stsNew = 0;
            for (Integer subtaskId : subtaskList) {
                Subtask subtask = subtasks.get(subtaskId);
                switch (subtask.getStatus()) {
                    case NEW:
                        stsNew++;
                        break;
                    case IN_PROGRESS:
                        epics.get(epicId).setStatus(Statuses.IN_PROGRESS);
                        return;
                    case DONE:
                        stsDone++;
                        break;
                }
                if (stsDone == 0) {
                    epics.get(epicId).setStatus(Statuses.NEW);
                } else if (stsNew == 0) {
                    epics.get(epicId).setStatus(Statuses.DONE);
                } else {
                    epics.get(epicId).setStatus(Statuses.IN_PROGRESS);
                }


            }
        }
    }
}
