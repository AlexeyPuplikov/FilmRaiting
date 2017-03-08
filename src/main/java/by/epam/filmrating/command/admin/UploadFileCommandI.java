package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class UploadFileCommandI implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_IMAGE = "image";
    private final static String PARAM_FILM_NAME = "filmName";
    private final static String IMAGE_FORMAT = "jpg";
    private final static String ADD_COVER_SUCCESSFUL= "successful";

    private FilmService filmService;

    public UploadFileCommandI() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            Part filePart = request.getPart(PARAM_IMAGE);
            if (filePart != null) {
                inputStream = filePart.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);
                image = image.getSubimage(0, 0, 350, 500);
                byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, IMAGE_FORMAT, byteArrayOutputStream);
                inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                filmService.addCoverToFilm(inputStream, filmService.findEntityBySign(request.getParameter(PARAM_FILM_NAME)));
            }
        } catch (IOException | ServiceException | ServletException e) {
            request.setAttribute(PARAM_EXCEPTION, configurationManager.getProperty(SERVICE_ERROR));
            return configurationManager.getProperty(PATH_ERROR_PAGE);
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&successfulAddCover=" + ADD_COVER_SUCCESSFUL;
    }
}
