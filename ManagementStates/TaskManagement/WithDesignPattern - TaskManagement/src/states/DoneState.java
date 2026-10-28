package states;

import entities.Task;
import enums.TaskStatus;

public class DoneState extends TaskState {
    @Override
    public TaskStatus getStatus(){
        return TaskStatus.DONE;
    }

    // reopen
    @Override
    public TaskState moveToTodo(Task task){
        return new TodoState();
    }
}
