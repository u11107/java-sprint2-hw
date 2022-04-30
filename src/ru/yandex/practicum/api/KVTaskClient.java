package ru.yandex.practicum.api;

import ru.yandex.practicum.task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String url;
    private final String api_key;

    public KVTaskClient(String url) throws IOException, InterruptedException {
        this.url = url;
        URI uri = URI.create(url + "/register");
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            api_key = response.body();
        } else {
            throw new IOException("Ошибка регистрации. код ответа " + response.statusCode());
        }
    }


    public void put(String key, String json) {
        String saveString = String.format("%s/save/%s?API_KEY=%s", url, key, api_key);
        URI path = URI.create(saveString);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(path)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String load(String key) {
        String response = "";
        String saveString = url.concat(String.format("load/%s?API_KEY=%s", key, api_key));
        URI path = URI.create(saveString);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(path)
                .GET()
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

}





