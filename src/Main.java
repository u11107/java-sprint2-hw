import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Task;
import ru.yandex.practicum.util.Managers;

import static ru.yandex.practicum.task.Status.DONE;
import static ru.yandex.practicum.task.Status.NEW;

public class Main  {
   private static final TaskManager managerImpl = Managers.getDefault(Managers.getDefaultHistory());

    public static void main(String[] args) {
        System.out.println("Время практики");
        System.out.println("Создаем задачу");

        Task test1 = new Task(managerImpl.generateId(), "Научиться учиться", "Яндекс помоги", NEW);
        Task test2 = new Task(managerImpl.generateId(), "Яндекс помоги", "учиться", NEW);
        Task test3 = new Task(managerImpl.generateId(), "Научиться", "помоги", NEW);
        Task test4 = new Task(managerImpl.generateId(), "учиться", "Яндекс ", NEW);
        Task test5 = new Task(managerImpl.generateId(), "Научиться", "помоги", NEW);
        managerImpl.createTasks(test1);
        managerImpl.createTasks(test2);
        managerImpl.createTasks(test3);
        managerImpl.createTasks(test4);
        managerImpl.createTasks(test5);
        System.out.println("Обновляем задачу");
        managerImpl.updateTask(test1 = new Task(test1.getId(), "123учиться", "Яндекс ", NEW));
        managerImpl.getTasks();

        printAll();
        System.out.println("Получение задачи по id");
        System.out.println(managerImpl.getByIdTask(test1.getId()));

        System.out.println("Удаление задачи по id");
        managerImpl.removeTaskId(test2.getId());

        System.out.println(managerImpl.getByIdTask(test2.getId()));

        System.out.println("Создаем эпик");
        Epic test6 = new Epic(managerImpl.generateId(), "Тест", "Java");
        managerImpl.createEpics(test6);
        printAll();
        SubTasks test7 = new SubTasks(managerImpl.generateId(), "Завтра на работу",
                "А я сижу в 3 утра и пишу код", DONE, test6.getId());
        managerImpl.createSubtask(test7);
        managerImpl.getEpics();
        managerImpl.clearAllSubTask();

        printAll();
        System.out.println("Обновление задачи");
        managerImpl.updateTask(new Task(test1.getId(), "Кодить", "помоги", NEW));
        System.out.println(managerImpl.getAllTasks());

        printAll();
        System.out.println("Создаем подзадачи");
        SubTasks testSubtask1 = new SubTasks(managerImpl.generateId(), "Завтра на работу",
                "А я сижу в 3 утра и пишу код", DONE, test6.getId());
        SubTasks testSubtask2 = new SubTasks(managerImpl.generateId(), "Я правлю код в сне",
                "я заболел java", NEW, test6.getId());
        SubTasks testSubtask3 = new SubTasks(managerImpl.generateId(), "Я правлю код в сне",
                "я заболел java", DONE, test6.getId());
        SubTasks testSubtask4 = new SubTasks(managerImpl.generateId(), "Я правлю код в сне",
                "я заболел java", DONE, test6.getId());
        managerImpl.createSubtask(testSubtask1);
        managerImpl.createSubtask(testSubtask2);
        managerImpl.createSubtask(testSubtask3);
        System.out.println(managerImpl.getAllSubtasks());
        System.out.println(managerImpl.getAllSubtasks());
        managerImpl.getSubtasks();
        managerImpl.createSubtask(testSubtask4);
        managerImpl.removeSubTaskId(testSubtask2.getId());
        System.out.println(managerImpl.getAllEpics());
        managerImpl.clearAllSubTask();
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
        managerImpl.removeSubTaskId(testSubtask2.getId());
        System.out.println("Присваеваем статус эпику относительно подзадачи");
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
        Epic testEpic1 = new Epic(managerImpl.generateId(), "Тестирование", "Проверка");
        managerImpl.createEpics(testEpic1);

        printAll();
        SubTasks testSubtask5 = new SubTasks(managerImpl.generateId(), "Подзадача", "Добавлена ",
                DONE, 17);
        managerImpl.createSubtask(testSubtask5);

        printAll();
        Task test9 = new Task(managerImpl.generateId(), "Яндекс", "учиться", NEW);
        managerImpl.createTasks(test9);
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test1.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByIdTask(test9.getId());
        managerImpl.getByEpicId(17);


        System.out.println(managerImpl.history());
    }

    private static void printAll() {
        System.out.println(managerImpl.getAllTasks());
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
    }
}