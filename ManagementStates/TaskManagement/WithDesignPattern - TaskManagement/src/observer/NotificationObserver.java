package observer;

import entities.Task;

// tells the assignee about every change to their task
public class NotificationObserver implements TaskObserver {
    @Override
    public void update(Task task, String event){
        if(task.getAssignee() != null){
            System.out.println("[notify " + task.getAssignee().getName() + "] " + task.getTitle() + ": " + event);
        }
    }
}
