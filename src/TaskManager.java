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
        tasks.put(task.getId(), task);
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

    public void listTasks()

    //Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
    /*Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    a. Получение списка всех задач.
    b. Удаление всех задач.
    c. Получение по идентификатору.
    d. Создание. Сам объект должен передаваться в качестве параметра.
    e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    f. Удаление по идентификатору.*/
    /*Дополнительные методы:
    a. Получение списка всех подзадач определённого эпика.
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
