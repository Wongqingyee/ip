package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A class that inherits from Command class.
 * Represents a command to delete a task from the TaskList.
 */
public class DeleteCommand extends Command {

    private int index;

    /**
     * Constructs a DeleteCommand with the specified index of the task to be deleted.
     *
     * @param index The index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the DeleteCommand by removing the task at the specified index from the TaskList,
     * displaying a deletion message, and saving the updated TaskList to storage.
     * Saves the changes into the file.
     *
     * @param tasks   The TaskList that holds the list of tasks.
     * @param ui      The Ui to interact with the user.
     * @param storage The Storage to save the tasks to a file.
     * @throws DukeException If there is an error while executing the command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (this.index <= tasks.getTaskSize() && this.index > 0) {
            ui.showDeleteMsg(tasks.getTasks().get(this.index - 1), tasks.getTaskSize());
            tasks.delete(this.index);
            storage.save(tasks);
        } else {
            throw new DukeException("Invalid index."
                    + "Please provide a valid index within the range 1 to "
                    + tasks.getTaskSize() + ".");
        }
    }

    /**
     * Checks if the DeleteCommand is an exit command.
     *
     * @return false, as the DeleteCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
