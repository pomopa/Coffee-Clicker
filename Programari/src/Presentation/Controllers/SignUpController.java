package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Business.SignUpManager;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for handling user actions in the SignUpView.
 */
public class SignUpController implements ActionListener {
    private SignUpManager signUpManager;
    private GameManager gameManager;
    private SignUpView signUpView;
    private MainControllerEvent mc;

    /**
     * Default constructor for the class Sign-Up Controller.
     *
     * @param signUpManager The SignUpManager responsible for user registration.
     * @param gameManager   The GameManager responsible for managing game data.
     * @param signUpView    The SignUpView associated with this controller.
     * @param mc            The MainControllerEvent used for switching between views.
     */
    public SignUpController(SignUpManager signUpManager, GameManager gameManager, SignUpView signUpView, MainControllerEvent mc) {
        this.signUpManager = signUpManager;
        this.gameManager = gameManager;
        this.signUpView = signUpView;
        this.mc = mc;
    }

    /**
     * Responds to user actions performed on the associated SignUpView.
     *
     * @param e The ActionEvent triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SignUpView.BACK_BUTTON:
                mc.switchView("BACK_BUTTON");
                break;

            case SignUpView.REGISTER_BUTTON:
                if (signUpManager.fieldsCompleted(signUpView.getUserName(), signUpView.getMail(), signUpView.getPassword(), signUpView.getConfirmPassword())) {
                    try {
                        if (signUpManager.checkData(signUpView.getUserName(), signUpView.getMail(), signUpView.getPassword(), signUpView.getConfirmPassword())){
                            signUpManager.registerUser(signUpView.getUserName(), signUpView.getMail(), signUpView.getPassword());
                            gameManager.setUsername(signUpView.getUserName());
                            signUpView.reset();
                            mc.switchView("START_GAME");
                        } else {
                            signUpView.errorPopUp(signUpManager.getErrorString());
                            signUpManager.setErrorStringNull();
                        }
                    } catch (BusinessException ex) {
                        ViewException.error(ex.getMessage());
                    }

                } else {
                    signUpView.errorPopUp(Strings.ERROR_PLEASE_COMPLETE_ALL_FIELDS.getValue());
                    signUpView.reset();
                }
                break;
        }
    }
}
