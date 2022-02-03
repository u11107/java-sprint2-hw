import ru.yandex.practicum.manager.Manager;
import ru.yandex.practicum.task.*;

public class Main  {
    static Manager manager = new Manager();

    public static void main(String[] args) {
        System.out.println("Пришло время практики!");


        /*Tasks tes1 = new Tasks("Помыть 11тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        manager.createTasks(tes1);
        Tasks tes2 = new Tasks("Помыть тарелки и вилки", "Купить 123тарелки и вилки", Status.NEW);
        manager.createTasks(tes2);
        manager.createTasks(tes1);
        manager.updateTask(new Tasks (2,"Прикинуть", "Убрать",Status.DONE));
        printAll();
        manager.removeTaskId(1);
        printAll();


        Epic ep4 = new Epic(manager.generateId(), "Помыть1", "выкинуть");
        Epic ep3 = new Epic(manager.generateId(), "Помыть", "выкинуть");
        manager.createEpics(ep3);
        manager.createEpics(ep4);
        printAll();

        Tasks tes16 = new Tasks("Помыть 1тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        Tasks tes21 = new Tasks("Помыть 12тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        Tasks tes3 = new Tasks("Помыть 13тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        Tasks tes4 = new Tasks("Помыть 14тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        Tasks tes5 = new Tasks("Помыть 15тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);

        manager.createTasks(tes16);
        manager.createTasks(tes21);
        manager.createTasks(tes3);
        manager.createTasks(tes4);
        printAll();


        SubTasks ep1 = new SubTasks(null,"23Проснуться", "Встать",Status.NEW, 121);
        manager.addSubtask(ep1);
        printAll();

        manager.removeEpicId(3);
        printAll();*/
        System.out.println("Пришло время практики!");
        SubTasks ep9 = new SubTasks(manager.generateId(), "23Проснуться", "Встать",Status.NEW, 120);
        SubTasks ep8 = new SubTasks(manager.generateId(), "23Проснуться", "Встать",Status.NEW, 122);
        SubTasks ep7 = new SubTasks(manager.generateId(), "23Проснуться", "Встать",Status.NEW, 123);
        manager.createSubtask(ep9);
        manager.createSubtask(ep7);
        manager.createSubtask(ep8);
        manager.addSubTaskEpic(ep9);
        manager.getAllSubtasks();
        // manager.addSubTaskEpic(ep9);














    }

    static void printAll(){
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
    }

}