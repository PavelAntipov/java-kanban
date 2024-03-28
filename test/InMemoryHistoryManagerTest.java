import issues.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

        TaskManager taskManager = Managers.getDefaultTaskManager();

    @Test
    public void ManagerCanAddAndSearchTasks(){
        Task task1 = new Task("name1","description1");
        taskManager.taskCreate(task1);

        Task task = taskManager.getTaskById(1);
        Task task2 = new Task("name1","description2");
        task2.setId(1);
        taskManager.taskUpdate(task2);

        task = taskManager.getTaskById(1);
        Task history1 = taskManager.getHistoryManager().get(0);
        Task history2 = taskManager.getHistoryManager().get(1);
        Assertions.assertNotEquals(history1.hashCode(),history2.hashCode());
    }

}