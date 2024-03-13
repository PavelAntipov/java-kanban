import java.util.Scanner;

public class Main {


    static Scanner scanner = new Scanner(System.in);
    static TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        testSetCreationMenu();
        taskManager.listTasks("ALL");


        while (true) {
            printMenu();
            int command = scanner.nextInt();

            switch (command) {
                case 1:
                    listTasks();
                    break;
                case 2:
                    deleteTasks();
                    break;
                case 3:
                    searchTaskById();
                    break;
                case 4:
                    createTask();
                    break;
                case 5:
                    updateTask();
                    break;
                case 6:
                    removeTaskById();
                    break;
                case 7:
                    searchEpicSubtasks();
                    break;
                case 8:
                    return;
                default:
                    noCommand();
                    break;
            }//*/
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Удалить задачи определённого типа");
        System.out.println("3 - Показать задачу по идентификатору");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Изменить задачу");
        System.out.println("6 - Удалить задачу по идентификатору");
        System.out.println("7 - Посмотреть подзадачи эпика по ID эпика");
        System.out.println("8 - Завершить работу программы");
    }

    private static void listTasks() {
        System.out.println("Какие задачи вы хотите посмотреть? \n1 - Задачи \n2 - Подзадачи \n3 - Эпики " +
                "\n4 - Все задачи \n5 - Возврат в предыдущее меню");
        int response = scanner.nextInt();
        switch (response) {
            case 1:
                taskManager.listTasks("TASK");
                break;
            case 2:
                taskManager.listTasks("SUBTASK");
                break;
            case 3:
                taskManager.listTasks("EPIC");
                break;
            case 4:
                taskManager.listTasks("ALL");
                break;
            case 5:
                return;
            default:
                noCommand();
                break;
            //System.out.println();
        }
    }

    private static void deleteTasks() {
        System.out.println("Какие задачи вы хотите удалить? \n1 - Задачи \n2 - Подзадачи \n3 - Эпики " +
                "\n4 - Все задачи \n5 - Возврат в предыдущее меню");
        int response = scanner.nextInt();
        switch (response) {
            case 1:
                taskManager.deleteAllTasksByType("TASK");
                break;
            case 2:
                taskManager.deleteAllTasksByType("SUBTASK");
                break;
            case 3:
                taskManager.deleteAllTasksByType("EPIC");
                break;
            case 4:
                taskManager.deleteAllTasksByType("ALL");
                break;
            case 5:
                return;
            default:
                noCommand();
                break;

        }

    }

    private static void searchTaskById() {
        System.out.println("Введите ID задачи");
        int response = scanner.nextInt();
        taskManager.searchTaskById(response);
    }

    private static void createTask() {
        System.out.println("Какой тип задачи вы хотите создать?\n1 - Задачу \n2 - Подзадачу \n3 - Эпик" +
                "                \n4 - Возврат в предыдущее меню");
        int response = scanner.nextInt();
        System.out.println("Введите название");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Введите описание");
        String description = scanner.nextLine();
        switch (response) {
            case 1:
                taskManager.taskCreate(name, description);
                break;
            case 2:
                System.out.println("Введите ID эпика");
                int epicId = scanner.nextInt();
                taskManager.subtaskCreate(name, description, epicId);
                break;
            case 3:
                taskManager.epicCreate(name, description);
                break;
            case 4:
                return;
        }

    }

    private static void updateTask() {
        int response;

        System.out.println("Введите ID задачи");
        int issId = scanner.nextInt();
        if (taskManager.checkIssueExistance(issId)) {
            return;
        }
        boolean iTask = taskManager.tasks.containsKey(issId);
        boolean iSubtask = taskManager.subtasks.containsKey(issId);
        boolean iEpic = taskManager.epics.containsKey(issId);

        if (iTask) {
            System.out.println("Введите новое название");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.println("Введите новое описание");
            String description = scanner.nextLine();
            Statuses status = null;
            System.out.println("Выберите новый статус\n1 - New\n2 - In Progress\n3 - Done");
            response = scanner.nextInt();
            status = setStatus(response);

            taskManager.taskUpdate(issId, name, description, status);
        }
        if (iSubtask) {
            System.out.println("Введите новое название");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.println("Введите новое описание");
            String description = scanner.nextLine();
            Statuses status = null;
            System.out.println("Выберите новый статус\n1 - New\n2 - In Progress\n3 - Done");
            response = scanner.nextInt();
            status = setStatus(response);
            System.out.println("Введите новый Epic ID");
            int epicId = scanner.nextInt();

            taskManager.subtaskUpdate(issId, name, description, status, epicId);
       }
        if (iEpic) {
            System.out.println("Введите новое название");
            scanner.nextLine();
            String name = scanner.nextLine();
            System.out.println("Введите новое описание");
            String description = scanner.nextLine();
            Statuses status = null;
            System.out.println("Выберите новый статус\n1 - New\n2 - In Progress\n3 - Done");
            response = scanner.nextInt();
            status = setStatus(response);

            taskManager.epicUpdate(issId, name, description, status);
        }
    }

    private static void removeTaskById() {
        System.out.println("Введите ID задачи");
        int response = scanner.nextInt();
        taskManager.removeTaskById(response);
    }

    private static void searchEpicSubtasks() {
        System.out.println("Введите ID эпика");
        int response = scanner.nextInt();
        taskManager.printEpicSubtasks(response);
    }


    private static void testSetCreationMenu() {
        System.out.println("Желаете ли вы создать тестовые задачи? \n 1 - Да \n 2 - Нет");
        int response = scanner.nextInt();
        switch (response) {
            case 1:
                TestData.createTestCases(taskManager);
                break;
            case 2:
                break;
            default:
                noCommand();
        }
    }


    // System.out.println("Поехали!");


    public static void noCommand() {
        System.out.println("Такой команды нет.");
    }

    public static Statuses setStatus(int response) {
        Statuses status = null;
        switch (response) {
            case 1:
                status = Statuses.NEW;
                break;
            case 2:
                status = Statuses.IN_PROGRESS;
                break;
            case 3:
                status = Statuses.DONE;
        }
        return status;
    }
}

