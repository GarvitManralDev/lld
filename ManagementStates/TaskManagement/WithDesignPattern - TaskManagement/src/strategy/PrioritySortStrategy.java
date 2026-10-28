package strategy;

import entities.Task;

import java.util.Comparator;
import java.util.List;

// highest priority first
public class PrioritySortStrategy implements TaskSortStrategy {
    @Override
    public void sort(List<Task> tasks){
        tasks.sort(Comparator.comparing(Task::getPriority).reversed());
    }
}
