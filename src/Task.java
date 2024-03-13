public class Task {
    public String name;
    public String description;

    public int id;
    public Statuses status;

    public Task() {
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Statuses.NEW;
        id = TaskManager.getNewTaskID();
    }

    @Override
    public String toString() {
        return "id: " + id + "\nНазвание: " + name + "\nСтатус: " + status +
                "\nОписание: " + description;
    }
}
