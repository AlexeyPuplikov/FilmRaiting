package by.epam.filmrating.factory;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.command.CommandEnum;
import by.epam.filmrating.command.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public final class ActionFactory {
    private final static String COMMAND_PARAM = "command";

    private ActionFactory() {}

    public static ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand command;
        String action = request.getParameter(COMMAND_PARAM);
        if(action == null || action.isEmpty()) {
            command = new EmptyCommand();
        } else {
            CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
            command = commandEnum.getCurrentCommand();
        }
        return command;
    }
}
