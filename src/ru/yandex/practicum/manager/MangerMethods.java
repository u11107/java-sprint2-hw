package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Tasks;

import java.util.ArrayList;



public interface MangerMethods  {

    // создание нового tasks
    void createTasks(Tasks tasks);
    // вывод всех tasks
    ArrayList<Tasks> getAllTasks();
    // получение tasks по id
    Tasks gettingById(Integer id);
    // удаление всех task,epic, subtask
    void clearAll();
    //удаление Task по идентефикатору
    void removeTaskId(Integer id);
    // обновление списка Task
    void updateTask(Tasks task);

    // создание нового epic
    void createEpics(Epic epic);
    // вывод всех epic
    ArrayList<Epic> getAllEpics();
    // обновление epic
    void updateEpic(Epic epic);
    // удаление epic по id
    void removeEpicId(Integer id);
    // получение epic по id
    Epic gettingByEpicId(Integer id);
    //Удаление всех epic
    void clearEpic();

    // создание subTask
    void createSubtask(SubTasks subTask);
    // удаление subtask
    void clearSubTask();
    // вывод всех subtask
    ArrayList<SubTasks> getAllSubtasks();
    //получение subTask по id
    SubTasks gettingBySubTaskId(Integer id);
    //Удаление subTask по id
    void removeSubTaskId(Integer id);
    //добавленеи subtask  в epic
    void addSubTaskEpic(SubTasks subTask);
    //обновление subtask
    void updateStatus(SubTasks status);

}