import issues.Epic;
import issues.Subtask;
import issues.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManagerTest {

    TaskManager taskManager = Managers.getDefaultTaskManager();


    //проверка того, что история пишется и порядок при повторном просмотре тасок меняется
    @Test
    public void historyManagerWritesHistory() {
        Task task1 = new Task("name1", "description1");
        Task task2 = new Task("name2", "description2");
        taskManager.taskCreate(task1);
        taskManager.taskCreate(task2);
        Task task = taskManager.getTaskById(task1.getId());
        task = taskManager.getTaskById(task2.getId());
        task = taskManager.getTaskById(task1.getId());
        //Task taskx = taskManager.getTaskById(task2.getId());
        List<Task> history = taskManager.getHistoryManager();
        Assertions.assertEquals(history.size(),2);
        Assertions.assertEquals(history.get(history.size()-1),task1);


    }
    //Проверка корректности удаления тасок
   @Test
   public void historyManagerTaskDeletion() {
        Task task1 = new Task("name1", "description1");
        Task task2 = new Task("name2", "description2");
        Task task3 = new Task("name3", "description3");
        Task task4 = new Task("name4", "description4");
        taskManager.taskCreate(task1);
        taskManager.taskCreate(task2);
        taskManager.taskCreate(task3);
        taskManager.taskCreate(task4);
        Task task = taskManager.getTaskById(task1.getId());
        task = taskManager.getTaskById(task2.getId());
        task = taskManager.getTaskById(task4.getId());
        task = taskManager.getTaskById(task3.getId());
        taskManager.deleteTaskById(4);
        List<Task> historyExpected = new ArrayList<>();
        historyExpected.add(task1);
        historyExpected.add(task2);
        historyExpected.add(task3);
        Assertions.assertEquals(historyExpected, taskManager.getHistoryManager());
    }
    //Проверка корректности удаления сабтасок
    @Test
    public void historyManagerSubtaskDeletion() {
        Epic epic = new Epic("name1", "description1");
        taskManager.epicCreate(epic);
        Subtask subtask1 = new Subtask("name1", "description1",epic.getId());
        Subtask subtask2 = new Subtask("name2", "description2",epic.getId());
        taskManager.subtaskCreate(subtask1);
        taskManager.subtaskCreate(subtask2);
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.deleteSubtaskById(subtask1.getId());
        List<Task> historyExpected = new ArrayList<>();
        historyExpected.add(subtask2);
        Assertions.assertEquals(historyExpected, taskManager.getHistoryManager());
    }
    //Проверка корректности удаления эпиков
    @Test
    public void historyManagerEpicDeletion() {

        //Task task1 = new Task("name1", "description1");
       // taskManager.taskCreate(task1);
       // Task task = taskManager.getTaskById(task1.getId());
        Epic epic = new Epic("name1", "description1");
        taskManager.epicCreate(epic);
        Subtask subtask1 = new Subtask("name1", "description1",epic.getId());
        Subtask subtask2 = new Subtask("name2", "description2",epic.getId());
        taskManager.subtaskCreate(subtask1);
        taskManager.subtaskCreate(subtask2);
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.deleteEpicById(epic.getId());
        //List<Task> historyExpected = new ArrayList<>();
        //historyExpected.add(subtask2);
        Assertions.assertEquals(null, taskManager.getHistoryManager());
    }
}