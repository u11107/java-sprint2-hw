package ru.yandex.practicum.task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTasks extends Task {
    private int idFromEpic;

    public SubTasks(Integer id, String title, String description, Status status) {
        super(id ,title, description, status);
    }

    public SubTasks(Integer Id, String title, String description, Status status, Integer idFromEpic) {
        super(Id, title, description, status);
        this.idFromEpic = idFromEpic;
    }

    public SubTasks(Integer id, String title, String description, Status status, Duration duration,
                    LocalDateTime startTime, int idFromEpic) {
        super(id, title, description, status, duration, startTime);
        this.idFromEpic = idFromEpic;
    }

    public  Integer getIdFromEpic() {
        return idFromEpic;
    }

    @Override
    public String toString() {
       return getId() + ", " + Type.SUBTASK + ", " + getTitle() + "," + getStatus() + ", "
                + getDescription() + ", " + getDuration()
                + "," + getStartTime() + ", "
                + getIdFromEpic();
    }
}
