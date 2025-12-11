package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;

import Presentation.Views.ViewException;
import Presentation.Views.MainFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Controller responsible for managing the main functionality and views of the application.
 */
public class MainController implements MainControllerEvent {
    private GameManager model;
    private MainFrame mainFrame;

    /**
     * Default constructor for the class Main Controller.
     *
     * @param model     The GameManager responsible for managing game data.
     * @param mainFrame The MainFrame representing the application's main window.
     */
    public MainController(GameManager model, MainFrame mainFrame) {
        this.model = model;
        this.mainFrame = mainFrame;

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                closeConnection();
            }
        });
    }

    /**
     * Mètode el qual tanca la connexió amb la base de dades via el model del Controlador
     * Falta implementar quan es genera una SQLException
     */
    private void closeConnection() {
        try {
            model.closeConnection();
        } catch (BusinessException e) {
            ViewException.error(e.getMessage());
             System.exit(1);
        } finally {
            mainFrame.dispose();
            System.exit(0);
        }
    }

    /**
     * Method that changes the view being shown according to the action.
     *
     * @param action Represents the action that the MainController has to manage.
     */
    @Override
    public void switchView(String action) {
        switch (action){
            case "SIGN_UP":
                mainFrame.resetSignUpView();
                mainFrame.switchPanel(MainFrame.SIGN_UP_VIEW);
                break;

            case "LOG_IN":
                mainFrame.switchPanel(MainFrame.LOG_IN_VIEW)    ;
                break;

            case "START_GAME":
                mainFrame.switchPanel(MainFrame.START_GAME_VIEW);
                break;

            case "RESUME_GAME":
                mainFrame.switchPanel(MainFrame.RESUME_GAME_VIEW);
                break;

            case "USER_BUTTON":
                mainFrame.switchPanel(MainFrame.USER_VIEW);
                break;

            case "LOGOUT":
                mainFrame.switchPanel(MainFrame.HOME_VIEW);
                break;

            case "PLAY_GAME":
                mainFrame.updateInfoPowerUps(model.getCurrentPowerUps());
                mainFrame.updateInfoGenerators(model.getCurrentGenerators());

                try {
                    model.clockGameTime();
                    model.clockSaveGame();
                    model.clock();
                } catch (BusinessException e) {
                    ViewException.error(e.getMessage());
                }

                mainFrame.switchPanel(MainFrame.GAME_VIEW);
                break;

            case "STATS_BUTTON":
                try {
                    mainFrame.updateInfo(model.getUserNames(), model.getAllFinishedGames());
                } catch (BusinessException e) {
                    ViewException.error(e.getMessage());
                }
                mainFrame.switchPanel(MainFrame.STATISTICS_VIEW);
                break;

            case "RECOVER_PASSWORD":
                mainFrame.switchPanel(MainFrame.RECOVER_PASSWORD_VIEW);
                break;

            case "BACK_BUTTON":
                mainFrame.switchPanelBackButton();
                break;
        }

    }

}
