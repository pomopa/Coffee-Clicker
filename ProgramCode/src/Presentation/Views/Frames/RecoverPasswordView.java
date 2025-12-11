package Presentation.Views.Frames;

import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;
import Presentation.Views.Frames.Helpers.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the LoginView frame, allowing users to log in to the system.
 */
public class RecoverPasswordView extends JFrame {

    /**
     * Variable that stores the padding used for all the borders.
     */
    private final int LABEL_PADDING = 5;
    /**
     * Represents the command string for the back button action.
     */
    public static final String BACK_BUTTON = "BACK_BUTTON";
    /**
     * Represents the command string for the recover password action.
     */
    public static final String RECOVER_PASSWORD = "RECOVER_PASSWORD";

    /**
     * Necessary button to request the password.
     */
    private JButton requestButton;

    /**
     * Necessary button to get to the previous view.
     */
    private JButton backButton;

    /**
     * Text field in which the user will introduce their username.
     */
    private JTextField usernameTextField;

    /**
     * Text field in which the user will introduce their mail.
     */
    private JTextField mailTextField;

    /**
     * Strings necessary to store the values introduced by the user.
     */
    private String username, mail;


    /**
     * Default constructor for the class Recover Password View.
     */
    public RecoverPasswordView() {}

    /**
     * Returns the main panel of the LoginView frame.
     * @return The JPanel representing the LoginView frame.
     */
    public JPanel getRecoverPasswordView() {

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createBackButton());
        JPanel logo = LogoCoffeePanel.getLogo();
        logo.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(logo);
        mainPanel.add(new Title(Strings.RECOVER_PASSWORD.getValue()));

        mainPanel.add(createUserField());
        mainPanel.add(createRequestButton());

        return mainPanel;
    }


    /**
     * Creates the back button panel.
     * @return The JPanel containing the back button.
     */
    private Component createBackButton() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new BackButton();
        backButton.setActionCommand(BACK_BUTTON);

        p.add(backButton);

        p.setBorder(new EmptyBorder(5, 20, 0, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the user field panel.
     * @return The JPanel containing the user field.
     */
    private JPanel createUserField() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel(Strings.USERNAME.getValue());
        usernameLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        usernameTextField = new JTextField();
        usernameTextField.setPreferredSize(new Dimension(20, 25));

        JLabel passwordLabel = new JLabel(Strings.EMAIL.getValue());
        passwordLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        mailTextField = new JTextField();
        mailTextField.setPreferredSize(new Dimension(20, 25));

        p.add(usernameLabel);
        p.add(usernameTextField);
        p.add(passwordLabel);
        p.add(mailTextField);

        //p.setBorder(new EmptyBorder(10,600,0,600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int margin = (int) ((screenSize.getWidth() * 0.4));
        p.setBorder(new EmptyBorder(30, margin, 5, margin));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the request button panel.
     * @return The JPanel containing the login button.
     */
    private Component createRequestButton() {
        JPanel p = new JPanel();
        requestButton = new JButton(Strings.REQUEST_PASSWORD.getValue());
        requestButton.setActionCommand(RECOVER_PASSWORD);

        requestButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        requestButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        requestButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        requestButton.setFocusPainted(false);

        requestButton.addActionListener(e -> {
            username = usernameTextField.getText();
            mail = mailTextField.getText();
        });
        p.add(requestButton);

        p.setBorder(new EmptyBorder(20, 0, 250, 0));
        p.setOpaque(false);

        return p;
    }

    /**
     * Displays an error pop-up dialog with the given message.
     * @param message The error message to display.
     */
    public void errorPopUp(String message) {
        CustomErrorDialog popUp = new CustomErrorDialog(this, Strings.CANT_ANGER_A_COFFEE.getValue(), message);

        Timer timer = new Timer(10000, (e) -> {
            popUp.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        popUp.setVisible(true);
    }

    /**
     * Retrieves the entered username.
     * @return The username entered by the user.
     */
    public String getUsername(){
        return usernameTextField.getText();
    }

    /**
     * Retrieves the entered mail.
     * @return The password entered by the user.
     */
    public String getMail(){
        return mailTextField.getText();
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        requestButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Resets the text fields of the login view.
     */
    public void reset() {
        usernameTextField.setText("");
        mailTextField.setText("");
    }

    /**
     * Displays the password pop-up dialog with the found password in the database.
     * @param message Password found.
     */
    public void passwordPopUp(String message) {
        CustomMessagePopUp popUp = new CustomMessagePopUp(this, Strings.RECOVER_PASSWORD.getValue(), Strings.MESSAGE_PASSWORD.getValue() + message, "resources/ImagePasswordRecover.png");

        Timer timer = new Timer(10000, (e) -> {
            popUp.dispose();
        });
        timer.setRepeats(false);
        timer.start();

        popUp.setVisible(true);
    }
}
