package Business;

import Business.Exceptions.BusinessException;
import Persistence.MySqlDAO;
import Persistence.Exceptions.PersistenceException;
import Persistence.SqlDAO;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The SignUpManager class manages user registration and validation of registration data.
 */
public class SignUpManager {

    /**
     * Error message indicating that the user already exists.
     */
    private static final String ERROR_ALREADY_EXISTING_USER_MESSAGE = "Aquest usuari ja existeix, tria'n un de diferent";

    /**
     * Error message indicating incorrect email format.
     */
    private static final String ERROR_EMAIL_FORMAT_INCORRECT_MESSAGE = "Format correu electrònic incorrecte, utilitzi @ i el domini";

    /**
     * Error message indicating incorrect password format.
     */
    private static final String ERROR_PASSWORD_FORMAT_MESSAGE = "Format contrasenya incorrecte, introdueix almenys una majúscula, una minúscula i un dígit";

    /**
     * Error message indicating invalid password length.
     */
    private static final String ERROR_PASSWORD_LENGTH_MESSAGE = "Mida contrasenya invàl·lida, mínim ha de ser de contenir 8 caràcters";

    /**
     * Error message indicating that the passwords do not match.
     */
    private static final String ERROR_PASSWORDS_DONT_MATCH_MESSAGE = "Les contrasenyes no coincideixen";

    private String errorString;
    private SqlDAO sqlDAO;

    /**
     * Default constructor for the class Sign-Up Manager.
     * @param mySqlDAO Class that implements the necessary functions to talk to the database.
     */
    public SignUpManager(MySqlDAO mySqlDAO){
        sqlDAO = mySqlDAO;
        errorString = null;
    }

    /**
     * Method that checks if all registration fields are completed.
     *
     * @param userName The username.
     * @param email The email address.
     * @param password The password.
     * @param confirmPassword The confirmation password.
     * @return true if all fields are completed, false otherwise.
     */
    public boolean fieldsCompleted(String userName, String email, String password, String confirmPassword) {
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Method that registers a new user.
     *
     * @param userName The username.
     * @param mail The email address.
     * @param password The password.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void registerUser(String userName, String mail, String password) throws BusinessException {
        try {
            sqlDAO.createUser(userName, mail, password);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Method that checks if the username is unique.
     *
     * @param newUserName The username to be checked.
     * @return true if the username is unique, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    private boolean checkUsername(String newUserName) throws BusinessException {

        try {
            ArrayList<String> userNames = sqlDAO.readAllUsers();

            for (String userName : userNames) {
                if (userName.equalsIgnoreCase(newUserName)) {
                    concatenateError(ERROR_ALREADY_EXISTING_USER_MESSAGE);
                    return false;
                }
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

        return true;
    }

    /**
     * Method that checks the email format.
     *
     * @param mail The email address.
     * @return true if the email format is correct, false otherwise.
     */
    private boolean checkEmailFormat(String mail) {
        String format = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mail);

        if (matcher.matches()) {
            return true;
        }

        concatenateError(ERROR_EMAIL_FORMAT_INCORRECT_MESSAGE);

        return false;
    }

    /**
     * Method that checks the password format, length, and if it matches the confirmation password.
     *
     * @param password The password.
     * @param checkPassword The confirmation password.
     * @return true if the password format, length, and match are correct, false otherwise.
     */
    private boolean checkPasswordFormat(String password, String checkPassword) {
        boolean passwordCorrect = true;

        //Format que haurà de seguir la contrasenya, minúscules, majúscules i algun dígit.
        String format = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            concatenateError(ERROR_PASSWORD_FORMAT_MESSAGE);
            passwordCorrect = false;
        }

        if (8 > password.length()) {
            concatenateError(ERROR_PASSWORD_LENGTH_MESSAGE);
            passwordCorrect = false;
        }

        if (!password.equals(checkPassword)) {
            concatenateError(ERROR_PASSWORDS_DONT_MATCH_MESSAGE);
            passwordCorrect = false;
        }

        return passwordCorrect;
    }

    /**
     * Method that checks if the registration data is correct.
     *
     * @param userName The username.
     * @param mail The email address.
     * @param password The password.
     * @param checkPassword The confirmation password.
     * @return true if the data is correct, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public boolean checkData(String userName, String mail, String password, String checkPassword) throws BusinessException {
        boolean userNameCorrect = checkUsername(userName);
        boolean emailCorrect = checkEmailFormat(mail);
        boolean passwordCorrect = checkPasswordFormat(password, checkPassword);

        if (userNameCorrect && emailCorrect && passwordCorrect) {
            return true;
        }
        return false;
    }

    /**
     * Method that concatenates an error message to the existing error string.
     *
     * @param message The error message to be concatenated.
     */
    private void concatenateError(String message) {
        if (null == errorString) {
            errorString = String.copyValueOf(("* " + message + "\n").toCharArray());
        } else {
            errorString += "* " + message + "\n";
        }
    }

    /**
     * Method that retrieves the error string.
     *
     * @return The error string.
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Method that resets the error string to null.
     */
    public void setErrorStringNull() {
        errorString = null;
    }
}
