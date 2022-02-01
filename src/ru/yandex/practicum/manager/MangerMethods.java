package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Tasks;

import java.util.ArrayList;
import java.util.Collection;


public interface MangerMethods  {

// создание нового tasks
    void addTask(Tasks tasks);
// вывод всех tasks
    ArrayList<Tasks> getAllTasks();
// получение tasks по id
    Tasks gettingById(int id);
// удаление всех task,epic, subtask
    void clearTask();
//удаление Task по идентефикатору
    void removeTaskId(int id);
// обновление списка Task
    void updateTask(Tasks task);

// создание нового epic
    void addEpic(Epic epic);
// вывод всех epic
    ArrayList<Epic> getAllEpics();
// обновление epic
    void updateEpic(Epic epic);
// удаление epic по id
    void removeEpicId(int id);
// получение epic по id
    Epic gettingByEpicId(int id);

// создание subTask
    void addSubtask(SubTasks subTasks);
// удаление subtask
    void clearSubTask();
// вывод всех subtask
    ArrayList<SubTasks> getAllSubtasks();
//получение subTask по id
    SubTasks gettingBySubTaskId(int id);
//Удаление subTask по id
    void removeSubTaskId(int id);





}