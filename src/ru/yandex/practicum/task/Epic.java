package ru.yandex.practicum.task;

import java.util.ArrayList;

import static ru.yandex.practicum.task.Status.*;

public class Epic extends Task {
    private ArrayList<SubTasks> subTaskList;

    public Epic(Integer id, String title, String description) {
        super(id, title, description, NEW);
        this.subTaskList = new ArrayList<>();
    }

    public ArrayList<SubTasks> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public Status getStatus() {
        int newStatus = 0;
        int doneStatus = 0;
        if (subTaskList.isEmpty()) {
            return NEW;
        } else {
            for (SubTasks subTasks : subTaskList) {
                if (subTasks.getStatus() == NEW) {
                    newStatus++;
                } else if (subTasks.getStatus() == DONE) {
                    doneStatus++;
                }
            }
            if (subTaskList.size() == newStatus) {
                return NEW;
            } else if (subTaskList.size() == doneStatus) {
                return DONE;
            } else {
                return IN_PROGRESS;
            }
        }
    }

    @Override
    public String toString() {
        return getId() + "," + TaskType.EPIC + "," + getTitle() + "," + getDescription();
    }
}