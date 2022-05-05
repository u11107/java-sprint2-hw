package manager;

import ru.yandex.practicum.manager.InMemoryTaskManager;
import ru.yandex.practicum.util.Managers;
import java.io.IOException;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    void setManager() throws IOException, InterruptedException {
        managerImpl = (InMemoryTaskManager) Managers.getDefault();
    }
}
