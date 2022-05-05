package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.api.*;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Subtask;
import ru.yandex.practicum.task.Task;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class HTTPTaskServerTest {
    private static KVServer kvServer;
    private static HttpTaskServer server;
    private static KVTaskClient taskClient;
    private TaskManager manager;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String urlServer = "http://localhost:8080";
    Task task1 = new Task(9, "Научиться учиться", "Яндекс помоги", Status.NEW,
            Duration.ofHours(3),
            LocalDateTime.of(2022, Month.MAY, 22, 12, 0));
    Epic epic1 = new Epic(3, "Тест", "Java");
    Subtask sub1 = new Subtask(4, "sub1", "description for sub1",
            Status.NEW, Duration.ofHours(4),
            LocalDateTime.of(2022, 5, 5, 8, 0), epic1.getId());


    static Gson gson;

    @BeforeAll
    static void beforeAll() {
        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder().
                registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .create();
    }

    @BeforeEach
    void beforeEach() throws IOException, InterruptedException {
        server = new HttpTaskServer();
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop();
    }

    @AfterAll
    static  void stopKvServer() {
        kvServer.stop();
    }

    @Test
    public void createTaskTest() {
        URI url = URI.create(urlServer + "/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(task1));
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            url = URI.create(urlServer + "/tasks/task?id=" + task1.getId());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void deleteTaskTest() {
        URI url = URI.create(urlServer + "/tasks/task?id=9");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAllTaskTest() {
        URI url = URI.create(urlServer + "/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void createEpicTest() {
        URI url = URI.create(urlServer + "/tasks/epic");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(epic1));
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            url = URI.create(urlServer + "/tasks/epic?id=" + epic1.getId());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            Epic epic = gson.fromJson(response.body(),Epic.class);
            assertEquals(epic1, epic);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void deleteEpicTest() {
        URI url = URI.create(urlServer + "/tasks/epic?id=3");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void deleteAllEpicTest() {
        URI url = URI.create(urlServer + "/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void createSubTaskTest() {
        URI url = URI.create(urlServer + "/tasks/subTask");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(sub1));
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            url = URI.create(urlServer + "/tasks/subTask?id=" + sub1.getId());
            request = HttpRequest.newBuilder().uri(url).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void deleteSubtask() {
        URI url = URI.create(urlServer + "/tasks/subTask?id=4");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void deleteAllSubtask() {
        URI url = URI.create(urlServer + "/tasks/subTask");
        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(url).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void getHistory() {
        URI url = URI.create(urlServer + "/tasks/history");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при запросе на сервер");
        }
    }

    @Test
    public void saveSubtask() {
        manager.createEpics(epic1);
        manager.createSubtask(sub1);
        String body = taskClient.load("/tasks?API_KEY=DEBUG");
        JsonArray jsonArray = JsonParser.parseString(body).getAsJsonArray();
        Epic epicSaved = gson.fromJson(jsonArray.get(0), Epic.class);
        Subtask subSaved1 = gson.fromJson(jsonArray.get(1), Subtask.class);
        assertEquals(epic1, epicSaved);
        assertEquals(sub1, subSaved1);
    }
}