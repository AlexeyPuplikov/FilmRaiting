package by.epam.filmrating.command;

import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.CountryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddCountryCommand implements ActionCommand {
    private CountryService countryService;

    public AddCountryCommand() {
        this.countryService = new CountryService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("countryName");
        HttpSession httpSession = request.getSession();
        if (checkName(name)) {
            Country country = new Country();
            country.setName(name);
            try {
                countryService.create(country);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            httpSession.removeAttribute("addError");
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE";
        } else {
            httpSession.setAttribute("addError", "Такой элемент уже добавлен");
            return "redirect:/controller?command=OPEN_ADD_FILM_PAGE";
        }
    }

    private boolean checkName(String name) {
        List<Country> countries;
        boolean check = true;
        try {
            countries = this.countryService.findAll();
            for (Country country : countries) {
                if (country.getName().equals(name)) {
                    check = false;
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return check;
    }
}
