package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Tasks;
import ru.yandex.practicum.task.SubTasks;


import java.util.ArrayList;
import java.util.HashMap;

public class Manager implements MangerMethods {
    HashMap<Integer, Tasks> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTasks> subTasks;
    int id = 1;

    public int generateId() {
        return id++;
    }


    public Manager() {
        tasks = new HashMap<Integer, Tasks>();
        epics = new HashMap<Integer, Epic>();
        subTasks = new HashMap<Integer, SubTasks>();
    }

    //вывод всех tasks
    @Override
    public ArrayList<Tasks> getAllTasks() {
        return new ArrayList<> (tasks.values());
    }

    // создание новой задачи
    public void addTask(Tasks tasks) {
        this.tasks.put(tasks.getId(), tasks);
    }

    //получение задачи по идентефикатору
    public Tasks gettingById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        System.out.println("Нет задачи под этим номером");
            return null;
    }

    // удаление всех задач
    public void clearTask() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        System.out.println("Все задачи удалены");
    }

    //удаление по идентефикатору
    public void removeTaskId(int id) {
        if(tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Нет задачи с таким номером");
        }
    }

    // Обновление списка задач
    public void updateTask(Tasks tasks) {
        this.tasks.put(tasks.getId(), tasks);
    }

    //добавление нового epic
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // вывод всех epic
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //удаление epic по id
    public void removeEpicId(int id) {
        epics.remove(id);
    }

    //Удаление всех epic
    public void clearEpic() {
        epics.clear();
    }

    //Обновление epic
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // получение epic по id
    public Epic gettingByEpicId(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        System.out.println("Эпик не найден");
        return null;
    }

    //создание subtask
    public void addSubtask(SubTasks subTask) {
        if(epics.containsKey(subTask.getIdFromEpic())) {
            subTasks.put(subTask.getId(), subTask);
            epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);

        }
    }

    //добавление subtask в epic
    public void addSubTaskEpic(SubTasks subTask) {
        epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);
    }

    //удаление subtask
    public void clearSubTask() {
        subTasks.clear();
    }

    // печать всех subtask
    @Override
    public ArrayList<SubTasks> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    //получение subTask по id
    @Override
    public SubTasks gettingBySubTaskId(int id) {
        if(subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        System.out.println("Subtask не найден");
        return null;
    }

    //Удаление subTask по id
    @Override
    public void removeSubTaskId(int id) {
        subTasks.remove(id);
    }

    public void updateStatus(SubTasks status) {}

}