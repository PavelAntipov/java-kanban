import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import issues.Task;


class ManagersTest {
    @Test
    public void taskManagerInitialization(){
        TaskManager taskManager = Managers.getDefaultTaskManager();
        Task task = new Task("Name1","Desc1");
        taskManager.taskCreate(task);
        Task task1 = taskManager.getTaskById(1);


        Assertions.assertNotNull(taskManager.getHistoryManager());
        Assertions.assertEquals(taskManager.getHistoryManager().get(0),task1);


    }


    }


