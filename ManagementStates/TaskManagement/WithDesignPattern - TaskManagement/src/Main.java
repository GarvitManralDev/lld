import entities.*;
import enums.TaskPriority;
import enums.TaskStatus;
import exceptions.TaskException;
import observer.ActivityLogObserver;
import observer.NotificationObserver;
import strategy.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManagementSystem system = TaskManagementSystem.getInstance();

        // Observer: the history and notifications both listen to task changes
        ActivityLogObserver activityLog = new ActivityLogObserver();
        system.addObserver(activityLog);
        system.addObserver(new NotificationObserver());

        User alice = system.createUser("Alice", "alice@example.com");
        User bob = system.createUser("Bob", "bob@example.com");

        TaskList sprint = system.createTaskList("Sprint 1", alice.getId());

        // Builder: set only what each task needs
        Task login = system.createTask(sprint.getId(), new Task.Builder("Build login", alice)
                .description("Login page with OAuth")
                .dueDate(LocalDate.now().plusDays(7))
                .priority(TaskPriority.HIGH));
        Task ui = system.createTask(sprint.getId(), new Task.Builder("Login UI", alice)
                .dueDate(LocalDate.now().plusDays(3)));
        Task bug = system.createTask(sprint.getId(), new Task.Builder("Fix login crash", bob)
                .priority(TaskPriority.CRITICAL));

        system.assignTask(login.getId(), bob.getId());
        system.assignTask(bug.getId(), bob.getId());
        system.addSubtask(login.getId(), ui.getId());
        system.addComment(login.getId(), alice.getId(), "Use Google OAuth");
        system.addTag(login.getId(), "auth");

        // State: each action is allowed or rejected by the task's current state
        run("complete " + login.getTitle(), () -> system.completeTask(login.getId()));
        run("start " + login.getTitle(), () -> system.startTask(login.getId()));
        run("complete " + login.getTitle(), () -> system.completeTask(login.getId()));
        run("start " + ui.getTitle(), () -> system.startTask(ui.getId()));
        run("complete " + ui.getTitle(), () -> system.completeTask(ui.getId()));
        run("complete " + login.getTitle(), () -> system.completeTask(login.getId()));
        run("block " + login.getTitle(), () -> system.blockTask(login.getId()));
        run("reopen " + login.getTitle(), () -> system.moveTaskToTodo(login.getId()));
        run("block " + bug.getTitle(), () -> system.blockTask(bug.getId()));

        sprint.display();
        activityLog.printHistory(login);

        // Strategy: same search, different sort order
        print("Search 'login' by priority", system.searchTasks("login", new PrioritySortStrategy()));
        print("Search 'login' by due date", system.searchTasks("login", new DueDateSortStrategy()));

        print("Bob's BLOCKED tasks", system.filterTasks(TaskStatus.BLOCKED, null, bob.getId()));
        print("MEDIUM tasks", system.filterTasks(null, TaskPriority.MEDIUM, null));
    }

    private static void run(String action, Runnable command) {
        try {
            command.run();
            System.out.println(action + ": ok");
        } catch (TaskException e) {
            System.out.println(action + ": " + e.getMessage());
        }
    }

    private static void print(String heading, List<Task> tasks) {
        System.out.println(heading + ":");
        for (Task task : tasks) {
            System.out.println("  - " + task.getTitle() + " [" + task.getStatus() + ", " + task.getPriority() + ", due " + task.getDueDate() + "]");
        }
    }
}
