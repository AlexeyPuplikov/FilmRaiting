package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.common.IActionCommand;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.ConfigurationManager;
import by.epam.filmrating.manager.TextManager;
import by.epam.filmrating.service.CountryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class AddCountryCommand implements IActionCommand {
    private final static String PATH_ERROR_PAGE = "path.page.error";
    private final static String PARAM_EXCEPTION = "exception";
    private final static String SERVICE_ERROR = "error.service";
    private final static String PARAM_COUNTRY_NAME = "countryName";
    private final static String ADD_PARAMETERS_SUCCESSFUL = "successful";
    private final static String PARAM_LOCALE = "locale";
    private final static String ADD_PARAMETERS_ERROR = "error";

    private CountryService countryService;

    public AddCountryCommand() {
        this.countryService = new CountryService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(PARAM_COUNTRY_NAME);
        if (checkName(name)) {
            Country country = new Country();
            country.setName(name);
            try {
                countryService.create(country);
            } catch (ServiceException e) {
                request.setAttribute(PARAM_EXCEPTION, TextManager.getProperty(SERVICE_ERROR, (Locale) request.getSession().getAttribute(PARAM_LOCALE)));
                return ConfigurationManager.getProperty(PATH_ERROR_PAGE);
            }
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&successfulAddParameters=" + ADD_PARAMETERS_SUCCESSFUL;
        } else {
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE&errorAddParameters=" + ADD_PARAMETERS_ERROR;
        }
    }

    private boolean checkName(String name) {
        List<Country> countries;
        boolean check = true;
        try {
            countries = this.countryService.findAll();
            for (Country country : countries) {
                if (country.getName().equalsIgnoreCase(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
