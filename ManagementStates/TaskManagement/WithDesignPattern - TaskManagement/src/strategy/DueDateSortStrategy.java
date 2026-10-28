package strategy;

import entities.Task;

import java.util.Comparator;
import java.util.List;

// earliest due date first, tasks without a due date last
public class DueDateSortStrategy implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks){
        tasks.sort(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder())));
    }
}
