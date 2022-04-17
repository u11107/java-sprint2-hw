package ru.yandex.practicum.manager;

import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;
import ru.yandex.practicum.util.Managers;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, SubTasks> subtasks;
    protected HistoryManager historyManager;
    protected static Integer id = 1;
    protected Set<Task> listTaskPriorities;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
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
    public HashMap<Integer, SubTasks> getSubtasks() {
        System.out.println("Получение подзадачи");
        return subtasks;
    }


    Comparator<Task> comparator = (o1, o2) -> {
        if (Objects.equals(o1.getId(), o2.getId())) return 0;
        if (o1.getStartTime() == null) return 1;
        if (o2.getStartTime() == null) return -1;
        if (o1.getStartTime().compareTo(o2.getStartTime()) == 0)
            return 1;
        return o1.getStartTime().compareTo(o2.getStartTime());
    };

    public ArrayList<Task> getPrioritizedTasks(){
        listTaskPriorities = new TreeSet<>(comparator);
        listTaskPriorities.addAll(getAllSubtasks());
        listTaskPriorities.addAll(getAllTasks());
        return new ArrayList<> (listTaskPriorities);
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
        epics.clear();
        subtasks.clear();
        historyManager = Managers.getDefaultHistory();
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
    public byte updateTask(Task tasks) {
        this.tasks.put(tasks.getId(), tasks);
        return 0;
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
            for (SubTasks i : epics.get(id).getSubTaskList()) {
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

        epics.get(subtask.getIdFromEpic()).getSubTaskList().remove(this.subtasks.get(subtask.getId()));
        this.subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getIdFromEpic()).getSubTaskList().add(subtask);
        System.out.println("Сабтаск № " + subtask.getId() + " обновлен.");
    }

    @Override
    public ArrayList<SubTasks> getSubTasksByEpicId(int id) {
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