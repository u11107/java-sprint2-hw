package ru.task.manager.Task;

import java.util.ArrayList;

public class Epic extends Task {
    //создали список подзадач в классе Epic
    ArrayList<Integer> subTask;
    // конструктор
    public Epic(int id, String title, String description) {
        super(id, title, description);
        this.subTask = new ArrayList<>();
    }

    public Epic(int id, String title, String description, String status) {
        super(id, title, description, status);
    }

    // получили значение
    public ArrayList<Integer> getSubTask() {
        return subTask;
    }
    // назначили значени
    public void setSubTask(ArrayList<Integer> subTask) {
        this.subTask = subTask;
    }
}
