import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class TaskManager {
    public static int idCounter = 1000;
    public static HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    public static HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    public static HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    Scanner scanner = new Scanner(System.in);
    //Метод для получения ID новой задачи
    public static int getNewTaskID() {
        idCounter++;
        return idCounter;
    }

    //Создание задачи
    public void taskCreate(String name, String description) {
        Task task = new Task(name, description);
        tasks.put(task.id, task);
    }


    public static void subtaskCreate(String name, String description, int epicId) {
        Subtask subtask = new Subtask(name, description, epicId);
        subtasks.put(subtask.id, subtask);
        Epic epic = TaskManager.epics.get(epicId);
        epic.subtaskList.add(subtask.id);
        checkEpicStatus(epicId);
    }

    public static void epicCreate(String name, String description) {
        Epic epic = new Epic(name, description);
        epics.put(epic.id, epic);
    }

    //Обновление информации о задаче
    public static void taskUpdate(int issId, String name, String description, Statuses status) {
        Task task = tasks.get(issId);
        task.name = name;
        task.description = description;
        task.status = status;
        tasks.put(issId, task);
    }

    public void subtaskUpdate(int issId, String name, String description, Statuses status, int epicId) {
        Subtask subtask = subtasks.get(issId);
        subtask.name = name;
        subtask.description = description;
        subtask.status = status;
        if (subtask.epicId != epicId) {
            Epic epic = epics.get(subtask.epicId);
            epic.subtaskList.remove(Integer.valueOf(subtask.id));
            checkEpicStatus(epic.id);
            epic = epics.get(epicId);
            epic.subtaskList.add(subtask.id);
        }
        subtask.epicId = epicId;
        subtasks.put(issId, subtask);
        checkEpicStatus(epicId);

    }

    public void epicUpdate(int issId, String name, String description, Statuses status) {
        Epic epic = epics.get(issId);
        epic.name = name;
        epic.description = description;
        epic.status = status;
        epics.put(issId, epic);
    }
//Проверка существования задачи
    public boolean checkIssueExistance(int issId) {
        boolean result = !tasks.containsKey(issId) && !subtasks.containsKey(issId) && !epics.containsKey(issId);
        if (result) {
            System.out.println("Задачи с таким номером не существует");
        }
        return result;

    }


    //Печать всех задач по типу
    public static void listTasks(String command) {
        switch (command) {
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

    public static void printTaskList() {
        for (Task task : tasks.values()) {
            System.out.println(task.toString());
        }
    }

    public static void printSubtaskList() {
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask.toString());
        }
    }

    public static void printEpicList() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.toString());
        }
    }

    public static void printEpicSubtasks(int epicId) {
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
            case "ALL":
                tasks.clear();
                subtasks.clear();
                epics.clear();
        }
    }

    // Поиск задачи по ID
    public void searchTaskById(int issId) {
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
    public void removeTaskById(int issId) {
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
    public static void checkEpicStatus(int epicId) {
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
