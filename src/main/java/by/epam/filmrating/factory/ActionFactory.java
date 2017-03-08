package by.epam.filmrating.factory;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.command.common.CommandEnum;
import by.epam.filmrating.command.common.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public final class ActionFactory {
    private final static String PARAM_COMMAND = "command";

    public static IActionCommand defineCommand(HttpServletRequest request) {
        IActionCommand command;
        String action = request.getParameter(PARAM_COMMAND);
        if(action == null || action.isEmpty()) {
            command = new EmptyCommand();
        } else {
            CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
            command = commandEnum.getCurrentCommand();
        }
        return command;
    }
}
