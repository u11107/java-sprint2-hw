package ru.yandex.practicum.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.manager.HttpTaskManager;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Subtask;
import ru.yandex.practicum.task.Task;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpserver;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static TaskManager managerImpl;

    private static final Gson gson = new GsonBuilder().
            registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
            .create();

    public static void main(String[] args) throws IOException, InterruptedException {
        new KVServer().start();
        new HttpTaskServer().start();
        Task test1 = new Task(1, "Научиться учиться", "Яндекс помоги",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 0));

        HttpClient client = HttpClient.newHttpClient();
        String urlServer = "http://localhost:8080";

        URI url = URI.create(urlServer + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(test1));
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response;
            url = URI.create(urlServer + "/tasks/task?id=" + test1.getId());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("++++++++++++++++++++++++++++++");
            System.out.println(response.body());
            Task task = gson.fromJson(response.body(), Task.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }

    }

    public HttpTaskServer() throws IOException, InterruptedException {
        httpserver = HttpServer.create(new InetSocketAddress(PORT), 0);
        managerImpl = new HttpTaskManager("http://localhost:8090");
        try {
            httpserver.createContext("/tasks/task", TaskHandler);
            httpserver.createContext("/tasks/epic", EpicHandler);
            httpserver.createContext("/tasks/subTask", SubTaskHandler);
            httpserver.createContext("/tasks/history", HistoryHandler);
            httpserver.createContext("/tasks/prioritized", PrioritizedTasksHandler);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Ошибка в пути");
        }
    }


    public void stop() {
        httpserver.stop(1);
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");
    }

    public void start() {
        httpserver.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
    }


    private HttpHandler TaskHandler = (h) -> {
        try (h) {
            String method = h.getRequestMethod();
            int statusCode = 200;
            String response = "";
            String query = h.getRequestURI().getQuery();
            InputStream inputStream = h.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(body, Task.class);
            switch (method) {
                case "GET":
                    if (query == null) {
                        response = gson.toJson(managerImpl.getAllTasks());
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        if (query.startsWith("id=")) {
                            String strId = query.split("id=")[1];
                            int id = Integer.parseInt(strId);
                            response = gson.toJson(managerImpl.getByIdTask(id));
                            h.sendResponseHeaders(200, 0);
                            try (OutputStream os = h.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                    break;
                case "POST":
                    try {
                        managerImpl.createTasks(task);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "PUT":
                    try {
                        managerImpl.updateTask(task);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "DELETE":
                    query = h.getRequestURI().getQuery();
                    if (query != null) {
                        String strId = query.split("id=")[1];
                        int id = Integer.parseInt(strId);
                        managerImpl.removeTaskId(id);
                        h.sendResponseHeaders(200, 0);
                    } else {
                        managerImpl.clearAll();
                        h.sendResponseHeaders(200, 0);
                    }
                    break;
                default:
                    statusCode = 405;
                    response = "Передан неизвестный метод %d";
            }
            h.sendResponseHeaders(statusCode, 0);
            OutputStream os = h.getResponseBody();
            os.write(response.getBytes(DEFAULT_CHARSET));
            os.close();
        }
    };

    private HttpHandler EpicHandler = (h) -> {
        try (h) {
            String method = h.getRequestMethod();
            int statusCode = 200;
            String response = "";
            String query = h.getRequestURI().getQuery();
            InputStream inputStream = h.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Epic epic = gson.fromJson(body, Epic.class);
            switch (method) {
                case "GET":
                    if (query == null) {
                        response = gson.toJson(managerImpl.getAllEpics());
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        if (query.startsWith("id=")) {
                            String strId = query.split("id=")[1];
                            int id = Integer.parseInt(strId);
                            response = gson.toJson(managerImpl.getByEpicId(id));
                            h.sendResponseHeaders(200, 0);
                            try (OutputStream os = h.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                    break;
                case "POST":
                    try {
                        managerImpl.createEpics(epic);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "PUT":
                    try {
                        managerImpl.updateEpic(epic);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "DELETE":
                    query = h.getRequestURI().getQuery();
                    if (query != null) {
                        String strId = query.split("id=")[1];
                        int id = Integer.parseInt(strId);
                        managerImpl.removeEpicId(id);
                        h.sendResponseHeaders(200, 0);
                    } else {
                        managerImpl.clearAll();
                        h.sendResponseHeaders(200, 0);
                    }
                    break;
                default:
                    statusCode = 405;
                    response = "Передан неизвестный метод %d";
            }
            h.sendResponseHeaders(statusCode, 0);
            OutputStream os = h.getResponseBody();
            os.write(response.getBytes(DEFAULT_CHARSET));
            os.close();
        }
    };

    private HttpHandler SubTaskHandler = (h) -> {
        try (h) {
            String method = h.getRequestMethod();
            int statusCode = 200;
            String response = "";
            String query = h.getRequestURI().getQuery();
            InputStream inputStream = h.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Subtask subTasks = gson.fromJson(body, Subtask.class);
            switch (method) {
                case "GET":
                    if (query == null) {
                        response = gson.toJson(managerImpl.getAllSubtasks());
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        if (query.startsWith("id=")) {
                            String strId = query.split("id=")[1];
                            int id = Integer.parseInt(strId);
                            response = gson.toJson(managerImpl.getBySubTaskId(id));
                            h.sendResponseHeaders(200, 0);
                            try (OutputStream os = h.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        }
                    }
                    break;
                case "POST":
                    try {
                        Subtask subTask = gson.fromJson(body, Subtask.class);
                        managerImpl.createSubtask(subTask);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "PUT":
                    try {
                        Subtask subTask = gson.fromJson(body, Subtask.class);
                        managerImpl.updateSubtask(subTask);
                        h.sendResponseHeaders(200, 0);
                    } catch (JsonSyntaxException e) {
                        h.sendResponseHeaders(403, 0);
                    }
                    break;
                case "DELETE":
                    query = h.getRequestURI().getQuery();
                    if (query != null) {
                        String strId = query.split("id=")[1];
                        int id = Integer.parseInt(strId);
                        managerImpl.removeSubTaskId(id);
                        h.sendResponseHeaders(200, 0);
                    } else {
                        managerImpl.clearAll();
                        h.sendResponseHeaders(200, 0);
                    }
                    break;
                default:
                    statusCode = 405;
                    response = "Передан неизвестный метод %d";
            }
            h.sendResponseHeaders(statusCode, 0);
            OutputStream os = h.getResponseBody();
            os.write(response.getBytes(DEFAULT_CHARSET));
            os.close();
        }
    };

    private final HttpHandler HistoryHandler = (h) -> {
        System.out.println("Обработка запроса клиента /tasks/history");
        try (h) {
            String method = h.getRequestMethod();
            int statusCode = 200;
            String response = null;
            if (method.equals("GET")) {
                response = gson.toJson(managerImpl.history());
                System.out.println("GET-запрос обработан для списка истории");
            } else {
                h.sendResponseHeaders(405,0);
            }
            h.sendResponseHeaders(statusCode, 0);
            try (OutputStream os = h.getResponseBody()) {
                if (response == null) throw new AssertionError();
                os.write(response.getBytes(DEFAULT_CHARSET));
            }
        }
    };

    private final HttpHandler PrioritizedTasksHandler = (h) -> {
        System.out.println("Обработка запроса клиента /tasks");
        try (h) {
            String method = h.getRequestMethod();
            int statusCode = 200;
            String response = null;
            if (method.equals("GET")) {
                System.out.println("GET-запрос обработан для списка истории");
                response = gson.toJson(managerImpl.getPrioritizedTasks());
            } else {
                h.sendResponseHeaders(405,0);
            }
            h.sendResponseHeaders(statusCode, 0);
            OutputStream os = h.getResponseBody();
            assert response != null;
            os.write(response.getBytes(DEFAULT_CHARSET));
            os.close();
        }
    };
}