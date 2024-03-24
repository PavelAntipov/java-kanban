import issues.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    @Test
    public void ManagerCanAddAndSearchTasks(){
        Task task1 = new Task("name1","description1");
        taskManager.taskCreate(task1);

        Task task = taskManager.getTaskById(1);
        Task task2 = new Task("name1","description2");
        task2.setId(1);
        taskManager.taskUpdate(task2);

        task = taskManager.getTaskById(1);
        Task history1 = taskManager.getHistory().getHistory().get(0);
        Task history2 = taskManager.getHistory().getHistory().get(1);
        assertNotEquals(history1.hashCode(),history2.hashCode());
    }

}