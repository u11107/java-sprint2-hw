package ru.yandex.practicum.task;

public class Task {
    protected Integer id;
    protected final String title;
    protected final String description;
    protected final Status status;

    public Task(Integer id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return getId() + "," + TaskType.TASK + "," + getTitle() + "," + getDescription() + "," + getStatus();
    }
}