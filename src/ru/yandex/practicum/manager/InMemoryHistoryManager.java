package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        private final Task value;
        private Node next;
        private Node prev;

        public Node(Node prev, Task value, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public Task getValue() {
            return value;
        }

        private Node getNext() {
            return next;
        }

        private Node getPrev() {
            return prev;
        }

        private void setNext(Node next) {
            this.next = next;
        }

        private void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    private Node head = null;// голова
    private Node tail = null;//хвост
    private int size = 0; // я решил оставить size так как делал lastlink на основе стандартного метода LinkedList
    private final Map<Integer, Node> newMap = new HashMap<>();

    // добавляем в конец списка
    public void linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        newMap.put(task.getId(), newNode);
        size++;
    }

    // удаляем узел
    private void removeNode(Node value) {
        if (size == 1) {
            head = null;
        } else if (value == head) {
            if (size == 2) {
                tail.setPrev(null);
                head.setNext(null);
                head = tail;
                tail = null;
            } else {
                head = head.getNext();
                head.getPrev().setNext(null);
                head.setPrev(null);
            }
        } else if (value == tail) {
            if (size == 2) {
                tail.setPrev(null);
                tail = null;
                head.setNext(null);
            } else {
                tail = tail.getPrev();
                tail.getNext().setPrev(null);
                tail.setNext(null);
            }
        } else {
            value.getPrev().setNext(value.getNext());
            value.getNext().setPrev(value.getPrev());
            value.setNext(null);
            value.setPrev(null);
        }
        --size;
    }

    //собираем задачи в арайлист
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        if (head != null) {
            Node i = head;
            while (i != null) {
                tasks.add(i.getValue());
                i = i.getNext();
            }
            return tasks;
        } else
            return tasks;
    }

    @Override
    public void add(Task task) {
        if (newMap.containsKey(task.getId()))
            remove(task.getId());
        linkLast(task);

    }

    @Override
    public void remove(int id) {
        if (!newMap.isEmpty()) {
            removeNode(newMap.get(id));
            newMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

}