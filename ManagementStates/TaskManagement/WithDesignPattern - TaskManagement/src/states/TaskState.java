package states;

import entities.Task;
import enums.TaskStatus;
import exceptions.TaskException;

// Each state allows only its own transitions and returns the next state.
// Anything it doesn't override is an invalid transition.
public abstract class TaskState {
    public abstract TaskStatus getStatus();

    public TaskState start(Task task){
        throw invalid("start");
    }

    public TaskState complete(Task task){
        throw invalid("complete");
    }

    public TaskState block(Task task){
        throw invalid("block");
    }

    public TaskState moveToTodo(Task task){
        throw invalid("move to TODO");
    }

    private TaskException invalid(String action){
        return new TaskException("Cannot " + action + " a task in " + getStatus());
    }
}
