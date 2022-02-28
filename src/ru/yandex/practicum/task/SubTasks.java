package ru.yandex.practicum.task;

public class SubTasks extends Task {
    private int idFromEpic;

    public SubTasks(Integer id, String title, String description, Status status, Integer idFromEpic) {
        super(id, title, description, status);
        this.idFromEpic = idFromEpic;
    }

    public Integer getIdFromEpic() {
        return idFromEpic;
    }

    @Override
    public String toString() {
        return "SubTasks{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", idFromEpic=" + getIdFromEpic() +
                '}';
    }
}
