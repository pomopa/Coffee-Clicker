package Presentation.Views.Frames.Enums;

/**
 * Enum representing various text styles.
 */
public enum TextStyle {

    /**
     * Font size for titles.
     */
    TITLE_FONT_SIZE(24),

    /**
     * Font size for subtitles.
     */
    SUBTITLE_FONT_SIZE(20),

    /**
     * Font size for body text.
     */
    BODY_FONT_SIZE(16),

    /**
     * Color for titles (Red).
     */
    TITLE_COLOR(0xFF0000), // Red color

    /**
     * Color for subtitles (Green).
     */
    SUBTITLE_COLOR(0x00FF00), // Green color

    /**
     * Color for body text (Blue).
     */
    BODY_COLOR(0x0000FF); // Blue color

    private final int value;

    /**
     * Defaults constructor for the class Title.
     *
     * @param value The value representing the text style.
     */
    TextStyle(int value) {
        this.value = value;
    }

    /**
     * Retrieves the value representing the text style.
     * @return The value of the text style.
     */
    public int getValue() {
        return value;
    }
}
