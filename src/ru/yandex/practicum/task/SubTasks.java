package ru.yandex.practicum.task;

public class SubTasks extends Tasks {
    private Integer idFromEpic;

    public SubTasks(int id, String title, String description, int idFromEpic) {
        super(id, title, description, null);
        this.idFromEpic = idFromEpic;
    }

    public int getIdFromEpic() {
        return idFromEpic;
    }

    public void setIdFromEpic(int idFromEpic) {
        this.idFromEpic = idFromEpic;
    }

    @Override
    public String toString() {
        return "SubTasks{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() + '\'' +
                ", idFromEpic=" + this.getIdFromEpic() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubTasks subTasks = (SubTasks) o;

        return idFromEpic == subTasks.idFromEpic;
    }

    @Override
    public int hashCode() {
        return idFromEpic;
    }
}
