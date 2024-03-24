import issues.Epic;
import issues.Subtask;
import issues.Task;

import java.util.ArrayList;

public interface TaskManager {
    void taskCreate(Task task);

    void subtaskCreate(Subtask subtask);

    void epicCreate(Epic epic);

    //Обновление информации о задаче
    void taskUpdate(Task task);

    void subtaskUpdate(Subtask subtask);

    void epicUpdate(Epic epic);

    ArrayList<Task> TaskList();

    ArrayList<Subtask> SubtaskList();

    ArrayList<Epic> EpicList();

    //Получение списка сабтасков эпика
    ArrayList<Integer> EpicSubtaskList(int epicId);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Task getTaskById(int issId);

    Subtask getSubtaskById(int issId);

    Epic getEpicById(int issId);

    void deleteTaskById(int issId);

    void deleteSubtaskById(int issId);

    void deleteEpicById(int issId);

    InMemoryHistoryManager getHistory();
}
