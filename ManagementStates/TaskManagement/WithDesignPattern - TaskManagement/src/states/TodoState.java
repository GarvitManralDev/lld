package states;

import entities.Task;
import enums.TaskStatus;

public class TodoState extends TaskState {
    @Override
    public TaskStatus getStatus(){
        return TaskStatus.TODO;
    }

    @Override
    public TaskState start(Task task){
        return new InProgressState();
    }

    @Override
    public TaskState block(Task task){
        return new BlockedState();
    }
}
