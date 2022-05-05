package ru.yandex.practicum.util;

import ru.yandex.practicum.api.KVServer;
import ru.yandex.practicum.manager.*;

import java.io.IOException;

public class Managers {

    public static TaskManager getDefault(){
        try {
            return new HttpTaskManager("http://localhost:8090/");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static KVServer getDefaultKVServer() throws IOException {
        final KVServer kvServer = new KVServer();
        kvServer.start();
        return kvServer;
    }

}