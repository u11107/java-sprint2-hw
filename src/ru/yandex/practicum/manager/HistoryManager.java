package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Task;
import java.util.List;

public interface HistoryManager {

    void add(Task task);
    List<Task> getHistory();
    void removeHistory(Task task);


}
