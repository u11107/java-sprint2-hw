package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    // создание нового tasks
    void createTasks(Task tasks);

    // вывод всех tasks
    ArrayList<Task> getAllTasks();

    // получение tasks по id
    Task getByIdTask(int id);

    // удаление всех task,epic, subtask
    void clearAll();

    //удаление Task по идентефикатору
    void removeTaskId(int id);

    // обновление списка Task
    void updateTask(Task task);

    // создание нового epic
    void createEpics(Epic epic);

    // вывод всех epic
    ArrayList<Epic> getAllEpics();

    // обновление epic
    void updateEpic(Epic epic);

    // удаление epic по id
    void removeEpicId(int id);

    // получение epic по id
    Epic getByEpicId(int id);

    //Удаление всех epic
    void clearEpic();

    // создание subTask
    void createSubtask(SubTasks subTask);

    // удаление subtask
    void clearAllSubTask();

    // вывод всех subtask
    ArrayList<SubTasks> getAllSubtasks();

    //получение subTask по id
    SubTasks getBySubTaskId(int id);

    //получение subTask определенного эпика
    ArrayList<SubTasks> getSubTasksByEpicId(int id);

    //Удаление subTask по id
    void removeSubTaskId(int id);

    //обновление subTask
    void updateSubtask(SubTasks SubTasks);

    Integer generateId();

    List<Task> history();
}