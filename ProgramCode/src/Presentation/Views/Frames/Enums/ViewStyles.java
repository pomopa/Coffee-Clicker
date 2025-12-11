package Presentation.Views.Frames.Enums;

import java.awt.Color;

/**
 * Enum representing various visual styles for frames and buttons.
 */
public enum ViewStyles {

    /**
     * Small window dimensions: width = 400, height = 300.
     */
    SMALL_WINDOW(400, 300),

    /**
     * Medium window dimensions: width = 800, height = 600.
     */
    MEDIUM_WINDOW(800, 600),

    /**
     * Large window dimensions: width = 1200, height = 900.
     */
    LARGE_WINDOW(1200, 900),

    /**
     * Popup window dimensions: width = 600, height = 400.
     */
    POPUP_WINDOW(600, 400),

    /**
     * Small button dimensions: width = 80, height = 30.
     */
    SMALL_BUTTON(80, 30),

    /**
     * Medium button dimensions: width = 120, height = 40.
     */
    MEDIUM_BUTTON(120, 40),

    /**
     * Large button dimensions: width = 160, height = 50.
     */
    LARGE_BUTTON(160, 50),

    /**
     * Home button dimensions: width = 300, height = 50.
     */
    HOME_BUTTON(300, 50),

    /**
     * Close popup button dimensions: width = 160, height = 50.
     */
    CLOSE_POPUP_BUTTON(160, 50),

    /**
     * Default background color for home buttons: white.
     */
    HOME_BUTTON_BACKGROUND_COLOR(Color.WHITE),

    /**
     * Default foreground color for home buttons: black.
     */
    HOME_BUTTON_FOREGROUND_COLOR(Color.BLACK);

    private final int width;
    private final int height;
    private final Color color;

    /**
     * Default constructor for the class View Styles.
     *
     * @param width The width dimension.
     * @param height The height dimension.
     */
    ViewStyles(int width, int height) {
        this.width = width;
        this.height = height;
        this.color = null;
    }

    /**
     * Constructs ViewStyles with specified color.
     * @param color The color.
     */
    ViewStyles(Color color) {
        this.width = 0;
        this.height = 0;
        this.color = color;
    }
    /**
     * Retrieves the width dimension.
     * @return The width dimension.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the height dimension.
     * @return The height dimension.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the color.
     * @return The color.
     */
    public Color getColor() {
        return color;
    }
}
