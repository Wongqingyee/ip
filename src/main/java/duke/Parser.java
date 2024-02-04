package duke;

import duke.command.*;
import duke.exception.DukeException;

/**
 * The Parser class is responsible for parsing user input and converting it into executable commands.
 * It recognizes different command types and returns the corresponding Command objects.
 */
public class Parser {

    private enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, HELP
    }

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param userInput The user-entered command.
     * @return A Command object representing the parsed command.
     * @throws DukeException When the command is invalid.
     */
    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    public static Command parse(String userInput) throws DukeException {
        String[] inputs = userInput.split(" ", 2);

        try {
            // handle case-insensitive
            CommandType c = CommandType.valueOf(inputs[0].toUpperCase());

            switch (c) {
            case BYE:
                return new ExitCommand();
            case LIST:
                return new ListCommand();
            case HELP:
                return new HelpCommand();
            case DELETE:
                return parseDeleteCommand(inputs);
            case MARK:
                return parseMarkCommand(inputs);
            case UNMARK:
                return parseUnmarkCommand(inputs);
            case TODO:
                return parseTodoCommand(inputs);
            case DEADLINE:
                return parseDeadlineCommand(inputs);
            case EVENT:
                return parseEventCommand(inputs);
            case FIND:
                return parseFindCommand(inputs);
            default:
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new DukeException("An error occurred: Invalid command.\n"
                    + "Please enter 'help' for a list of valid commands.");
        }
    }

    /**
     * Parses a todo command and returns the corresponding ToDoCommand.
     *
     * @param inputs The string array containing the user input.
     * @return A ToDoCommand.
     * @throws DukeException If the description of the todo is missing.
     */
    private static Command parseTodoCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("OOPS! The description of a todo cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        }
        String description = inputs[1];
        return new ToDoCommand(description);
    }

    /**
     * Parses a delete command and returns the corresponding DeleteCommand.
     *
     * @param inputs The string array containing the user input.
     * @return A DeleteCommand.
     * @throws DukeException If the index of the task to delete is missing.
     */
    private static Command parseDeleteCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("Please indicate the index of task you want to delete.\n"
                    + "Please enter 'help' command to find out more.");
        }
        int index = Integer.parseInt(inputs[1]);
        return new DeleteCommand(index);
    }

    /**
     * Parses the user input to create a MarkCommand.
     *
     * @param inputs The string array containing the user input.
     * @return A MarkCommand.
     * @throws DukeException If the index is not provided.
     */
    private static Command parseMarkCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("Please indicate the index of task you want to mark as done.\n"
                    + "Please enter 'help' command to find out more.");
        }
        int index = Integer.parseInt(inputs[1]);
        return new MarkCommand(index);
    }

    /**
     * Parses the user input to create an UnmarkCommand.
     *
     * @param inputs The string array containing the user input.
     * @return An UnmarkCommand.
     * @throws DukeException If the index is not provided.
     */
    private static Command parseUnmarkCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("Please indicate the index of task you want to mark as not done.\n"
                    + "Please enter 'help' command to find out more.");
        }
        int index = Integer.parseInt(inputs[1]);
        return new UnmarkCommand(index);
    }

    /**
     * Parses the user input to create a DeadlineCommand.
     *
     * @param inputs The string array containing the user input.
     * @return A DeadlineCommand.
     * @throws DukeException If the description or date/time is missing in the input.
     */
    private static Command parseDeadlineCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("OOPS! The description of a deadline cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        } else if (!inputs[1].contains("/by")) {
            throw new DukeException("OOPS! The date/time for the deadline cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        }
        String[] descriptions = inputs[1].split("/by ");
        return new DeadlineCommand(descriptions[0], descriptions[1]);
    }

    /**
     * Parses the user input to create an EventCommand.
     *
     * @param inputs The string array containing the user input.
     * @return An EventCommand.
     * @throws DukeException If the description or start/end time is missing in the input.
     */
    private static Command parseEventCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("OOPS! The description of an event cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        } else if (!inputs[1].contains("/from") && !inputs[1].contains("/to")) {
            throw new DukeException("OOPS! The start time and end time cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        }
        String[] descriptions = inputs[1].split("/from|/to");
        return new EventCommand(descriptions[0], descriptions[1], descriptions[2]);
    }

    /**
     * Parses the user input to create a FindCommand for searching tasks by a keyword.
     *
     * @param inputs The string array containing the user inputs.
     * @return A FindCommand for searching tasks by the specified keyword.
     * @throws DukeException If the keyword is left blank.
     */
    public static Command parseFindCommand(String[] inputs) throws DukeException {
        if (inputs.length == 1) {
            throw new DukeException("OOPS! The keyword to find cannot be left blank.\n"
                    + "Please enter 'help' command to find out more.");
        }
        return new FindCommand(inputs[1]);
    }
}
