package observer;

import entities.ActivityLog;
import entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// keeps the activity history of every task
public class ActivityLogObserver implements TaskObserver {
    private final Map<String, List<ActivityLog>> logs = new HashMap<>();

    @Override
    public void update(Task task, String event){
        logs.computeIfAbsent(task.getId(), id -> new ArrayList<>()).add(new ActivityLog(event));
    }

    public void printHistory(Task task){
        System.out.println("History of " + task.getTitle() + ":");
        for(ActivityLog log : logs.getOrDefault(task.getId(), List.of())){
            System.out.println("  " + log);
        }
    }
}
