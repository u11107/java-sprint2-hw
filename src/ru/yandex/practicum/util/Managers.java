package ru.yandex.practicum.util;

import ru.yandex.practicum.api.KVServer;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.manager.HttpTaskManager;
import java.io.IOException;

public class Managers {

    public static TaskManager getDefault(){
        try {
            return new HttpTaskManager("http://localhost:8090/");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getDefaultKVServer() throws IOException {
        final KVServer kvServer = new KVServer();
        kvServer.start();
    }
}