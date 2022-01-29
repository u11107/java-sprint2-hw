package ru.yandex.practicum.manager;
import ru.yandex.practicum.task.Epic;
import ru.yandex.practicum.task.Tasks;
import ru.yandex.practicum.task.SubTasks;

import java.util.HashMap;

public class manager implements mangerMethods {
    HashMap<Integer, Tasks> task;
    HashMap<Integer, Epic> epic;
    HashMap<Integer, SubTasks> subTask;
    int id = 1;

    public manager() {
        task = new HashMap<Integer, Tasks>();
        epic = new HashMap<Integer, Epic>();
        subTask = new HashMap<Integer, SubTasks>();
    }
// вывод списка задач
    public void printTaskAll() {
        System.out.println("Список задач");
        if (!(task.isEmpty())) { // проверка true
            for (Tasks task : task.values()) {
                System.out.println(task);
            }
        } else {
            System.out.println("Задач нет");
        }
    }

// добавление новой задачи
    public void addTask(Tasks tasks) {
        task.put(tasks.getId(), tasks);
    }

//получение задачи по идентефикатору
    public Tasks gettingById(int id) {
        return task.get(id);
    }

// удаление всех задач
    public void clearTask() {
        task.clear();
    }
//

}
