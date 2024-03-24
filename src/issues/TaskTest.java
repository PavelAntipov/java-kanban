package issues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void checkTaskEquals(){
        Task task1 = new Task("Name1","Desc1");
        Task task2 = new Task("Name2","Desc2");
        task1.setId(205);
        task2.setId(205);
        boolean result = task1.equals(task2);
        assertTrue(result);


    }

}