package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Tasks;
import ru.yandex.practicum.task.SubTasks;


import java.util.ArrayList;
import java.util.HashMap;

public class Manager implements MangerMethods {
    HashMap<Integer, Tasks> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTasks> subTasks;
    Integer id = 1;

    public Integer generateId() {
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
        return new ArrayList<>(tasks.values());
    }

    // создание новой задачи
    @Override
    public void createTasks(Tasks tasks){
        tasks.setId(generateId());
        this.tasks.put(tasks.getId(), tasks);
    }

    //получение задачи по идентефикатору
    @Override
    public Tasks gettingById(Integer id) {
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

    @Override
    // удаление всех задач
    public void clearTask() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    //удаление по идентефикатору
    public void removeTaskId(Integer id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Нет задачи с таким номером");
        }
    }

    @Override
    // Обновление списка задач
    public void updateTask(Tasks tasks) {
        this.tasks.put(tasks.getId(), tasks);
    }

    @Override
    //добавление нового epic
    public void createEpics(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    // вывод всех epic
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    //удаление epic по id
    public void removeEpicId(Integer id) {
        epics.remove(id);
    }

    @Override
    //Удаление всех epic
    public void clearEpic() {
        epics.clear();
    }

    @Override
    //Обновление epic
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
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
            subTasks.put(subTask.getId(), subTask);
            epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);

        }
    }

    @Override
    //добавление subtask в epic
    public void addSubTaskEpic(SubTasks subTask) {
        epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);

    }

    @Override
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
    public SubTasks gettingBySubTaskId(Integer id) {
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        System.out.println("Subtask не найден");
        return null;
    }

    //Удаление subTask по id
    @Override
    public void removeSubTaskId(Integer id) {
        subTasks.remove(id);
    }

    //обновление subtask
    @Override
    public void updateStatus(SubTasks tasks) {
        int newStatus = 0;
        int doneStatus = 0;
        ArrayList<SubTasks> subTasksStatus = epics.get(tasks.getIdFromEpic()).getSubTaskList(); //получение данных id эпика и данных bp списка
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
