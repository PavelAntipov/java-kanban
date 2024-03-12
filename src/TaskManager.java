import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class TaskManager {
    public static int idCounter = 0;
    public static HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    public static HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    public static HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    Scanner scanner = new Scanner(System.in);

    public static int getNewTaskID() {
        idCounter++;
        return idCounter;
    }

    //Создание задачи
    public void taskCreate(String name, String description) {
        Task task = new Task(name, description);
        tasks.put(task.id, task);
    }


    public void subtaskCreate(String name, String description, int epicId) {
        Subtask subtask = new Subtask(name, description,epicId);
        subtasks.put(subtask.id, subtask);
        checkEpicStatus(epicId);
    }

    public void epicCreate(String name, String description) {
        Epic epic = new Epic(name, description);
        epics.put(epic.id, epic);
    }

    //Обновление информации о задаче
    public void taskUpdate(Task task) {
        tasks.put(task.id, task);

    }
    public void subtaskUpdate(Subtask subtask) {
        subtasks.put(subtask.id, subtask);

    }
    public void epicUpdate(Epic epic) {
        epics.put(epic.id, epic)

    }
    //Печать всех задач по типу
    public void listTasks(String command) {
        switch(command) {
            case "TASK":
                printTaskList();
                break;
            case "SUBTASK":
                printSubtaskList();
                break;
            case "EPIC":
                printEpicList();
                break;
            case "ALL":
                printTaskList();
                printSubtaskList();
                printEpicList();
                break;
            default:
                //int epicId = Integer.parseInt(command);
                printEpicSubtasks(Integer.parseInt(command));

        }
    }

    public void printTaskList() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }
    public void printSubtaskList() {
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }
    public void printEpicList() {
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }
    public void printEpicSubtasks(int epicId) {
        for (Subtask subtask : subtasks.values()) {
            if (subtask.epicId == epicId) {
                System.out.println(subtask);
            }
        }
    }
// Удаление всех задач по типу
    public void deleteAllTasksByType(String command) {
        switch (command) {
            case "TASK":
                tasks.clear();
                break;
            case "SUBTASK":
                subtasks.clear();
                break;
            case "EPIC":
                epics.clear();
                break;
        }
    }
    // Поиск задачи по ID
    public void searchTaskById (int issId) {
        boolean taskId = tasks.containsKey(issId);
        boolean subtaskId = subtasks.containsKey(issId);
        boolean epicId = epics.containsKey(issId);

        if (taskId) {
            System.out.println(tasks.get(issId));
        } else if (subtaskId) {
            System.out.println(subtasks.get(issId));
        } else if (epicId) {
            System.out.println(epics.get(issId));
        } else {
            System.out.println("Такой задачи не существует");
        }
    }
    // удаление задачи по ID
    public void removeTaskById (int issId) {
        boolean taskId = tasks.containsKey(issId);
        boolean subtaskId = subtasks.containsKey(issId);
        boolean epicId = epics.containsKey(issId);
        if (taskId) {
            tasks.remove(issId);
        } else if (subtaskId) {
            subtasks.remove(issId);
        } else if (epicId) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.epicId == issId) {
                    subtasks.remove(subtask.id);
                }
            }
            epics.remove(issId);
        } else {
            System.out.println("Такой задачи не существует");
        }
    }

    //Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
    /*Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    ------ a. Получение списка всех задач.
    ------ b. Удаление всех задач.
    ------c. Получение по идентификатору.
    d. Создание. Сам объект должен передаваться в качестве параметра.
    e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    -------f. Удаление по идентификатору.*/
    /*Дополнительные методы:
    -------- a. Получение списка всех подзадач определённого эпика.
    Управление статусами осуществляется по следующему правилу:
    a. Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче. По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
    b. Для эпиков:
    если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
    если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
    во всех остальных случаях статус должен быть IN_PROGRESS.*/

    public void checkEpicStatus(int epicId) {
        Epic epic = TaskManager.epics.get(epicId);
        ArrayList<Integer> subtaskList = epic.subtaskList;
        if (subtaskList.size() == 0) {
            epic.status = Statuses.NEW;
        } else {
            int stsDone = 0;
            int stsInProgress = 0;
            int stsNew = 0;
            for (Integer subtaskId : subtaskList) {
                Subtask subtask = TaskManager.subtasks.get(subtaskId);
                switch (subtask.status) {
                    case NEW:
                        stsNew++;
                        break;
                    case IN_PROGRESS:
                        stsInProgress++;
                        break;
                    case DONE:
                        stsDone++;
                        break;
                }
                if (stsInProgress == 0 && stsDone == 0) {
                    epic.status = Statuses.NEW;
                } else if (stsInProgress == 0 && stsNew == 0) {
                    epic.status = Statuses.DONE;
                } else {
                    epic.status = Statuses.IN_PROGRESS;
                }


            }
        }
    }
}
