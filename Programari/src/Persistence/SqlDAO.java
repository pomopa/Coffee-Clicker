package Persistence;

import Business.Entities.Game;
import Business.Entities.Generator;
import Business.Entities.PowerUp;
import Persistence.Exceptions.PersistenceException;

import java.sql.*;
import java.util.ArrayList;

/**
 * This interface defines methods to interact with an SQL database
 * for managing users, games, generators, and power-ups.
 */
public interface SqlDAO {
    /**
     * Establishes a connection with the database.
     * @return The established connection.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    Connection connect() throws PersistenceException;

    /**
     * Closes a previously established connection with the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void disconnect() throws PersistenceException;

// CRUD methods
// Methods for user

    /**
     * Creates a new user in the database.
     *
     * @param userName The username of the new user.
     * @param mail     The email of the new user.
     * @param password The password of the new user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void createUser(String userName, String mail, String password) throws PersistenceException;

    /**
     * Verifies the credentials of a user.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return true if the credentials are valid, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean verifyUserCredentialsUsername(String userName, String password) throws PersistenceException;

    /**
     * Verifies the credentials of a user.
     *
     * @param mail     The mail of the user.
     * @param password The password of the user.
     * @return true if the credentials are valid, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean verifyUserCredentialsEmail(String mail, String password) throws PersistenceException;

    /**
     * Retrieves the password of the specified user.
     *
     * @param userName The username of the user.
     * @param mail The email of the user.
     * @return The password as a String if the credentials are valid, null otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    String getRecoverPassword(String userName, String mail) throws PersistenceException;

    /**
     * Updates the information of a user in the database.
     *
     * @param userName   The username of the user to be updated.
     * @param newMail    The new email of the user.
     * @param newPassword The new password of the user.
     * @return true if the user is updated successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean updateUser(String userName, String newMail, String newPassword) throws PersistenceException;

    /**
     * Retrieves all the userNames from the database.
     *
     * @return A list with all the userNames from the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<String> readAllUsers() throws PersistenceException;

    /**
     * Deletes a user from the database.
     *
     * @param userName The username of the user to be deleted.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void deleteUser(String userName) throws PersistenceException;


// Methods for game
    /**
     * Starts a new game for the specified user.
     * @param userName The username of the user starting the game.
     * @param gameName The name of the new game.
     * @return true if the game is started successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean createGame(String userName, String gameName) throws PersistenceException;

    /**
     * Verifies if the game's name already exists in the database.
     *
     * @param userName Username to whom it will be verified if the game's name exists.
     * @param gameName Game's name which needs to be verified.
     * @return true if game's name already exists, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean doesGameNameExistsForUser(String userName, String gameName) throws PersistenceException;

    /**
     * Ends the current game for the specified user.
     *
     * @param userName The username of the user ending the game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void endGameCurrentGame(String userName) throws PersistenceException;

    /**
     * Retrieves the current game for the specified user.
     * @param userName The username of the user whose current game is to be retrieved.
     * @return The current game of the specified user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    Game readCurrentGame(String userName) throws PersistenceException;

    /**
     * Retrieves the game with the specified name.
     * @param gameName The name of the game to be retrieved.
     * @return The game with the specified name.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    Game readGame(String gameName) throws PersistenceException;

    /**
     * Retrieves all games associated with the specified user.
     * @param userName The username of the user whose games are to be retrieved.
     * @return An ArrayList containing all games associated with the specified user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<Game> readAllGamesFromUser(String userName) throws PersistenceException;

    /**
     * Retrieves all games stored in the database.
     * @return An ArrayList containing all games stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<Game> readAllGames() throws PersistenceException;

    /**
     * Updates the information of the specified game in the database.
     *
     * @param game The game object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void updateGame(Game game) throws PersistenceException;

    /**
     * Deletes the game with the specified name from the database.
     * @param gameName The name of the game to be deleted.
     * @return true if the game is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean deleteGame(String gameName) throws PersistenceException;



// Methods for generator
    /**
     * Checks if a generator type exists for a given game in the database.
     *
     * @param gameName      The name of the game to check for the generator type.
     * @param generatorsType The type of generator to check for.
     * @return {@code true} if the generator type exists for the game, {@code false} otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean doesGeneratorTypeExistsForGame(String gameName, String generatorsType) throws PersistenceException;


    /**
     * Retrieves the generator of the specified type for the current game of the given user.
     * @param userName The username of the user whose current game's generator is to be retrieved.
     * @param generatorType The type of generator to be retrieved.
     * @return The generator of the specified type for the current game of the given user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    Generator readGeneratorOnCurrentGame(String userName, String generatorType) throws PersistenceException;

    /**
     * Retrieves all generators associated with the specified game.
     * @param gameName The name of the game whose generators are to be retrieved.
     * @return An ArrayList containing all generators associated with the specified game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<Generator> readAllGeneratorFromGame(String gameName) throws PersistenceException;

    /**
     * Retrieves all generators stored in the database.
     * @return An ArrayList containing all generators stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<Generator> readAllGenerators() throws PersistenceException;

    /**
     * Updates the information of the specified generator in the database.
     *
     * @param gameName  The name of the game to which the generator belongs.
     * @param generator The generator object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void updateGenerator(String gameName, Generator generator) throws PersistenceException;

    /**
     * Deletes the specified generator from the specified game in the database.
     * @param gameName The name of the game from which the generator is to be deleted.
     * @param generatorType The generator to be deleted.
     * @return true if the generator is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean deleteGenerator(String gameName, String generatorType) throws PersistenceException;

// Methods for power-up
    /**
     * Checks if a power-up type exists for a specific game in the database.
     *
     * @param gameName   The name of the game to check.
     * @param powerUpName The name of the power-up type to check.
     * @return True if the power-up type exists for the game, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean doesPowerUpTypeExistsForGame(String gameName, String powerUpName) throws PersistenceException;


    /**
     * Retrieves the power-up of the specified type for the current game of the given user.
     * @param userName The username of the user whose current game's power-up is to be retrieved.
     * @param powerUpName The type of power-up to be retrieved.
     * @return The power-up of the specified type for the current game of the given user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    PowerUp readPowerUpOnCurrentGame(String userName, String powerUpName) throws PersistenceException;

    /**
     * Retrieves all power-ups associated with the specified game.
     * @param gameName The name of the game whose power-ups are to be retrieved.
     * @return An ArrayList containing all power-ups associated with the specified game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<PowerUp> readAllPowerUpFromGame(String gameName) throws PersistenceException;

    /**
     * Retrieves all power-ups stored in the database.
     * @return An ArrayList containing all power-ups stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<PowerUp> readAllPowerUps() throws PersistenceException;

    /**
     * Updates the information of the specified power-up in the database.
     * @param gameName The name of the game to which the power-up belongs.
     * @param powerUp The power-up object containing the updated information.
     * @return true if the power-up is updated successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean updatePowerUp(String gameName, PowerUp powerUp) throws PersistenceException;

    /**
     * Updates the purchased field of the specified power-up in the database.
     *
     * @param gameName The name of the game to which the power-up belongs.
     * @param powerUp  The power-up object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    void updatePowerUpPurchased(String gameName, PowerUp powerUp) throws PersistenceException;

    /**
     * Deletes the specified power-up from the specified game in the database.
     * @param gameName The name of the game from which the power-up is to be deleted.
     * @param powerUpName The power-up to be deleted.
     * @return true if the power-up is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    boolean deletePowerUp(String gameName, String powerUpName) throws PersistenceException;

    /**
     * Recupera todos los juegos históricos almacenados en la base de datos.
     *
     * @return Un ArrayList que contiene todos los juegos históricos almacenados en la base de datos.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<Game> readAllHistoric() throws PersistenceException;

    /**
     * Recupera todos los nombres de juegos almacenados en la base de datos.
     *
     * @return Un ArrayList que contiene todos los nombres de juegos almacenados en la base de datos.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    ArrayList<String> readAllGamesName() throws PersistenceException;

}
