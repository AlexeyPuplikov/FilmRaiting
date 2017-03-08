package by.epam.filmrating.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextManager {
    private static final String PATH_TEXT = "properties.text";

    public static String getProperty(String res, Locale locale) {
        ResourceBundle resourceBundle;
        if (locale != null) {
            resourceBundle = ResourceBundle.getBundle(PATH_TEXT, locale);
        } else {
            resourceBundle = ResourceBundle.getBundle(PATH_TEXT);
        }
        return resourceBundle.getString(res);
    }
}
