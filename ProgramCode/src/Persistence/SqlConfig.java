package Persistence;

import Persistence.Exceptions.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class provides functionalities to read MySQL configuration from a JSON file.
 */
public class SqlConfig {

    private static final String CONFIG_FILE_PATH = "config/database_config.json";
    private static final String ERROR_MESSAGE =
            """
                    ERROR / EXCEPTION: S'ha produït un error al document de de configuració de la base de dades, els errors més comuns són:

                    - El document no es troba dins la ruta: "config/database_config.json"
                    - El document no té el nom: "database_config.json"

                     El codi de l'error és el següent
                     
                    """;

    private String host;
    private String port;
    private String user;
    private String password;
    private String database;

    /**
     * Default constructor for the class Sql Config, which reads the MySQL configuration from the JSON file
     * when an object is instantiated.
     *
     * @throws PersistenceException If the configuration file is not found or if an error occurs while reading the file.
     */
    public SqlConfig() throws PersistenceException {
        mySQLConfigRead();
    }

    /**
     * Reads MySQL configuration from a JSON file and sets the configuration values.
     *
     * @throws PersistenceException If the configuration file is not found or if an error occurs while reading the file.
     */
    private void mySQLConfigRead() throws PersistenceException {
        Gson gson = new Gson();
        FileReader reader;
        try {
            reader = new FileReader(CONFIG_FILE_PATH);
            JsonObject configData = gson.fromJson(reader, JsonObject.class);
            host = configData.get("host").getAsString();
            port = configData.get("port").getAsString();
            user = configData.get("user").getAsString();
            password = configData.get("password").getAsString();
            database = configData.get("database").getAsString();
        } catch (FileNotFoundException e) {
              throw new PersistenceException(ERROR_MESSAGE + e.getMessage());
        }
    }

    /**
     * Gets the name of the database specified in the configuration.
     *
     * @return The name of the database.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Constructs and returns the JDBC connection URL based on the provided configuration.
     *
     * @return The JDBC connection URL.
     */
    public String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }

    /**
     * Gets the username specified in the configuration.
     *
     * @return The MySQL username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the password specified in the configuration.
     *
     * @return The MySQL password.
     */
    public String getPassword() {
        return password;
    }
}
