package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.manager.InMemoryHistoryManager;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.manager.InMemoryTaskManager.generateId;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager inMemoryHistoryManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void createTaskHistoryManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        task1 = new Task(generateId(), "Изучить", "Java", Status.NEW);
        task2 = new Task(generateId(), "Понять", "Java", Status.NEW);
        task3 = new Task(generateId(), "Простить", "Java", Status.NEW);
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
    void shouldRemove() {
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.remove(task2.getId());
        final List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(0, history.size(), "История не пустая.");
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
