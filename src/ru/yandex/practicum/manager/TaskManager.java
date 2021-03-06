package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Subtask;
import ru.yandex.practicum.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    //создание задачи
    void createTasks(Task tasks);

    //получение всех задач
    ArrayList<Task> getAllTasks();

    //получение задачи по идентифкатору
    Task getByIdTask(int id);

    //удаление задач, эпиков, подзадач
    void clearAll();

    //удаление задачи по идентифкатору
    void removeTaskId(int id);

    //обновление задачи
    void updateTask(Task task);

    //создание эпика
    void createEpics(Epic epic);

    //получение всех эпиков
    ArrayList<Epic> getAllEpics();

    //обновление эпиков
    Object updateEpic(Epic epic);

    //удаление эпической задачи по идентификатору
    void removeEpicId(int id);

    //получение эпической задачи по идентификатору
    Epic getByEpicId(int id);

    //удаление эпических задач
    void clearEpic();

    //создание подзадачи
    void createSubtask(Subtask subTask);

    //удаление всех подзадач
    void clearAllSubTask();

    //получение всех подзадач
    ArrayList<Subtask> getAllSubtasks();

    //получение подзадачи по идентифкатору
    Subtask getBySubTaskId(int id);

    //получение подзадач включенных в эпик
    ArrayList<Subtask> getSubTasksByEpicId(int id);

    //удаление позадачи по идентифкатору
    void removeSubTaskId(int id);

    //обновление подзадачи
    void updateSubtask(Subtask SubTasks);

    //история
    List<Task> history();

    //получение всех простых задач
    HashMap<Integer, Task> getTasks();

    //получение всех эпических задач
    HashMap<Integer, Epic> getEpics();

    //получение всех подзадач
    HashMap<Integer, Subtask> getSubtasks();

    //получение задач по приоритету
    ArrayList<Task> getPrioritizedTasks();
}