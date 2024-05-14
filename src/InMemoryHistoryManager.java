import issues.Task;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (task.equals(null)) {
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
        if (listOfTasks.isEmpty()) { //мапа пустая
            listOfTasks.put(task.getId(), newNode);
            first = newNode;
            last = newNode;
        } else if (!listOfTasks.containsKey(task.getId())) { //задача раньше не просматривалась
            addLast(newNode);
        } else { //задача раньше просматривалась
            remove(task.getId());
            if (listOfTasks.isEmpty()) { //мапа пустая после удаления элемента истории
                listOfTasks.put(task.getId(), newNode);
                first = newNode;
                last = newNode;
            } else {
                addLast(newNode);
            }
        }

    }

    private void addLast(Node<Task> newNode) {
        last.setTail(newNode);
        newNode.setHead(last);
        listOfTasks.put(newNode.getData().getId(), newNode);
        last = newNode;
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
