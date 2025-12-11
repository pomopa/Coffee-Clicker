package Presentation.Views.Frames;

import Presentation.Views.Frames.Helpers.BackGroundPanel;
import Presentation.Views.Frames.Helpers.LogoCoffeePanel;
import Presentation.Views.Frames.Helpers.Title;
import Presentation.Views.Frames.Helpers.UserButton;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the ResumeGameView frame, allowing users to resume an open game.
 */
public class ResumeGameView extends JFrame {
    /**
     * Represents the command string for the user button action.
     */
    public static final String USER_BUTTON = "USER_BUTTON";

    /**
     * Represents the command string for the resume game button action.
     */
    public static final String RESUME_BUTTON = "PLAY_GAME";

    /**
     * Represents the command string for the statistics button action.
     */
    public static final String STATS_BUTTON = "STATS_BUTTON";

    /**
     * Necessary button to get to the user's view.
     */
    private JButton userButton;

    /**
     * Necessary button to direct to the game view when a game is already running.
     */
    private JButton resumeGameButton;

    /**
     * Necessary button to let the user view the stats of their and others games.
     */
    private JButton seeStatsButton;


    /**
     * Default constructor for the class Resume Game View.
     */
    public ResumeGameView(){}

    /**
     * Returns the main panel of the ResumeGameView frame.
     * @return The JPanel representing the ResumeGameView frame.
     */
    public JPanel getResumeGameView(){

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(getTopPanel());
        mainPanel.add(LogoCoffeePanel.getLogo());
        mainPanel.add(new Title(Strings.YOU_HAVE_AN_OPEN_GAME.getValue()));

        mainPanel.add(getResumeGameButton());
        mainPanel.add(getSeeStatsButton());

        return mainPanel;
    }

    /**
     * Creates the resume game button panel.
     * @return The JPanel containing the resume game button.
     */
    private JPanel getResumeGameButton() {
        JPanel p = new JPanel();
        resumeGameButton = new JButton(Strings.CONTINUE_GAME.getValue());
        resumeGameButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));

        resumeGameButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        resumeGameButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        resumeGameButton.setFocusPainted(false);

        resumeGameButton.setActionCommand(RESUME_BUTTON);

        p.add(resumeGameButton);
        p.setBorder(new EmptyBorder(42, 10, 20, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the see statistics button panel.
     * @return The JPanel containing the see statistics button.
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
        p.setBorder(new EmptyBorder(1, 10, 200, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the top panel with user button.
     * @return The JPanel containing the user button.
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
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        userButton.addActionListener(listener);
        resumeGameButton.addActionListener(listener);
        seeStatsButton.addActionListener(listener);
    }


}