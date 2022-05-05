package ru.yandex.practicum.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import static ru.yandex.practicum.task.Status.NEW;
import static ru.yandex.practicum.task.Status.DONE;
import static ru.yandex.practicum.task.Status.IN_PROGRESS;

public class Epic extends Task {
    private final ArrayList<Subtask> subTaskList;

    public Epic(Integer id, String title, String description) {
        super(id,title, description, null);
        this.subTaskList = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public Status getStatus() {
        int newStatus = 0;
        int doneStatus = 0;
        if (subTaskList.isEmpty()) {
            return NEW;
        } else {
            for (Subtask subTasks : subTaskList) {
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
    public LocalDateTime getStartTime() {
        if(subTaskList.isEmpty()) {
            return null;
        }
        LocalDateTime tmp = subTaskList.get(0).getStartTime();
        for (int i = 1; i < subTaskList.size(); i++) {
            if(subTaskList.get(i).getStartTime().isBefore(tmp)) {
                tmp = subTaskList.get(i).getStartTime();
            }
        }
        return tmp;
    }

    @Override
    public LocalDateTime getEndTime() {
       if(subTaskList.isEmpty()) {
           return null;
       }
       LocalDateTime tmp = subTaskList.get(0).getEndTime();
        for (int i = 1; i < subTaskList.size(); i++) {
            if(subTaskList.get(i).getEndTime().isAfter(tmp)) {
                tmp = subTaskList.get(i++).getEndTime();
            }
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskList=" + subTaskList +
                '}';
    }
}