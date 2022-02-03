package ru.yandex.practicum.task;

import java.util.ArrayList;

public class Epic extends Tasks {
   private ArrayList<SubTasks> subTaskList;


    public Epic(Integer id, String title, String description) {
        super(id, title, description, null);
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


}