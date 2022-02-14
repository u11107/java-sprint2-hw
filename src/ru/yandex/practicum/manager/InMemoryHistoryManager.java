package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void removeHistory(Task task) {
        if (history.contains(task)) {
            history.remove(task);
        } else {
            System.out.println("Ошибка, нет такой задачи!");
        }
    }

}