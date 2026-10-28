package observer;

import entities.Task;

public interface TaskObserver {
    void update(Task task, String event);
}
