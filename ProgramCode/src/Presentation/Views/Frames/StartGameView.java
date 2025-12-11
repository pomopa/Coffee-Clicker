package Presentation.Views.Frames;

import Presentation.Views.Frames.Helpers.*;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the StartGameView frame, which allows users to start a new game.
 */
public class StartGameView extends JFrame {

    /**
     * Command string for the user button.
     */
    public static final String USER_BUTTON = "USER_BUTTON";

    /**
     * Command string for the start game button.
     */
    public static final String CREATE_BUTTON = "PLAY_GAME";

    /**
     * Command string for the view statistics button.
     */
    public static final String STATS_BUTTON = "STATS_BUTTON";

    /**
     * Variable that stores the padding used for all the borders.
     */
    private final int LABEL_PADDING = 5;

    /**
     * Text field in which the user will introduce the game name for the new Game.
     */
    private JTextField gameNameField;

    /**
     * Necessary button to get to the user's view.
     */
    private JButton userButton;

    /**
     * Necessary button to get to the game View
     */
    private JButton startGameButton;

    /**
     * Necessary button to let the user view the stats of their and other's games.
     */
    private JButton seeStatsButton;


    /**
     * Default constructor for the class Start Game.
     */
    public StartGameView(){}

    /**
     * Returns the main panel of the StartGameView frame.
     * @return The JPanel representing the StartGameView frame.
     */
    public JPanel getStartGameView(){

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(getTopPanel());
        mainPanel.add(LogoCoffeePanel.getLogo());
        mainPanel.add(new Title(Strings.YOU_HAVE_NO_OPEN_GAME.getValue()));

        mainPanel.add(getGameName());
        mainPanel.add(getStartGameButton());
        mainPanel.add(getSeeStatsButton());

        return mainPanel;
    }

    /**
     * Creates the panel for entering the game name.
     * @return The JPanel containing the game name field.
     */
    private JPanel getGameName() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int margin = (int) ((screenSize.getWidth() * 0.4));

        JLabel nameLabel = new JLabel(Strings.GAME_NAME.getValue());
        nameLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        gameNameField = new JTextField();
        gameNameField.setPreferredSize(new Dimension(margin, 50));

        p.add(nameLabel);
        p.add(gameNameField);

        p.setBorder(new EmptyBorder(30, margin, 20, margin));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the start game button panel.
     * @return The JPanel containing the start game button.
     */
    private JPanel getStartGameButton() {
        JPanel p = new JPanel();
        startGameButton = new JButton(Strings.START_GAME.getValue());
        startGameButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));

        startGameButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        startGameButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        startGameButton.setFocusPainted(false);

        startGameButton.setActionCommand(CREATE_BUTTON);

        p.add(startGameButton);
        p.setBorder(new EmptyBorder(10, 10, 20, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the view statistics button panel.
     * @return The JPanel containing the view statistics button.
     */
    private JPanel getSeeStatsButton() {
        JPanel p = new JPanel();
        seeStatsButton = new JButton(Strings.VIEW_STATISTICS.getValue());
        seeStatsButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));

        seeStatsButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        seeStatsButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        seeStatsButton.setActionCommand(STATS_BUTTON);
        seeStatsButton.setFocusPainted(false);

        p.add(seeStatsButton);
        p.setBorder(new EmptyBorder(1, 10, 300, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the top panel with user button.
     * @return The component representing the top panel.
     */
    private Component getTopPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        userButton = new UserButton();
        userButton.setActionCommand(USER_BUTTON);

        p.add(userButton);

        p.setBorder(new EmptyBorder(5, 0, 0, 25));
        p.setOpaque(false);

        return p;
    }

    /**
     * Displays an error popup dialog with the given message.
     * @param message The error message to display.
     */
    public void errorPopUp(String message) {
        CustomErrorDialog popUp = new CustomErrorDialog(this, Strings.CANT_ANGER_A_COFFEE.getValue(), message);

        Timer timer = new Timer(12000, (e) -> {
            popUp.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        popUp.setVisible(true);
    }

    /**
     * Retrieves the game name entered by the user.
     * @return The game name.
     */
    public String getGameNameField() {
        return gameNameField.getText();
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        userButton.addActionListener(listener);
        startGameButton.addActionListener(listener);
        seeStatsButton.addActionListener(listener);
    }

    /**
     * Refreshes the game name text box.
     */
    public void refreshTextBox() {
        gameNameField.setText("");
    }
}