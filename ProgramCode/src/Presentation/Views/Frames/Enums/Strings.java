package Presentation.Views.Frames.Enums;

/**
 * Enum representing various strings used in the user interface.
 */
public enum Strings {
    /**
     * Welcome message displayed to users.
     */
    WELCOME("Welcome to Coffee Clicker"),

    /**
     * Login option text.
     */
    LOGIN("Log in"),

    /**
     * Recover password text.
     */
    RECOVER_PASSWORD("Recover Password"),

    /**
     * Recover password text.
     */
    REQUEST_PASSWORD("Request Password"),

    /**
     * Recover password text.
     */
    MESSAGE_PASSWORD("Your password is: "),

    /**
     * Recover password text.
     */
    NO_USER_IN_DATABASE("The user does not exist in the database. Please, try again"),

    /**
     * Restore password option text.
     */
    RESTORE_PASS("Restore password"),

    /**
     * Register option text.
     */
    REGISTER("Register"),

    /**
     * Label for username field.
     */
    USERNAME("Username:"),

    /**
     * Label for username or mail field.
     */
    USERNAME_MAIL("Username or email:"),

    /**
     * Label for new game name field.
     */
    GAME_NAME("New game name:"),

    /**
     * Label for email field.
     */
    EMAIL("Email:"),

    /**
     * Format hint for email field.
     */
    EMAIL_FORMAT("* Includes @ and domain (.com/.cat/.es/etc)"),

    /**
     * Format hint for password field.
     */
    PASSWORD_FORMAT("* includes at least 8 alphanumerical characters"),

    /**
     * Label for password field.
     */
    PASSWORD("Password:"),

    /**
     * Label for confirm password field.
     */
    CONFIRM_PASSWORD("Confirm password:"),

    /**
     * Error message for when the timer ends.
     */
    TIMER_ENDED("You took to long to take action, if you want to do something press any button again"),

    /**
     * Error message for incomplete fields.
     */
    ERROR_PLEASE_COMPLETE_ALL_FIELDS("Please, fill in all the fields."),

    /**
     * Prompt for entering new user data.
     */
    ENTER_NEW_USER_DATA("Insert your username"),

    /**
     * Message for when the user has no open game.
     */
    YOU_HAVE_NO_OPEN_GAME("Are you ready to play? :)"),

    /**
     * Message for when the user has an open game.
     */
    YOU_HAVE_AN_OPEN_GAME("You have an open game"),

    /**
     * Button text for continuing a game.
     */
    CONTINUE_GAME("Continue game"),

    /**
     * Error message for trying to anger a coffee.
     */
    CANT_ANGER_A_COFFEE("You can not anger a coffee"),

    /**
     * Confirmation message for deleting an account.
     */
    CONFIRMATION_DELETE_ACCOUNT("Are you sure you want to delete your account?"),

    /**
     * Confirmation message for logging out.
     */
    CONFIRMATION_LOG_OUT("Are you sure you want to close your session?"),

    /**
     * Confirmation option text.
     */
    CONFIRM("I am sure"),

    /**
     * Non-confirmation option text.
     */
    NOT_CONFIRM("I am not sure"),

    /**
     * Button text for viewing statistics.
     */
    VIEW_STATISTICS("View statistics"),

    /**
     * Button text for starting a game.
     */
    START_GAME("Start playing"),

    /**
     * Button text for deleting a user.
     */
    DELETE_USER("Delete account"),

    /**
     * Title for power-ups section.
     */
    POWER_UPS_TITLE("POWER-UPS"),

    /**
     * Title for power-ups  GENERADORS.
     */
    GENERATORS_TITLE("GENERATORS"),

    /**
     * Advisory message for deleting a user.
     */
    DELETE_USER_ADVISE("(!) If you delete the account from the system you will never be able to recover the data (!)"),

    /**
     * Logout option text.
     */
    LOGOUT("Close session"),

    /**
     * Profile management option text.
     */
    PROFILE("Profile management"),

    /**
     * Button text for buying something.
     */
    BUY("Buy"),

    /**
     * Error message for not having a generator.
     */
    NO_GENERATOR("To buy an upgrade you must have the generators it affects!"),

    /**
     * Error message for not having enough coffees.
     */
    NOT_ENOUGH_COFFEES("You don't have enough coffees to buy the upgrade!"),

    /**
     * Button text for ending a game.
     */
    END_GAME("End game");

    private final String value;

    /**
     * Constructs Strings enum with the specified value.
     * @param value The string value.
     */
    Strings(String value) {
        this.value = value;
    }

    /**
     * Retrieves the string value.
     * @return The value of the string.
     */
    public String getValue() {
        return value;
    }
}
