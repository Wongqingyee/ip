package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A class that inherits from the Command class.
 * Represents a command to find tasks containing a specified keyword.
 */
public class FindCommand extends Command {

    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command, searching for tasks with the specified keyword.
     *
     * @param tasks   The list of tasks to search.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks to a file.
     * @throws DukeException If the keyword is not found in the list of tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (!tasks.findTasksByKeyword(keyword).isEmpty()) {
            ui.showFindMsg(tasks.findTasksByKeyword(keyword));
        } else {
            throw new DukeException("Keyword is not found.");
        }
    }

    /**
     * Checks if the FindCommand is an exit command.
     *
     * @return false, as the FindCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
