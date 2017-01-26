package by.epam.filmrating.command;

public enum CommandEnum {
    VIEW_FILMS {
        {
            this.command = new ViewFilmsCommand();
        }
    };
    ActionCommand command;
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
