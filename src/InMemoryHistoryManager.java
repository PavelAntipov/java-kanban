import issues.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> listOfTasks = new HashMap<>();
    private Node<Task> first;
    private Node<Task> last;


    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> head = first;
        if (listOfTasks.isEmpty()) {
            return null;
        }

        taskList.add(head.getData());

        while (head.getTail() != null) {
            taskList.add(head.getTail().getData());
            head = head.getTail();
        }
        return taskList;
    }

    private boolean taskIsNull(Task task) {
        boolean result = false;
        if (task == null) {
            result = true;
        }
        return result;
    }

    @Override
    public void add(Task task) {
        if (taskIsNull(task)) {
            return;
        }
        Node<Task> newNode = new Node<>(task);
        if (listOfTasks.containsKey(task.getId())) { //задача раньше просматривалась
            remove(task.getId());
        }
        addLast(task);
    }

    public void addLast(Task task) {
        final Node<Task> l = last;
        final Node<Task> newNode = new Node<>(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.setTail(newNode);
        }
        listOfTasks.put(task.getId(), newNode);


    }

    @Override
    public void remove(int taskId) {
        if (!listOfTasks.containsKey(taskId)) { //проверка, есть ли такая таска в истории
            return;
        }
        Node<Task> taskNode = listOfTasks.get(taskId);
        Node<Task> head = taskNode.getHead();
        Node<Task> tail = taskNode.getTail();
        if (head == null && tail == null) { //проверка того, есть ли в истории элементы, кроме конкретно этого
            listOfTasks.clear();
            first = null;
            last = null;
        } else if (head == null) { //проверка на первый элемент в истории
            tail.setHead(null);
            listOfTasks.remove(taskId);
            first = tail;
        } else if (tail == null) { //проверка на последний элемент в истории
            head.setTail(null);
            last = head;
        } else {
            head.setTail(tail);
            tail.setHead(head);
        }

    }
}
