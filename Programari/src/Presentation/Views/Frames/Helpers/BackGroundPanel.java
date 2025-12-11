package Presentation.Views.Frames.Helpers;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that sets the gif as background.
 */
public class BackGroundPanel extends JPanel {

    /**
     * ImageIcon that stores the gif.
     */
    private ImageIcon gifBackground;

    /**
     * Default constructor for the class Background Panel, which specifies the background gif.
     *
     * @param gifPath the path to the GIF background image
     */
    public BackGroundPanel(String gifPath) {
        this.gifBackground = new ImageIcon(gifPath);
    }

    /**
     * Paints the background gif on the panel.
     *
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (null != gifBackground) {
            int width = getWidth();
            int height = getHeight();

            gifBackground.paintIcon(this, g, 0, 0);

            g.drawImage(gifBackground.getImage(), 0, 0, width, height, null);
        }
    }
}
