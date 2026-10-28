package entities;

import enums.TaskPriority;
import enums.TaskStatus;
import exceptions.TaskException;
import observer.TaskObserver;
import strategy.TaskSortStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagementSystem {
    private static TaskManagementSystem instance;
    private final Map<String, User> users;
    private final Map<String, Task> tasks;
    private final Map<String, TaskList> taskLists;
    private final List<TaskObserver> observers;

    // Singleton: one system for the whole app
    private TaskManagementSystem(){
        this.users = new HashMap<>();
        this.tasks = new HashMap<>();
        this.taskLists = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public static TaskManagementSystem getInstance(){
        if(instance == null){
            instance = new TaskManagementSystem();
        }
        return instance;
    }

    // Observer: every change to a task is pushed to all registered observers
    public void addObserver(TaskObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(Task task, String event){
        for(TaskObserver observer : observers){
            observer.update(task, event);
        }
    }

    public User createUser(String name, String email){
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    public TaskList createTaskList(String name, String ownerId){
        TaskList taskList = new TaskList(name, getUser(ownerId));
        taskLists.put(taskList.getId(), taskList);
        return taskList;
    }

    public Task createTask(String listId, Task.Builder builder){
        TaskList taskList = taskLists.get(listId);
        if(taskList == null){
            throw new TaskException("No task list found for " + listId);
        }
        Task task = builder.build();
        tasks.put(task.getId(), task);
        taskList.addTask(task);
        notifyObservers(task, "Task created by " + task.getCreatedBy().getName());
        return task;
    }

    public void updateTask(String taskId, String title, String description, LocalDate dueDate){
        getTask(taskId).update(title, description, dueDate);
    }

    public void deleteTask(String taskId){
        Task task = getTask(taskId);
        tasks.remove(taskId);
        for(TaskList taskList : taskLists.values()){
            taskList.removeTask(task);
        }
    }

    public void assignTask(String taskId, String userId){
        Task task = getTask(taskId);
        User user = getUser(userId);
        task.setAssignee(user);
        notifyObservers(task, "Assigned to " + user.getName());
    }

    public void startTask(String taskId){
        Task task = getTask(taskId);
        TaskStatus oldStatus = task.getStatus();
        task.start();
        notifyObservers(task, "Status: " + oldStatus + " -> " + task.getStatus());
    }

    public void completeTask(String taskId){
        Task task = getTask(taskId);
        TaskStatus oldStatus = task.getStatus();
        task.complete();
        notifyObservers(task, "Status: " + oldStatus + " -> " + task.getStatus());
    }

    public void blockTask(String taskId){
        Task task = getTask(taskId);
        TaskStatus oldStatus = task.getStatus();
        task.block();
        notifyObservers(task, "Status: " + oldStatus + " -> " + task.getStatus());
    }

    public void moveTaskToTodo(String taskId){
        Task task = getTask(taskId);
        TaskStatus oldStatus = task.getStatus();
        task.moveToTodo();
        notifyObservers(task, "Status: " + oldStatus + " -> " + task.getStatus());
    }

    public void addSubtask(String parentId, String subtaskId){
        getTask(parentId).addSubtask(getTask(subtaskId));
    }

    public void addComment(String taskId, String userId, String content){
        getTask(taskId).addComment(new Comment(content, getUser(userId)));
    }

    public void addTag(String taskId, String tagName){
        getTask(taskId).addTag(tagName);
    }

    // a null argument means "don't filter on this field"
    public List<Task> filterTasks(TaskStatus status, TaskPriority priority, String assigneeId){
        List<Task> result = new ArrayList<>();
        for(Task task : tasks.values()){
            if(status != null && task.getStatus() != status) continue;
            if(priority != null && task.getPriority() != priority) continue;
            if(assigneeId != null && (task.getAssignee() == null || !task.getAssignee().getId().equals(assigneeId))) continue;
            result.add(task);
        }
        return result;
    }

    public List<Task> searchTasks(String keyword, TaskSortStrategy sortStrategy){
        String lower = keyword.toLowerCase();
        List<Task> result = new ArrayList<>();
        for(Task task : tasks.values()){
            if(task.getTitle().toLowerCase().contains(lower) || task.getDescription().toLowerCase().contains(lower)){
                result.add(task);
            }
        }
        sortStrategy.sort(result);
        return result;
    }

    public List<Task> listTasksByUser(String userId){
        return filterTasks(null, null, userId);
    }

    private User getUser(String userId){
        User user = users.get(userId);
        if(user == null){
            throw new TaskException("No user found for " + userId);
        }
        return user;
    }

    private Task getTask(String taskId){
        Task task = tasks.get(taskId);
        if(task == null){
            throw new TaskException("No task found for " + taskId);
        }
        return task;
    }
}
