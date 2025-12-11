package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Business.StartGameManager;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.ResumeGameView;
import Presentation.Views.Frames.StartGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for managing interactions and data flow related to starting a new game.
 * This class handles user actions such as navigating to user view, creating a new game, and viewing game statistics.
 * It interacts with StartGameManager to validate the game name and create a new game, with GameManager to manage game data,
 * and with MainControllerEvent to switch between views.
 */
public class StartGameController implements ActionListener {

    private MainControllerEvent mc;
    private StartGameManager startGameManager;
    private GameManager gameManager;
    private StartGameView startGameView;

    /**
     * Default constructor for the class Start Game Controller.
     *
     * @param startGameView     The StartGameView associated with this controller.
     * @param mc                The MainControllerEvent used for switching between views.
     * @param startGameManager The StartGameManager used for game initialization and validation.
     * @param gameManager       The GameManager used for managing game data.
     */
    public StartGameController(StartGameView startGameView, MainControllerEvent mc, StartGameManager startGameManager, GameManager gameManager) {
        this.startGameView = startGameView;
        this.startGameManager = startGameManager;
        this.mc = mc;
        this.gameManager = gameManager;
    }

    /**
     * Responds to user actions performed on the associated StartGameView.
     *
     * @param e The ActionEvent triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case StartGameView.USER_BUTTON:
                mc.switchView(StartGameView.USER_BUTTON);
                break;
            case StartGameView.CREATE_BUTTON:
                if (startGameManager.fieldCompleted(startGameView.getGameNameField())) {
                    try {
                        if (startGameManager.checkGameName(startGameView.getGameNameField())) {
                            boolean created = startGameManager.createNewGame(gameManager.getUsername(), startGameView.getGameNameField());
                            if (created) {
                                gameManager.setGameInfo();
                                startGameView.refreshTextBox();
                                mc.switchView(StartGameView.CREATE_BUTTON);
                            } else {
                                startGameView.errorPopUp(startGameManager.getErrorString());
                                startGameView.refreshTextBox();
                            }

                        } else {
                            startGameView.errorPopUp(startGameManager.getErrorString());
                            startGameView.refreshTextBox();
                        }
                    } catch (BusinessException ex) {
                        ViewException.error(ex.getMessage());
                    }
                } else {
                    startGameView.errorPopUp(startGameManager.getErrorString());
                }
                break;
            case ResumeGameView.STATS_BUTTON:
                mc.switchView(ResumeGameView.STATS_BUTTON);
                break;
        }
    }
}
