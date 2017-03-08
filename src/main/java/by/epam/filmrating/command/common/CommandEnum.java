package by.epam.filmrating.command.common;

import by.epam.filmrating.command.admin.*;
import by.epam.filmrating.command.client.AddCommentCommand;
import by.epam.filmrating.command.client.FindFilmCommand;
import by.epam.filmrating.command.client.SetRatingCommand;
import by.epam.filmrating.command.client.ViewFilmCommand;
import by.epam.filmrating.command.client.ViewFilmsCommand;
import by.epam.filmrating.command.client.ViewPersonCommand;

public enum CommandEnum {
    VIEW_FILMS {
        {
            this.command = new ViewFilmsCommand();
        }
    },
    VIEW_FILM {
        {
            this.command = new ViewFilmCommand();
        }
    },
    VIEW_PERSON {
        {
            this.command = new ViewPersonCommand();
        }
    },
    ADD_COMMENT {
        {
            this.command = new AddCommentCommand();
        }
    },
    OPEN_LOGIN_PAGE {
        {
            this.command = new OpenLoginPageCommand();
        }
    },
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    FIND_FILM {
        {
            this.command = new FindFilmCommand();
        }
    },
    SET_RATING {
        {
            this.command = new SetRatingCommand();
        }
    },
    ADD_FILM {
        {
            this.command = new AddFilmCommandI();
        }
    },
    ADD_STAGE_DIRECTOR {
        {
            this.command = new AddStageDirectorCommandI();
        }
    },
    ADD_ACTOR {
        {
            this.command = new AddActorCommandI();
        }
    },
    ADD_GENRE {
        {
            this.command = new AddGenreCommandI();
        }
    },
    ADD_COUNTRY {
        {
            this.command = new AddCountryCommandI();
        }
    },
    OPEN_ADD_FILM_PAGE {
        {
            this.command = new OpenAddFilmPageCommandI();
        }
    },
    SELECT_CREATE_PARAMETERS_TO_FILM {
        {
            this.command = new SelectCreateParametersToFilmCommandI();
        }
    },
    ADDITIONAL_PARAMETERS_TO_FILM {
        {
            this.command = new AdditionalParametersToFilmCommandI();
        }
    },
    UPLOAD_FILE {
        {
            this.command = new UploadFileCommandI();
        }
    },
    DELETE_FILM {
        {
            this.command = new DeleteFilmCommand();
        }
    },
    OPEN_MAIN_ADMIN_PAGE {
        {
            this.command = new OpenMainAdminPageCommand();
        }
    },
    USER_CONTROL {
        {
            this.command = new UserControlCommandI();
        }
    },
    OPEN_USER_CONTROL_PAGE {
        {
            this.command = new OpenUserControlPageCommandI();
        }
    };

    IActionCommand command;

    public IActionCommand getCurrentCommand() {
        return command;
    }
}
