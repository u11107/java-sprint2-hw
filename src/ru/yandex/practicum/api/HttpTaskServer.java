package ru.yandex.practicum.api;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.manager.HttpTaskManager;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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

    public static void main(String[] args) throws IOException, InterruptedException {
        new KVServer().start();
        new HttpTaskServer(managerImpl).start();


        Task test1 = new Task(1, "Научиться учиться", "Яндекс помоги", Status.NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 0));
        Epic test6 = new Epic(2, "Тест", "Java");
        SubTasks test7 = new SubTasks(4, "Завтра на работу",
                "А я сижу в 3 утра и пишу код", Status.NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 0), test6.getId());
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .create();
        System.out.println(gson.toJson(test7));
        System.out.println(gson.toJson(test6));
        System.out.println(gson.toJson(test1));
    }

    public HttpTaskServer(TaskManager managerImpl) throws IOException, InterruptedException {
        httpserver = HttpServer.create(new InetSocketAddress(PORT), 0);
        managerImpl = new HttpTaskManager("http://localhost:8090");
        httpserver.createContext("/tasks/task", new TaskHandler());
        httpserver.createContext("/tasks/epic", new EpicHandler());
        httpserver.createContext("/tasks/subTask", new EpicHandler.SubTaskHandler());
        httpserver.createContext("/tasks/history", new EpicHandler.SubTaskHandler.HistoryHandler());
        httpserver.createContext("/tasks/prioritized", new EpicHandler.SubTaskHandler.PrioritizedTasksHandler());

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


    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try (exchange) {
                String response = "";
                String method = exchange.getRequestMethod();
                String query = exchange.getRequestURI().getQuery();
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(Duration.class, new DurationAdapter())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                        .create();

                switch (method) {
                    case "POST":
                        try {
                            Task task = gson.fromJson(body, Task.class);
                            managerImpl.createTasks(task);
                            exchange.sendResponseHeaders(200, 0);
                        } catch (JsonSyntaxException e) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        break;
                    case "PUT":
                        try {
                            Task task = gson.fromJson(body, Task.class);
                            managerImpl.updateTask(task);
                            exchange.sendResponseHeaders(200, 0);
                        } catch (JsonSyntaxException e) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        break;
                    case "GET":
                        if (query == null) {
                            response = gson.toJson(managerImpl.getAllTasks());
                            exchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        } else {
                            if (query.startsWith("id=")) {
                                String strId = query.split("id=")[1];
                                int id = Integer.parseInt(strId);
                                response = gson.toJson(managerImpl.getByIdTask(id));
                                exchange.sendResponseHeaders(200, 0);
                                try (OutputStream os = exchange.getResponseBody()) {
                                    os.write(response.getBytes(StandardCharsets.UTF_8));
                                }
                            }
                        }
                        break;
                    case "DELETE":
                        if (query != null) {
                            String strId = query.split("id=")[1];
                            int id = Integer.parseInt(strId);
                            managerImpl.removeTaskId(id);
                            exchange.sendResponseHeaders(200, 0);
                        } else {
                            managerImpl.clearAll();
                            exchange.sendResponseHeaders(200, 0);
                        }
                        break;
                    default:
                        exchange.sendResponseHeaders(405, 0);
                }
            }
        }
    }

    static class EpicHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try (exchange) {
                String response = "";
                String method = exchange.getRequestMethod();
                String query = exchange.getRequestURI().getQuery();
                InputStream inputStream = exchange.getRequestBody();
                String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                Gson gson = new GsonBuilder().
                        registerTypeAdapter(Duration.class, new DurationAdapter())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                        .create();

                switch (method) {
                    case "POST":
                        try {
                            Epic epic = gson.fromJson(body, Epic.class);
                            managerImpl.createEpics(epic);
                            exchange.sendResponseHeaders(200, 0);
                        } catch (JsonSyntaxException e) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        break;
                    case "PUT":
                        try {
                            Epic epic = gson.fromJson(body, Epic.class);
                            managerImpl.updateEpic(epic);
                            exchange.sendResponseHeaders(200, 0);
                        } catch (JsonSyntaxException e) {
                            exchange.sendResponseHeaders(403, 0);
                        }
                        break;
                    case "GET":
                        if (query == null) {
                            response = gson.toJson(managerImpl.getAllEpics());
                            exchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        } else {
                            if (query.startsWith("id=")) {
                                String strId = query.split("id=")[1];
                                int id = Integer.parseInt(strId);
                                response = gson.toJson(managerImpl.getByEpicId(id));
                                exchange.sendResponseHeaders(200, 0);
                                try (OutputStream os = exchange.getResponseBody()) {
                                    os.write(response.getBytes(StandardCharsets.UTF_8));
                                }
                            }
                        }
                        break;
                    case "DELETE":
                        if (query != null) {
                            String strId = query.split("id=")[1];
                            int id = Integer.parseInt(strId);
                            response = gson.toJson(managerImpl.removeEpicId(id));
                            exchange.sendResponseHeaders(200, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes(StandardCharsets.UTF_8));
                            }
                        }
                        break;
                    default:
                        exchange.sendResponseHeaders(405, 0);
                }
            }
        }

        static class SubTaskHandler implements HttpHandler {

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try (exchange) {
                    String response = "";
                    String method = exchange.getRequestMethod();
                    String query = exchange.getRequestURI().getQuery();
                    InputStream inputStream = exchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(Duration.class, new DurationAdapter())
                            .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                            .create();

                    switch (method) {
                        case "POST":
                            try {
                                SubTasks subTask = gson.fromJson(body, SubTasks.class);
                                managerImpl.createSubtask(subTask);
                                exchange.sendResponseHeaders(200, 0);
                            } catch (JsonSyntaxException e) {
                                exchange.sendResponseHeaders(403, 0);
                            }
                            break;
                        case "PUT":
                            try {
                                SubTasks subTask = gson.fromJson(body, SubTasks.class);
                                managerImpl.updateSubtask(subTask);
                                exchange.sendResponseHeaders(200, 0);
                            } catch (JsonSyntaxException e) {
                                exchange.sendResponseHeaders(403, 0);
                            }
                            break;
                        case "GET":
                            if (query == null) {
                                response = gson.toJson(managerImpl.getAllSubtasks());
                                exchange.sendResponseHeaders(200, 0);
                                try (OutputStream os = exchange.getResponseBody()) {
                                    os.write(response.getBytes(StandardCharsets.UTF_8));
                                }
                            } else {
                                if (query.startsWith("id=")) {
                                    String strId = query.split("id=")[1];
                                    int id = Integer.parseInt(strId);
                                    response = gson.toJson(managerImpl.getBySubTaskId(id));
                                    exchange.sendResponseHeaders(200, 0);
                                    try (OutputStream os = exchange.getResponseBody()) {
                                        os.write(response.getBytes(StandardCharsets.UTF_8));
                                    }
                                }
                            }
                            break;
                        case "DELETE":
                            if (query != null) {
                                String strId = query.split("id=")[1];
                                int id = Integer.parseInt(strId);
                                response = gson.toJson(managerImpl.removeSubTaskId(id));
                                exchange.sendResponseHeaders(200, 0);
                                try (OutputStream os = exchange.getResponseBody()) {
                                    os.write(response.getBytes(StandardCharsets.UTF_8));
                                }
                            }
                            break;
                        default:
                            response = "Вы использовали неизвестный метод!";
                    }
                }
            }

            static class HistoryHandler implements HttpHandler {

                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String response = "";
                    String method = exchange.getRequestMethod();
                    String query = exchange.getRequestURI().getQuery();
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(Duration.class, new DurationAdapter())
                            .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                            .create();
                    if (method.equals("GET")) {
                        response = gson.toJson(managerImpl.history());
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }

            static class PrioritizedTasksHandler implements HttpHandler {

                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String response = "";
                    String method = exchange.getRequestMethod();
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(Duration.class, new DurationAdapter())
                            .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                            .create();
                    if (method.equals("GET")) {
                        response = gson.toJson(managerImpl.getPrioritizedTasks());
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                    }
                }
            }
        }
    }
}

