import ru.yandex.practicum.task.Tasks;

import ru.yandex.practicum.task.StatusTasks;
import ru.yandex.practicum.task.SubTasks;
import ru.yandex.practicum.task.Tasks;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.manager.mangerMethods;
import ru.yandex.practicum.manager.manager;


public class Main  {

    public static void main(String[] args) {

        int number = 1;
        Tasks tes1 = new Tasks(number, "Помыть тарелки и вилки", "Купить тарелки и вилки", StatusTasks.Status.NEW);
        System.out.println(tes1);
        number++;
        Tasks tes2 = new Tasks(number, "Тренировка", "Разбить ебло соседу", StatusTasks.Status.NEW);
        number++;
        Tasks tes3 = new Tasks(number, "Помыть тарелки и вилки", "ШОшаур", StatusTasks.Status.NEW);
        number++;
        Tasks tes4 = new Tasks(number, "Помыть тарелки и вилки", "Вычистить все нахрен", StatusTasks.Status.NEW);
        number++;

        Tasks tes5 = new Tasks(number, "Помыть тарелки и вилки", "Вычистить все нахрен", StatusTasks.Status.NEW);
        System.out.println(tes2);
        System.out.println(tes3);
        System.out.println(tes4);
        System.out.println(tes5);






    }

}

