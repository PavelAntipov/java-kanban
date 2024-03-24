package issues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void checkSubtaskEquals(){
        Subtask task1 = new Subtask("Name1","Desc1",123);
        Subtask task2 = new Subtask("Name2","Desc2", 321);
        task1.setId(205);
        task2.setId(205);
        boolean result = task1.equals(task2);
        assertTrue(result);


    }

    @Test
    public void checkEpicIdCantBeSubtaskId(){
        Subtask task1 = new Subtask("Name1","Desc1",123);
        task1.setId(738);
        task1.setEpicId(task1.getId());
        assertNotEquals(task1.getEpicId(),task1.getId());
    }

}