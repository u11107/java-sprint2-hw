package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;
import java.util.*;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        private final Task value;
        private Node next;
        private Node prev;

        private Node(Task task) {
            this.value = task;
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

    private final Map<Integer, Node> newMap = new HashMap<>();
    // добавляем в конец списка
    private void linkLast(Task value) {
            if (head == 0) {
                Node newNode = new Node(value);
                newMap.put(value.getId(), newNode);
                head = newNode;
            } else if (size == 1) {
                Node newNode = new Node(value);
                head.setNext(newNode);
                newMap.put(value.getId(), newNode);
                newNode.setPrev(head);
                tail = newNode;
            } else {
                Node newNode = new Node(value);
                newNode.setPrev(tail);
                newMap.put(value.getId(), newNode);
                tail.setNext(newNode);
                tail = newNode;
            }
            ++size;
        } else {
            removeNode(head);
            linkLast(value);
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
    private List <Task> getTasks() {
        if (head != null) {
            List<Task> tasks = new ArrayList<>();
            Node iter = head;
            while (iter.getNext() != null) {
                tasks.add(iter.getValue());
                iter = iter.getNext();
            }
            return tasks;
        } else
            return null;
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
        return  getTasks();
    }
}