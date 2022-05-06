package manager;

import ru.yandex.practicum.manager.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    void setManager() {
        managerImpl = new InMemoryTaskManager();
    }
}
