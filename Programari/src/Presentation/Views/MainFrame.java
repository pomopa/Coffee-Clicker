package Presentation.Views;

import Business.Entities.Game;
import Business.Entities.Generator;
import Business.Entities.PowerUp;
import Presentation.Views.Frames.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the main frame of the application, which manages different views using CardLayout.
 * This frame includes methods for switching between panels, playing background music, and updating information in views.
 */
public class MainFrame extends JFrame {

    /**
     * Represents the title of the main frame.
     */
    private static final String TITLE = "Coffee Clicker";

    /**
     * Represents the identifier for the HomeView.
     */
    public static final String HOME_VIEW = "HOME_VIEW";

    /**
     * Represents the identifier for the SignUpView.
     */
    public static final String SIGN_UP_VIEW = "SIGN_UP_VIEW";

    /**
     * Represents the identifier for the LogInView.
     */
    public static final String LOG_IN_VIEW = "LOG_IN_VIEW";

    /**
     * Represents the identifier for the StartGameView.
     */
    public static final String START_GAME_VIEW = "START_GAME_VIEW";

    /**
     * Represents the identifier for the ResumeGameView.
     */
    public static final String RESUME_GAME_VIEW = "RESUME_GAME_VIEW";

    /**
     * Represents the identifier for the UserView.
     */
    public static final String USER_VIEW = "USER_VIEW";

    /**
     * Represents the identifier for the StatisticsView.
     */
    public static final String STATISTICS_VIEW = "STATISTICS_VIEW";

    /**
     * Represents the identifier for the GameView.
     */
    public static final String GAME_VIEW = "GAME_VIEW";

    /**
     * Represents the identifier for the RecoverPasswordView.
     */
    public static final String RECOVER_PASSWORD_VIEW = "RECOVER_PASSWORD_VIEW";

    /**
     * Represents the current view being displayed.
     */
    private String currentView;

    /**
     * Represents the last view seen before switching to the current view.
     */
    private String lastViewSeen;

    /**
     * Represents the CardLayout used to manage the views.
     */
    private CardLayout cardLayout;

    /**
     * Represents the HomeView instance.
     */
    private HomeView homeView;

    /**
     * Represents the SignUpView instance.
     */
    private SignUpView signUpView;

    /**
     * Represents the StartGameView instance.
     */
    private StartGameView startGameView;

    /**
     * Represents the ResumeGameView instance.
     */
    private ResumeGameView resumeGameView;

    /**
     * Represents the LoginView instance.
     */
    private LoginView loginView;

    /**
     * Represents the UserView instance.
     */
    private UserView userView;

    /**
     * Represents the StatisticsView instance.
     */
    private StatisticsView statisticsView;

    /**
     * Represents the GameView instance.
     */
    private GameView gameView;

    /**
     * Represents the RecoverPasswordView instance.
     */
    private RecoverPasswordView recoverPasswordView;

    /**
     * Represents the background music player.
     */
    private Clip backgroundMusic;

    /**
     * Default constructor for the class Main Frame, which initialises the frame configuration, panels,
     * and adds the views to the card layout.
     *
     * @param homeView            The HomeView instance.
     * @param signUpView          The SignUpView instance.
     * @param startGameView       The StartGameView instance.
     * @param resumeGameView      The ResumeGameView instance.
     * @param loginView           The LoginView instance.
     * @param userView            The UserView instance.
     * @param statisticsView      The StatisticsView instance.
     * @param gameView            The GameView instance.
     * @param recoverPasswordView The RecoverPasswordView instance.
     */
    public MainFrame(HomeView homeView, SignUpView signUpView, StartGameView startGameView, ResumeGameView resumeGameView,
                     LoginView loginView, UserView userView, StatisticsView statisticsView, GameView gameView, RecoverPasswordView recoverPasswordView) {
        this.homeView = homeView;
        this.signUpView = signUpView;
        this.startGameView = startGameView;
        this.resumeGameView = resumeGameView;
        this.loginView = loginView;
        this.userView = userView;
        this.statisticsView = statisticsView;
        this.gameView = gameView;
        this.recoverPasswordView = recoverPasswordView;

        configurePanels();
        configureFrame();

        playMusic();
    }

    /**
     * Configures the frame settings such as size, title, and visibility.
     */
    private void configureFrame() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setTitle(TITLE);
        
        setVisible(true);
    }

    /**
     * Configures the panels for the CardLayout and adds all views to the layout.
     */
    private void configurePanels() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(HOME_VIEW, homeView.getHomeView());

        add(SIGN_UP_VIEW, signUpView.getSignUpView());
        add(LOG_IN_VIEW, loginView.getLoginView());
        add(RECOVER_PASSWORD_VIEW, recoverPasswordView.getRecoverPasswordView());

        add(USER_VIEW, userView.getUserView());

        add(START_GAME_VIEW, startGameView.getStartGameView());
        add(RESUME_GAME_VIEW, resumeGameView.getResumeGameView());

        add(STATISTICS_VIEW,statisticsView.getStatisticsView());

        add(GAME_VIEW, gameView.getGameView());
    }

    /**
     * Switches the panel to the specified view.
     * @param panel The identifier of the view to switch to.
     */
    public void switchPanel(String panel) {
        cardLayout.show(getContentPane(), panel);

        if (null != currentView) {
            lastViewSeen = String.copyValueOf(currentView.toCharArray());
        } else {
            lastViewSeen = String.copyValueOf(HOME_VIEW.toCharArray());

        }
        currentView = String.copyValueOf(panel.toCharArray());

    }

    /**
     * Switches the panel back to the last viewed panel when using the back button.
     */
    public void switchPanelBackButton() {
        cardLayout.show(getContentPane(), lastViewSeen);
        currentView = String.copyValueOf(lastViewSeen.toCharArray());
    }

    /**
     * Resets the SignUpView.
     */
    public void resetSignUpView() {
        signUpView.reset();
    }

    /**
     * Plays the background music for the application.
     */
    private void playMusic() {

        try {
            File route = new File("resources/musiquita.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(route);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audio);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            ViewException.error(e.getMessage());
        }

    }

    /**
     * Updates the information in the StatisticsView with the provided usernames and game data.
     * @param userNames The list of usernames.
     * @param allGames The list of all games.
     */
    public void updateInfo(ArrayList<String> userNames,ArrayList<Game> allGames) {
        statisticsView.setInfo(userNames,allGames);
    }

    /**
     * Updates the information in the GameView with the provided power-ups.
     * @param currentPowerUps The list of current power-ups.
     */
    public void updateInfoPowerUps(ArrayList<PowerUp> currentPowerUps) {
        gameView.setPowerUps(currentPowerUps);
    }

    /**
     * Updates the information about generators displayed in the GameView.
     *
     * @param currentGenerators The list of generators to be updated in the GameView.
     */
    public void updateInfoGenerators(ArrayList<Generator> currentGenerators) {
        gameView.setGenerators(currentGenerators);
    }

}
