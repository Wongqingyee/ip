package duke.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the Duke chatbot application.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index The index of the task to be deleted.
     */
    public void delete(int index) {
        tasks.remove(index - 1);
    }

    /**
     * Lists all tasks in the task list.
     */
    public void list() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Marks a task as done based on its index.
     *
     * @param index The index of the task to be marked as done.
     */
    public void mark(int index) {
        tasks.get(index - 1).markAsDone();
    }

    /**
     * Marks a task as undone based on its index.
     *
     * @param index The index of the task to be marked as undone.
     */
    public void unmark(int index) {
        tasks.get(index - 1).markAsUndone();
    }

    /**
     * Gets the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int getTaskSize() {
        return tasks.size();
    }

    /**
     * Gets the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the description of a task based on its index.
     *
     * @param index The index of the task.
     * @return The description of the task.
     */
    public String getTaskDescription(int index) {
        return this.tasks.get(index).description;
    }

    /**
     * Finds tasks in the list that contain the specified keyword in their descriptions.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return An ArrayList of tasks containing the specified keyword.
     */
    public ArrayList<Task> findTasksByKeyword(String keyword) {
        ArrayList<Task> matchingTask = new ArrayList<>();
        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                matchingTask.add(task);
            }
        }
        return matchingTask;
    }
}
