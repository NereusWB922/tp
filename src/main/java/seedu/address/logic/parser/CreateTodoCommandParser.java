package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_MISSING_ARGUMENT;
import static seedu.address.logic.commands.CreateTodoCommand.COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.OPTION_ALL;
import static seedu.address.logic.parser.CliSyntax.OPTION_DESC;

import seedu.address.logic.commands.Category;
import seedu.address.logic.commands.CreateTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.path.AbsolutePath;
import seedu.address.model.path.RelativePath;
import seedu.address.model.task.ToDo;

/**
 * Parses input arguments and creates a new CreateTodoForGroupCommand object
 */
public class CreateTodoCommandParser implements Parser<CreateTodoCommand> {
    //todo only need one todo command for both group and student

    /**
     * Parses the given {@code String} of arguments in the context of the CreateTodoForGroupCommand
     * and returns an CreateTodoForGroupCommand object for execution.
     *
     * @param args The command arguments to be parsed.
     * @param currPath The current path of the application.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateTodoCommand parse(String args, AbsolutePath currPath) throws ParseException {

        if (ParserUtil.hasHelpOption(args)) {
            return CreateTodoCommand.HELP_MESSAGE;
        }

        ParserUtil.verifyAllOptionsValid(args, OPTION_DESC, OPTION_ALL);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, OPTION_DESC, OPTION_ALL);

        if (!ParserUtil.areOptionsPresent(argMultimap, OPTION_DESC)) {
            throw new ParseException(MESSAGE_MISSING_ARGUMENT.apply(COMMAND_WORD));
        }

        argMultimap.verifyNoDuplicateOptionsFor(OPTION_DESC, OPTION_ALL);

        // If no path given, default to current path.
        AbsolutePath fullTargetPath = null;
        if (argMultimap.getPreamble().isEmpty()) {
            fullTargetPath = currPath;
        } else {
            RelativePath target = ParserUtil.parseRelativePath(argMultimap.getPreamble());
            fullTargetPath = ParserUtil.resolvePath(currPath, target);
        }

        ToDo todo = ParserUtil.parseToDo(argMultimap.getValue(OPTION_DESC).get());

        if (argMultimap.getValue(OPTION_ALL).isEmpty()) {
            return new CreateTodoCommand(fullTargetPath, todo, Category.NONE);
        }
        Category category = ParserUtil.parseCategory(argMultimap.getValue(OPTION_ALL).get());
        return new CreateTodoCommand(fullTargetPath, todo, category);
    }
}
