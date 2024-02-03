import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;


/**
 * A Personal Assistant Chatbot that helps a person to keep track of various things.
 */
public class Duke {

    private static final String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    private static final String COMMANDS =
            "bye: Terminate the program.\n" +
            "list: Display the list of tasks.\n" +
            "mark <index>: Mark a task as done.\n" +
            "unmark <index>: Mark a task as not done.\n" +
            "todo <description>: Add a todo task.\n" +
            "deadline <description> /by <dueDate>: Add a deadline task.\n" +
            "event <description> /from <startDate> /to <endDate>: Add an event task.\n";


    private enum Command {
        HELLO, BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, HELP
    }

    private Scanner scanner;

    private ArrayList<Task> tasks;

    private int counter;

    private static final String DIRECTORY_PATH = "./data/";

    private static final String FILE_PATH = "./data/duke.txt";

    public Duke() {
        this.scanner = new Scanner(System.in);
        this.tasks = new ArrayList<>();
        loadSavedTasks();
        checkReminder();
        this.counter = tasks.size();
    }

    /**
     * Saves the current list of tasks to a file.
     * If the file or directory doesn't exist, it will be created.
     * Each task is written to a new line in the file.
     *
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTasks() {
        File directory = new File(DIRECTORY_PATH);

        // Create a directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Write the tasks in the list into the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file into the current list of tasks.
     * If the file doesn't exist, a new file will be created.
     * Each line in the file is considered as a task and is added to the list.
     *
     * @throws IOException If an error occurs while reading from the file.
     */
    public void loadSavedTasks() {
        File file = new File(FILE_PATH);

        // Create a new file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = Task.fromString(line);
                    tasks.add(task);
                }
            } catch (IOException e) {
                System.out.println("Error loading tasks: " + e.getMessage());
            }
        }
    }

    /**
     * Reminder for task that is going to due soon.
     */
    public void checkReminder() {
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                System.out.println("Reminder: ");
                ((Deadline) task).reminder();
            }
        }
    }


    /**
     * Runs the program, processing user commands until the "bye" command is entered.
     * The user is prompted with a greeting and can interact with the chatbot.
     * Recognized commands include list, mark, unmark, todo, deadline, event, and more.
     * Further carry out the following functions based on the command entered.
     */
    public void run() {
        System.out.println("Hello from\n" + logo);

        while (scanner.hasNextLine()) {
            try {
                String[] input = scanner.nextLine().split(" ", 2);
                Command command;

                try {
                    // convert string to enum and handle case insensitive
                    command = Command.valueOf(input[0].toUpperCase());

                }  catch (IllegalArgumentException e) {
                    throw new DukeException("An error occurred: Invalid command.\n" +
                            "Please enter 'help' for a list of valid commands.\n");
                }
                switch (command) {
                    case HELLO:
                        System.out.println("Hey there! This is Dooloodoodooloodoo!\n" + "What can I do for you?\n");
                        break;
                    case BYE:
                        exit();
                        break;
                    case LIST:
                        listTasks();
                        break;
                    case MARK:
                        markTaskAsDone(input);
                        break;
                    case UNMARK:
                        markTaskAsUndone(input);
                        break;
                    case TODO:
                        addToDoTask(input);
                        break;
                    case DEADLINE:
                        addDeadline(input);
                        break;
                    case EVENT:
                        addEvent(input);
                        break;
                    case DELETE:
                        delete(input);
                        break;
                    case HELP:
                        System.out.println(COMMANDS);
                        break;
                }

            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Delete a task from the list.
     * @param input A string array containing the command and index number of a task.
     */
    public void delete(String[] input) throws DukeException {
        if (input.length == 1) {
            throw new DukeException("Please indicate the index of task you want to delete.\n" +
                    "Please enter 'help' command to find out more.");
        }
        int index = Integer.parseInt(input[1]) - 1;
        Task deletedTask = tasks.remove(index);
        counter--;
        System.out.println("Noted. I've removed this task:\n" +
                deletedTask);
        System.out.format("Now you have %d tasks in the list.\n", counter);
        saveTasks();
    }

    /**
     * Adds a ToDo task to the task list.
     *
     * @param input A string array containing the command and task description.
     */
    public void addToDoTask(String[] input) throws DukeException {
        if (input.length == 1) {
            throw new DukeException("OOPS! The description of a todo cannot be left blank.\n" +
                    "Please enter 'help' command to find out more.");
        }
        ToDo todo = new ToDo(input[1]);
        addTasks(todo);
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input A string array containing the command and task description with the "/by" as separator.
     */
    public void addDeadline(String[] input) throws DukeException {
        if (input.length == 1) {
            throw new DukeException("OOPS! The description of a deadline cannot be left blank.\n" +
                    "Please enter 'help' command to find out more.");
        } else if (!input[1].contains("/by")) {
            throw new DukeException("OOPS! The date/time for the deadline cannot be left blank.\n" +
                    "Please enter 'help' command to find out more.");
        }
        String[] description = input[1].split("/by ");
        Deadline dd = new Deadline(description[0], description[1]);
        addTasks(dd);
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param input A string array containing the command and task description with the "/from" and "/to" as separators.
     */
    public void addEvent(String[] input) throws DukeException {
        if (input.length == 1) {
            throw new DukeException("OOPS! The description of an event cannot be left blank.\n" +
                    "Please enter 'help' command to find out more.");
        } else if (!input[1].contains("/from") && !input[1].contains("/to")) {
            throw new DukeException("OOPS! The start time and end time cannot be left blank.\n" +
                    "Please enter 'help' command to find out more.");
        }
        String[] description = input[1].split("/from|/to");
        Event e = new Event(description[0], description[1], description[2]);
        addTasks(e);
    }

    /**
     * Marks a task as done.
     *
     * @param input A string array containing the command and the index of the task to mark as done.
     */
    public void markTaskAsDone(String[] input) {
        int index = Integer.parseInt(input[1]);
        tasks.get(index - 1).markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks.get(index - 1).toString());
        saveTasks();
    }

    /**
     * Marks a task as undone.
     *
     * @param input A string array containing the command and index of the task to mark as undone.
     */
    public void markTaskAsUndone(String[] input) {
        int index = Integer.parseInt(input[1]);
        tasks.get(index - 1).markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks.get(index - 1).toString());
        saveTasks();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    public void addTasks(Task task) {
        tasks.add(task);
        counter++;
        System.out.println("Got it. I've added this task: \n" + task);
        System.out.format("Now you have %d tasks in the list.\n", counter);
        saveTasks();
    }

    /**
     * Prints out the list of tasks.
     */
    public void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for(int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Saves the changes and terminates the program.
     */
    public void exit() {
        System.out.println("Goodbye. Have a great day ahead!");
        System.exit(0);
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}

