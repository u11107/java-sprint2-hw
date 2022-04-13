package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.task.FileBackedTasksManager;
import ru.yandex.practicum.util.Managers;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

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