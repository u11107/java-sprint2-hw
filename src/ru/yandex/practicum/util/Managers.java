package ru.yandex.practicum.util;

import ru.yandex.practicum.manager.*;

import java.io.IOException;

public class Managers {

    public static TaskManager getDefault(HistoryManager historyManager) throws IOException, InterruptedException {
        return new InMemoryTaskManager(historyManager);
//        return new HttpTaskManager("http://localhost:8090/");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


    public static TaskManager getDefault() {
        try {
            return new HttpTaskManager("http://localhost:8090/");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}