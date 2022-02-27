package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;
import java.util.*;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int SIZE = 10;
    private final MyList<Task> myList = new MyList<>();

    @Override
    public void add(Task task) {
        if (myList.map.containsKey(task.getId()))
            remove(task.getId());
        myList.linkLast(task);

    }

    @Override
    public void remove(int id) {
        if (!myList.map.isEmpty()) {
            myList.removeNode(myList.map.get(id));
            myList.map.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return myList.getTasks();
    }


    private static class MyList<T extends Task> {
        private Node<T> head = null;// голова
        private Node<T> tail = null;//хвост
        private int size = 0;
        private final Map<Integer, Node<T>> map = new HashMap<>();
        // добавляем в конец списка
        private void linkLast(T value) {
            if (size + 1 <= SIZE) {
                if (size == 0) {
                    Node<T> newNode = new Node<>(value);
                    map.put(value.getId(), newNode);
                    head = newNode;
                } else if (size == 1) {
                    Node<T> newNode = new Node<>(value);
                    head.setNext(newNode);
                    map.put(value.getId(), newNode);
                    newNode.setPrev(head);
                    tail = newNode;
                } else {
                    Node<T> newNode = new Node<>(value);
                    newNode.setPrev(tail);
                    map.put(value.getId(), newNode);
                    tail.setNext(newNode);
                    tail = newNode;
                }
                ++size;
            } else {
                removeNode(head);
                linkLast(value);
            }
        }

        // удаляем узел
        private void removeNode(Node<T> value) {
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
        private List<T> getTasks() {
            if (head != null) {
                List<T> tasks = new ArrayList<>();
                Node<T> iter = head;
                while (true) {
                    tasks.add(iter.getValue());

                    if (iter.getNext() == null)
                        break;
                    iter = iter.getNext();
                }
                return tasks;
            } else
                return null;
        }

    }
    //класс ноды
    private static class Node<T> {
        private final T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T task) {
            this.value = task;
        }

        public T getValue() {
            return value;
        }

        private Node<T> getNext() {
            return next;
        }

        private Node<T> getPrev() {
            return prev;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        private void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}