package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Tasks;
import ru.yandex.practicum.task.SubTasks;

import java.util.ArrayList;
import java.util.HashMap;


public class Manager implements MangerMethods {
    HashMap<Integer, Tasks> task;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTasks> subtask;
    Integer id = 1;

    // генератор id
    public Integer generateId() {
        return id++;
    }


    public Manager() {
        task = new HashMap<Integer, Tasks>();
        epics = new HashMap<Integer, Epic>();
        subtask = new HashMap<Integer, SubTasks>();
    }

    //вывод всех tasks
    @Override
    public ArrayList<Tasks> getAllTasks() {
        return new ArrayList<>(task.values());
    }

    // создание новой задачи
    @Override
    public void createTasks(Tasks tasks){
        if(tasks.getId() == null){
            System.out.println("Ошибка, неверный ID");
            return;
        }
        if (task.containsKey(tasks.getId())){
            System.out.println("Задача с таким ключем уже существует");
            return;
        }
        tasks.setId(generateId());
        this.task.put(tasks.getId(), tasks);
    }

    //получение задачи по идентефикатору
    @Override
    public Tasks gettingById(Integer id) {
        if (task.containsKey(id)) {
            return task.get(id);
        }
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        if (subtask.containsKey(id)) {
            return subtask.get(id);
        }
        System.out.println("Нет задачи под этим номером");
        return null;
    }

    @Override
    // удаление всех задач
    public void clearAll() {
        task.clear();
        epics.clear();
        subtask.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    //удаление по идентефикатору
    public void removeTaskId(Integer id) {
        if (task.get(id) != null) {
            task.remove(id);
        } else {
            System.out.println("Нет задачи с таким номером!");
        }
        System.out.println("Задача удалена");
    }

    @Override
    // Обновление списка задач
    public void updateTask(Tasks tasks) {
        this.task.put(tasks.getId(), tasks);
    }

    @Override
    //добавление нового epic
    public void createEpics(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            System.out.println("Эпик с таким ключем уже существует");
            return;
        }
        if(epic.getId() == null) {
            System.out.println("Эпик с таким id уже существует");
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Новый эпик добавлен");
    }

    // вывод всех epic
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    //удаление epic по id
    public void removeEpicId(Integer id) {
        if(epics.get(id) == null) {
            System.out.println("Эпика с таким id не существует");
            return;
        }
        epics.remove(id);
        System.out.println("Эпик удален");
    }

    @Override
    //Удаление всех epic
    public void clearEpic() {
        epics.clear();
    }

    @Override
    //Обновление epic
    public void updateEpic(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            System.out.println("Такого эпика не сущетсвует");
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлен");
    }

    @Override
    // получение epic по id
    public Epic gettingByEpicId(Integer id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        System.out.println("Эпик не найден");
        return null;
    }

    @Override
    //создание subtask
    public void createSubtask(SubTasks subTask) {
        if (epics.containsKey(subTask.getIdFromEpic())) {
            subtask.put(subTask.getId(), subTask);
            epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);
        }
        addSubTaskEpic(subTask);
        System.out.println("Подзадача создана");
    }

    @Override
    //добавление subtask в epic
    public void addSubTaskEpic(SubTasks subTask) {
        if(subtask.containsKey(subTask.getIdFromEpic())) {
            System.out.println("Ошибка");
            return;
        }
        if(subTask.getIdFromEpic() != null) {
            System.out.println("Ошибка, эпика с таким id не существует, подзадачу невозможно добавить");
            return;
        }
        epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);
        System.out.println("Подзадачи добавлены в эпик");
    }

    @Override
    //удаление subtask
    public void clearSubTask() {
        subtask.clear();
    }

    // печать всех subtask
    @Override
    public ArrayList<SubTasks> getAllSubtasks() {
        return new ArrayList<>(subtask.values());
    }

    //получение subTask по id
    @Override
    public SubTasks gettingBySubTaskId(Integer id) {
        if (subtask.containsKey(id)) {
            return subtask.get(id);
        }
        System.out.println("Subtask не найден");
        return null;
    }

    //Удаление subTask по id
    @Override
    public void removeSubTaskId(Integer id) {
        subtask.remove(id);
        System.out.println("Подзадачи удалены");
    }

    //обновление subtask
    @Override
    public void updateStatus(SubTasks tasks) {
        if(subtask == null){
            System.out.println("Ошибка, такой подзадачи нет");
        }
        if(epics == null){
            System.out.println("Ошибка, невозможно обновить статус, эпика не существует");
        }
        int newStatus = 0;
        int doneStatus = 0;
        ArrayList<SubTasks> subTasksStatus = epics.get(tasks.getIdFromEpic()).getSubTaskList();
        for(SubTasks subTasks : subTasksStatus) {
            if(subTasks.getStatus() == Status.NEW) {
                newStatus++;
            } else if (subTasks.getStatus() == Status.DONE) {
                doneStatus++;

            }
        }
        if(newStatus == subTasksStatus.size()) {
            epics.get(tasks.getIdFromEpic()).setStatus(Status.NEW);
        } else if (doneStatus == subTasksStatus.size()) {
            epics.get(tasks.getIdFromEpic()).setStatus(Status.DONE);
        } else {
            epics.get(tasks.getIdFromEpic()).setStatus(Status.IN_PROGRESS);
        }
    }

}
