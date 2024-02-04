package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A class that inherits from Command class.
 * Represents a command to unmark a task as done.
 */
public class UnmarkCommand extends Command {

    private int index;

    /**
     * Constructs an UnmarkCommand with the specified index of the task to unmark.
     *
     * @param index The index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the UnmarkCommand by setting the specified task as not done in the TaskList.
     * Shows a message indicating the task has been unmarked.
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
            tasks.unmark(this.index);
            ui.showUnmarkMsg(tasks.getTasks().get(this.index - 1));
            storage.save(tasks);
        } else {
            throw new DukeException("Invalid index. "
                    + "Please provide a valid index within the range 1 to "
                    + tasks.getTaskSize() + ".");
        }
    }

    /**
     * Checks if the UnmarkCommand is an exit command.
     *
     * @return false, as the UnmarkCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
