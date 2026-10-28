package entities;

import enums.TaskPriority;
import enums.TaskStatus;
import exceptions.TaskException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagementSystem {
    private final Map<String, User> users;
    private final Map<String, Task> tasks;
    private final Map<String, TaskList> taskLists;

    public TaskManagementSystem(){
        this.users = new HashMap<>();
        this.tasks = new HashMap<>();
        this.taskLists = new HashMap<>();
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

    public Task createTask(String listId, String title, String description, LocalDate dueDate, TaskPriority priority, String creatorId){
        TaskList taskList = taskLists.get(listId);
        if(taskList == null){
            throw new TaskException("No task list found for " + listId);
        }
        Task task = new Task(title, description, dueDate, priority, getUser(creatorId));
        tasks.put(task.getId(), task);
        taskList.addTask(task);
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
        getTask(taskId).setAssignee(getUser(userId));
    }

    public void changeStatus(String taskId, TaskStatus newStatus){
        getTask(taskId).changeStatus(newStatus);
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

    public List<Task> searchTasks(String keyword){
        String lower = keyword.toLowerCase();
        List<Task> result = new ArrayList<>();
        for(Task task : tasks.values()){
            if(task.getTitle().toLowerCase().contains(lower) || task.getDescription().toLowerCase().contains(lower)){
                result.add(task);
            }
        }
        result.sort(Comparator.comparing(Task::getPriority).reversed());
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
