package ru.yandex.practicum.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.api.DurationAdapter;
import ru.yandex.practicum.api.KVTaskClient;
import ru.yandex.practicum.api.LocalDataTimeAdapter;
import ru.yandex.practicum.task.Task;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient kvTaskClient;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
            .create();

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    protected void save() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(getAllTasks());
        tasks.addAll(getAllEpics());
        tasks.addAll(getAllSubtasks());
        List<String> idHistory = new ArrayList<>();
        for (Task task : history()) {
            idHistory.add(String.valueOf(task.getId()));
        }
        String tasksJson = gson.toJson(tasks);
        String historyJson = gson.toJson(idHistory);
        kvTaskClient.put("/tasks", tasksJson);
        kvTaskClient.put("/history", historyJson);
    }
}