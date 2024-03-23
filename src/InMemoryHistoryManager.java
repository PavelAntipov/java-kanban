import issues.Task;
import java.util.List;
import java.util.ArrayList;
public class InMemoryHistoryManager implements HistoryManager{
    List<Task> history = new ArrayList<>();
    @Override
    public void add(Task task){
       int maxSize = 10;
       if (history.size() < maxSize) {
            history.add(task);
        } else {
            for (int i = 0; i < maxSize-2; i++) {
                history.add(i, history.get(i+1));
            }
           history.add(maxSize-1,task);
        }

    }
    @Override
    public List<Task> getHistory(){
        return history;

    }

}
