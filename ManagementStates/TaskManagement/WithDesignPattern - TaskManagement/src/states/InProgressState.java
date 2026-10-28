package states;

import entities.Task;
import enums.TaskStatus;
import exceptions.TaskException;

public class InProgressState extends TaskState {
    @Override
    public TaskStatus getStatus(){
        return TaskStatus.IN_PROGRESS;
    }

    @Override
    public TaskState complete(Task task){
        if(!task.canComplete()){
            throw new TaskException("Finish all subtasks before completing " + task.getTitle());
        }
        return new DoneState();
    }

    @Override
    public TaskState block(Task task){
        return new BlockedState();
    }

    @Override
    public TaskState moveToTodo(Task task){
        return new TodoState();
    }
}
