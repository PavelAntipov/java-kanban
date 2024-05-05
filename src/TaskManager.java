import issues.Epic;
import issues.Subtask;
import issues.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    void taskCreate(Task task);

    void subtaskCreate(Subtask subtask);

    void epicCreate(Epic epic);

    //Обновление информации о задаче
    void taskUpdate(Task task);

    void subtaskUpdate(Subtask subtask);

    void epicUpdate(Epic epic);

    List<Task> taskList();

    List<Subtask> subtaskList();

    List<Epic> epicList();

    //Получение списка сабтасков эпика
    ArrayList<Integer> epicSubtaskList(int epicId);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Task getTaskById(int issId);

    Subtask getSubtaskById(int issId);

    Epic getEpicById(int issId);

    void deleteTaskById(int issId);

    void deleteSubtaskById(int issId);

    void deleteEpicById(int issId);

    public List<Task> getHistoryManager();
}
