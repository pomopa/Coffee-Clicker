package Business.Exceptions;

/**
 * Exception class representing business logic errors.
 * This exception is thrown when there is an issue related to business logic operations.
 */
public class BusinessException extends Exception{

    /**
     * Default constructor for the class Business Exception, with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BusinessException(String message) {
        super(message);
    }

}
