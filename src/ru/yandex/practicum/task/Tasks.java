package ru.yandex.practicum.task;



public class Tasks {
    private int id;
    private String title;
    private String description;
    private StatusTasks.Status status;
// конструктор обьекта


    public Tasks(int id, String title, String description, StatusTasks.Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = StatusTasks.Status.NEW;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTasks.Status getStatus() {
        return status;
    }

    public void setStatus(StatusTasks.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
