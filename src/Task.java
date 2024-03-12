public class Task {
    public String name;
    public String description;

    public int id;
    public Statuses status;

    public Integer getId() {
        return id;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        status = Statuses.NEW;
        id = TaskManager.getNewTaskID();
    }
}
