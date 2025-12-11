package Presentation.Views.Frames;

import Presentation.Views.Frames.Helpers.BackGroundPanel;
import Presentation.Views.Frames.Helpers.LogoCoffeePanel;
import Presentation.Views.Frames.Helpers.Title;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the HomeView frame, displaying options for logging in or signing up.
 */
public class HomeView extends JFrame {

    /**
     * Represents the action command string for signing up.
     */
    public static final String SIGN_UP = "SIGN_UP";

    /**
     * Represents the action command string for logging in.
     */
    public static final String LOG_IN = "LOG_IN";

    /**
     * Necessary button to go to the log in view.
     */
    private JButton logInButton;

    /**
     * Necessary button to go to the sign-up view.
     */
    private JButton signUpButton;

    /**
     * Default constructor for the class Home View.
     */
    public HomeView (){}

    /**
     * Returns the main panel of the HomeView frame.
     * @return The JPanel representing the HomeView frame.
     */
    public JPanel getHomeView(){

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel emptyPanel = new JPanel();
        emptyPanel.setBorder(new EmptyBorder(36, 0, 0, 0));
        emptyPanel.setOpaque(false);

        mainPanel.add(emptyPanel);
        mainPanel.add(getTopPanel());
        mainPanel.add(LogoCoffeePanel.getLogo());
        mainPanel.add(new Title(Strings.WELCOME.getValue()));
        mainPanel.add(getLogInButton());
        mainPanel.add(getSignUpButton());

        return mainPanel;
    }

    /**
     * Creates the login button panel.
     * @return The JPanel containing the login button.
     */
    private JPanel getLogInButton() {
        JPanel p = new JPanel();
        logInButton = new JButton(Strings.LOGIN.getValue());
        logInButton.setActionCommand(LOG_IN);

        logInButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));

        logInButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        logInButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        logInButton.setFocusPainted(false);

        p.add(logInButton);
        p.setBorder(new EmptyBorder(42, 10, 20, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the sign-up button panel.
     * @return The JPanel containing the sign-up button.
     */
    private JPanel getSignUpButton() {
        JPanel p = new JPanel();
        signUpButton = new JButton(Strings.REGISTER.getValue());
        signUpButton.setActionCommand(SIGN_UP);

        signUpButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));

        signUpButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        signUpButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        signUpButton.setFocusPainted(false);

        p.add(signUpButton);

        p.setBorder(new EmptyBorder(1, 10, 200, 10));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the top panel.
     * @return The JPanel representing the top panel.
     */
    private Component getTopPanel() {
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(5, 20, 0, 0));
        p.setOpaque(false);
        return p;
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        logInButton.addActionListener(listener);
        signUpButton.addActionListener(listener);
    }

}
