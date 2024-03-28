import issues.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final LinkedList<Task> history = new LinkedList<>();
    private static final int MAX_SIZE = 10;
    @Override
    public void add(Task task){

       if (history.size() == MAX_SIZE) {
           history.remove(0);
       }
       history.add(task);
    }
    @Override
    public List<Task> getHistory(){
        return new ArrayList<>(history);

    }

}
