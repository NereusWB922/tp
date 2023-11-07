package seedu.address.logic.parser;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_MISSING_ARGUMENT;
import static seedu.address.logic.commands.ChangeDirectoryCommand.COMMAND_WORD;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ChangeDirectoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.path.AbsolutePath;
import seedu.address.model.path.RelativePath;

/**
 * Parses input arguments and creates a new ChangeDirectoryCommand object
 */
public class ChangeDirectoryCommandParser implements Parser<ChangeDirectoryCommand> {
    private static final Logger logger = LogsCenter.getLogger(ChangeDirectoryCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeDirectoryCommand
     * and returns an ChangeDirectoryCommand object for execution.
     *
     * @param args The command arguments to be parsed.
     * @param currPath The current path of the application.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeDirectoryCommand parse(String args, AbsolutePath currPath) throws ParseException {
        requireAllNonNull(args, currPath);

        // Checks if help option included.
        if (ParserUtil.hasHelpOption(args)) {
            return ChangeDirectoryCommand.HELP_MESSAGE;
        }

        // No option for cd command
        ParserUtil.verifyNoOption(args, COMMAND_WORD);

        String preamble = ArgumentTokenizer.extractPreamble(args);

        if (preamble.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_ARGUMENT.apply(COMMAND_WORD));
        }

        RelativePath path = ParserUtil.parseRelativePath(preamble);
        AbsolutePath targetPath = ParserUtil.resolvePath(currPath, path);

        logger.info("Creating ChangeDirectoryCommand with dest: " + path.toString());

        return new ChangeDirectoryCommand(targetPath);
    }
}
