package Persistence.Exceptions;

/**
 * Custom exception class for persistence-related errors.
 */
public class PersistenceException extends Exception{

    /**
     * Constructor that takes a message string and passes it to the superclass (Exception).
     *
     * @param message the detail message about the exception.
     */
    public PersistenceException(String message) {
        super(message);
    }

}
