package by.epam.filmrating.servlet;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.factory.ActionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig(maxFileSize = 16177215)
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IActionCommand command = ActionFactory.defineCommand(req);
        String page = command.execute(req);
        if (page.startsWith("redirect")){
            page = page.replace("redirect:", "");
            resp.sendRedirect(page);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(req, resp);
        }
    }
}
