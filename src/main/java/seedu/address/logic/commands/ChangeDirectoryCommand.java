package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_PATH_NOT_FOUND;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.path.AbsolutePath;


/**
 * Change directory to target path.
 */
public class ChangeDirectoryCommand extends Command {

    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_SUCCESS = "Directory changed to: %1$s";

    public static final String MESSAGE_CURRENT_DIRECTORY = "You are already in the directory: %1$s";

    public static final String MESSAGE_INVALID_DEST = "Student path is not navigable.";

    public static final String MESSAGE_USAGE =
            "Usage: " + COMMAND_WORD + " <path> \n"
            + "\n"
            + "Change directory.\n"
            + "\n"
            + "Argument: \n"
            + "    path                 Navigable path e.g. root or group\n"
            + "\n"
            + "Option: \n"
            + "    -h, --help           Show this help menu\n"
            + "\n"
            + "Examples: \n"
            + "cd grp-003";

    public static final ChangeDirectoryCommand HELP_MESSAGE = new ChangeDirectoryCommand();

    private final AbsolutePath dest;
    private final boolean isHelp;

    /**
     * Constructs a {@code ChangeDirectoryCommand} with the target path.
     *
     * @param dest The target path.
     */
    public ChangeDirectoryCommand(AbsolutePath dest) {
        requireAllNonNull(dest);
        this.dest = dest;
        this.isHelp = false;
    }

    private ChangeDirectoryCommand() {
        this.dest = null;
        isHelp = true;
    }

    /**
     * Executes the {@code ChangeDirectoryCommand}, change current directory of ProfBook to target path.
     *
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isHelp) {
            return new CommandResult(MESSAGE_USAGE);
        }

        if (!model.hasPath(dest)) {
            throw new CommandException(String.format(MESSAGE_PATH_NOT_FOUND, dest));
        }

        if (model.getCurrPath().equals(dest)) {
            throw new CommandException(String.format(MESSAGE_CURRENT_DIRECTORY, model.getCurrPath()));
        }

        if (dest.isStudentDirectory()) {
            throw new CommandException(MESSAGE_INVALID_DEST);
        }

        model.changeDirectory(dest);

        return new CommandResult(String.format(MESSAGE_SUCCESS, dest));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeDirectoryCommand)) {
            return false;
        }

        ChangeDirectoryCommand otherChangeDirectoryCommand = (ChangeDirectoryCommand) other;
        return this.dest.equals(otherChangeDirectoryCommand.dest);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Destination", dest)
                .toString();
    }
}
