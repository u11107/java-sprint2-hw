package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    //создание задачи
    void createTasks(Task tasks);

    //получение всех задач
    ArrayList<Task> getAllTasks();

    //получение задачи по id
    Task getByIdTask(int id);

    //удаление задач, эпиков, подзадач
    void clearAll();

    //удаление задачи по id
    void removeTaskId(int id);

    //обновление задачи
    void updateTask(Task task);

    //создание эпика
    void createEpics(Epic epic);

    //получение всех эпиков
    ArrayList<Epic> getAllEpics();

    //обновление эпиков
    void updateEpic(Epic epic);

    //удаление эпика по id
    void removeEpicId(int id);

    //получение эпика по id
    Epic getByEpicId(int id);

    //удаление всех эпиков
    void clearEpic();

    //созадние подзадачи
    void createSubtask(SubTasks subTask);

    //удаление всех подзадач
    void clearAllSubTask();

    //получение всех подзадач
    ArrayList<SubTasks> getAllSubtasks();

    //получение подзадачи по id
    SubTasks getBySubTaskId(int id);

    //получение подзадач включенных в эпик
    ArrayList<SubTasks> getSubTasksByEpicId(int id);

    //удаление позадачи
    void removeSubTaskId(int id);

    //обновление подзадачи
    void updateSubtask(SubTasks SubTasks);

    //генерация id
    Integer generateId();

    //исторя
    List<Task> history();

    //получение задач
    HashMap<Integer, Task> getTasks();

    //получение эпиков
    HashMap<Integer, Epic> getEpics();

    //получение подзадач
    HashMap<Integer, SubTasks> getSubtasks();
}