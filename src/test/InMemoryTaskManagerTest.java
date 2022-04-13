package test;

import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.util.Managers;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    void setManager() {
        managerImpl = (InMemoryTaskManager) Managers.getDefault(Managers.getDefaultHistory());
    }
}
