package Business;

import Business.Exceptions.BusinessException;
import Persistence.MySqlDAO;
import Persistence.Exceptions.PersistenceException;
import Persistence.SqlDAO;

/**
 * Manages login-related operations such as validating user credentials, checking for ongoing games,
 * and handling password recovery.
 */
public class LoginManager {
    private SqlDAO dao;

    /**
     * Default constructor for the class Login Manager.
     * @param mySqlDAO Class that implements the necessary functions to talk to the database.
     */
    public LoginManager(MySqlDAO mySqlDAO) {
        this.dao = mySqlDAO;
    }

    /**
     * Validates user credentials by checking if the provided username and password match the records in the database.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the credentials are valid, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public boolean validateCredentials(String username, String password) throws BusinessException {
        boolean okay;

        try {
            okay = dao.verifyUserCredentialsUsername(username, password);

            if (okay) {
                return true;
            } else {
                return dao.verifyUserCredentialsEmail(username, password);
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * Checks if the specified user has an ongoing game.
     *
     * @param username The username of the user to check.
     * @return true if the user has an ongoing game, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public boolean hasOnGoingGame(String username) throws BusinessException {

        try {
            if (null == dao.readCurrentGame(username)) {
                return false;
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

        return true;
    }

    /**
     * Checks if both username and email fields are completed.
     *
     * @param username The username input.
     * @param mail The email input.
     * @return True if both fields are completed, otherwise false.
     */
    public boolean fieldsCompleted(String username, String mail) {
        if (username.isEmpty() || mail.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Checks the data to recover the password.
     *
     * @param username The username input.
     * @param mail The email input.
     * @return The recovered password if successful, otherwise null.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public String checkData(String username, String mail) throws BusinessException {
        try {
            return dao.getRecoverPassword(username, mail);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
