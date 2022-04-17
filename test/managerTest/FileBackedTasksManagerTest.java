package managerTest;

import org.junit.jupiter.api.AfterEach;
import ru.yandex.practicum.manager.FileBackedTasksManager;

import java.io.File;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private static final File FILE = new File("test.csv");

    @Override
    void setManager() {
        managerImpl = new FileBackedTasksManager(FILE);
    }
     @AfterEach
    void after() {
        if(FILE.exists()) {
            FILE.delete();
        }
     }
}