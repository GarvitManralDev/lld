package entities;

import enums.TaskPriority;
import enums.TaskStatus;
import exceptions.TaskException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private final User createdBy;
    private User assignee;
    private final Set<String> tags;
    private final List<Comment> comments;
    private final List<Task> subtasks;
    private final List<ActivityLog> activityLogs;

    public Task(String title, String description, LocalDate dueDate, TaskPriority priority, User createdBy){
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = TaskStatus.TODO;
        this.createdBy = createdBy;
        this.tags = new HashSet<>();
        this.comments = new ArrayList<>();
        this.subtasks = new ArrayList<>();
        this.activityLogs = new ArrayList<>();
        log("Task created by " + createdBy.getName());
    }

    public void update(String title, String description, LocalDate dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public void changeStatus(TaskStatus newStatus){
        if(!isValidTransition(status, newStatus)){
            throw new TaskException("Invalid transition " + status + " -> " + newStatus);
        }
        if(newStatus == TaskStatus.DONE && !canComplete()){
            throw new TaskException("Finish all subtasks before completing " + title);
        }
        log("Status: " + status + " -> " + newStatus);
        this.status = newStatus;
    }

    private boolean isValidTransition(TaskStatus from, TaskStatus to){
        switch(from){
            case TODO: return to == TaskStatus.IN_PROGRESS || to == TaskStatus.BLOCKED;
            case IN_PROGRESS: return to == TaskStatus.TODO || to == TaskStatus.DONE || to == TaskStatus.BLOCKED;
            case DONE: return to == TaskStatus.TODO;
            case BLOCKED: return to == TaskStatus.TODO || to == TaskStatus.IN_PROGRESS;
            default: return false;
        }
    }

    public boolean canComplete(){
        for(Task subtask : subtasks){
            if(subtask.getStatus() != TaskStatus.DONE){
                return false;
            }
        }
        return true;
    }

    public void setAssignee(User user){
        this.assignee = user;
        log("Assigned to " + user.getName());
    }

    public void updatePriority(TaskPriority priority){
        this.priority = priority;
    }

    public void addSubtask(Task task){
        subtasks.add(task);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addTag(String tagName){
        tags.add(tagName);
    }

    private void log(String description){
        activityLogs.add(new ActivityLog(description));
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public User getAssignee(){
        return assignee;
    }

    public TaskStatus getStatus(){
        return status;
    }

    public TaskPriority getPriority(){
        return priority;
    }
}
