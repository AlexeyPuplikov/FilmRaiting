package by.epam.filmrating.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final String PATH_CONFIG = "properties.config";

    public static String getProperty(String res) {
        return ResourceBundle.getBundle(PATH_CONFIG).getString(res);
    }
}
