package by.epam.filmrating.command;

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
            this.command = new AddFilmCommand();
        }
    },
    ADD_STAGE_DIRECTOR {
        {
            this.command = new AddStageDirectorCommand();
        }
    },
    ADD_ACTOR {
        {
            this.command = new AddActorCommand();
        }
    },
    ADD_GENRE {
        {
            this.command = new AddGenreCommand();
        }
    },
    ADD_COUNTRY {
        {
            this.command = new AddCountryCommand();
        }
    },
    OPEN_ADD_FILM_PAGE {
        {
            this.command = new OpenAddFilmPageCommand();
        }
    },
    SELECT_CREATE_PARAMETERS_TO_FILM {
        {
            this.command = new SelectCreateParametersToFilmCommand();
        }
    },
    ADDITIONAL_PARAMETERS_TO_FILM {
        {
            this.command = new AdditionalParametersToFilmCommand();
        }
    },
    UPLOAD_FILE {
        {
            this.command = new UploadFileCommand();
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
    }
    ;

    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
