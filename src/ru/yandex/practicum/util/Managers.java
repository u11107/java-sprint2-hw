package ru.yandex.practicum.util;

import ru.yandex.practicum.manager.HistoryManager;
import ru.yandex.practicum.manager.InMemoryHistoryManager;
import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.manager.TaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}