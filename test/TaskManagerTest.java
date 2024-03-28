import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import issues.*;

class TaskManagerTest {
    private TaskManager taskManager;
    Task task1;
    Epic epic1;
    Subtask subtask1;
    @BeforeEach
    public void BeforeEach(){
        TaskManager taskManager = Managers.getDefaultTaskManager();
        Task task1 = new Task("name1", "description1");
        Epic epic1 = new Epic("epicname1", "epicdescription1");
        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescription1", 2);
    }



    @Test
    public void ManagerCanAddAndSearchTasks() {
        taskManager.taskCreate(task1);
        taskManager.epicCreate(epic1);
        taskManager.subtaskCreate(subtask1);
        task1.setId(1);
        epic1.setId(2);
        subtask1.setId(3);
        assertEquals(task1.hashCode(), taskManager.getTaskById(1).hashCode());
        assertEquals(epic1.hashCode(), taskManager.getEpicById(2).hashCode());
        assertEquals(subtask1.hashCode(), taskManager.getSubtaskById(3).hashCode());
        assertEquals(task1,taskManager.getTaskById(1));
        assertEquals(epic1,taskManager.getEpicById(2));
        assertEquals(subtask1,taskManager.getSubtaskById(3));


    }

    @Test
    public void TaskIdDoNotConflict() {
        //Task task1 = new Task("name1", "description1");
        taskManager.taskCreate(task1);
        task1.setId(1);
        Task task2 = new Task("name2", "description2");
        task2.setId(1);
        taskManager.taskCreate(task2);
        Assertions.assertNotEquals(task2, taskManager.getTaskById(1));
        task2.setId(2);
        Assertions.assertEquals(task2, taskManager.getTaskById(2));

    }

    @Test
    public void CheckTaskIntegrityAfterCreation() {
        taskManager.taskCreate(task1);
        Task task2 = taskManager.getTaskById(1);
        assertTrue(task2.getName().equals(task1.getName()) &&
                task2.getDescription().equals(task1.getDescription()) &&
                task2.getStatus().equals(task1.getStatus()));
    }

}