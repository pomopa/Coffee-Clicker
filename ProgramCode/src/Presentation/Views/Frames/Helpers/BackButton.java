package Presentation.Views.Frames.Helpers;

import javax.swing.*;
import java.awt.*;

/**
 * A customised JButton with a back arrow icon.
 */
public class BackButton extends JButton {

    /**
     * Default constructor for the class Back Button.
     */
    public BackButton(){
        ImageIcon icon = new ImageIcon("resources/backArrow.png");
        Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIcon(scaledIcon);
        setPreferredSize(new Dimension(50, 50));

        setBorderPainted(false);
        setContentAreaFilled(false);
        setText("");
        setFocusPainted(false);
        setOpaque(false);
    }
}
