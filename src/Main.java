import ru.yandex.practicum.manager.Manager;
import ru.yandex.practicum.task.*;

public class Main  {
    static Manager manager = new Manager();

    public static void main(String[] args) {



        Tasks tes1 = new Tasks(manager.generateId(),"Помыть 11тарелки и вилки", "Купить тарелки и вилки", Status.IN_PROGRESS);
        manager.createTasks(tes1);
        Tasks tes2 = new Tasks(manager.generateId(),"Помыть тарелки и вилки", "Купить 123тарелки и вилки", Status.NEW);
        manager.createTasks(tes2);
        manager.createTasks(tes1);
        printAll();
        manager.removeTaskId(1);
        manager.updateTask(new Tasks(2,"jhbdsfjksd", "ihwefiwef", Status.NEW));
        printAll();


        Epic ep4 = new Epic(manager.generateId(), "Помыть1", "выкинуть");
        Epic ep3 = new Epic(manager.generateId(), "Помыть", "выкинуть");
        manager.createEpics(ep3);
        manager.createEpics(ep4);
        printAll();

        SubTasks ep1 = new SubTasks(manager.generateId(),"23Проснуться", "Встать",Status.NEW,2323);
        manager.createSubtask(ep1);
        manager.addSubTaskEpic(ep1);
        printAll();

        manager.removeEpicId(3);
        printAll();








    }

    static void printAll(){
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());
    }

}