import entities.*;
import enums.TaskPriority;
import enums.TaskStatus;
import exceptions.TaskException;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManagementSystem system = new TaskManagementSystem();

        User alice = system.createUser("Alice", "alice@example.com");
        User bob = system.createUser("Bob", "bob@example.com");

        TaskList sprint = system.createTaskList("Sprint 1", alice.getId());

        Task login = system.createTask(sprint.getId(), "Build login", "Login page with OAuth",
                LocalDate.now().plusDays(7), TaskPriority.HIGH, alice.getId());
        Task ui = system.createTask(sprint.getId(), "Login UI", "Form layout for login",
                LocalDate.now().plusDays(3), TaskPriority.MEDIUM, alice.getId());
        Task bug = system.createTask(sprint.getId(), "Fix crash", "App crashes on startup",
                null, TaskPriority.CRITICAL, bob.getId());

        system.assignTask(login.getId(), bob.getId());
        system.assignTask(bug.getId(), bob.getId());
        system.addSubtask(login.getId(), ui.getId());
        system.addComment(login.getId(), alice.getId(), "Use Google OAuth");
        system.addTag(login.getId(), "auth");

        changeStatus(system, login, TaskStatus.DONE);
        changeStatus(system, login, TaskStatus.IN_PROGRESS);
        changeStatus(system, login, TaskStatus.DONE);
        changeStatus(system, ui, TaskStatus.IN_PROGRESS);
        changeStatus(system, ui, TaskStatus.DONE);
        changeStatus(system, login, TaskStatus.DONE);

        sprint.display();

        print("Search 'login'", system.searchTasks("login"));
        print("Tasks assigned to Bob", system.listTasksByUser(bob.getId()));
        print("DONE tasks", system.filterTasks(TaskStatus.DONE, null, null));

        system.deleteTask(bug.getId());
        sprint.display();
    }

    private static void changeStatus(TaskManagementSystem system, Task task, TaskStatus status) {
        try {
            system.changeStatus(task.getId(), status);
            System.out.println(task.getTitle() + " -> " + status);
        } catch (TaskException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void print(String heading, List<Task> tasks) {
        System.out.println(heading + ":");
        for (Task task : tasks) {
            System.out.println("  - " + task.getTitle() + " [" + task.getStatus() + ", " + task.getPriority() + "]");
        }
    }
}
