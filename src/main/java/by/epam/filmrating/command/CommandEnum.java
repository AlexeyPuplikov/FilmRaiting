package by.epam.filmrating.command;

public enum CommandEnum {
    VIEW_FILMS {
        {
            this.command = new ViewFilmsCommand();
        }
    },
    VIEW_FILM {
        {
            this.command = new FilmCommand();
        }
    },
    ADD_COMMENT {
        {
            this.command = new AddCommentCommand();
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
            this.command = new ChangeLanguage();
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
    ADD_PARAMETERS_TO_FILM {
        {
            this.command = new AddParametersToFilmCommand();
        }
    },
    ADD_CREATE_PARAMETERS_TO_FILM {
        {
            this.command = new AddCreateParametersToFilmCommand();
        }
    },
    ADD_CORRECT_PARAMETERS_TO_FILM {
        {
            this.command = new AddCorrectParametersToFilm();
        }
    },
    UPLOAD_FILE {
        {
            this.command = new UploadFileCommand();
        }
    };

    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
