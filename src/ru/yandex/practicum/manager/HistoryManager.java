package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;

import java.util.List;

public interface HistoryManager {

    //добавление в историю
    void add(Task task);

    // просмотр истории
    List<Task> getHistory();

    // удаление по id
    void remove(int id);

}
