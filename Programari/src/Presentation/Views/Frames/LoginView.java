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
public class LoginView extends JFrame {

    /**
     * Variable that stores the padding used for all the borders.
     */
    private final int LABEL_PADDING = 5;
    /**
     * Action command string representing the back button.
     */
    public static final String BACK_BUTTON = "BACK_BUTTON";
    /**
     * Action command string representing the login button.
     */
    public static final String LOGIN_BUTTON = "LOG_IN_BUTTON";
    /**
     * Action command string representing the recover password button.
     */
    public static final String RECOVER_PASSWORD = "RECOVER_PASSWORD";

    /**
     * Necessary button to log into the game.
     */
    private JButton loginButton;

    /**
     * Necessary button to recover the password of the user.
     */
    private JButton recoverPasswordButton;

    /**
     * Necessary button to go back to the previous view.
     */
    private JButton backButton;

    /**
     * Text field in which the user will introduce their username or email.
     */
    private JTextField usernameOrMailTextField;

    /**
     * Password field in which the user will introduce their password.
     */
    private JPasswordField passwordTextField;

    /**
     * Strings necessary to store the values introduced by the user.
     */
    private String usernameOrMail, password;


    /**
     * Default constructor for the class Log In View.
     */
    public LoginView() {}

    /**
     * Returns the main panel of the LoginView frame.
     * @return The JPanel representing the LoginView frame.
     */
    public JPanel getLoginView () {

        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createBackButton());
        JPanel logo = LogoCoffeePanel.getLogo();
        logo.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(logo);
        mainPanel.add(new Title(Strings.LOGIN.getValue()));

        mainPanel.add(createUserField());
        mainPanel.add(createLoginButton());
        mainPanel.add(createRecoverPasswordButton());

        return mainPanel;
    }

    /**
     * Creates the recover password button panel.
     * @return The JPanel containing the recover password button.
     */
    private Component createRecoverPasswordButton() {
        JPanel p = new JPanel();
        recoverPasswordButton = new JButton(Strings.RECOVER_PASSWORD.getValue());
        recoverPasswordButton.setActionCommand(RECOVER_PASSWORD);

        recoverPasswordButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        recoverPasswordButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        recoverPasswordButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        recoverPasswordButton.setFocusPainted(false);

        p.add(recoverPasswordButton);

        p.setBorder(new EmptyBorder(0, 0, 250, 0));
        p.setOpaque(false);

        return p;
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

        JLabel usernameLabel = new JLabel(Strings.USERNAME_MAIL.getValue());
        usernameLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        usernameOrMailTextField = new JTextField();
        usernameOrMailTextField.setPreferredSize(new Dimension(20, 25));

        JLabel passwordLabel = new JLabel(Strings.PASSWORD.getValue());
        passwordLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        passwordTextField = new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(20, 25));

        p.add(usernameLabel);
        p.add(usernameOrMailTextField);
        p.add(passwordLabel);
        p.add(passwordTextField);

        //p.setBorder(new EmptyBorder(10,600,0,600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int margin = (int) ((screenSize.getWidth() * 0.4));
        p.setBorder(new EmptyBorder(30, margin, 5, margin));
        p.setOpaque(false);

        return p;
    }

    /**
     * Creates the login button panel.
     * @return The JPanel containing the login button.
     */
    private Component createLoginButton() {
        JPanel p = new JPanel();
        loginButton = new JButton(Strings.LOGIN.getValue());
        loginButton.setActionCommand(LOGIN_BUTTON);

        loginButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        loginButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        loginButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            usernameOrMail = usernameOrMailTextField.getText();
            password = passwordTextField.getText();
        });
        p.add(loginButton);

        p.setBorder(new EmptyBorder(20, 0, 20, 0));
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
     * Retrieves the entered username or mail.
     * @return The username or mail entered by the user.
     */
    public String getUsernameOrMail(){
        return usernameOrMailTextField.getText();
    }

    /**
     * Retrieves the entered password.
     * @return The password entered by the user.
     */
    public String getPassword(){
        return passwordTextField.getText();
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        loginButton.addActionListener(listener);
        backButton.addActionListener(listener);
        recoverPasswordButton.addActionListener(listener);
    }

    /**
     * Resets the text fields of the login view.
     */
    public void reset() {
        usernameOrMailTextField.setText("");
        passwordTextField.setText("");
    }
}
