package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A class that inherits from Command class.
 * Represents a command to display the list of tasks to the user.
 */
public class ListCommand extends Command {

    /**
     * Executes the ListCommand by displaying the list of tasks to the user.
     *
     * @param tasks   The TaskList that holds the list of tasks.
     * @param ui      The Ui to interact with the user.
     * @param storage The Storage to save the tasks to a file.
     * @throws DukeException If there is an error while executing the command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showList();
        tasks.list();
    }

    /**
     * Checks if the ListCommand is an exit command.
     *
     * @return false, as the ListCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
