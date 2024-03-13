public class TestData {

    //Создаёт две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.

    public static void createTestCases(TaskManager taskManager) {
        int firstEpicId = 0;
        taskManager.taskCreate("Создание первой задачи", "Описание первой задачи");
        taskManager.taskCreate("Создание второй задачи", "Описание второй задачи");
        taskManager.epicCreate("Создание первого эпика", "Эпик с двумя подзадачами");
        for (int epicId : TaskManager.epics.keySet()) {
            taskManager.subtaskCreate("Первая подзадача эпика с двумя подзадачами", "Описание", epicId);
            taskManager.subtaskCreate("Вторая подзадача эпика с двумя подзадачами", "Описание", epicId);
            firstEpicId = epicId;
        }
        taskManager.epicCreate("Создание второго эпика", "Эпик с одной подзадачей");
        for (int epicId : taskManager.epics.keySet()) {
            if (epicId != firstEpicId) {
                taskManager.subtaskCreate("Подзадача эпика с одной подзадачей", "Описание", epicId);
            }
        }

    }


}
