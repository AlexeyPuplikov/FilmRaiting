package by.epam.filmrating.command;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.service.FilmService;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;


public class UploadFileCommand implements ActionCommand {
    private final static String PATH_PAGE_MAIN_ADMIN = "path.page.admin";

    private FilmService filmService;

    public UploadFileCommand() {
        this.filmService = new FilmService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        InputStream inputStream = null;
        Part filePart = null;
        try {
            filePart = request.getPart("image");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        if (filePart != null) {
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            try {
                inputStream = filePart.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);
                image = image.getSubimage(0, 0, 350, 500);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                filmService.addCoverToFilm(inputStream, filmService.findFilmByName(request.getParameter("filmName")));
            } catch (IOException | ServiceException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return configurationManager.getProperty(PATH_PAGE_MAIN_ADMIN);
    }
}
