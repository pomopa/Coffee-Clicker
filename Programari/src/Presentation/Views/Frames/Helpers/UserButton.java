package Presentation.Views.Frames.Helpers;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a button with a user icon.
 */
public class UserButton extends JButton {

    /**
     * Default constructor for the class User Button.
     */
    public UserButton(){

        ImageIcon icon = new ImageIcon("resources/user.png");
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIcon(scaledIcon);
        setPreferredSize(new Dimension(50, 50));

        setBorderPainted(false);
        setContentAreaFilled(false);
        setText("");
        setFocusPainted(false);

    }
}
