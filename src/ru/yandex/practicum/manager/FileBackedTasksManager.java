package ru.yandex.practicum.manager;

import ru.yandex.practicum.exception.ManagerSaveException;
import ru.yandex.practicum.task.*;
import ru.yandex.practicum.util.Managers;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        super(Managers.getDefaultHistory());
         this.file = file;
    }

    public static void main(String[] args){
        TaskManager manager = loadFromFile(new File("file24.csv"));
        Task task1 = new Task(generateId(),"Купить билет", "Купить билет на метро", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.JANUARY, 1, 1, 0));
        Task task2 = new Task(generateId(),"Купить билет", "Купить билет на метро", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.AUGUST, 22, 2, 0));
        Task task3 = new Task(generateId(),"Купить билет", "Купить билет на метро", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.FEBRUARY, 23, 3, 0));
        Epic epic1 = new Epic(generateId(), "Купить подарочную карту", "Зайти в магазин купить " +
                "три подарочных карты");
        SubTasks sb1 = new SubTasks(generateId(), "Заправить авто", "Помыть авто", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.APRIL, 1, 10, 0),
                epic1.getId());
        SubTasks sb2 = new SubTasks(generateId(), "Заправить авто", "Помыть авто", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.FEBRUARY, 1, 12, 0),
                epic1.getId());
        SubTasks sb3 = new SubTasks(generateId(), "Заправить авто", "Помыть авто", Status.NEW,
                Duration.ofHours(2), LocalDateTime.of(2022, Month.MAY, 1, 10, 0),
                epic1.getId());
        manager.createTasks(task2);
        manager.createTasks(task3);
        manager.createTasks(task1);
        manager.createEpics(epic1);
        manager.createSubtask(sb1);
        manager.createSubtask(sb2);
        manager.createSubtask(sb3);
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
        manager.getByIdTask(task1.getId());
        manager.getByEpicId(epic1.getId());
        manager.getPrioritizedTasks();
        System.out.println(manager.getPrioritizedTasks());
    }

    //метод сохранения
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))  {
                writer.write("id,type,title,status,description,epic,duration,startTime" + "\n");
                for (Task task : getAllTasks()) {
                    writer.write(toStringTask(task));
                    writer.newLine();
                }
                for (Epic epic : getAllEpics()) {
                    writer.write(String.format(toStringEpic(epic)));
                    writer.newLine();
                }
                for (SubTasks subtask : getAllSubtasks()) {
                    writer.write(toStringSubtask(subtask));
                    writer.newLine();
                }
                List<String> idHistory = new ArrayList<>();
                for (Task task : history()) {
                    idHistory.add(String.valueOf(task.getId()));
                }
                writer.write("\n" + String.join(",", idHistory));

        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Can't save to file: " + file.getName(), e);
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Task fromString(String values) {
        Task task = null;
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        String[] element = values.split(",".trim());
        TaskType newType = TaskType.valueOf(element[1].trim());
        switch (newType) {
            case TASK:
                task= new Task(element[2].trim(), element[4].trim(),
                Status.valueOf(element[3].trim()));
                fileBackedTasksManager.createTasks(task);
                break;
            case EPIC:
                Epic epic = new Epic(Integer.parseInt(element[0].trim()), element[2].trim(), element[4].trim());
                fileBackedTasksManager.createEpics(epic);
                break;
            case SUBTASK:
                SubTasks subTask = new SubTasks(Integer.parseInt(element[0].trim()), element[2].trim(), element[4].trim(),
                Status.valueOf(element[3].trim()),
                        Integer.parseInt(element[5].trim()));
                fileBackedTasksManager.createSubtask(subTask);
                break;
        }
        return task;
    }

    private static FileBackedTasksManager loadFromFile(File file){
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
                    if(line == null) {
                        break;
                    }
                    String[] history = line.split(",");
                    for (String id : history) {
                        fileBackedTasksManager.getByIdTask(Integer.parseInt(id));
                    }
                    break;
                }
                fileBackedTasksManager.fromString(line);

            }
        } catch(IOException e) {
            try {
                throw new ManagerSaveException("Can't save to file: " + file.getName(), e);
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }
        return  fileBackedTasksManager;
    }

    public static String toStringTask(Task task) {
        return task.getId() + "," + TaskType.TASK + "," + task.getTitle() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getDuration() + "," + task.getStartTime();
    }

    public static String toStringEpic(Epic epic) {
        return epic.getId() + "," + TaskType.EPIC + ","  + epic.getTitle() + "," + epic.getStatus() + ","
                + epic.getDescription() + "," + epic.getEndTime();
    }

    public static String toStringSubtask(SubTasks subTasks) {
        return subTasks.getId() + "," + TaskType.SUBTASK + "," + subTasks.getTitle() + "," + subTasks.getStatus() + ","
                + subTasks.getDescription() + "," + subTasks.getDuration()
                + "," + subTasks.getStartTime() + ","
                + subTasks.getIdFromEpic();
    }

    @Override
    public List<Task> history() {
        return super.history();
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
    public byte updateTask(Task tasks) {
        super.updateTask(tasks);
        return 0;
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
    public Object updateEpic(Epic epic) {
        super.updateEpic(epic);
        return null;
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