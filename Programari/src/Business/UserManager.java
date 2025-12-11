package Business;

import Business.Exceptions.BusinessException;
import Persistence.MySqlDAO;
import Persistence.Exceptions.PersistenceException;
import Persistence.SqlDAO;

/**
 * The UserManager class manages user-related operations such as user deletion.
 */
public class UserManager {
    private SqlDAO sqlDAO;

    /**
     * Default constructor for the class User Manager.
     * @param mySqlDAO Class that implements the necessary functions to talk to the database.
     */
    public UserManager(MySqlDAO mySqlDAO){
        sqlDAO = mySqlDAO;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userName The username of the user to be deleted.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void deleteUser(String userName) throws BusinessException {
        try {
            sqlDAO.deleteUser(userName);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
