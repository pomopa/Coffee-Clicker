package Business.Entities;

import javax.swing.*;

/**
 * Represents a generator in the game.
 */
public class Generator {
    private int idGenerator;
    private String generatorsType;
    private int numGenerator;
    private double cost;
    private double baseCost;
    private double incremental;
    private double production;
    private String image;
    private String gameName;
    private JLabel costLabel;


    /**
     * Default constructor for the class Generator with the game's name.
     *
     * @param idGenerator The ID of the generator.
     * @param generatorsType The type of the generator.
     * @param numGenerator The number of generators.
     * @param cost The cost of the generator.
     * @param baseCost The cost of the generator in the first purchase.
     * @param incremental The incremental value of the generator.
     * @param production The production value of the generator.
     * @param image The image associated with the generator.
     * @param gameName The name of the game associated with the generator.
     */
    public Generator(int idGenerator, String generatorsType, int numGenerator, double cost, double baseCost,
                     double incremental, double production, String image, String gameName) {
        this.idGenerator = idGenerator;
        this.generatorsType = generatorsType;
        this.numGenerator = numGenerator;
        this.cost = cost;
        this.baseCost = baseCost;
        this.incremental = incremental;
        this.production = production;
        this.image = image;
        this.gameName = gameName;
    }

    /**
     * Default constructor for the class Generator without the game's name.
     *
     * @param generatorsType The type of the generator.
     * @param numGenerator The number of generators.
     * @param cost The cost of the generator.
     * @param baseCost The cost of the generator in the first purchase.
     * @param incremental The incremental value of the generator.
     * @param production The production value of the generator.
     * @param image The image associated with the generator.
     */
    public Generator(String generatorsType, int numGenerator, double cost, double baseCost, double incremental, double production, String image) {
        this.idGenerator = -1;
        this.generatorsType = generatorsType;
        this.numGenerator = numGenerator;
        this.cost = cost;
        this.baseCost = baseCost;
        this.incremental = incremental;
        this.production = production;
        this.image = image;
        this.gameName = null;
    }

    /**
     * Gets the label used to display the cost of the generator in the user interface.
     *
     * @return The label used to display the cost of the generator.
     */
    public JLabel getCostLabel() {
        return costLabel;
    }

    /**
     * Sets the label used to display the cost of the generator in the user interface.
     * This allows dynamically updating the label with the current cost value.
     *
     * @param costLabel The label used to display the cost of the generator.
     */
    public void setCostLabel(JLabel costLabel) {
        this.costLabel = costLabel;
    }

    /**
     * Gets the type of the generator.
     *
     * @return The type of the generator.
     */
    public String getGeneratorsType() {
        return generatorsType;
    }

    /**
     * Gets the number of generators.
     *
     * @return The number of generators.
     */
    public int getNumGenerator() {
        return numGenerator;
    }

    /**
     * Gets the cost of the generator.
     *
     * @return The cost of the generator.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the base cost of the generator.
     *
     * @return The base cost of the generator.
     */
    public double getBaseCost() {
        return baseCost;
    }

    /**
     * Gets the incremental value of the generator.
     *
     * @return The incremental value of the generator.
     */
    public double getIncremental() {
        return incremental;
    }

    /**
     * Gets the production value of the generator.
     *
     * @return The production value of the generator.
     */
    public double getProduction() {
        return production;
    }

    /**
     * Gets the image associated with the generator.
     *
     * @return The image associated with the generator.
     */
    public String getImage() {
        return image;
    }

    /**
     * Gets the name of the game associated with the generator.
     *
     * @return The name of the game associated with the generator.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the number of generators.
     *
     * @param numGenerator The number of generators.
     */
    public void setNumGenerator(int numGenerator) {
        this.numGenerator = numGenerator;
    }

    /**
     * Sets the cost of the generator.
     *
     * @param cost The cost of the generator.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Sets the base cost of the generator.
     *
     * @param baseCost The base cost of the generator to be set.
     */
    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    /**
     * Sets the production value of the generator.
     *
     * @param production The production value of the generator.
     */
    public void setProduction(double production) {
        this.production = production;
    }
}