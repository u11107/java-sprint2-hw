import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Status;
import ru.yandex.practicum.task.SubTasks;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.manager.InMemoryTaskManager.generateId;

class EpicTest {
    private Epic testEpic;
    private SubTasks testNewSubtask;
    private SubTasks testNew1Subtask;
    private SubTasks testDoneSubtask;
    private SubTasks testDone1Subtask;
    private SubTasks testInProgressSubtask;
    private SubTasks testInProgress1Subtask;

    @BeforeEach
    private void beforeEachCreateEpicAndSubtasks() {
        testEpic = new Epic(generateId(),"Переобуть автомобиль","Забрать резину");
        testNewSubtask = new SubTasks(generateId(),"Купить билет","Назначить время", Status.NEW);
        testNew1Subtask = new SubTasks(generateId(),"Успеть на тренировку","Назначить время", Status.NEW);
        testDoneSubtask = new SubTasks(generateId(),"Успеть на тренировку","Назначить время", Status.DONE);
        testDone1Subtask = new SubTasks(generateId(),"Купить билет","Назначить время", Status.DONE);
        testInProgressSubtask = new SubTasks(generateId(),"Успеть на тренировку","Назначить время", Status.IN_PROGRESS);
        testInProgress1Subtask = new SubTasks(generateId(),"Купить билет","Назначить время", Status.IN_PROGRESS);
    }

    @Test
    public void shouldEmptyListOfSubtasks() {
        assertTrue(testEpic.getSubTaskList().isEmpty());
    }

    @Test
    public void shouldAllSubtasksStatusNEW() {
        testEpic.getSubTaskList().add(testNewSubtask);
        testEpic.getSubTaskList().add(testNew1Subtask);
        assertEquals(Status.NEW, testEpic.getStatus());
    }

    @Test
    public void shouldAllSubtasksStatusDONE() {
        testEpic.getSubTaskList().add(testDoneSubtask);
        testEpic.getSubTaskList().add(testDone1Subtask);
        testDoneSubtask.setStatus(Status.DONE);
        testDone1Subtask.setStatus(Status.DONE);
        assertEquals(Status.DONE, testEpic.getStatus());
    }

    @Test
    public void shouldAllSubtasksStatusIN_PROGRESS() {
        testEpic.getSubTaskList().add(testInProgressSubtask);
        testEpic.getSubTaskList().add(testInProgress1Subtask);
        testInProgressSubtask.setStatus(Status.IN_PROGRESS);
        testInProgress1Subtask.setStatus(Status.IN_PROGRESS);
        boolean IN_Progress = false;
        for (SubTasks epicSubtask : testEpic.getSubTaskList()) {
            if (epicSubtask.getStatus().equals(Status.IN_PROGRESS)) {
                IN_Progress = true;
                break;
            }
        }
        assertTrue(IN_Progress);
    }

    @Test
    public void shouldAllSubtasksStatusDONE_AND_NEW() {
        testEpic.getSubTaskList().add(testNewSubtask);
        testEpic.getSubTaskList().add(testDoneSubtask);
        testDoneSubtask.setStatus(Status.DONE);
        boolean New = false;
        boolean Done = false;
        for (SubTasks epicSubtask : testEpic.getSubTaskList()) {
            if (epicSubtask.getStatus().equals(Status.NEW))
                New = true;
            else if (epicSubtask.getStatus().equals(Status.DONE))
                Done = true;
        }
        assertTrue(New && Done);
    }
}