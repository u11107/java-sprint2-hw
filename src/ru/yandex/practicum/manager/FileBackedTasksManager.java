package ru.yandex.practicum.manager;

import exception.ManagerSaveException;
import ru.yandex.practicum.task.*;
import ru.yandex.practicum.util.Managers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        super(Managers.getDefaultHistory());
        this.file = file;
    }

    public static void main(String[] args) throws ManagerSaveException {
        TaskManager manager = loadFromFile(new File("file6.csv"));
        Task task1 = new Task(manager.generateId(), "Купить билет", "Купить билет на метро", Status.NEW);
        Task task2 = new Task(manager.generateId(), "Купить билет", "Купить билет на метро", Status.NEW);
        Epic epic1 = new Epic(manager.generateId(), "Купить подарочную карту", "Зайти в магазаин купить " +
                "три подарочных карты");
        SubTasks sb1 = new SubTasks(manager.generateId(), "Заправить авто", "Помыть авто", Status.NEW,
                epic1.getId());
        manager.createTasks(task2);
        manager.createTasks(task1);
        manager.createEpics(epic1);
        manager.createSubtask(sb1);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        manager.getByIdTask(task1.getId());
        manager.getByEpicId(epic1.getId());


    }

    //метод сохранения
    public void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write("id,type,title,status,description,epic" + "\n");

            for (Task task : getAllTasks()) {
                writer.write(task + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(epic + "\n");
            }
            for (SubTasks subtask : getAllSubtasks()) {
                writer.write(subtask + "\n");
            }
            List<String> idHistory = new ArrayList<>();
            for (Task task : history()) {
                idHistory.add(String.valueOf(task.getId()));
            }
            writer.write("\n" + String.join(",", idHistory));
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка чтения файла.");
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Task fromString(String values) {
        Task task = null;
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        String[] element = values.split(",".trim());
        TaskType newType = TaskType.valueOf(element[1].trim());
        switch (newType) {
            case TASK:
                task = new Task(Integer.parseInt(element[0].trim()), element[2].trim(), element[4].trim(), Status.valueOf(element[1].trim()));
                break;
            case EPIC:
                task = new Epic(Integer.parseInt(element[0].trim()), element[2].trim(), element[4].trim());
                break;
            case SUBTASK:
                task = new SubTasks(Integer.parseInt(element[0].trim()), element[2].trim(), element[4].trim(), Status.valueOf(element[1].trim()),
                        Integer.parseInt(element[5].trim()));
                break;
        }
        return task;
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
                if (line.isEmpty()) {
                    line = br.readLine();
                    String[] history = line.split(",");
                    for (String id : history) {
                        fileBackedTasksManager.getByIdTask(Integer.parseInt(id));
                    }
                    break;
                }
                fileBackedTasksManager.fromString(line);
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
        Task task = super.getByIdTask(id);
        save();
        return task;
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
        Epic epic = super.getByEpicId(id);
        save();
        return epic;
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
        SubTasks subTask = super.getBySubTaskId(id);
        save();
        return subTask;
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
