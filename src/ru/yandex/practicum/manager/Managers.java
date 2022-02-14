package ru.yandex.practicum.manager;

public class Managers {
    public static TaskManager getDefault(HistoryManager hm) {
        return new InMemoryTaskManager(hm);
    }


  public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}