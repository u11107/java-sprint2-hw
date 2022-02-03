import ru.yandex.practicum.manager.Manager;
import ru.yandex.practicum.task.*;

public class Main  {
    static Manager manager = new Manager();

    public static void main(String[] args) {
        System.out.println("Время практики");
        System.out.println("Создаем задачу");
        Tasks tes1 = new Tasks(manager.generateId(),"Научиться учиться", "Яндекс помоги", Status.NEW);
        manager.createTasks(tes1);
        printAll();
        System.out.println("Получение задачи по id");
        System.out.println(manager.gettingById(2));
        System.out.println("Удаление задачи по id");
        manager.removeTaskId(2);
        System.out.println(manager.gettingById(2));
        System.out.println("Создаем эпик");
        Epic test2 = new Epic(manager.generateId(), "Java", "работай");
        manager.createEpics(test2);
        printAll();
        System.out.println("Создаем подзадачи");
        SubTasks ep1 = new SubTasks(manager.generateId(),"Завтра на работу", "А я сижу в 3 утра и пишу код",Status.NEW,3);
        SubTasks ep2 = new SubTasks(manager.generateId(),"Я правлю код в сне", "я заболел java",Status.NEW,3);
        manager.createSubtask(ep1);
        System.out.println(manager.getAllSubtasks());
        manager.addSubTaskEpic(ep1);
        manager.createSubtask(ep2);
        manager.addSubTaskEpic(ep2);
        System.out.println("Присваеваем статус эпику относительно подзадачи");
        manager.updateStatus(ep1);
        printAll();
        System.out.println("Удаляем подзадачи по id");
        manager.removeSubTaskId(4);
        manager.getAllSubtasks();
        printAll();
        System.out.println("Удаляем эпик по id");
        manager.removeEpicId(3);
        printAll();
        System.out.println("Удаление всего");
        manager.clearAll();
        printAll();
    }

    static void printAll(){
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
    }

}