import issues.Task;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    Map<Integer, Node<Task>> listOfTasks = new HashMap<>();


    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> head = null;
        if (listOfTasks.isEmpty()) {
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
        if (listOfTasks.isEmpty()) {
            listOfTasks.put(task.getId(), taskNode);
            //Вариант, когда задачу уже просматривали раньше
        } else if (listOfTasks.containsKey(task.getId())) {
            Node<Task> oldEntry = listOfTasks.get(task.getId());

            if (oldEntry.getTail() == null) { //последний элемент (+ случай, когда элемент первый и последний)
                return;
            } else if (oldEntry.getHead() == null && oldEntry.getTail() != null) { //первый элемент
                oldEntry.getTail().setHead(null);

                for (Node<Task> entry : listOfTasks.values()) {
                    if (entry.getTail() == null) {
                        entry.setTail(taskNode);
                        taskNode.setHead(entry);
                    }
                }
                listOfTasks.put(task.getId(), taskNode);

            } else { // Общий случай
                Node<Task> tail = oldEntry.getTail();
                Node<Task> head = oldEntry.getHead();
                tail.setHead(head);
                head.setTail(tail);

                for (Node<Task> entry : listOfTasks.values()) {
                    if (entry.getTail() == null) {
                        entry.setTail(taskNode);
                        taskNode.setHead(entry);
                    }
                }
                listOfTasks.put(task.getId(), taskNode);
            }


            //общий случай
            //for (Node<Task> oldEntry : listOfTasks.values()) {
            //Ищем соседние элементы в истории и связываем их между собой, чтобы переместить
            // вызываемый элемент в конец списка

            //Ищем хвост списка. Если хвост не равен текущей задаче, то ставим теперь уже бывшему
            //хвосту новый хвост, а новому хвосту (нашей задаче) новую голову. Новый хвост имеет в поле
            // tail ссылку на null из-за конструктора

            if (oldEntry.getTail() == null && !oldEntry.getData().equals(taskNode.getData())) {
                taskNode.setHead(oldEntry);
                oldEntry.setTail(taskNode);
            }
        }
        //listOfTasks.put(task.getId(), taskNode);
        else if (!listOfTasks.containsKey(task.getId())) {
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
        if (taskNode == null) {
            return;
        }
        Node<Task> head = taskNode.getHead();
        Node<Task> tail = taskNode.getTail();
        if (head == null && tail == null) {
            listOfTasks.clear();
        } else if (head == null && tail != null) {
            tail.setHead(null);
        } else if (head != null && tail == null) {
            head.setTail(null);
        } else {
            head.setTail(tail);
            tail.setHead(head);
        }
        //head.setTail(tail);
        //tail.setHead(head);
        listOfTasks.remove(taskId);

    }
}
