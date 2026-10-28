package entities;

import enums.TaskPriority;
import enums.TaskStatus;
import states.TaskState;
import states.TodoState;

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
    private TaskState state;
    private final User createdBy;
    private User assignee;
    private final Set<String> tags;
    private final List<Comment> comments;
    private final List<Task> subtasks;

    private Task(Builder builder){
        this.id = UUID.randomUUID().toString();
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.priority = builder.priority;
        this.state = new TodoState();
        this.createdBy = builder.createdBy;
        this.tags = new HashSet<>();
        this.comments = new ArrayList<>();
        this.subtasks = new ArrayList<>();
    }

    // Builder pattern: title and creator are required, everything else is optional
    public static class Builder {
        private final String title;
        private final User createdBy;
        private String description = "";
        private LocalDate dueDate;
        private TaskPriority priority = TaskPriority.MEDIUM;

        public Builder(String title, User createdBy){
            this.title = title;
            this.createdBy = createdBy;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder dueDate(LocalDate dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Builder priority(TaskPriority priority){
            this.priority = priority;
            return this;
        }

        public Task build(){
            return new Task(this);
        }
    }

    public void update(String title, String description, LocalDate dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // State pattern: the current state decides if the action is allowed and returns the next state
    public void start(){
        this.state = state.start(this);
    }

    public void complete(){
        this.state = state.complete(this);
    }

    public void block(){
        this.state = state.block(this);
    }

    public void moveToTodo(){
        this.state = state.moveToTodo(this);
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

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public User getCreatedBy(){
        return createdBy;
    }

    public User getAssignee(){
        return assignee;
    }

    public TaskStatus getStatus(){
        return state.getStatus();
    }

    public TaskPriority getPriority(){
        return priority;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }
}
