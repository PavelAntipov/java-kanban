import issues.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        //опциональный сценарий

        //создание task
        Task task1 = new Task("Task1","Task1 description");
        Task task2 = new Task("Task2","Task2 description");
        taskManager.taskCreate(task1);
        taskManager.taskCreate(task2);

        //Создание эпика с тремя сабтасками
        Epic epic1 = new Epic ("Epic1","Epic1 description");
        taskManager.epicCreate(epic1);
        Subtask subtask1 = new Subtask("Subtask1","Subtask1 description",3);
        Subtask subtask2 = new Subtask("Subtask2","Subtask2 description",3) ;
        Subtask subtask3 = new Subtask("Subtask3","Subtask3 description",3) ;
        taskManager.subtaskCreate(subtask1);
        taskManager.subtaskCreate(subtask2);
        taskManager.subtaskCreate(subtask3);

        //Создание эпика без сабтасков
        Epic epic2 = new Epic ("Epic2","Epic2 description");
        taskManager.epicCreate(epic2);

        Task task = taskManager.getTaskById(1);
        Epic epic = taskManager.getEpicById(7);
        Subtask subtask = taskManager.getSubtaskById(4);
        //Task task12 = taskManager.getTaskById(2);
        Epic epic28 = taskManager.getEpicById(3);
        task = taskManager.getTaskById(1);
        System.out.println("История" + taskManager.getHistoryManager());
        //List<Task> history = taskManager.getHistoryManager();
        //System.out.println(taskManager.getHistoryManager());
        taskManager.deleteTaskById(1);
        taskManager.deleteEpicById(3);
        System.out.println("История" + taskManager.getHistoryManager());



    }
}

