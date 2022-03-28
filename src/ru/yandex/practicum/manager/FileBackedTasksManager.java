package ru.yandex.practicum.manager;

import exception.ManagerSaveException;
import ru.yandex.practicum.task.*;
import ru.yandex.practicum.util.Managers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        super(Managers.getDefaultHistory());
        this.file = file;
    }

    public static void main(String[] args) throws ManagerSaveException {
         TaskManager manager = loadFromFile(new File("file.csv"));
         Task task1 = new Task(manager.generateId(), "Купить билет", "Купить билет на метро", Status.NEW);
         Epic epic1 = new Epic(manager.generateId(), "Купить подарочную карту", "Зайти в магазаин купить три подарочных карты");
         SubTasks sb1 = new SubTasks(manager.generateId(), "Заправить авто", "Помыть авто",Status.NEW, epic1.getId());
         manager.createTasks(task1);
         manager.createEpics(epic1);
         manager.createSubtask(sb1);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
    }

    //метод сохранения
    public void save() {
        try {
            StringBuilder savedFile = new StringBuilder();
            savedFile.append("id,type,title,status,description,epic");
            savedFile.append("\n");
            for (Task task : tasks.values()) {
                savedFile.append(task.toString());
                savedFile.append("\n");
            }
            for (Task task : epics.values()) {
                savedFile.append(task.toString());
                savedFile.append("\n");
            }
            for (Task task : subtasks.values()) {
                savedFile.append(task.toString());
                savedFile.append("\n");
            }
            for (Task task : history()) {
                savedFile.append(task.getId().toString());
                savedFile.append("\n");
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(savedFile));
            } catch (IOException e) {
                throw new ManagerSaveException("Файл не может быть прочитан");
            }
        } catch (ManagerSaveException e) {
            System.err.println(e.getMessage());
        }
    }

    //сохранение
    public static String toString(HistoryManager manager) {
        List<Task> historyTask = manager.getHistory();
        StringBuilder historyId = new StringBuilder();
        historyId.append("\n");
        return historyId.toString();
    }

    //восстановления менеджера истории из CSV
    public static List<Integer> fromStringHistory(String value) {
        List<Integer> historyManagerId = new ArrayList<>();
        String[] idManager = value.split(",");
        for (String id : idManager) {
            historyManagerId.add(Integer.parseInt(id));
        }
        return historyManagerId;
    }

    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try(FileReader fr =new FileReader(file)) {
            BufferedReader br = new BufferedReader(fr);
            if(br.readLine() == null) {
                throw new IOException();
            }
            while(br.ready()) {
                String line = br.readLine();
                if(line.isEmpty()) {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл, будет оздан пустой менеджер");
        }
        return  fileBackedTasksManager;
    }



    @Override
    public List<Task> history() {
        return super.history();
    }

    @Override
    public Integer generateId() {
        return super.generateId();
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public HashMap<Integer, SubTasks> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public void createTasks(Task task) {
        super.createTasks(task);
        save();
    }

    @Override
    public Task getByIdTask(int id) {
        return super.getByIdTask(id);
    }

    @Override
    public void clearAll() {
        super.clearAll();
    }

    @Override
    public void removeTaskId(int id) {
        super.removeTaskId(id);
    }

    @Override
    public void updateTask(Task tasks) {
        super.updateTask(tasks);
    }

    @Override
    public void createEpics(Epic epic) {
        super.createEpics(epic);
        save();
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public void removeEpicId(int id) {
        super.removeEpicId(id);
    }

    @Override
    public void clearEpic() {
        super.clearEpic();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public Epic getByEpicId(int id) {
        return super.getByEpicId(id);
    }

    @Override
    public void createSubtask(SubTasks subTask) {
        super.createSubtask(subTask);
        save();
    }

    @Override
    public void clearAllSubTask() {
        super.clearAllSubTask();
    }

    @Override
    public ArrayList<SubTasks> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public SubTasks getBySubTaskId(int id) {
        return super.getBySubTaskId(id);
    }

    @Override
    public void removeSubTaskId(int id) {
        super.removeSubTaskId(id);
    }

    @Override
    public void updateSubtask(SubTasks subtask) {
        super.updateSubtask(subtask);
    }

    @Override
    public ArrayList<SubTasks> getSubTasksByEpicId(int id) {
        return super.getSubTasksByEpicId(id);
    }
}
