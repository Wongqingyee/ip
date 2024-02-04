package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.task.ToDo;
import duke.ui.Ui;

/**
 * A class that inherits from Command class.
 * Represents a command to add a ToDo task.
 */
public class ToDoCommand extends Command {

    private ToDo toDo;

    /**
     * Constructs a ToDoCommand with the specified description of the ToDo task.
     *
     * @param description The description of the ToDo task.
     */
    public ToDoCommand(String description) {
        this.toDo = new ToDo(description);
    }

    /**
     * Executes the ToDoCommand by adding the specified ToDo task to the TaskList.
     * Shows a message indicating the task has been added.
     * Saves the changes into the file.
     *
     * @param tasks   The TaskList that holds the list of tasks.
     * @param ui      The Ui to interact with the user.
     * @param storage The Storage to save the tasks to a file.
     * @throws DukeException If there is an error while executing the command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.add(toDo);
        ui.showAddMsg(toDo, tasks.getTaskSize());
        storage.save(tasks);
    }

    /**
     * Checks if the ToDoCommand is an exit command.
     *
     * @return false, as the ToDoCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
