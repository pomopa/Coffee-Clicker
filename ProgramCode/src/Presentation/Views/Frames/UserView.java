package Presentation.Views.Frames;

import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;
import Presentation.Views.Frames.Helpers.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents the UserView frame, which displays the user's profile information and options.
 */
public class UserView extends JFrame {

    /**
     * Represents the action command for the back button.
     */
    public static final String BACK_BUTTON = "BACK_BUTTON";

    /**
     * Represents the action command for the logout button.
     */
    public static final String LOGOUT_BUTTON = "LOGOUT_BUTTON";

    /**
     * Represents the action command for the delete account button.
     */
    public static final String DELETE_BUTTON = "DELETE_BUTTON";

    /**
     * Necessary button to let the user log out of their account.
     */
    private JButton logoutButton;

    /**
     * Necessary button to let the user delete their account.
     */
    private JButton deleteAccountButton;

    /**
     * Necessary button to get to the previous view.
     */
    private JButton backButton;


    /**
     * Default constructor for the class User View.
     */
    public UserView() { }

    /**
     * Returns the main panel of the UserView frame.
     * @return The JPanel representing the UserView frame.
     */
    public JPanel getUserView(){

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createbackButton());
        mainPanel.add(LogoCoffeePanel.getLogo());
        mainPanel.add(new Title(Strings.PROFILE.getValue()));
        mainPanel.add(createLogoutButton());
        mainPanel.add(createDeleteButton());
        mainPanel.add(getAdvise());

        return mainPanel;
    }

    /**
     * Creates a panel with an advisory message.
     * @return The JPanel with the advisory message.
     */
    private JPanel getAdvise() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel jLabel = new JLabel(Strings.DELETE_USER_ADVISE.getValue());

        p.add(jLabel);
        p.setBorder(new EmptyBorder(30, 0, 150, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the back button panel.
     * @return The JPanel containing the back button.
     */
    private Component createbackButton() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new BackButton();
        backButton.setActionCommand(BACK_BUTTON);

        p.add(backButton);

        p.setBorder(new EmptyBorder(5, 20, 35, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the logout button panel.
     * @return The JPanel containing the logout button.
     */
    private JPanel createLogoutButton() {
        JPanel p = new JPanel();
        logoutButton = new JButton(Strings.LOGOUT.getValue());

        logoutButton.setActionCommand(LOGOUT_BUTTON);
        logoutButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        logoutButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        logoutButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        logoutButton.setFocusPainted(false);

        p.add(logoutButton);
        p.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the delete account button panel.
     * @return The JPanel containing the delete account button.
     */
    private JPanel createDeleteButton() {
        JPanel p = new JPanel();
        deleteAccountButton = new JButton(Strings.DELETE_USER.getValue());

        deleteAccountButton.setActionCommand(DELETE_BUTTON);
        deleteAccountButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        deleteAccountButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        deleteAccountButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        deleteAccountButton.setFocusPainted(false);

        p.add(deleteAccountButton);
        p.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Displays an error popup dialog with the given message.
     * @param message The error message to display.
     */
    public void errorPopUp(String message) {
        CustomErrorDialog popUp = new CustomErrorDialog(this, Strings.CANT_ANGER_A_COFFEE.getValue(), message);

        Timer timer = new Timer(6000, (e) -> {
            popUp.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        popUp.setVisible(true);
    }

    /**
     * Displays a confirmation popup dialog with the given message and buttons.
     * @param message The confirmation message to display.
     * @param button1 The text for the first button.
     * @param button2 The text for the second button.
     * @return The button selected by the user.
     */
    public int confirmationPopUp(String message, String button1, String button2) {
        CustomYesOrNODialog customYesOrNODialog = new CustomYesOrNODialog(this, message, button1, button2);

        Timer timer = new Timer(20000, (e) -> {
            customYesOrNODialog.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        customYesOrNODialog.setVisible(true);

        while(0 == customYesOrNODialog.getButtonSelected() && timer.isRunning()) {
        }

        return customYesOrNODialog.getButtonSelected();
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        logoutButton.addActionListener(listener);
        deleteAccountButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }
}
