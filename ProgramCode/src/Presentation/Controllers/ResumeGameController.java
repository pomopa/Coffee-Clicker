package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.ResumeGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for handling user actions in the ResumeGameView.
 */
public class ResumeGameController implements ActionListener {

    private MainControllerEvent mc;
    private ResumeGameView resumeGameView;
    private GameManager gameManager;

    /**
     * Default constructor for the class Resume Game Controller.
     *
     * @param resumeGameView The ResumeGameView associated with this controller.
     * @param mc             The MainControllerEvent used for switching between views.
     * @param gameManager    The GameManager responsible for managing game data.
     */
    public ResumeGameController(ResumeGameView resumeGameView, MainControllerEvent mc, GameManager gameManager) {
        this.resumeGameView = resumeGameView;
        this.mc = mc;
        this.gameManager = gameManager;
    }

    /**
     * Responds to user actions performed on the associated ResumeGameView.
     *
     * @param e The ActionEvent triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ResumeGameView.USER_BUTTON:
                mc.switchView(ResumeGameView.USER_BUTTON);
                break;
            case ResumeGameView.RESUME_BUTTON:
                try {
                    gameManager.setGameInfo();
                    mc.switchView(ResumeGameView.RESUME_BUTTON);
                } catch (BusinessException ex) {
                    ViewException.error(ex.getMessage());
                }
                break;
            case ResumeGameView.STATS_BUTTON:
                mc.switchView(ResumeGameView.STATS_BUTTON);
                break;
        }
    }
}
