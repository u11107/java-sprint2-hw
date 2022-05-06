package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Subtask;
import ru.yandex.practicum.task.Task;
import ru.yandex.practicum.util.Managers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;
    private HistoryManager historyManager;
    protected static Integer id = 1;
    private TreeSet<Task> listTaskPriorities;

    InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        listTaskPriorities = new TreeSet<>(Comparator.comparing(Task::getStartTime,
                Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId));
    }

    public static Integer generateId() {
        return id++;
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        System.out.println("Получение задачи");
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        System.out.println("Получение эпика");
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        System.out.println("Получение подзадачи");
        return subtasks;
    }


    public ArrayList<Task> getPrioritizedTasks(){
       return new ArrayList<>(listTaskPriorities);
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
        listTaskPriorities.add(task);
        this.tasks.put(task.getId(), task);
    }

    @Override
    public Task getByIdTask(int id) {
        if (tasks.containsKey(id)) {
            System.out.println("Задача найдена");
        } else {
            System.out.println("Задача не найдена");
        }
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
            System.out.println("Задача добавлена в историю");
        } else {
            System.out.println("Задача не будет добавлена в историю");
        }
        return tasks.get(id);
    }

    @Override
    public void clearAll() {
        tasks.clear();
        listTaskPriorities.clear();
        epics.clear();
        subtasks.clear();
        historyManager = Managers.getDefaultHistory();
        System.out.println("Все задачи удалены");
    }

    @Override
    public void removeTaskId(int id) {
        if (tasks.get(id) != null) {
            listTaskPriorities.remove(tasks.get(id));
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
        if (epics.containsKey(epic.getId())) {
            System.err.println("Эпик с таким ключем уже существует");
            return;
        }
        if (epic.getId() == null) {
            System.err.println("Эпик с таким id уже существует");
            return;
        }
        epics.put(epic.getId(), epic);
        System.err.println("Новый эпик добавлен");
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
            for (Subtask i : epics.get(id).getSubTaskList()) {
                subtasks.remove(i.getId());
            }
        }
        historyManager.remove(id);
        epics.remove(id);
        System.out.println("Эпик  удален, с включенными в него подзадачами");
    }

    @Override
    public void clearEpic() {
        epics.clear();
        subtasks.clear();
        System.out.println("Удалены все эпики и подзадачи");
    }

    @Override
    public Object updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {

            System.out.println("Такого эпика не существует");
            return null;
        }
        epics.put(epic.getId(), epic);
        System.out.println("Эпик обновлен");
        return null;
    }

    @Override
    public Epic getByEpicId(int id) {
        if (epics.containsKey(id)) {
            System.out.println("Эпик найден");
        } else {
            System.out.println("Эпик не найден");
        }
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
            System.out.println("Эпик добавлен в историю");
        } else {
            System.out.println("Эпик не будет добавлен в историю");
        }
        return epics.get(id);
    }

    @Override
    public void createSubtask(Subtask subTask) {
        if (epics.containsKey(subTask.getIdFromEpic())) {
            subtasks.put(subTask.getId(), subTask);
            addSubTaskEpic(subTask);
            listTaskPriorities.add(subTask);
            updateSubtask(subTask);
            return;
        }
        System.out.println("Подзадача создана и добавлена в эпик");
    }

    private void addSubTaskEpic(Subtask subTask) {
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
        listTaskPriorities.remove(subtasks);
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskList().clear();
        }
        System.out.println("Все подзадачи удалены, статус эпика обновлен");

    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Subtask getBySubTaskId(int id) {
        if (subtasks.containsKey(id)) {
            System.out.println("Subtask найден");
        }
        if (subtasks.get(id) != null) {
            historyManager.add(subtasks.get(id));
            System.out.println("Subtask добавлен ы историю");
        } else {
            System.out.println("Subtask не будет добавлен в историю");
        }
        return subtasks.get(id);
    }

    @Override
    public void removeSubTaskId(int id) {
        if (subtasks.get(id) == null) {
            System.out.println("Ошибка, подзадачи с таким id нет!");
            return;
        }
        Subtask s = subtasks.remove(id);
        listTaskPriorities.remove(s);
        Epic epic = epics.get(s.getIdFromEpic());
        epic.getSubTaskList().remove(s);
        System.out.println("Подзадача удалена");
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getIdFromEpic())) {
            System.out.println("Ошибка. Указанный неверный id эпика.");
            return;
        }
        if (!this.subtasks.containsKey(subtask.getId())) {
            System.out.println("Ошибка. Передан несуществующий id сабтаска.");
            return;
        }

        epics.get(subtask.getIdFromEpic()).getSubTaskList().remove(this.subtasks.get(subtask.getId()));
        this.subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getIdFromEpic()).getSubTaskList().add(subtask);
        System.out.println("Сабтаск № " + subtask.getId() + " обновлен.");
    }

    @Override
    public ArrayList<Subtask> getSubTasksByEpicId(int id) {
        if (!epics.containsKey(id)) {
            System.out.println("Ошибка, эпика с таким id не существует");
            return new ArrayList<>();
        }
        return epics.get(id).getSubTaskList();
    }

    @Override
    public List<Task> history() {
        System.out.println("История просмотров" + historyManager.getHistory());
        return historyManager.getHistory();
    }
}