package issues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void checkEpicEquals(){
        Epic epic1 = new Epic("Name1","Desc1");
        Epic epic2 = new Epic("Name2","Desc2");
        epic1.setId(205);
        epic2.setId(205);
        boolean result = epic1.equals(epic2);
        assertTrue(result);
    }

    @Test
    public void epicCantBeAddedAsSubtaskToItself(){
        Epic epic1 = new Epic("Name1","Desc1");
        epic1.setId(205);
        epic1.ammendSubtaskList(205);
        assertNull(epic1.getSubtaskList());
    }
}