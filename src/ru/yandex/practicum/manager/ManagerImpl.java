package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Task;
import ru.yandex.practicum.task.SubTasks;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerImpl implements Manager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, SubTasks> subtask;
    Integer id = 1;

    // генератор id
    public Integer generateId() {
        return id++;
    }


    public ManagerImpl() {
        tasks = new HashMap<Integer, Task>();
        epics = new HashMap<Integer, Epic>();
        subtask = new HashMap<Integer, SubTasks>();
    }

    //вывод всех tasks
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // создание новой задачи
    @Override
    public void createTasks(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка, неверный ID");
            return;
        }
        task.setId(generateId());
        this.tasks.put(task.getId(), task);
    }

    //получение задачи по идентефикатору
    @Override
    public Task getByIdTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
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
        tasks.clear();
        epics.clear();
        subtask.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    //удаление по идентефикатору
    public void removeTaskId(int id) {
        if (tasks.get(id) != null) {
            tasks.remove(id);
        } else {
            System.out.println("Нет задачи с таким номером!");
        }
        System.out.println("Задача удалена");
    }

    @Override
    // Обновление task
    public void updateTask(Task tasks) {
        this.tasks.put(tasks.getId(), tasks);
    }

    @Override
    //создание нового epic
    public void createEpics(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            System.out.println("Эпик с таким ключем уже существует");
            return;
        }
        if (epic.getId() == null) {
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
    public void removeEpicId(int id) {
        if (epics.get(id) == null) {
            System.out.println("Эпика с таким id не существует");
            return;
        }
        if (epics.get(id).getSubTaskList().size() > 0) {
            for (SubTasks i : epics.get(id).getSubTaskList()) {
                subtask.remove(i.getId());
            }
        }
        epics.remove(id);
        System.out.println("Эпик удален, с включенными в него подзадачами");
    }


    @Override
    //Удаление всех epic
    public void clearEpic() {
        epics.clear();
        subtask.clear();
        System.out.println("Удалены все эпики и подзадачи");
    }

    @Override
    //Обновление epic
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            System.out.println("Такого эпика не сущетсвует");
            return;
        }
        epics.put(epic.getId(), epic);
        updateStatus(epic);
        System.out.println("Эпик обновлен");
    }

    @Override
    // получение epic по id
    public Epic gettingByEpicId(int id) {
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
            addSubTaskEpic(subTask);
            updateStatus(epics.get(subTask.getIdFromEpic()));
            return;
        }
        System.out.println("Подзадача создана и добавлена в эпик");
    }


    //добавление subtask в epic
    private void addSubTaskEpic(SubTasks subTask) {
        if (subtask.containsKey(subTask.getIdFromEpic())) {
            System.out.println("Ошибка");
            return;
        }
        if (subTask.getIdFromEpic() == null) {
            System.out.println("Ошибка, эпика с таким id не существует, подзадачу невозможно добавить");
            return;
        }
        epics.get(subTask.getIdFromEpic()).getSubTaskList().add(subTask);

        System.out.println("Подзадачи добавлены в эпик");
    }

    @Override
    //удаление всех subtask
    public void clearAllSubTask() {
        subtask.clear();
    }

    // получение всех subtask
    @Override
    public ArrayList<SubTasks> getAllSubtasks() {
        return new ArrayList<>(subtask.values());
    }

    //получение subTask по id
    @Override
    public SubTasks gettingBySubTaskId(int id) {
        if (subtask.containsKey(id)) {
            return subtask.get(id);
        }
        System.out.println("Subtask не найден");
        return null;
    }

    //Удаление subTask по id
    @Override
    public void removeSubTaskId(int id) {
        SubTasks s = subtask.remove(id);
        Epic epic = epics.get(s.getIdFromEpic());
        epic.getSubTaskList().remove(s);
        System.out.println("Подзадачи удалены");
        updateStatus(epics.get(s.getIdFromEpic()));
    }

    private void updateStatus(Epic epic) {
        int newStatus = 0;
        int doneStatus = 0;
        if (epic.getSubTaskList().size() == 0) {
            epic.setStatus(Status.NEW);
        } else {
            for (SubTasks subTasks : epic.getSubTaskList()) {
                if (subTasks.getStatus() == Status.NEW) {
                    newStatus++;
                } else if (subTasks.getStatus() == Status.DONE) {
                    doneStatus++;

                }
            }
            if (epic.getSubTaskList().size() == newStatus) {
                epic.setStatus(Status.NEW);
            } else if (epic.getSubTaskList().size() == doneStatus) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<SubTasks> gettingSubTasksByEpicId(int id) {
        return epics.get(id).getSubTaskList();
    }
}


