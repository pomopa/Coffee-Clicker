package Presentation.Views;

import javax.swing.*;

/**
 * The ViewException class provides utility methods for displaying error messages
 * in a graphical user interface using JOptionPane.
 */
public class ViewException {
    /**
     * Default constructor for the class View Exception.
     */
    public ViewException(){

    }

    /**
     * Displays an error message dialog with the specified message.
     *
     * @param message the error message to display
     */
    public static void error(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
