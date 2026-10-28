package states;

import entities.Task;
import enums.TaskStatus;

public class BlockedState extends TaskState {
    @Override
    public TaskStatus getStatus(){
        return TaskStatus.BLOCKED;
    }

    @Override
    public TaskState start(Task task){
        return new InProgressState();
    }

    @Override
    public TaskState moveToTodo(Task task){
        return new TodoState();
    }
}
