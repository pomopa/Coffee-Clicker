package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Business.UserManager;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.UserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for managing user-related actions.
 */
public class UserController implements ActionListener {
    private UserManager userManager;
    private GameManager gameManager;
    private UserView userView;
    private MainControllerEvent mc;

    /**
     * Default constructor for the class User Controller.
     *
     * @param userManager The user manager.
     * @param gameManager The game manager.
     * @param userView The user view.
     * @param mc The main controller event.
     */
    public UserController(UserManager userManager, GameManager gameManager, UserView userView, MainControllerEvent mc) {
        this.userManager = userManager;
        this.gameManager = gameManager;
        this.userView = userView;
        this.mc = mc;
    }

    /**
     * Responds to user actions performed in the user view.
     * @param e The ActionEvent representing the user action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case UserView.BACK_BUTTON:
                mc.switchView(UserView.BACK_BUTTON);
                break;

            case UserView.LOGOUT_BUTTON:
                int action2 = userView.confirmationPopUp(Strings.CONFIRMATION_LOG_OUT.getValue(), Strings.CONFIRM.getValue(), Strings.NOT_CONFIRM.getValue());

                switch (action2) {
                    case 0:
                        userView.errorPopUp(Strings.TIMER_ENDED.getValue());
                        break;
                    case 1:
                        try {
                            gameManager.saveGame();
                            gameManager.stopThread();
                        } catch (BusinessException ex) {
                            ViewException.error(ex.getMessage());
                        }
                        gameManager.cleanRam();
                        mc.switchView("LOGOUT");
                        break;
                    case 2:
                        break;
                    //El case 2 directament seria seguir en la mateixa view del user.
                }

                break;

            case UserView.DELETE_BUTTON:
                try {
                    int action = userView.confirmationPopUp(Strings.CONFIRMATION_DELETE_ACCOUNT.getValue(), Strings.CONFIRM.getValue(), Strings.NOT_CONFIRM.getValue());

                    switch (action) {
                        case 0:
                            userView.errorPopUp(Strings.TIMER_ENDED.getValue());
                            break;
                        case 1:
                            gameManager.stopThread();
                            userManager.deleteUser(gameManager.getUsername());
                            gameManager.cleanRam();
                            mc.switchView("LOGOUT");
                            break;
                        case 2:
                            break;
                        //El case 2 directament seria seguir en la mateixa view del user.
                    }
                } catch (BusinessException ex) {
                    ViewException.error(ex.getMessage());
                }
                break;
        }
    }
}
