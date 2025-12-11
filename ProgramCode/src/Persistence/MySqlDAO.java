package Persistence;

import Business.Entities.Game;
import Business.Entities.Generator;
import Business.Entities.PowerUp;
import Persistence.Exceptions.PersistenceException;
import com.google.gson.*;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Represents a class for interacting with a MySQL database.
 */
public class MySqlDAO implements SqlDAO {

    private static final String GENERATOR_PATH = "config/generador_Config.json";
    private static final String EMPOWERER_PATH = "config/powerUps_Config.json";
    private static final String CONNECT_ERROR_MESSAGE =
            """
                    ERROR / EXCEPTION: Error en connectar amb la base de dades, els errors més comuns són:

                    1. No estas connectat a internet.
                    2. No tens la llibreria "mysql-connector-j-8.3.0.jar" importada.
                    3. No tens ben completat el document de configuració de la base de dades.

                     El codi de l'error és el següent
                     
                    """;

    private static final String DISCONNECT_ERROR_MESSAGE =
            """
                    ERROR / EXCEPTION: Error en desconnectar de la base de dades:

                    1. No estas connectat a internet
                    2. No tens la llibreria "mysql-connector-j-8.3.0.jar" importada
                    3. No tens ben completat el document de configuració de la base de dades

                     El codi de l'error és el següent
                     
                    """;


    private static final String SQL_ERROR_MESSAGE =
            """
                    ERROR / EXCEPTION: Error a la consulta SQL.

                     El codi de l'error és el següent
                     
                    """;

    private static final String FILE_ERROR_MESSAGE =
            """
                    ERROR / EXCEPTION: Error amb la lectura del document.

                     El codi de l'error és el següent
                     
                    """;

    private static Connection connection;

    /**
     * Default constructor for the class MySqlDAO.
     */
    public MySqlDAO() {
    }

    /**
     * Establishes a connection with the database.
     *
     * @return The established connection.
     */
    @Override
    public Connection connect() throws PersistenceException {
        if (connection != null) return connection;

        SqlConfig sqlConfig = null;
        try {
            sqlConfig = new SqlConfig();
            connection = DriverManager.getConnection(sqlConfig.getUrl(), sqlConfig.getUser(), sqlConfig.getPassword());
            Statement statement = connection.createStatement();
            statement.execute("USE " + sqlConfig.getDatabase());
        } catch (SQLException e) {
            throw new PersistenceException(CONNECT_ERROR_MESSAGE + e.getMessage());
        }

        return connection;
    }

