package Business.Entities;

import javax.swing.*;

/**
 * Represents a power-up in the game.
 */
public class PowerUp {
    private int powerUpId;
    private String type;
    private String affects;
    private String name;
    private int cost;
    private double bonus;
    private int threshold;
    private String image;
    private boolean purchased;
    private String gameName;
    private JLabel costLabel;

    /**
     * Default constructor for the class Power Up with the game's name.
     *
     * @param powerUpId The ID of the power-up.
     * @param type The type of the power-up.
     * @param affects The aspect of the game affected by the power-up.
     * @param name The name of the power-up.
     * @param cost The cost of the power-up.
     * @param bonus The bonus provided by the power-up.
     * @param threshold The threshold for activating the power-up.
     * @param image The image associated with the power-up.
     * @param purchased Indicates whether the power-up has been purchased.
     * @param gameName The name of the game associated with the power-up.
     */
    public PowerUp(int powerUpId, String type, String affects, String name, int cost, double bonus, int threshold,
                   String image, boolean purchased, String gameName) {
        this.powerUpId = powerUpId;
        this.type = type;
        this.affects = affects;
        this.name = name;
        this.cost = cost;
        this.bonus = bonus;
        this.threshold = threshold;
        this.image = image;
        this.gameName = gameName;
    }

    /**
     * Default constructor for the class Power Up without the game's name.
     *
     * @param type The type of the power-up.
     * @param affects The aspect of the game affected by the power-up.
     * @param name The name of the power-up.
     * @param cost The cost of the power-up.
     * @param bonus The bonus provided by the power-up.
     * @param threshold The threshold for activating the power-up.
     * @param image The image associated with the power-up.
     * @param purchased Indicates whether the power-up has been purchased.
     */
    public PowerUp(String type, String affects, String name, int cost, double bonus, int threshold, String image, boolean purchased) {
        this.type = type;
        this.affects = affects;
        this.name = name;
        this.cost = cost;
        this.bonus = bonus;
        this.threshold = threshold;
        this.image = image;
        this.purchased = purchased;
    }

    /**
     * Gets the cost label associated with the power-up.
     *
     * @return The cost label associated with the power-up.
     */
    public JLabel getCostLabel() {
        return costLabel;
    }

    /**
     * Sets the cost label associated with the power-up.
     *
     * @param costLabel The cost label to be associated with the power-up.
     */
    public void setCostLabel(JLabel costLabel) {
        this.costLabel = costLabel;
    }

    /**
     * Gets the type of the power-up.
     *
     * @return The type of the power-up.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the aspect of the game affected by the power-up.
     *
     * @return The aspect of the game affected by the power-up.
     */
    public String getAffects() {
        return affects;
    }

    /**
     * Gets the name of the power-up.
     *
     * @return The name of the power-up.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost of the power-up.
     *
     * @return The cost of the power-up.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the bonus provided by the power-up.
     *
     * @return The bonus provided by the power-up.
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * Gets the threshold for activating the power-up.
     *
     * @return The threshold for activating the power-up.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Gets the image associated with the power-up.
     *
     * @return The image associated with the power-up.
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets whether the power-up has been purchased.
     *
     * @return True if the power-up has been purchased, false otherwise.
     */

    public boolean getPurchased() {
        return purchased;
    }

    /**
     * Sets whether the power-up has been purchased.
     *
     * @param purchased True if the power-up has been purchased, false otherwise.
     */
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

}
