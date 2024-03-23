import issues.Epic;
import issues.Statuses;
import issues.Subtask;
import issues.Task;

import java.util.HashMap;
import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager{
    private int idCounter = 1;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    //Метод для получения ID новой задачи

    private int getNewTaskID() {
        idCounter++;
        return idCounter;
    }
    //Создание задач по типам

    @Override
    public void taskCreate(Task task) {
        int taskId = getNewTaskID();
        task.setId(taskId);
        tasks.put(taskId, task);
    }

    @Override
    public void subtaskCreate(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            return;
        } else {
            int subtaskId = getNewTaskID();
            subtask.setId(subtaskId);
            subtasks.put(subtaskId, subtask);
            epics.get(epicId).ammendSubtaskList(subtaskId);
            checkEpicStatus(epicId);

        }
    }

    @Override
    public void epicCreate(Epic epic) {
        int epicId = getNewTaskID();
        epics.put(epicId, epic);
    }


    //Обновление информации о задаче
    @Override
    public void taskUpdate(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        int issId = task.getId();
        tasks.put(issId, task);
    }

    @Override
    public void subtaskUpdate(Subtask subtask) {
        int issId = subtask.getId();
        if (!subtasks.containsKey(subtask.getId()) || !epics.containsKey(subtask.getEpicId())) {
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(subtask.getId());
    }

    @Override
    public void epicUpdate(Epic epic) {
        if (!epics.containsKey(epic.getId())) {
            return;
        }
        epics.get(epic.getId()).setName(epic.getName());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    //Возвращение всех задач в зависимости от типа задачи

    @Override
    public ArrayList<Task> TaskList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> SubtaskList() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> EpicList() {
        return new ArrayList<>(epics.values());
    }

    //Получение списка сабтасков эпика
    @Override
    public ArrayList<Integer> EpicSubtaskList(int epicId) {
        return epics.get(epicId).getSubtaskList();
    }

    // Удаление всех задач по типу

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskList().clear();
            epic.setStatus(Statuses.NEW);
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }


    @Override
    public Task getTaskById(int issId) {
        return tasks.get(issId);
    }

    @Override
    public Subtask getSubtaskById(int issId) {
        return subtasks.get(issId);
    }

    @Override
    public Epic getEpicById(int issId) {
        return epics.get(issId);
    }

    @Override
    public void deleteTaskById(int issId) {
        if (tasks.containsKey(issId)) {
            tasks.remove(issId);
        }
    }

    @Override
    public void deleteSubtaskById(int issId) {
        if (subtasks.containsKey(issId)) {
            int epicId = subtasks.get(issId).getEpicId();
            epics.get(epicId).getSubtaskList().remove(issId);
            checkEpicStatus(epicId);
            subtasks.remove(issId);
        }
    }

    @Override
    public void deleteEpicById(int issId) {

        for (int subtaskId : getEpicById(issId).getSubtaskList()) {

            deleteSubtaskById(issId);
        }
        epics.remove(issId);

    }

    // Проверка наличия эпика
    public boolean checkEpicExists(int epicId) {
        return epics.containsKey(epicId);
    }


    private void checkEpicStatus(int epicId) {
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
