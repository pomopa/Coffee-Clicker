package Business;

import Business.Entities.Game;
import Business.Exceptions.BusinessException;
import Persistence.MySqlDAO;
import Persistence.Exceptions.PersistenceException;
import Persistence.SqlDAO;

import java.util.ArrayList;

/**
 * The StartGameManager class manages the creation of new games and checks for existing games.
 */
public class StartGameManager {

    /**
     * Error message indicating that another game with the same name already exists.
     */
    private static final String ERROR_ALREADY_EXISTING_GAME_MESSAGE = "Existeix una altra partida amb el mateix nom, tria'n un de diferent";

    /**
     * Error message indicating that the game could not be created.
     */
    private static final String ERROR_COULDNT_CREATE_GAME_MESSAGE = "La partida no s'ha pogut generar. Intenta-ho de nou més tard.";

    /**
     * Error message indicating that the game name field is empty.
     */
    private static final String ERROR_EMPTY_FIELD_MESSAGE = "Una partida no pot existir sense un nom! El camp ha d'estar complet per continuar.";

    private String errorString;
    private SqlDAO sqlDAO;

    /**
     * Default constructor for the class Start Game View.
     * @param mySqlDAO Class that implements the necessary functions to talk to the database.
     */
    public StartGameManager(MySqlDAO mySqlDAO){
        sqlDAO = mySqlDAO;
        errorString = null;
    }

    /**
     * Checks if the game name field is completed.
     *
     * @param gameName The name of the game to be checked.
     * @return true if the field is completed, false otherwise.
     */
    public boolean fieldCompleted(String gameName) {
        if (gameName.isEmpty()) {
            changeErrorMessage(ERROR_EMPTY_FIELD_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Checks if a game name already exists.
     *
     * @param newGameName The name of the game to be checked.
     * @return true if the game name is unique, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public boolean checkGameName(String newGameName) throws BusinessException {

        try {
            ArrayList<Game> allGames = sqlDAO.readAllGames();

            for (Game game : allGames) {
                if (game.getGameName().equals(newGameName)) {
                    changeErrorMessage(ERROR_ALREADY_EXISTING_GAME_MESSAGE);
                    return false;
                }
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }


        return true;
    }

    /**
     * Sets the error message.
     *
     * @param message The error message to be set.
     */
    private void changeErrorMessage(String message) {
        errorString = message;

    }

    /**
     * Retrieves the error message.
     *
     * @return The error message.
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Creates a new game.
     *
     * @param username The username of the user starting the game.
     * @param gamename The name of the new game.
     * @return true if the game is created successfully, false otherwise.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public boolean createNewGame(String username, String gamename) throws BusinessException {

        try {
            boolean created = sqlDAO.createGame(username, gamename);
            if (!created) {
                changeErrorMessage(ERROR_COULDNT_CREATE_GAME_MESSAGE);
            }
            return created;
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }


    }
}
