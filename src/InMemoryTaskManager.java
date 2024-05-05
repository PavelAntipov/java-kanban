import issues.Epic;
import issues.Statuses;
import issues.Subtask;
import issues.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idCounter = 0;
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Subtask> subtasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    //Метод для получения ID новой задачи
    private HistoryManager historyManager;

    public InMemoryTaskManager() {

        this.historyManager = Managers.getDefaultHistoryManager();
    }


    public List<Task> getHistoryManager() {
        return historyManager.getHistory();
    }

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
        if (epics.containsKey(epicId)) {
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
        epic.setId(epicId);
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
    public List<Task> taskList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> subtaskList() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> epicList() {
        return new ArrayList<>(epics.values());
    }

    //Получение списка сабтасков эпика
    @Override
    public ArrayList<Integer> epicSubtaskList(int epicId) {
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
        historyManager.add(tasks.get(issId));
        return tasks.get(issId);
    }

    @Override
    public Subtask getSubtaskById(int issId) {
        historyManager.add(subtasks.get(issId));
        return subtasks.get(issId);
    }

    @Override
    public Epic getEpicById(int issId) {
        historyManager.add(epics.get(issId));
        return epics.get(issId);
    }

    @Override
    public void deleteTaskById(int issId) {
        historyManager.remove(issId);
        tasks.remove(issId);
    }

    @Override
    public void deleteSubtaskById(int issId) {
        if (subtasks.containsKey(issId)) {
            int epicId = subtasks.get(issId).getEpicId();
            Epic ep = epics.get(epicId);
            ArrayList<Integer> ar = ep.getSubtaskList();
            ar.remove(Integer.valueOf(issId));
            //epics.get(epicId).getSubtaskList().remove(issId);
            checkEpicStatus(epicId);
            historyManager.remove(issId);
            subtasks.remove(issId);
        }
    }

    @Override
    public void deleteEpicById(int issId) {
        ArrayList<Integer> subtaskList = epics.get(issId).getSubtaskList();
        for (int subtaskId : subtaskList) {
            // Проверка на наличие просмотра подзадач. Если они не были просмотрены, то
            // получим nullPointerException
            if (historyManager.getHistory().contains(subtasks.get(subtaskId))) {
                historyManager.remove(subtaskId);
            }

            subtasks.remove(subtaskId);
        }
        historyManager.remove(issId);
        epics.remove(issId);
    }

    // Проверка наличия эпика
    public boolean checkEpicExists(int epicId) {
        return epics.containsKey(epicId);
    }


    private void checkEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtaskList = epic.getSubtaskList();
        if (subtaskList.isEmpty()) {
            epic.setStatus(Statuses.NEW);
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
                        epic.setStatus(Statuses.IN_PROGRESS);
                        return;
                    case DONE:
                        stsDone++;
                        break;
                }
                if (stsDone == 0) {
                    epic.setStatus(Statuses.NEW);
                } else if (stsNew == 0) {
                    epic.setStatus(Statuses.DONE);
                } else {
                    epic.setStatus(Statuses.IN_PROGRESS);
                }


            }
        }
    }

}
