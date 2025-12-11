package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.LoginManager;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.LoginView;
import Presentation.Views.Frames.RecoverPasswordView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class responsible for handling actions related to password recovery.
 */
public class RecoverPasswordController implements ActionListener {
    private MainControllerEvent mc;
    private RecoverPasswordView recoverPasswordView;
    private LoginManager loginManager;

    /**
     * Default constructor for the class Recover Password Controller.
     *
     * @param mc The main controller instance.
     * @param recoverPasswordView The recover password view instance.
     * @param loginManager The login manager instance.
     */
    public RecoverPasswordController(MainController mc, RecoverPasswordView recoverPasswordView, LoginManager loginManager) {
        this.mc = mc;
        this.recoverPasswordView = recoverPasswordView;
        this.loginManager = loginManager;
    }

    /**
     * Handles the action performed by the user.
     * @param e The ActionEvent representing the user action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case RecoverPasswordView.BACK_BUTTON:
                recoverPasswordView.reset();
                mc.switchView(LoginView.BACK_BUTTON);
                break;

            case RecoverPasswordView.RECOVER_PASSWORD:
                if (loginManager.fieldsCompleted(recoverPasswordView.getUsername(), recoverPasswordView.getMail())) {
                    try {
                        String password = loginManager.checkData(recoverPasswordView.getUsername(), recoverPasswordView.getMail());
                        if (null != password){
                            recoverPasswordView.passwordPopUp(password);
                            recoverPasswordView.reset();
                            mc.switchView("LOG_IN");
                        } else {
                            recoverPasswordView.errorPopUp(Strings.NO_USER_IN_DATABASE.getValue());
                        }
                    } catch (BusinessException ex) {
                        ViewException.error(ex.getMessage());
                    }

                } else {
                    System.out.println("estas mal");
                    recoverPasswordView.errorPopUp(Strings.ERROR_PLEASE_COMPLETE_ALL_FIELDS.getValue());
                }
                break;
        }
    }
}
