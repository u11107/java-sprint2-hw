package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.manager.InMemoryTaskManager.generateId;

public abstract  class TaskManagerTest<T extends TaskManager> {
    T managerImpl;
    abstract void setManager();

    @BeforeEach
    void beforeEach() {
        setManager();
    }

    @Test
    void createTasks() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  null,
                LocalDateTime.of( 2022,
                01,01,01,01));
        managerImpl.createTasks(test1);
        Task tasksId = managerImpl.getByIdTask(test1.getId());
        assertEquals(test1.getId(), tasksId.getId(),"Id задач разные");
        assertEquals("Убрать", tasksId.getDescription(),"Ошибка в описании");
        assertEquals("Купить мыло", tasksId.getTitle(), "Ошибка в заголовке");
        assertEquals(Status.NEW, tasksId.getStatus(), "Ошибка в статусе");
        assertEquals("2022-01-01T01:01", tasksId.getStartTime().toString(), "Ошибка в времени");
    }

    @Test
    void getAllTasks() {
        managerImpl.clearAll();
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  null,
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        Task[] expectedAllTask = new Task[]{test1};
        Task[] allTask = managerImpl.getAllTasks().toArray(Task[]::new);
        assertArrayEquals(expectedAllTask, allTask);
    }

    @Test
    void getByIdTask() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  null,
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        assertEquals(test1,managerImpl.getByIdTask(test1.getId()));
    }

    @Test
    void clearAll() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01));
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createTasks(test1);
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        managerImpl.clearAll();
        assertTrue(managerImpl.getAllTasks().isEmpty());
        assertTrue(managerImpl.getAllEpics().isEmpty());
        assertTrue(managerImpl.getAllSubtasks().isEmpty());
    }

    @Test
    void removeTaskId() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  null,
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        managerImpl.removeTaskId(test1.getId());
        assertTrue(managerImpl.getAllTasks().isEmpty());
    }

    @Test
    void updateTask() {
        Task test3 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW, Duration.ofHours(2),
                LocalDateTime.of(2022, 01, 1, 10, 0));
        managerImpl.createTasks(test3);
        managerImpl.updateTask(new Task(test3.getId(),"Купить","Убрать", Status.NEW,Duration.ofHours(2),
                LocalDateTime.of(2022, 01, 1, 10, 0)));
        Task expectedTask = new Task(test3.getId(),"Купить", "Убрать", Status.NEW,
                Duration.ofHours(2),  LocalDateTime.of(2022, 01, 1, 10, 0));
        assertEquals(expectedTask,managerImpl.getByIdTask(test3.getId()));
    }

    @Test
    void createEpics() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        Epic[] expectedAllEpics = new Epic[]{epic};
        Epic[] allEpic = managerImpl.getAllEpics().toArray(Epic[]::new);
        assertArrayEquals(expectedAllEpics, allEpic);
    }

    @Test
    void getAllEpics() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        Epic[] expectedAllEpics = new Epic[]{epic};
        Epic[] allEpic = managerImpl.getAllEpics().toArray(Epic[]::new);
        assertArrayEquals(expectedAllEpics, allEpic);
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        Assertions.assertNull(managerImpl.updateEpic( new Epic(generateId(),"Купить", "Забрать")));
    }

    @Test
    void removeEpicId() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        managerImpl.removeEpicId(epic.getId());
        assertTrue(managerImpl.getAllEpics().isEmpty());
    }

    @Test
    void getByEpicId() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        assertEquals(epic, managerImpl.getByEpicId(epic.getId()));
    }

    @Test
    void clearEpic() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic);
        managerImpl.clearEpic();
        assertTrue(managerImpl.getAllEpics().isEmpty());
    }

    @Test
    void createSubtask() {
        Epic epic12 = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic12);
        SubTasks subTask12 = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic12.getId());
        managerImpl.createSubtask(subTask12);
        SubTasks subTasksTest = managerImpl.getBySubTaskId(subTask12.getId());
        assertEquals(subTask12.getId(), subTasksTest.getId(),"Id задач разные");
        assertEquals("Сдать проект", subTasksTest.getDescription(),"Ошибка в описании");
        assertEquals("Изучить java", subTasksTest.getTitle(), "Ошибка в заголовке");
        assertEquals(Status.NEW, subTasksTest.getStatus(), "Ошибка в статусе");
        assertEquals(epic12.getId(), subTask12.getIdFromEpic(), "Ошибка в id эпика");
        assertEquals(Duration.ofHours(3),subTasksTest.getDuration(),"Ошибка в duration");
        assertEquals(LocalDateTime.of( 2022,
                01,01,01,01),subTasksTest.getStartTime(),"Ошибка в LocalTime");
    }

    @Test
    void clearAllSubTask() {
        Epic epic1 = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic1);
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(2), LocalDateTime.of(2022, 01, 1, 10, 0), epic1.getId());
        managerImpl.createSubtask(subTask);
        managerImpl.clearAll();
        assertTrue(managerImpl.getAllSubtasks().isEmpty());
    }

    @Test
    void getAllSubtasks() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        SubTasks[] expectedAllSubtasks = new SubTasks[]{subTask};
        SubTasks[] allSubtasks = managerImpl.getAllSubtasks().toArray(SubTasks[]::new);
        assertArrayEquals(expectedAllSubtasks, allSubtasks);

    }

    @Test
    void getBySubTaskId() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(2),  LocalDateTime.of(2022, 01, 1, 10, 0), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        assertEquals(subTask, managerImpl.getBySubTaskId(subTask.getId()));
    }

    @Test
    void getSubTasksByEpicId() {
        Epic epic1 = new Epic(generateId(),"Забрать", "Покупку");
        managerImpl.createEpics(epic1);
        SubTasks subTask1 = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW,  Duration.ofHours(2),  LocalDateTime.of(2022, 01, 1, 10, 0), epic1.getId());
        managerImpl.createSubtask(subTask1);
        assertEquals(List.of(subTask1), managerImpl.getSubTasksByEpicId(epic1.getId()));
    }

    @Test
    void removeSubTaskId() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        managerImpl.removeSubTaskId(subTask.getId());
        assertTrue(managerImpl.getAllSubtasks().isEmpty());
    }

    @Test
    void updateSubtask() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        managerImpl.updateSubtask(new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, epic.getId()));
    }

    @Test
    void history() {
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        managerImpl.getByEpicId(epic.getId());
        managerImpl.getBySubTaskId(subTask.getId());
        assertEquals(List.of(epic,subTask), managerImpl.history());
    }

    @Test
    void getTasks() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        assertFalse(managerImpl.getTasks().isEmpty());
    }

    @Test
    void getEpics() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);
        managerImpl.createSubtask(subTask);
        assertFalse(managerImpl.getEpics().isEmpty());
    }

    @Test
    void getSubtasks() {
        Task test1 = new Task(generateId(),"Купить мыло", "Убрать", Status.NEW,  Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01));
        managerImpl.createTasks(test1);
        Epic epic = new Epic(generateId(),"Забрать", "Покупку");
        SubTasks subTask = new SubTasks(generateId(),"Изучить java", "Сдать проект",
                Status.NEW, Duration.ofHours(3),
                LocalDateTime.of( 2022,
                        01,01,01,01), epic.getId());
        managerImpl.createEpics(epic);managerImpl.createSubtask(subTask);
        assertFalse(managerImpl.getSubtasks().isEmpty());
    }
}