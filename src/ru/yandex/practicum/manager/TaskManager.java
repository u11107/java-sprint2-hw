package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    void createTasks(Task tasks);

    ArrayList<Task> getAllTasks();

    Task getByIdTask(int id);

    void clearAll();

    void removeTaskId(int id);

    void updateTask(Task task);

    void createEpics(Epic epic);

    ArrayList<Epic> getAllEpics();

    void updateEpic(Epic epic);

    void removeEpicId(int id);

    Epic getByEpicId(int id);

    void clearEpic();

    void createSubtask(SubTasks subTask);

    void clearAllSubTask();

    ArrayList<SubTasks> getAllSubtasks();

    SubTasks getBySubTaskId(int id);

    ArrayList<SubTasks> getSubTasksByEpicId(int id);

    void removeSubTaskId(int id);

    void updateSubtask(SubTasks SubTasks);

    Integer generateId();

    List<Task> history();

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, SubTasks> getSubtasks();
}