package Presentation.Views.Frames;

import Presentation.Views.Frames.Enums.ViewStyles;
import Presentation.Views.Frames.Helpers.*;
import Presentation.Views.Frames.Enums.Strings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the SignUpView frame, allowing users to register a new account.
 */
public class SignUpView extends JFrame {

    /**
     * Action command for the back button.
     */
    public static final String BACK_BUTTON = "BACK_BUTTON";

    /**
     * Action command for the register button.
     */
    public static final String REGISTER_BUTTON = "REGISTER_BUTTON";

    /**
     * Variable that stores the padding used for all the borders.
     */
    private final int LABEL_PADDING = 5;

    /**
     * Necessary button to register user into the database.
     */
    private JButton registerButton;

    /**
     * Text field in which the user will introduce their username.
     */
    private JTextField usernameField;

    /**
     * Text field in which the user will introduce their mail.
     */
    private JTextField emailField;

    /**
     * Password field in which the user will introduce their password.
     */
    private JPasswordField passwordField;

    /**
     * Text field in which the user will introduce their password again to check it's the same as before.
     */
    private JPasswordField confirmPasswordField;

    /**
     * Necessary button to get to the previous view.
     */
    private JButton backButton;

    /**
     * Default constructor for the class Sign-Up View.
     */
    public SignUpView() {
    }

    /**
     * Returns the main panel of the SignUpView frame.
     * @return The JPanel representing the SignUpView frame.
     */
    public JPanel getSignUpView(){
        JPanel mainPanel = new BackGroundPanel("resources/background.gif");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        mainPanel.add(createBackButton());
        mainPanel.add(LogoCoffeePanel.getLogo());
        mainPanel.add(new Title(Strings.ENTER_NEW_USER_DATA.getValue()));
        mainPanel.add(getInfo());
        mainPanel.add(createRegisterButton());

        return mainPanel;
    }

    /**
     * Creates the panel for entering user information.
     * @return The JPanel containing user information fields.
     */
    private JPanel getInfo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(configUsername());
        p.add(usernameField);
        p.add(configEmail());
        p.add(emailField);
        p.add(configEmailFormat());
        p.add(configPassword());
        p.add(passwordField);
        p.add(configPasswordFormat());
        p.add(configConfirmPassword());
        p.add(confirmPasswordField);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int margin = (int) ((screenSize.getWidth() * 0.4));
        p.setBorder(new EmptyBorder(10, margin, 5, margin));
        p.setOpaque(false);

        return p;
    }

    /**
     * Method that creates the necessary information to handle the Confirm Password.
     *
     * @return Label of the Confirm Password.
     */
    private JLabel configConfirmPassword() {
        JLabel confirmPasswordLabel = new JLabel(Strings.CONFIRM_PASSWORD.getValue());
        confirmPasswordLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        confirmPasswordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(20, 25));

        return confirmPasswordLabel;
    }

    /**
     * Method that shows the format the Password introduced by the user needs to follow.
     *
     * @return Label with the format of the password.
     */
    private JLabel configPasswordFormat() {
        JLabel passwordFormat = new JLabel(Strings.PASSWORD_FORMAT.getValue());
        passwordFormat.setForeground(Color.RED);
        Font font2 = passwordFormat.getFont();
        passwordFormat.setFont(new Font(font2.getFontName(), font2.getStyle(), 10));

        return passwordFormat;
    }

    /**
     * Method that creates the necessary information to handle the Password.
     *
     * @return Label of the Password.
     */
    private JLabel configPassword() {
        JLabel passwordLabel = new JLabel(Strings.PASSWORD.getValue());
        passwordLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(20, 25));

        return passwordLabel;
    }

    /**
     * Method that shows the format the Email introduced by the user needs to follow.
     *
     * @return Label with the format of the Email.
     */
    private JLabel configEmailFormat() {
        JLabel emailFormat = new JLabel(Strings.EMAIL_FORMAT.getValue());
        emailFormat.setForeground(Color.RED);
        Font font = emailFormat.getFont();
        emailFormat.setFont(new Font(font.getFontName(), font.getStyle(), 10));

        return emailFormat;
    }

    /**
     * Method that creates the necessary information to handle the Email.
     *
     * @return Label of the Email.
     */
    private JLabel configEmail() {
        JLabel emailLabel = new JLabel(Strings.EMAIL.getValue());
        emailLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(20, 25));

        return emailLabel;
    }

    /**
     * Method that creates the necessary information to handle the Username.
     *
     * @return Label of the Username.
     */
    private JLabel configUsername() {
        JLabel usernameLabel = new JLabel(Strings.USERNAME.getValue());
        usernameLabel.setBorder(new EmptyBorder(LABEL_PADDING, 0, LABEL_PADDING, 0));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(20, 25));

        return usernameLabel;
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
     * Creates the register button panel.
     * @return The JPanel containing the register button.
     */
    private JPanel createRegisterButton() {
        JPanel p = new JPanel();
        registerButton = new JButton(Strings.REGISTER.getValue());

        registerButton.setPreferredSize(new Dimension(ViewStyles.HOME_BUTTON.getWidth(), ViewStyles.HOME_BUTTON.getHeight()));
        registerButton.setActionCommand(REGISTER_BUTTON);

        registerButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        registerButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        registerButton.setFocusPainted(false);

        p.add(registerButton);

        p.setBorder(new EmptyBorder(30, 0, 300, 0));
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
     * Resets the text fields in the SignUpView frame.
     */
    public void reset() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        registerButton.addActionListener(listener);
        backButton.addActionListener(listener);
    }

    /**
     * Retrieves the entered username.
     * @return The username.
     */
    public String getUserName() {
        return usernameField.getText();
    }

    /**
     * Retrieves the entered email.
     * @return The email.
     */
    public String getMail() {
        return emailField.getText();
    }

    /**
     * Retrieves the entered password.
     * @return The password.
     */
    public String getPassword() {
        return passwordField.getText();
    }

    /**
     * Retrieves the entered confirmed password.
     * @return The confirmed password.
     */
    public String getConfirmPassword() {
        return confirmPasswordField.getText();
    }
}
