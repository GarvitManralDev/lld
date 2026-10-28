package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskList {
    private final String id;
    private final String name;
    private final User owner;
    private final List<Task> tasks;

    public TaskList(String name, User owner){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.owner = owner;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }

    public String getId(){
        return id;
    }

    public void display(){
        System.out.println(name + " (owner: " + owner.getName() + ")");
        for(Task task : tasks){
            System.out.println("  - " + task.getTitle() + " [" + task.getStatus() + ", " + task.getPriority() + "]");
        }
    }
}
