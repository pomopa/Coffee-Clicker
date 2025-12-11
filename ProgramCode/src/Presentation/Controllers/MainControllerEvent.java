package Presentation.Controllers;

/**
 * Interface representing events in the main controller.
 */
public interface MainControllerEvent {

    /**
     * Method that changes the view being shown according to the action.
     *
     * @param action Represents the action that the MainController has to manage.
     */
    void switchView(String action);

}
