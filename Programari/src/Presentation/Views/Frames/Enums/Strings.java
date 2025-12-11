package Presentation.Views.Frames.Enums;

/**
 * Enum representing various strings used in the user interface.
 */
public enum Strings {
    /**
     * Welcome message displayed to users.
     */
    WELCOME("Benvingut al Coffee Clicker"),

    /**
     * Login option text.
     */
    LOGIN("Iniciar sessió"),

    /**
     * Recover password text.
     */
    RECOVER_PASSWORD("Recuperar contrasenya"),

    /**
     * Recover password text.
     */
    REQUEST_PASSWORD("Solicitar contrasenya"),

    /**
     * Recover password text.
     */
    MESSAGE_PASSWORD("La vostra contrasenya és: "),

    /**
     * Recover password text.
     */
    NO_USER_IN_DATABASE("No existeix aquest usuari a la base de dades. Si us plau, prova de nou"),

    /**
     * Restore password option text.
     */
    RESTORE_PASS("Recuperar contrasenya"),

    /**
     * Register option text.
     */
    REGISTER("Registrar-se"),

    /**
     * Label for username field.
     */
    USERNAME("Nom d'usuari:"),

    /**
     * Label for username or mail field.
     */
    USERNAME_MAIL("Nom d'usuari o mail:"),

    /**
     * Label for new game name field.
     */
    GAME_NAME("Nom de la nova partida:"),

    /**
     * Label for email field.
     */
    EMAIL("Correu electrònic:"),

    /**
     * Format hint for email field.
     */
    EMAIL_FORMAT("* Inclou @ i domini (.com/.cat/.es/etc)"),

    /**
     * Format hint for password field.
     */
    PASSWORD_FORMAT("* Inclou almenys 8 caràcters alfanumèrics"),

    /**
     * Label for password field.
     */
    PASSWORD("Contrasenya:"),

    /**
     * Label for confirm password field.
     */
    CONFIRM_PASSWORD("Confirma contrasenya:"),

    /**
     * Error message for when the timer ends.
     */
    TIMER_ENDED("Ha trigat massa a interectuar, si desitja realitzar alguna acció prem algun botó de nou"),

    /**
     * Error message for incomplete fields.
     */
    ERROR_PLEASE_COMPLETE_ALL_FIELDS("Si us plau, completa tots els camps."),

    /**
     * Prompt for entering new user data.
     */
    ENTER_NEW_USER_DATA("Introdueix les dades del teu nou usuari"),

    /**
     * Message for when the user has no open game.
     */
    YOU_HAVE_NO_OPEN_GAME("Estas preparat per jugar :)"),

    /**
     * Message for when the user has an open game.
     */
    YOU_HAVE_AN_OPEN_GAME("Tens una partida oberta"),

    /**
     * Button text for continuing a game.
     */
    CONTINUE_GAME("Continuar partida"),

    /**
     * Error message for trying to anger a coffee.
     */
    CANT_ANGER_A_COFFEE("No pots enfadar a un cafè"),

    /**
     * Confirmation message for deleting an account.
     */
    CONFIRMATION_DELETE_ACCOUNT("Segur que vols eliminar el compte?"),

    /**
     * Confirmation message for logging out.
     */
    CONFIRMATION_LOG_OUT("Segur que vols tancar sessió?"),

    /**
     * Confirmation option text.
     */
    CONFIRM("Estic segur"),

    /**
     * Non-confirmation option text.
     */
    NOT_CONFIRM("No estic segur"),

    /**
     * Button text for viewing statistics.
     */
    VIEW_STATISTICS("Veure estadístiques"),

    /**
     * Button text for starting a game.
     */
    START_GAME("Començar a jugar"),

    /**
     * Button text for deleting a user.
     */
    DELETE_USER("Eliminar el compte"),

    /**
     * Title for power-ups section.
     */
    POWER_UPS_TITLE("MILLORES"),

    /**
     * Title for power-ups  GENERADORS.
     */
    GENERATORS_TITLE("GENERADORS"),

    /**
     * Advisory message for deleting a user.
     */
    DELETE_USER_ADVISE("(!) Si s'elimina el compte del sistema les dades d'aquest perfil no es podran recuperar mai (!)"),

    /**
     * Logout option text.
     */
    LOGOUT("Tanca sessió"),

    /**
     * Profile management option text.
     */
    PROFILE("Gestió del compte"),

    /**
     * Button text for buying something.
     */
    BUY("Comprar"),

    /**
     * Error message for not having a generator.
     */
    NO_GENERATOR("Per a comprar una millora has de tenir el generador al que afecta!"),

    /**
     * Error message for not having enough coffees.
     */
    NOT_ENOUGH_COFFEES("No té suficients cafés per a comprar la millora!"),

    /**
     * Button text for ending a game.
     */
    END_GAME("Finalitzar partida");

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
