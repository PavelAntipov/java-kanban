import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import issues.*;

class TaskManagerTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void ManagerCanAddAndSearchTasks(){
        Task task1 = new Task("name1","description1");
        Epic epic1 = new Epic("epicname1","epicdescription1");
        taskManager.taskCreate(task1);
        taskManager.epicCreate(epic1);
        Subtask subtask1 = new Subtask("subtaskname1","subtaskdescription1",2);
        taskManager.subtaskCreate(subtask1);
        task1.setId(1);
        epic1.setId(2);
        subtask1.setId(3);
        assertEquals(task1.hashCode(),taskManager.getTaskById(1).hashCode());
        assertEquals(epic1.hashCode(),taskManager.getEpicById(2).hashCode());
        assertEquals(subtask1.hashCode(),taskManager.getSubtaskById(3).hashCode());

    }
    @Test
    public void TaskIdDoNotConflict(){
        Task task1 = new Task("name1","description1");
        taskManager.taskCreate(task1);
        task1.setId(1);
        Task task2 = new Task("name2","description2");
        task2.setId(1);
        taskManager.taskCreate(task2);
        assertNotEquals(task2,taskManager.getTaskById(1));
        task2.setId(2);
        assertEquals(task2,taskManager.getTaskById(2));

    }

    /*Задача:создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    Мне видится невозможным создание такого теста с учётом того, что в предыдущем ТЗ было задание вынести генерацию ID
    задачи в TaskManager. Соответственно, при добавлении задачи ID всегда будет меняться
     */



}