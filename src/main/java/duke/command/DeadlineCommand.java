package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * A class that inherits from Command class.
 * Represents a command that adds a deadline task.
 */
public class DeadlineCommand extends Command {

    private Deadline deadline;

    /**
     * Constructs a DeadlineCommand with the specified description and deadline date/time.
     *
     * @param description The description of the deadline task.
     * @param by          The date/time by which the task should be completed.
     */
    public DeadlineCommand(String description, String by) {
        this.deadline = new Deadline(description, by);
    }

    /**
     * Executes the DeadlineCommand by adding the created deadline task to the TaskList,
     * displaying a confirmation message, and saving the updated TaskList to storage.
     * Saves the changes into the file.
     *
     * @param tasks   The TaskList that holds the list of tasks.
     * @param ui      The Ui to interact with the user.
     * @param storage The Storage to save the tasks to a file.
     * @throws DukeException If there is an error while executing the command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.add(deadline);
        ui.showAddMsg(deadline, tasks.getTaskSize());
        storage.save(tasks);
    }

    /**
     * Checks if the DeadlineCommand is an exit command.
     *
     * @return false, as the DeadlineCommand is not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
