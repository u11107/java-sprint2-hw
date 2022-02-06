package ru.yandex.practicum.task;

import java.util.ArrayList;
import java.util.Objects;

import static ru.yandex.practicum.task.Status.*;

public class Epic extends Task {
    private ArrayList<SubTasks> subTaskList;

    public Epic(Integer id, String title, String description, Status status) {
        super(id, title, description, NEW);
        this.subTaskList = new ArrayList<>();
    }

    public ArrayList<SubTasks> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<SubTasks> subTaskList) {
        this.subTaskList = subTaskList;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + this.getId() +
                ", title='" + this.getTitle() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Epic epic = (Epic) o;

        return subTaskList.equals(epic.subTaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubTaskList());
    }
}