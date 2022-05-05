package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.manager.InMemoryHistoryManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Subtask;
import ru.yandex.practicum.task.Task;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.yandex.practicum.manager.InMemoryTaskManager.generateId;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager inMemoryHistoryManager;
    private Task task;
    private Task task1;
    private Task task2;
    private Task task3;
    private Epic epic;
    private Subtask subTask;

    @BeforeEach
    public void createTaskHistoryManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        task = new Task(generateId(), "Изучить", "Java", Status.NEW);
        task1 = new Task(generateId(), "Изучить", "Java", Status.NEW);
        task2 = new Task(generateId(), "Понять", "Java", Status.NEW);
        task3 = new Task(generateId(), "Простить", "Java", Status.NEW);
        epic = new Epic(generateId(), "java", "Spring");
        subTask = new Subtask(generateId(), "Java", "Изучить", Status.NEW);
        inMemoryHistoryManager.remove(task1.getId());

        inMemoryHistoryManager.remove(task2.getId());
        inMemoryHistoryManager.remove(task3.getId());
    }

    @Test
    void shouldAdd() {
        inMemoryHistoryManager.add(task1);
        final List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void removeMiddle() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(epic);
        inMemoryHistoryManager.add(subTask);

        List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(3, history.size(), "История не пустая");
        assertEquals(task, history.get(0), "Задачи в порядке добавления");
        assertEquals(epic, history.get(1), "Задачи в порядке добавления");
        assertEquals(subTask, history.get(2), "Задачи в порядке добавления");

        inMemoryHistoryManager.remove(epic.getId());
        history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(2, history.size(), "История не пустая");
        assertEquals(task, history.get(0), "Задачи в порядке добавления");
        assertEquals(subTask, history.get(1), "Задачи в порядке добавления");
    }

    @Test
    void shouldGetHistory() {
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task1);
        final List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(3, history.size(), "История не пустая.");
    }
}
