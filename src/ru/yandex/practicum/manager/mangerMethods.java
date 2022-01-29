package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Tasks;

public interface mangerMethods  {

    void addTask(Tasks tasks);

    void printTaskAll();

    Tasks gettingById(int id);

    void clearTask();



}
