package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTasks> subtasks;
    private final HistoryManager historyManager;
    private Integer id = 1;

     InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<Integer, Task>();
        epics = new HashMap<Integer, Epic>();
        subtasks = new HashMap<Integer, SubTasks>();
    }

    @Override
    public Integer generateId() {
        return id++;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, SubTasks> getSubtasks() {
        return subtasks;
    }


    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void createTasks(Task task) {
        if (task.getId() == null) {
            System.out.println("Ошибка, неверный ID");
            return;
        }
        task.setId(generateId());
        this.tasks.put(task.getId(), task);
    }

    @Override
    public Task getByIdTask(int id) {
        historyManager.add(tasks.get(id));
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);

        }
        System.out.println("Нет задачи под этим номером");
        return null;
    }

    @Override
    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Все задачи удалены");
    }

    @Override
    public void removeTaskId(int id) {
        if (tasks.get(id) != null) {
            tasks.remove(id);
        } else {
            System.out.println("Нет задачи с таким номером!");
        }
        System.out.println("Задача удалена");
    }

    @Override
    public void updateTask(Task tasks) {
        this.tasks.put(tasks.getId(), tasks);
    }

    @Override
    public void createEpics(Epic epic) {
        historyManager.add(epics.get(id));
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

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeEpicId(int id) {
        if (epics.get(id) == null) {
            System.out.println("Эпика с таким id не существует");
            return;
        }
        if (!epics.get(id).getSubTaskList().isEmpty()) {
            for (SubTasks i : epics.get(id).getSubTaskList()) {
                subtasks.remove(i.getId());
            }
        }
        epics.remove(id);
        System.out.println("Эпик удален, с включенными в него подзадачами");
    }

    @Override
    public void clearEpic() {
        epics.clear();
        subtasks.clear();
        System.out.println("Удалены все эпики и подзадачи");
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {

            System.out.println("Такого эпика не существует");
            return;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлен");
    }

    @Override
    public Epic getByEpicId(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);

        }
        System.out.println("Эпик не найден");
        return null;
    }

    @Override
    public void createSubtask(SubTasks subTask) {
        if (epics.containsKey(subTask.getIdFromEpic())) {
            subtasks.put(subTask.getId(), subTask);
            addSubTaskEpic(subTask);
            updateSubtask(subTask);
            return;
        }
        System.out.println("Подзадача создана и добавлена в эпик");
    }


    private void addSubTaskEpic(SubTasks subTask) {
        if (subtasks.containsKey(subTask.getIdFromEpic())) {
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
    public void clearAllSubTask() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskList().clear();
        }
        System.out.println("Все подзадачи удалены, статус эпика обновлен");

    }

    @Override
    public ArrayList<SubTasks> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public SubTasks getBySubTaskId(int id) {
        historyManager.add(subtasks.get(id));
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        System.out.println("Subtask не найден");
        return null;
    }

    @Override
    public void removeSubTaskId(int id) {
        if(subtasks.get(id) == null) {
            System.out.println("Ошибка, подзадачи с таким id нет!");
            return;
        }
        SubTasks s = subtasks.remove(id);
        Epic epic = epics.get(s.getIdFromEpic());
        epic.getSubTaskList().remove(s);
        System.out.println("Подзадача удалена");


    }

    @Override
    public void updateSubtask(SubTasks subtask) {
        if (!epics.containsKey(subtask.getIdFromEpic())) {
            System.out.println("Ошибка. Указанный неверный id эпика.");
            return;
        }
        if (!this.subtasks.containsKey(subtask.getId())) {
            System.out.println("Ошибка. Передан несуществующий id сабтаска.");
            return;
        }
        if (this.subtasks.get(subtask.getId()).getIdFromEpic().equals(subtask.getIdFromEpic())) {
            System.out.println("Ошибка. Не совпадает id эпика.");
            return;
        }
        epics.get(subtask.getIdFromEpic()).getSubTaskList().remove(this.subtasks.get(subtask.getId()));
        this.subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getIdFromEpic()).getSubTaskList().add(subtask);
        System.out.println("Сабтаск № " + subtask.getId() + " обновлен.");
    }

    @Override
    public ArrayList<SubTasks> getSubTasksByEpicId(int id) {
        return epics.get(id).getSubTaskList();
    }

    @Override
    public List<Task> history() {
        System.out.println("История просмотров");
        return historyManager.getHistory();
    }
}