package Presentation.Views.Frames.Helpers;

import Presentation.Views.Frames.Enums.TextStyle;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a title label with custom styling.
 */
public class Title extends JPanel {

    /**
     * Default constructor for the class Title.
     *
     * @param title The text of the title.
     */
    public Title(String title){
        JPanel p = new JPanel();
        JLabel label = new JLabel(title);
        label.setForeground(Color.decode("#563224"));
        Font font = new Font("Gotham", Font.BOLD, TextStyle.TITLE_FONT_SIZE.getValue());
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        setOpaque(false);
    }

}
