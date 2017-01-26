package by.epam.filmrating.factory;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.command.CommandEnum;
import by.epam.filmrating.command.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private final static String COMMAND_PARAM = "command";

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand command = new EmptyCommand();
        String action = request.getParameter(COMMAND_PARAM);
        if(action == null || action.isEmpty()) {
            return command;
        }
        CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
        command = commandEnum.getCurrentCommand();
        return command;
    }
}
