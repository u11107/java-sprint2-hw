package ru.yandex.practicum.task;

import java.util.ArrayList;
import java.util.Objects;

import static ru.yandex.practicum.task.Status.NEW;

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
    public Status getStatus() {
        int newStatus = 0;
        int doneStatus = 0;
        if (subTaskList.size() == 0) {
            return Status.NEW;
        } else {
            for (SubTasks subTasks : subTaskList) {
                if (subTasks.getStatus() == Status.NEW) {
                    newStatus++;
                } else if (subTasks.getStatus() == Status.DONE) {
                    doneStatus++;

                }
            }
            if (subTaskList.size() == newStatus) {
                return Status.NEW;
            } else if (subTaskList.size() == doneStatus) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubTaskList());
    }
}