    /**
     * Closes a previously established connection with the database.
     *
     * @throws PersistenceException If an error occurs while closing the connection.
     */
    @Override
    public void disconnect() throws PersistenceException {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new PersistenceException(DISCONNECT_ERROR_MESSAGE + e.getMessage());
            }
        }
    }

    /**
     * Creates a new user in the database.
     *
     * @param userName The username of the new user.
     * @param mail     The email of the new user.
     * @param password The password of the new user.
     * @throws PersistenceException If an error occurs while executing the operation in the database and
     *                              If there is an issue with the database configuration file
     */
    @Override
    public void createUser(String userName, String mail, String password) throws PersistenceException {
        String query = "INSERT INTO User (userName, mail, password) VALUES (?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, mail);
            statement.setString(3, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


    }


    /**
     * Verifies the credentials of a user.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return true if the credentials are valid, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database and
     *      *                              If there is an issue with the database configuration file
     */
    @Override
    public boolean verifyUserCredentialsUsername(String userName, String password) throws PersistenceException {
        boolean isValidUser = false;
        String query = "SELECT * FROM User WHERE userName = ? AND password = ?";

        Connection connection = null;
        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValidUser = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


        return isValidUser;
    }

    /**
     * Verifies the credentials of a user.
     *
     * @param mail The mail of the user.
     * @param password The password of the user.
     * @return true if the credentials are valid, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database and
     *      *                              If there is an issue with the database configuration file
     */
    @Override
    public boolean verifyUserCredentialsEmail(String mail, String password) throws PersistenceException {
        boolean isValidUser = false;
        String query = "SELECT * FROM User WHERE mail = ? AND password = ?";

        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValidUser = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return isValidUser;
    }

    /**
     * Retrieves the password of the specified user.
     *
     * @param userName The username of the user.
     * @param mail The email of the user.
     * @return The password as a String if the credentials are valid, null otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public String getRecoverPassword(String userName, String mail) throws PersistenceException {
        String recoveredPassword = null;
        String query = "SELECT password FROM User WHERE userName = ? AND mail = ?";

        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                recoveredPassword = resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return recoveredPassword;
    }

    /**
     * Updates the information of a user in the database.
     *
     * @param userName    The username of the user to be updated.
     * @param newMail     The new email of the user.
     * @param newPassword The new password of the user.
     * @return true if the user is updated successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     *
     */
    @Override
    public boolean updateUser(String userName, String newMail, String newPassword) throws PersistenceException {
        boolean updateUser = false;
        String query = "UPDATE User SET mail = ?, password = ? WHERE userName = ?";
        Connection connection = null;


        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newMail);
            statement.setString(2, newPassword);
            statement.setString(3, userName);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                updateUser = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


        return updateUser;
    }

    /**
     * Retrieves all the userNames from the database.
     *
     * @return A list with all the userNames from the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<String> readAllUsers() throws PersistenceException {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT userName FROM User";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                users.add(userName);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return users;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userName The username of the user to be deleted.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public void deleteUser(String userName) throws PersistenceException {
        String query = "DELETE FROM User WHERE userName = ?";
        Connection connection = null;

        try{
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


    }

    /**
     * Starts a new game for the specified user.
     *
     * @param userName The username of the user starting the game.
     * @return true if the game is started successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean createGame(String userName, String newGameName) throws PersistenceException {
        boolean gameStarted = false;
        String query = "INSERT INTO Game (gameName, ongoingGame, numResources, timeOnGame, userName) VALUES (?, true, 0, 0, ?)";
        Connection connection = null;

        try{
            connection = connect();
            deleteUserGames(userName);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newGameName);
            statement.setString(2, userName);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                gameStarted = true;
            }
            createNewPowerUpsOnCurrentGame(userName);
            createNewGeneratorsOnCurrentGame(userName);
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return gameStarted;
    }

    /**
     * Finalises all the ongoing games for a specific user in the database.
     *
     * @param userName Username which all games will be finalised.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    private void deleteUserGames(String userName) throws PersistenceException  {
        String query = "UPDATE Game SET ongoingGame = 0 WHERE userName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


    }

    /**
     * Verifies if the game's name already exists in the database.
     *
     * @param userName Username to whom it will be verified if the game's name exists.
     * @param gameName Game's name which needs to be verified.
     * @return true if game's name already exists, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean doesGameNameExistsForUser(String userName, String gameName) throws PersistenceException {
        boolean gameNameExists = false;
        String query = "SELECT COUNT(*) FROM Game WHERE userName = ? AND gameName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, gameName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    gameNameExists = true;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


        return gameNameExists;
    }

    /**
     * Ends the current game for the specified user.
     *
     * @param userName The username of the user ending the game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public void endGameCurrentGame(String userName) throws PersistenceException {
        String query = "UPDATE Game SET ongoingGame = false WHERE userName = ? AND ongoingGame = true";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


    }

    /**
     * Retrieves the current game for the specified user.
     *
     * @param userName The username of the user whose current game is to be retrieved.
     * @return The current game of the specified user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public Game readCurrentGame(String userName) throws PersistenceException {
        Game currentGame = null;
        String query = "SELECT * FROM Game WHERE userName = ? AND ongoingGame = true";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                boolean ongoingGame = resultSet.getBoolean("ongoingGame");
                long numResources = resultSet.getLong("numResources");
                int timeOnGame = resultSet.getInt("timeOnGame");
                currentGame = new Game(gameName, ongoingGame, numResources, timeOnGame, userName);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }



        return currentGame;
    }

    /**
     * Retrieves the game with the specified name.
     *
     * @param gameName The name of the game to be retrieved.
     * @return The game with the specified name.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public Game readGame(String gameName) throws PersistenceException {
        Game game = null;
        String query = "SELECT * FROM Game WHERE gameName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean ongoingGame = resultSet.getBoolean("ongoingGame");
                long numResources = resultSet.getLong("numResources");
                int timeOnGame = resultSet.getInt("timeOnGame");
                String userName = resultSet.getString("userName");
                game = new Game(gameName, ongoingGame, numResources, timeOnGame, userName);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return game;
    }

    /**
     * Retrieves all games associated with the specified user.
     *
     * @param userName The username of the user whose games are to be retrieved.
     * @return An ArrayList containing all games associated with the specified user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<Game> readAllGamesFromUser(String userName) throws PersistenceException {
        ArrayList<Game> userGames = new ArrayList<>();
        String query = "SELECT * FROM Game WHERE userName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                boolean ongoingGame = resultSet.getBoolean("ongoingGame");
                long numResources = resultSet.getLong("numResources");
                int timeOnGame = resultSet.getInt("timeOnGame");
                Game game = new Game(gameName, ongoingGame, numResources, timeOnGame, userName);
                userGames.add(game);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }



        return userGames;
    }

    /**
     * Retrieves all games stored in the database.
     *
     * @return An ArrayList containing all games stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<Game> readAllGames() throws PersistenceException {
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT * FROM Game";
        Connection connection = null;


        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                boolean ongoingGame = resultSet.getBoolean("ongoingGame");
                long numResources = resultSet.getLong("numResources");
                int timeOnGame = resultSet.getInt("timeOnGame");
                String userName = resultSet.getString("userName");
                Game game = new Game(gameName, ongoingGame, numResources, timeOnGame, userName);
                games.add(game);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return games;
    }

    /**
     * Updates the information of the specified game in the database.
     *
     * @param game The game object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public void updateGame(Game game) throws PersistenceException {
        boolean gameUpdated = false;
        String query = "UPDATE Game SET ongoingGame = ?, numResources = ?, timeOnGame = ? WHERE gameName = ?";
        String insertQuery = "INSERT INTO Historic (gameName, numResources, timeOnGame,userName) VALUES (?, ?, ? ,?)";

        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, game.isOngoingGame());
            statement.setLong(2, game.getNumResources());
            statement.setInt(3, game.getTimeOnGame());
            statement.setString(4, game.getGameName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                gameUpdated = true;
            }

            if (gameUpdated) {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, game.getGameName());
                insertStatement.setLong(2, game.getNumResources());
                insertStatement.setInt(3, game.getTimeOnGame());
                insertStatement.setString(4, game.getUserName());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

    }

    /**
     * Retrieves all the games saved on the database with extra information for the generation of graphics.
     *
     * @return List of games saved on the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<Game> readAllHistoric() throws PersistenceException {
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT * FROM Historic";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                long numResources = resultSet.getLong("numResources");
                int timeOnGame = resultSet.getInt("timeOnGame");
                String userName = resultSet.getString("userName");

                Game game = new Game(gameName, numResources, timeOnGame, userName);
                games.add(game);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return games;
    }

    /**
     * Retrieves all the game's names of the games stored in the database.
     *
     * @return List containing the names of the games stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<String> readAllGamesName() throws PersistenceException {
        ArrayList<String> gameNames = new ArrayList<>();
        String query = "SELECT gameName FROM Game";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String gameName = resultSet.getString("gameName");
                gameNames.add(gameName);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return gameNames;
    }

    /**
     * Deletes the game with the specified name from the database.
     *
     * @param gameName The name of the game to be deleted.
     * @return true if the game is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean deleteGame(String gameName) throws PersistenceException {
        boolean gameDeleted = false;
        String query = "DELETE FROM Game WHERE gameName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                gameDeleted = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return gameDeleted;
    }

    /**
     * Checks if a generator type exists for a given game in the database.
     *
     * @param gameName       The name of the game to check for the generator type.
     * @param generatorsType The type of generator to check for.
     * @return {@code true} if the generator type exists for the game, {@code false} otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean doesGeneratorTypeExistsForGame(String gameName, String generatorsType) throws PersistenceException {
        boolean generatorTypeExists = false;
        String query = "SELECT COUNT(*) FROM Generator WHERE generatorsType = ? AND gameName = ?";
        Connection connection = null;

        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, generatorsType);
            statement.setString(2, gameName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    generatorTypeExists = true;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }
        return generatorTypeExists;
    }


    /**
     * Sets up a generator of the specified type for the current game of the given user.
     *
     * @param userName      The username of the user for whom the generator is set up.
     * @return true if the generator is set up successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    private boolean createNewGeneratorsOnCurrentGame(String userName) throws PersistenceException {
        boolean generatorSetUp = false;
        Connection connection = null;

        try {
            ArrayList<Generator> jsonGenerators = readGeneratorsFromJson();

            connection = connect();
            Game currenGame = readCurrentGame(userName);
            String gameName = currenGame.getGameName();

            for (Generator generator : jsonGenerators) {
                String query = "INSERT INTO Generator (generatorsType, numGenerator, cost, incremental, production, image, gameName) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, generator.getGeneratorsType());
                statement.setInt(2, generator.getNumGenerator());
                statement.setDouble(3, generator.getCost());
                statement.setDouble(4, generator.getIncremental());
                statement.setDouble(5, generator.getProduction());
                statement.setString(6, generator.getImage());
                statement.setString(7, gameName);
                statement.executeUpdate();

                generatorSetUp = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return generatorSetUp;
    }


    /**
     * Reads generator information from a JSON file and returns it as a list of Generator objects.
     *
     * @return A list of Generator objects.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    private ArrayList<Generator> readGeneratorsFromJson() throws PersistenceException {
        ArrayList<Generator> generators = new ArrayList<>();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(GENERATOR_PATH));
            JsonArray generatorArray = jsonElement.getAsJsonArray();
            for (JsonElement element : generatorArray) {
                JsonObject generatorObject = element.getAsJsonObject();
                String generatorsType = generatorObject.get("generatorsType").getAsString();
                int numGenerator = generatorObject.get("numGenerator").getAsInt();
                double baseCost = generatorObject.get("baseCost").getAsDouble();
                double incremental = generatorObject.get("incremental").getAsDouble();
                double production = generatorObject.get("production").getAsDouble();
                String image = generatorObject.get("image").getAsString();
                Generator generator = new Generator(generatorsType, numGenerator, baseCost, baseCost, incremental, production, image);
                generators.add(generator);
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException(FILE_ERROR_MESSAGE + e.getMessage());
        }

        return generators;
    }

    /**
     * Retrieves the generator of the specified type for the current game of the given user.
     *
     * @param userName      The username of the user whose current game's generator is to be retrieved.
     * @param generatorType The type of generator to be retrieved.
     * @return The generator of the specified type for the current game of the given user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
   @Override
    public Generator readGeneratorOnCurrentGame(String userName, String generatorType) throws PersistenceException {
        Game currentGame = readCurrentGame(userName);
        Connection connection = null;

        try {
            if (currentGame != null) {
                String gameName = currentGame.getGameName();
                String query = "SELECT * FROM Generator WHERE gameName = ? AND generatorsType = ?";
                Generator generator = null;

                connection = connect();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, gameName);
                statement.setString(2, generatorType);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int idGenerator = resultSet.getInt("idGenerator");
                    int numGenerator = resultSet.getInt("numGenerator");
                    double cost = resultSet.getDouble("cost");
                    double incremental = resultSet.getDouble("incremental");
                    double production = resultSet.getDouble("production");
                    String image = resultSet.getString("image");
                    String gameNameBBDD = resultSet.getString("gameName");

                    generator = new Generator(idGenerator, generatorType, numGenerator, cost, 0.0, incremental, production, image, gameNameBBDD);
                }

                return generator;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

    }

    /**
     * Retrieves all generators associated with the specified game.
     *
     * @param gameName The name of the game whose generators are to be retrieved.
     * @return An ArrayList containing all generators associated with the specified game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<Generator> readAllGeneratorFromGame(String gameName) throws PersistenceException {
        ArrayList<Generator> generators = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connect();
            String query = "SELECT * FROM Generator WHERE gameName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idGenerator = resultSet.getInt("idGenerator");
                String generatorsType = resultSet.getString("generatorsType");
                int numGenerator = resultSet.getInt("numGenerator");
                double cost = resultSet.getDouble("cost");
                double incremental = resultSet.getDouble("incremental");
                double production = resultSet.getDouble("production");
                String image = resultSet.getString("image");

                Generator generator = new Generator(idGenerator, generatorsType, numGenerator, cost, 0.0,  incremental, production, image, gameName);
                generators.add(generator);
            }

            generators = retrieveBaseCost(generators);
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


        return generators;
    }

    /**
     * Retrieves the base cost for each generator from a JSON file and updates the generators accordingly.
     *
     * @param generators The list of generators to be updated with their base costs.
     * @return The list of generators with updated base costs.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    private ArrayList<Generator> retrieveBaseCost(ArrayList<Generator> generators) throws PersistenceException {

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(GENERATOR_PATH));
            JsonArray generatorArray = jsonElement.getAsJsonArray();

            for (JsonElement element : generatorArray) {
                for (Generator generatorToModify : generators) {
                    JsonObject generatorObject = element.getAsJsonObject();
                    if (generatorObject.get("generatorsType").getAsString().equals(generatorToModify.getGeneratorsType())) {
                        generatorToModify.setBaseCost(generatorObject.get("baseCost").getAsDouble());
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException(FILE_ERROR_MESSAGE + e.getMessage());
        }

        return generators;
    }

    /**
     * Retrieves all generators stored in the database.
     *
     * @return An ArrayList containing all generators stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<Generator> readAllGenerators() throws PersistenceException {
        ArrayList<Generator> generators = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connect();
            String query = "SELECT * FROM Generator";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idGenerator = resultSet.getInt("idGenerator");
                String generatorsType = resultSet.getString("generatorsType");
                int numGenerator = resultSet.getInt("numGenerator");
                double baseCost = resultSet.getDouble("cost");
                double incremental = resultSet.getDouble("incremental");
                double production = resultSet.getDouble("production");
                String image = resultSet.getString("image");
                String gameName = resultSet.getString("gameName");

                Generator generator = new Generator(idGenerator, generatorsType, numGenerator, baseCost, 0.0, incremental, production, image, gameName);
                generators.add(generator);
            }

            generators = retrieveBaseCost(generators);
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return generators;
    }

    /**
     * Updates the information of the specified generator in the database.
     *
     * @param gameName  The name of the game to which the generator belongs.
     * @param generator The generator object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public void updateGenerator(String gameName, Generator generator) throws PersistenceException {
        Connection connection = null;

        try {
            connection = connect();
            String query = "UPDATE Generator SET numGenerator = ?, cost = ?, incremental = ?, production = ?, image = ? WHERE gameName = ? AND generatorsType = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, generator.getNumGenerator());
            statement.setDouble(2, generator.getCost());
            statement.setDouble(3, generator.getIncremental());
            statement.setDouble(4, generator.getProduction());
            statement.setString(5, generator.getImage());
            statement.setString(6, generator.getGameName());
            statement.setString(7, generator.getGeneratorsType());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

    }

    /**
     * Deletes the specified generator from the specified game in the database.
     *
     * @param gameName      The name of the game from which the generator is to be deleted.
     * @param generatorType The generator to be deleted.
     * @return true if the generator is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean deleteGenerator(String gameName, String generatorType) throws PersistenceException {
        Connection connection = null;
        boolean deleted = false;

        try {
            connection = connect();
            String query = "DELETE FROM Generator WHERE gameName = ? AND generatorsType = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            statement.setString(2, generatorType);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return deleted;
    }

    /**
     * Checks if a power-up type exists for a specific game in the database.
     *
     * @param gameName    The name of the game to check.
     * @param powerUpName The name of the power-up type to check.
     * @return True if the power-up type exists for the game, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean doesPowerUpTypeExistsForGame(String gameName, String powerUpName) throws PersistenceException {
        boolean generatorTypeExists = false;
        String query = "SELECT COUNT(*) FROM PowerUps WHERE name = ? AND gameName = ?";
        Connection connection = null;


        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, powerUpName);
            statement.setString(2, gameName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    generatorTypeExists = true;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }
        return generatorTypeExists;
    }

    /**
     * Sets up a power-up of the specified type for the current game of the given user.
     *
     * @param userName    The username of the user for whom the power-up is set up.
     * @return true if the power-up is set up successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */

    private boolean createNewPowerUpsOnCurrentGame(String userName) throws PersistenceException {
        Connection connection = null;
        boolean setUp = false;

        try {
            Game currentGame = readCurrentGame(userName);

            if (currentGame != null) {
                connection = connect();

                ArrayList<PowerUp> jsonPowerUp = readPowerUpsFromJson();
                for (PowerUp powerUp : jsonPowerUp) {
                    String query = "INSERT INTO PowerUps (powerUpsType, affects, name, cost, bonus, threshold, image, purchased, gameName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);

                    statement.setString(1, powerUp.getType());
                    statement.setString(2, powerUp.getAffects());
                    statement.setString(3, powerUp.getName());
                    statement.setInt(4, powerUp.getCost());
                    statement.setDouble(5, powerUp.getBonus());
                    statement.setInt(6, powerUp.getThreshold());
                    statement.setString(7, powerUp.getImage());
                    statement.setBoolean(8, powerUp.getPurchased());
                    statement.setString(9, currentGame.getGameName());
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        setUp = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return setUp;
    }

    /**
     * Reads power up information from a JSON file and returns it as a list of PowerUp objects.
     *
     * @return A list of PowerUp objects representing the power ups.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    private ArrayList<PowerUp> readPowerUpsFromJson() throws PersistenceException {
        ArrayList<PowerUp> powerUps = new ArrayList<>();

        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(EMPOWERER_PATH));
            JsonArray generatorArray = jsonElement.getAsJsonArray();
            for (JsonElement element : generatorArray) {
                JsonObject generatorObject = element.getAsJsonObject();
                String type = generatorObject.get("type").getAsString();
                String affects = null;
                if (generatorObject.has("affects") && !generatorObject.get("affects").isJsonNull()) {
                    affects = generatorObject.get("affects").getAsString();
                }
                String name = generatorObject.get("name").getAsString();
                int cost = generatorObject.get("cost").getAsInt();
                double bonus = generatorObject.get("bonus").getAsDouble();
                int threshold = generatorObject.get("threshold").getAsInt();
                String image = generatorObject.get("image").getAsString();
                PowerUp powerUp = new PowerUp(type, affects, name, cost, bonus, threshold, image, false);
                powerUps.add(powerUp);
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException(FILE_ERROR_MESSAGE + e.getMessage());
        }

        return powerUps;
    }

    /**
     * Retrieves the power-up of the specified type for the current game of the given user.
     *
     * @param userName    The username of the user whose current game's power-up is to be retrieved.
     * @param powerUpName The type of power-up to be retrieved.
     * @return The power-up of the specified type for the current game of the given user.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public PowerUp readPowerUpOnCurrentGame(String userName, String powerUpName) throws PersistenceException {
        Connection connection = null;
        PowerUp powerUp = null;

        try {
            Game currentGame = readCurrentGame(userName);
            if (currentGame != null) {
                connection = connect();
                String query = "SELECT * FROM PowerUps WHERE gameName = ? AND name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, currentGame.getGameName());
                statement.setString(2, powerUpName);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int idPowerUps = resultSet.getInt("idPowerUps");
                    String type = resultSet.getString("powerUpsType");
                    String affects = resultSet.getString("affects");
                    String name = resultSet.getString("name");
                    int cost = resultSet.getInt("cost");
                    double bonus = resultSet.getDouble("bonus");
                    int threshold = resultSet.getInt("threshold");
                    String image = resultSet.getString("image");
                    String gameName = resultSet.getString("gameName");

                    powerUp = new PowerUp(idPowerUps, type, affects, name, cost, bonus, threshold, image, false, gameName);
                    return powerUp;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }


        return powerUp;
    }

    /**
     * Retrieves all power-ups associated with the specified game.
     *
     * @param gameName The name of the game whose power-ups are to be retrieved.
     * @return An ArrayList containing all power-ups associated with the specified game.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<PowerUp> readAllPowerUpFromGame(String gameName) throws PersistenceException {
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connect();
            String query = "SELECT * FROM PowerUps WHERE gameName = ? AND purchased = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameName);
            statement.setBoolean(2, false);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idPowerUps = resultSet.getInt("idPowerUps");
                String type = resultSet.getString("powerUpsType");
                String affects = resultSet.getString("affects");
                String name = resultSet.getString("name");
                int cost = resultSet.getInt("cost");
                double bonus = resultSet.getDouble("bonus");
                int threshold = resultSet.getInt("threshold");
                String image = resultSet.getString("image");

                PowerUp powerUp = new PowerUp(idPowerUps, type, affects, name, cost, bonus, threshold, image, false, gameName);
                powerUps.add(powerUp);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return powerUps;
    }

    /**
     * Retrieves all power-ups stored in the database.
     *
     * @return An ArrayList containing all power-ups stored in the database.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public ArrayList<PowerUp> readAllPowerUps() throws PersistenceException {
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connect();
            String query = "SELECT * FROM PowerUps";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idPowerUps = resultSet.getInt("idPowerUps");
                String type = resultSet.getString("powerUpsType");
                String affects = resultSet.getString("affects");
                String name = resultSet.getString("name");
                int cost = resultSet.getInt("cost");
                double bonus = resultSet.getDouble("bonus");
                int threshold = resultSet.getInt("threshold");
                String image = resultSet.getString("image");
                String gameName = resultSet.getString("gameName");

                PowerUp powerUp = new PowerUp(idPowerUps, type, affects, name, cost, bonus, threshold, image, false, gameName);
                powerUps.add(powerUp);
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return powerUps;
    }

    /**
     * Updates the information of the specified power-up in the database.
     *
     * @param gameName The name of the game to which the power-up belongs.
     * @param powerUp  The power-up object containing the updated information.
     * @return true if the power-up is updated successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean updatePowerUp(String gameName, PowerUp powerUp) throws PersistenceException {
        Connection connection = null;
        boolean updatePowerUp = false;

        try {
            connection = connect();
            String query = "UPDATE PowerUps SET powerUpsType=?, affects=?, cost=?, bonus=?, threshold=?, image=?, purchased=? WHERE gameName=? AND name =?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, powerUp.getType());
            statement.setString(2, powerUp.getAffects());
            statement.setInt(3, powerUp.getCost());
            statement.setDouble(4, powerUp.getBonus());
            statement.setInt(5, powerUp.getThreshold());
            statement.setString(6, powerUp.getImage());
            statement.setBoolean(7, powerUp.getPurchased());
            statement.setString(8, gameName);
            statement.setString(9, powerUp.getName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                updatePowerUp = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return updatePowerUp;
    }

    /**
     * Updates the purchased field of the specified power-up in the database.
     *
     * @param gameName The name of the game to which the power-up belongs.
     * @param powerUp  The power-up object containing the updated information.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public void updatePowerUpPurchased(String gameName, PowerUp powerUp) throws PersistenceException {
        Connection connection = null;

        try {
            connection = connect();
            String query = "UPDATE PowerUps SET purchased=? WHERE gameName=? AND name =?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setBoolean(1, powerUp.getPurchased());
            statement.setString(2, gameName);
            statement.setString(3, powerUp.getName());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

    }

    /**
     * Deletes the specified power-up from the specified game in the database.
     *
     * @param gameName    The name of the game from which the power-up is to be deleted.
     * @param powerUpName The power-up to be deleted.
     * @return true if the power-up is deleted successfully, false otherwise.
     * @throws PersistenceException If an error occurs while executing the operation in the database.
     */
    @Override
    public boolean deletePowerUp(String gameName, String powerUpName) throws PersistenceException {
        Connection connection = null;
        boolean deletePowerUp = false;

        try {
            connection = connect();
            String query = "DELETE FROM PowerUps WHERE gameName=? AND name=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, gameName);
            statement.setString(2, powerUpName);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                deletePowerUp = true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(SQL_ERROR_MESSAGE + e.getMessage());
        }

        return deletePowerUp;
    }

}
