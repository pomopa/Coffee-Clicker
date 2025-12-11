package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Business.LoginManager;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for managing the login functionality and views of the application.
 */

public class LoginController implements ActionListener {

    /**
     * Error message displayed when the entered credentials do not match any user in the database.
     */
    private static final String ERROR_CREDENTIALS_INCORRECT = "Els valors introduïts no corresponen a cap usuari de la base de dades, prova de nou";

    private LoginManager loginManager;
    private GameManager gameManager;
    private LoginView loginView;
    private MainControllerEvent mainController;


    /**
     * Default constructor for the class Login Controller.
     *
     * @param loginManager   The LoginManager responsible for managing user login operations.
     * @param gameManager    The GameManager responsible for managing game data.
     * @param loginView      The LoginView representing the login interface.
     * @param mainController The MainControllerEvent responsible for managing main application views.
     */
    public LoginController(LoginManager loginManager, GameManager gameManager, LoginView loginView, MainControllerEvent mainController) {
        this.loginManager = loginManager;
        this.gameManager = gameManager;
        this.loginView = loginView;
        this.mainController = mainController;
    }

    /**
     * Handles the actionPerformed event, determining the action based on the source.
     *
     * @param e The ActionEvent triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case LoginView.BACK_BUTTON:
                loginView.reset();
                mainController.switchView("LOGOUT");
                break;

            case LoginView.LOGIN_BUTTON:
                try {
                    if (loginManager.validateCredentials(loginView.getUsernameOrMail(), loginView.getPassword())) {
                        if (loginManager.hasOnGoingGame(loginView.getUsernameOrMail())) {
                            gameManager.setUsername(loginView.getUsernameOrMail());
                            loginView.reset();
                            mainController.switchView("RESUME_GAME");
                        } else {
                            gameManager.setUsername(loginView.getUsernameOrMail());
                            loginView.reset();
                            mainController.switchView("START_GAME");
                        }
                    } else {
                        loginView.errorPopUp(ERROR_CREDENTIALS_INCORRECT);
                    }
                } catch (BusinessException ex) {
                    ViewException.error(ex.getMessage());
                }
                break;

            case LoginView.RECOVER_PASSWORD:
                mainController.switchView("RECOVER_PASSWORD");
                break;
        }
    }

}
