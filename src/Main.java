import ru.yandex.practicum.exception.ManagerSaveException;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;
import ru.yandex.practicum.util.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static ru.yandex.practicum.manager.InMemoryTaskManager.generateId;
import static ru.yandex.practicum.task.Status.DONE;
import static ru.yandex.practicum.task.Status.NEW;

public class Main {
    private static final TaskManager managerImpl = Managers.getDefault(Managers.getDefaultHistory());

    public static void main(String[] args) throws ManagerSaveException {
        System.out.println("Время практики");
        System.out.println("Создаем задачу");

        Task test1 = new Task(generateId(), "Научиться учиться", "Яндекс помоги", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test2 = new Task(generateId(), "Яндекс помоги", "учиться", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test3 = new Task(generateId(), "Научиться", "помоги", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test4 = new Task(generateId(), "учиться", "Яндекс ", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test5 = new Task(generateId(), "Научиться", "помоги", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        managerImpl.createTasks(test1);
        managerImpl.getByIdTask(test2.getId());
        managerImpl.createTasks(test2);
        managerImpl.createTasks(test3);
        managerImpl.createTasks(test4);
        managerImpl.createTasks(test5);
        System.out.println("Обновляем задачу");
        managerImpl.updateTask(test1 = new Task("123учиться", "Яндекс ", NEW));
        printAll();
        System.out.println("Получение задачи по id");
        System.out.println(managerImpl.getByIdTask(test2.getId()));
        System.out.println("Удаление задачи по id");
        managerImpl.removeTaskId(test2.getId());
        System.out.println(managerImpl.getByIdTask(test2.getId()));
        System.out.println("Создаем эпик");
        Epic test6 = new Epic(generateId(), "Тест", "Java");
        managerImpl.createEpics(test6);
        printAll();
        SubTasks test7 = new SubTasks(generateId(), "Завтра на работу",
                "А я сижу в 3 утра и пишу код", DONE, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00), test6.getId());
        managerImpl.createSubtask(test7);
        managerImpl.getBySubTaskId(11);
        managerImpl.getEpics();
        managerImpl.clearAllSubTask();
        printAll();
        System.out.println("Обновление задачи");
        managerImpl.updateTask(new Task("Кодить", "помоги", NEW));
        System.out.println(managerImpl.getAllTasks());
        printAll();
        System.out.println("Создаем подзадачи");
        SubTasks testSubtask1 = new SubTasks(generateId(), "Завтра на работу",
                "А я сижу в 3 утра и пишу код", DONE, Duration.ofHours(2),
                LocalDateTime.of(2022, Month.MAY, 21, 23, 00), test6.getId());
        SubTasks testSubtask2 = new SubTasks(generateId(), "Я правлю код в сне",
                "я заболел java", NEW, Duration.ofHours(2),
                LocalDateTime.of(2022, Month.MAY, 21, 23, 00), test6.getId());
        SubTasks testSubtask3 = new SubTasks(generateId(), "Я правлю код в сне",
                "я заболел java", DONE, Duration.ofHours(2),
                LocalDateTime.of(2022, Month.MAY, 21, 23, 00), test6.getId());
        SubTasks testSubtask4 = new SubTasks(generateId(), "Я правлю код в сне",
                "я заболел java", DONE, Duration.ofHours(2),
                LocalDateTime.of(2022, Month.MAY, 21, 23, 00), test6.getId());
        managerImpl.createSubtask(testSubtask1);
        managerImpl.createSubtask(testSubtask2);
        managerImpl.createSubtask(testSubtask3);
        System.out.println("-----------------------Вывод всех сабтасок---------------------------------------");
        System.out.println(managerImpl.getAllSubtasks());
        System.out.println(managerImpl.getAllSubtasks());
        System.out.println(managerImpl.getAllSubtasks());
        managerImpl.getSubtasks();
        managerImpl.createSubtask(testSubtask4);
        System.out.println(managerImpl.getAllEpics());
        managerImpl.clearAllSubTask();
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
        managerImpl.removeSubTaskId(testSubtask2.getId());
        System.out.println("Присваиваем статус эпику относительно подзадачи");
        System.out.println(managerImpl.getSubTasksByEpicId(test6.getId()));
        printAll();
        System.out.println("Удаляем подзадачу по id");
        managerImpl.removeSubTaskId(13);
        managerImpl.getAllSubtasks();
        printAll();
        System.out.println("Удаляем эпик по id");
        managerImpl.removeEpicId(test6.getId());
        printAll();
        managerImpl.clearEpic();
        printAll();
        System.out.println("Удаление всего");
        managerImpl.clearAll();
        printAll();
        Epic testEpic1 = new Epic(generateId(), "Тестирование", "Проверка");
        managerImpl.createEpics(testEpic1);
        printAll();
        SubTasks testSubtask5 = new SubTasks(generateId(), "Подзадача", "Добавлена ",
                DONE, Duration.ofHours(3),
                LocalDateTime.of(2022, 04, 22, 12, 00), 17);
        managerImpl.createSubtask(testSubtask5);
        printAll();
        Task test9 = new Task(generateId(), "Яндекс", "учиться", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test10 = new Task(generateId(), "Яндекс", "учиться", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test11 = new Task(generateId(), "Яндекс", "учиться", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        Task test12 = new Task(generateId(), "Яндекс", "учиться", NEW, Duration.ofHours(3),
                LocalDateTime.of(2022, Month.MAY, 22, 12, 00));
        managerImpl.createTasks(test9);
        managerImpl.createTasks(test10);
        managerImpl.createTasks(test11);
        managerImpl.createTasks(test12);
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test10.getId());
        managerImpl.getByIdTask(test11.getId());
        managerImpl.getByIdTask(test12.getId());
        printAll();
        System.out.println(managerImpl.getTasks());
        managerImpl.removeTaskId(23);
        System.out.println(managerImpl.getByIdTask(test12.getId()));
        System.out.println(managerImpl.getAllTasks());
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
        System.out.println(managerImpl.getPrioritizedTasks());
    }

    private static void printAll() {
        System.out.println(managerImpl.getAllTasks());
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
    }
}