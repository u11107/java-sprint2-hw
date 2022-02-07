import ru.yandex.practicum.manager.ManagerImpl;
import ru.yandex.practicum.task.*;
import static ru.yandex.practicum.task.Status.*;

public class Main  {
    static ManagerImpl managerImpl = new ManagerImpl();

    public static void main(String[] args) {
        System.out.println("Время практики");
        System.out.println("Создаем задачу");


        Task tes1 = new Task(managerImpl.generateId(),"Научиться учиться", "Яндекс помоги", NEW);
        Task t1 = new Task(managerImpl.generateId(),"Яндекс помоги", "учиться", NEW);
        Task t2 = new Task(managerImpl.generateId(),"Научиться", "помоги", NEW);
        Task t3 = new Task(managerImpl.generateId(),"учиться", "Яндекс ", NEW);
        Task t4 = new Task(managerImpl.generateId(),"Научиться", "помоги", NEW);
        managerImpl.createTasks(tes1);
        managerImpl.createTasks(t1);
        managerImpl.createTasks(t2);
        managerImpl.createTasks(t3);
        managerImpl.createTasks(t4);
        System.out.println("Обновляем задачу");
        managerImpl.updateTask(t3 = new Task(tes1.getId(),"123учиться", "Яндекс ", NEW));
        printAll();
        System.out.println("Получение задачи по id");
        System.out.println(managerImpl.getByIdTask(tes1.getId()));

        System.out.println("Удаление задачи по id");
        managerImpl.removeTaskId(t1.getId());

        System.out.println(managerImpl.getByIdTask(t3.getId()));

        System.out.println("Создаем эпик");
        Epic test6 = new Epic(managerImpl.generateId(), "Тест", "Java", null);
        managerImpl.createEpics(test6);
        printAll();
        SubTasks ep13 = new SubTasks(managerImpl.generateId(),"Завтра на работу", "А я сижу в 3 утра и пишу код", DONE,test6.getId());
        printAll();
        managerImpl.clearAllSubTask();
        printAll();
        System.out.println("Обновление задачи");
        managerImpl.updateTask(new Task(tes1.getId(), "Научwedиться", "помоги", NEW));
        System.out.println(managerImpl.getAllTasks());
        printAll();
        System.out.println("Создаем подзадачи");
        SubTasks ep1 = new SubTasks(managerImpl.generateId(),"Завтра на работу", "А я сижу в 3 утра и пишу код", DONE,test6.getId());
        SubTasks ep2 = new SubTasks(managerImpl.generateId(),"Я правлю код в сне", "я заболел java", NEW,test6.getId());
        SubTasks ep3 = new SubTasks(managerImpl.generateId(),"Я правлю код в сне", "я заболел java", DONE,test6.getId());
        SubTasks ep6 = new SubTasks(managerImpl.generateId(),"Я правлю код в сне", "я заболел java", DONE,test6.getId());
        managerImpl.createSubtask(ep13);
        managerImpl.createSubtask(ep6);
        managerImpl.createSubtask(ep3);
        System.out.println(managerImpl.getAllSubtasks());
        System.out.println(managerImpl.getAllSubtasks());

        managerImpl.createSubtask(ep2);
        managerImpl.removeSubTaskId(ep6.getId());
        System.out.println(managerImpl.getAllEpics());
        managerImpl.clearAllSubTask();
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());

        managerImpl.removeSubTaskId(ep13.getId());

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
        printAll();
        System.out.println("Удаление всего");
        managerImpl.clearAll();
        printAll();

        Epic q1 = new Epic(managerImpl.generateId(),"Тестирование", "Проверка", null);
        managerImpl.createEpics(q1);

        printAll();

        SubTasks w1 = new SubTasks(managerImpl.generateId(), "Подзадача", "Добавлена ", DONE, 17);
        managerImpl.createSubtask(w1);

        printAll();

    }

     public static void printAll(){
        System.out.println(managerImpl.getAllTasks());
        System.out.println(managerImpl.getAllEpics());
        System.out.println(managerImpl.getAllSubtasks());
    }

}