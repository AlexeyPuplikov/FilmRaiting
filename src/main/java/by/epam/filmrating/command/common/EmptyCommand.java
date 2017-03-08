package by.epam.filmrating.command.common;

import by.epam.filmrating.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements IActionCommand {
    private final static String PATH_INDEX_PAGE = "path.page.index";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty(PATH_INDEX_PAGE);
    }
}
