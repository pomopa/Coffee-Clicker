package Presentation.Views.Frames.Helpers;

import Presentation.Views.ViewException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Helper class to create a panel with a logo image.
 */
public class LogoCoffeePanel extends JPanel {

    /**
     * Default constructor for the class LogoCoffeePanel.
     */
    public LogoCoffeePanel() {}

    /**
     * Gets a JPanel containing a logo image.
     *
     * @return JPanel with the logo image.
     */
    public static JPanel getLogo() {
        JPanel p = new JPanel();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources/taza.png"));
        } catch (IOException e) {
            ViewException.error(e.getMessage());
        }
        Image scaledImage = img.getScaledInstance(475, 347, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(imageIcon);

        p.setBorder(new EmptyBorder(0, 10, 1, 10));

        p.add(imageLabel);
        p.setOpaque(false);

        return p;
    }
}
