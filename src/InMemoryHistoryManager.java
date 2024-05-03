import issues.Task;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    Map<Integer, Node<Task>> listOfTasks = new HashMap<>();
    private final LinkedList<Task> history = new LinkedList<>();
    private static final int MAX_SIZE = 10;

    /*@Override
    public void add(Task task){

       if (history.size() == MAX_SIZE) {
           history.remove(0);
       }
       history.add(task);
    }*/
    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> head = null;
        if (listOfTasks.size() == 0) {
            return null;
        }
        for (Node<Task> node : listOfTasks.values()) {
            if (node.getHead() == null) {
                head = node;
            }
        }
        taskList.add(head.getData());

        while (head.getTail() != null) {
            taskList.add(head.getTail().getData());
            head = head.getTail();
        }


        return taskList;

    }


    @Override
    public void add(Task task) {
        Node<Task> taskNode = new Node<>(task);
        if (listOfTasks.size() == 0) {
            listOfTasks.put(task.getId(), taskNode);
        } else if (listOfTasks.containsKey(task.getId())) {
            for (Node<Task> oldEntry : listOfTasks.values()) {
                if (taskNode.equals(oldEntry)) {
                    Node<Task> tail = oldEntry.getTail();
                    Node<Task> head = oldEntry.getHead();
                    tail.setHead(head);
                    head.setTail(tail);
                }

                if (oldEntry.getTail() == null && !oldEntry.equals(taskNode)) {
                    taskNode.setHead(oldEntry);
                    oldEntry.setTail(taskNode);
                }
            }
            //listOfTasks.put(task.getId(), taskNode);
        } else if (!listOfTasks.containsKey(task.getId())) {
            for (Node<Task> oldEntry : listOfTasks.values()) {
                if (oldEntry.getTail() == null) {
                    taskNode.setHead(oldEntry);
                    oldEntry.setTail(taskNode);
                }
            }
            listOfTasks.put(task.getId(), taskNode);

        }


    }

    @Override
    public void remove(int taskId) {
        Node<Task> taskNode = listOfTasks.get(taskId);
        Node<Task> head = taskNode.getHead();
        Node<Task> tail = taskNode.getTail();
        /*if (head == null && tail != null) {
            tail.setHead(null);
        } else if (head != null && tail == null) {
            head.setTail(null);
        } else {
            head.setTail(tail);
            tail.setHead(head);
        }*/
        head.setTail(tail);
        tail.setHead(head);
        listOfTasks.remove(taskId);

    }
}
