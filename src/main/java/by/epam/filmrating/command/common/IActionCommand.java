package by.epam.filmrating.command.common;

import javax.servlet.http.HttpServletRequest;

public interface IActionCommand {
    String execute(HttpServletRequest request);
}
