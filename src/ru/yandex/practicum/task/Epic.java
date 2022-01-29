package ru.yandex.practicum.task;

import java.util.ArrayList;

public class Epic extends Tasks {
    ArrayList<Integer> subTask;

    public Epic(int id, String title, String description, StatusTasks.Status status) {
        super(id, title, description, status);
        this.subTask = new ArrayList<Integer>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Epic epic = (Epic) o;

        return subTask.equals(epic.subTask);
    }

    @Override
    public int hashCode() {
        return subTask.hashCode();
    }
}
