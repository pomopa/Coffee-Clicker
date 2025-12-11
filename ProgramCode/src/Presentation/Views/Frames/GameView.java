package Presentation.Views.Frames;

import Business.Entities.Generator;
import Business.Entities.PowerUp;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Enums.ViewStyles;
import Presentation.Views.Frames.Helpers.BackButton;
import Presentation.Views.Frames.Helpers.CustomErrorDialog;
import Presentation.Views.Frames.Helpers.Title;
import Presentation.Views.Frames.Helpers.UserButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class that represents the main view of the game.
 */
public class GameView extends JFrame {

    /**
     * Constant representing the command for the "RESUME_GAME" button.
     */
    public static final String BACK_BUTTON = "RESUME_GAME";

    /**
     * Constant representing the command for the "START_GAME" button.
     */
    public static final String END_GAME_BUTTON = "START_GAME";

    /**
     * Constant representing the command for the "USER_BUTTON" button.
     */
    public static final String USER_BUTTON = "USER_BUTTON";

    /**
     * Constant representing the command for the "COFFEE_CLICK" button.
     */
    public static final String COFFEE_CLICK = "COFFEE_CLICK";

    // Declaracions pels butons per defecte
    /**
     * Necessary button to go back to the previous view
     */
    private JButton backButton;

    /**
     * Necessary button to finalise the game.
     */
    private JButton endGameButton;

    /**
     * Necessary button to go to the user's profile.
     */
    private JButton profileButton;

    // Declaracions necessaries per l'actualització de la taula de informació dels generadors
    /**
     * Array of String that saves the titles to be shown on the table of generators.
     */
    private final String[] columnNames = {"Nom", "Quantitat", "Producció unitat", "Producció total", "% Producció global"};

    /**
     * Arraylist of arrays of Strings that handles the updated information of the generators.
     */
    private ArrayList<String[]> productionData;

    /**
     * Necessary for the creation of the table of generators.
     */
    DefaultTableModel tableData = new DefaultTableModel(columnNames, 0);

    // Declaracions per el recompte del botó de cafés.
    /**
     * Button activated everytime a coffee has been clicked.
     */
    private JButton clickCoffeeButton;

    /**
     * Label to be updated repeatedly with the number of coffees owned by the user.
     */
    private JLabel numberCoffeesLabel;

    /**
     * Label to be updated repeatedly with the number of coffees generated per second.
     */
    private JLabel coffeesPerSecondLabel;

    /**
     * Panel in which the label with the number of coffees per second is stored.
     */
    private JPanel coffeesPerSecondPanel;

    /**
     * Panel in which the label with the number of coffees is stored.
     */
    private JPanel numberCoffeesPanel;

    /**
     * Variable that will store the number of coffees owned by the user.
     */
    private long coffeeCounter;

    /**
     * Variable that will store the number of coffees generated per second
     */
    private long coffeesPerSecond;

    //Declaracions per la llista de power ups
    /**
     * List of the panels containing the power up views.
     */
    private ArrayList<JPanel> powerUpViews;

    /**
     * List of the total power ups of the game.
     */
    private ArrayList<PowerUp> powerUps;

    /**
     * General panel containing the different panels of the power up views.
     */
    private JPanel powerUpsPanel;

    /**
     * Necessary listener for the buttons of the view.
     */
    private ActionListener listener;

    //Declaracions per la llista de power ups
    /**
     * List of the panels containing the generator views.
     */
    private ArrayList<JPanel> generatorsViews;

    /**
     * List of the total generators of the game.
     */
    private ArrayList<Generator> generators;

    /**
     * General panel containing the different panels of the generator views.
     */
    private JPanel generatorsPanel;


    /**
     * Default constructor for the class GameView
     */
    public GameView() {
        productionData = new ArrayList<>();

        coffeeCounter = 0;
        coffeesPerSecond = 0;

        powerUpViews = new ArrayList<>();
        powerUpsPanel = new JPanel();
        powerUpsPanel.setLayout(new BoxLayout(powerUpsPanel, BoxLayout.Y_AXIS));

        generatorsViews = new ArrayList<>();
        generatorsPanel = new JPanel();
        generatorsPanel.setLayout(new BoxLayout(generatorsPanel, BoxLayout.Y_AXIS));
    }

    /**
     * Main method with the different functions called upon to generate the game view.
     *
     * @return Panel with the whole game view.
     */
    public JPanel getGameView() {
        Image originalImage = new ImageIcon("resources/as.jpeg").getImage();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(originalImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBackground(new Color(232, 172, 91));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Creacio de BackButton, EndGameButton i ProfileButton
        mainPanel.add(createTopButtons());

        // Creacio del CoffeClicker, TableProducction, TotalCoffesLabel i CoffesPerSecondLabel
        mainPanel.add(createCoffeePanel());

        // Creacio de GeneratorShop i PowerUpShop
        mainPanel.add(createShopsPanel());

        return mainPanel;
    }

    /**
     * Method in charge of displaying and handling the buttons set at the top of the frame, like the user or back button.
     *
     * @return Component with the buttons set.
     */
    private Component createTopButtons() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        // Back button creation
        backButton = new BackButton();
        backButton.setActionCommand(BACK_BUTTON);
        backButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        // EndGame button creation
        endGameButton = new JButton(Strings.END_GAME.getValue());
        configEndGameButton();


        // Profile button creation
        profileButton = new UserButton();
        profileButton.setActionCommand(USER_BUTTON);
        profileButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Panel general
        p.add(backButton);
        p.add(Box.createRigidArea(new Dimension(15, 0)));
        p.add(endGameButton);
        p.add(Box.createHorizontalGlue());
        p.add(profileButton);

        p.setBorder(new EmptyBorder(5, 20, 5, 20));
        p.setBackground(new Color(175, 130, 96));

        return p;
    }

    /**
     * Method that sets the necessary information for the end game button.
     */
    private void configEndGameButton() {
        endGameButton.setActionCommand(END_GAME_BUTTON);
        endGameButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        endGameButton.setMaximumSize(new Dimension(150, 40));
        endGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        endGameButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        endGameButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());
        endGameButton.setFocusPainted(false);
    }

    /**
     * Method that creates the coffee panel where the button of the coffee is displayed, and any information regarding
     * the amount of coffees and coffees per second.
     *
     * @return Panel with the main coffee button.
     */
    private JPanel createCoffeePanel() {
        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.X_AXIS));
        pMain.add(Box.createRigidArea(new Dimension(50, 0)));
        pMain.setBorder(new EmptyBorder(20, 50, 0, 50));

        JPanel coffeePanel = configCoffeePanel();

        // Crear el boto de clicks de coffees
        clickCoffeeButton = new JButton();
        configClickCoffeeButton();

        // Crear el text amb la quantitat de coffees totals
        numberCoffeesPanel = new JPanel();
        configNumberCoffeesPanel();

        // Crear el text amb els coffees per second que s'estan generant
        coffeesPerSecondPanel = new JPanel();
        configCoffeesPerSecondPanel();

        // Crear la Taula de valors
        JScrollPane tPanel = configTablePanel();

        // Panell amb Botó click coffees i els dos labels
        coffeePanel.add(clickCoffeeButton, BorderLayout.NORTH);
        coffeePanel.add(numberCoffeesPanel, BorderLayout.CENTER);
        coffeePanel.add(coffeesPerSecondPanel, BorderLayout.SOUTH);
        // Panell general de tota la part "central" de la View
        pMain.add(coffeePanel);
        pMain.add(tPanel);

        pMain.setOpaque(false);
        coffeePanel.setOpaque(false);
        return pMain;
    }

    /**
     * Method that configures and returns a JScrollPane containing the styled JTable.
     *
     * @return a JScrollPane containing the JTable with specified configurations.
     */
    private JScrollPane configTablePanel() {
        JScrollPane tPanel =new JScrollPane();
        tPanel.setBackground(new Color(254, 216, 177));
        tPanel.setBorder(BorderFactory.createLineBorder(new Color(175, 130, 96), 2));
        tPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        tPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        JTable tableProduction = new JTable(tableData);
        tableProduction.setAlignmentY(Component.TOP_ALIGNMENT);
        tableProduction.setFont(new Font("Arial", Font.PLAIN, 20));
        tableProduction.setRowHeight(32);
        tableProduction.setOpaque(false);
        setAlternateRowColor(tableProduction);

        tableProduction.getTableHeader().setBackground(new Color(216, 174, 126));
        tableProduction.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));

        tPanel.setViewportView(tableProduction);
        tPanel.getViewport().setOpaque(false);

        return tPanel;
    }

    /**
     * Method that configures the panel that displays the number of coffees produced per second.
     */
    private void configCoffeesPerSecondPanel() {
        coffeesPerSecondPanel.setBackground(new Color(254, 216, 177));
        coffeesPerSecondLabel = new JLabel(String.valueOf(coffeesPerSecond) + " Coffee per second", JLabel.CENTER);
        coffeesPerSecondLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        coffeesPerSecondLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coffeesPerSecondPanel.add(coffeesPerSecondLabel);
    }

    /**
     * Method that configures the panel that displays the total number of coffees produced.
     */
    private void configNumberCoffeesPanel() {
        numberCoffeesPanel.setBackground(new Color(254, 216, 177));
        numberCoffeesLabel = new JLabel(String.valueOf(coffeeCounter) + " Coffes", JLabel.CENTER);
        numberCoffeesLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        numberCoffeesLabel.setBackground(Color.BLUE);
        numberCoffeesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberCoffeesLabel.setOpaque(false);
        numberCoffeesPanel.add(numberCoffeesLabel);
    }

    /**
     * Method that configures the button used to click for producing coffee.
     */
    private void configClickCoffeeButton() {
        clickCoffeeButton.setActionCommand(COFFEE_CLICK);
        ImageIcon coffeeIcon = new ImageIcon("resources/japanese-tea.png");
        Image scaledImage = coffeeIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledCoffeeIcon = new ImageIcon(scaledImage);
        clickCoffeeButton.setIcon(scaledCoffeeIcon);
        clickCoffeeButton.setOpaque(true);
        clickCoffeeButton.setBackground(new Color(254, 216, 177));
        clickCoffeeButton.setBorder(BorderFactory.createLineBorder(new Color(175, 130, 96), 2));
        clickCoffeeButton.setFocusPainted(false);
    }

    /**
     * Method that configures and returns the JPanel for displaying coffee-related content.
     *
     * @return a JPanel with specified configurations for coffee display
     */
    private JPanel configCoffeePanel() {
        JPanel coffeePanel = new JPanel();
        coffeePanel.setLayout(new BorderLayout());
        coffeePanel.setMaximumSize(new Dimension(300, 400));
        coffeePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        coffeePanel.setOpaque(false);
        coffeePanel.setBorder(BorderFactory.createLineBorder(new Color(175, 130, 96), 2));
        return coffeePanel;
    }

    /**
     * Method that creates the panel of the shops, with the generators panel and the power ups panel.
     *
     * @return Panel with the generators panel and the power ups panel.
     */
    private JPanel createShopsPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);

        JPanel panelPowerUps = new JPanel();
        panelPowerUps.setLayout(new BoxLayout(panelPowerUps, BoxLayout.Y_AXIS));
        panelPowerUps.add(new Title(Strings.POWER_UPS_TITLE.getValue()));
        panelPowerUps.setBackground(new Color(254, 216, 177));
        panelPowerUps.setBorder(BorderFactory.createLineBorder(new Color(175, 130, 96), 2));

        JPanel panelGenerators = new JPanel();
        panelGenerators.setLayout(new BoxLayout(panelGenerators, BoxLayout.Y_AXIS));
        panelGenerators.add(new Title(Strings.GENERATORS_TITLE.getValue()));
        panelGenerators.setBackground(new Color(254, 216, 177));
        panelGenerators.setBorder(BorderFactory.createLineBorder(new Color(175, 130, 96), 2));

        JScrollPane pPowerUps = new JScrollPane(powerUpsPanel);
        panelPowerUps.add(pPowerUps);
        JScrollPane pGenerators = new JScrollPane(generatorsPanel);
        panelGenerators.add(pGenerators);

        pPowerUps.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pPowerUps.setPreferredSize(new Dimension(600, 250));

        pGenerators.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pGenerators.setPreferredSize(new Dimension(600, 250));

        p.add(panelPowerUps);
        p.add(panelGenerators);
        p.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        return p;
    }

    /**
     * Method that registrates the controller onto the view and adds listeners for the buttons of the view.
     *
     * @param listener Controller of the game view.
     */
    public void registerController(ActionListener listener) {
        backButton.addActionListener(listener);
        endGameButton.addActionListener(listener);
        profileButton.addActionListener(listener);
        clickCoffeeButton.addActionListener(listener);
        this.listener = listener;
    }

    /**
     * Method that sets the alternate row color for a given JTable.
     *
     * @param table The JTable for which alternate row color is to be set.
     */
    private static void setAlternateRowColor(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JComponent component = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    component.setBackground((row % 2 == 0) ? new Color(255, 242, 215) : new Color(255, 224, 181));
                }

                Border border = BorderFactory.createEmptyBorder(0, 10, 0, 10); // Adjust insets as needed
                component.setBorder(border);

                return component;
            }
        });
    }

    /**
     * method that sets the data for the table.
     *
     * @param data The data to be set in the table, provided as an ArrayList of String arrays.
     */
    public void setTableData(ArrayList<String[]> data) {
        this.productionData = data;
    }

    /**
     * Method that refreshes the table by updating its rows with the latest data.It first removes the existing rows
     * in the table and then adds the new rows based on the updated data.
     */
    public void refreshTable() {
        tableData.setRowCount(0);

        for (String[] rowData : productionData) {
            tableData.addRow(rowData);
        }
    }

    /**
     * Method that sets a specific amount of coffees the user has at the moment.
     *
     * @param coffeeCounter Number of coffees the user has at the moment.
     */
    public void setCoffeeCounter(long coffeeCounter) {
        this.coffeeCounter = coffeeCounter;
    }

    /**
     * Method that sets a specific amount of coffees per second.
     *
     * @param coffeesPerSecond New amount of coffees per second.
     */
    public void setCoffeesPerSecond(long coffeesPerSecond) {
        this.coffeesPerSecond = coffeesPerSecond;
    }

    /**
     * Method that constantly updates the number of coffees the user has.
     */
    public void updateNumberCoffeesPanel(){
        numberCoffeesLabel.setText(String.valueOf(coffeeCounter) + " Coffees");
    }

    /**
     * Method that constantly updates the number of coffees produced by second.
     */
    public void updateCoffeesPerSecondPanel(){
        coffeesPerSecondLabel.setText(String.valueOf(coffeesPerSecond) + " Coffees per second");
    }

    /**
     * Method in charge of creating the panel with the different power up views.
     */
    private void createPowerUpsPanel() {
        powerUpsPanel.removeAll();
        powerUpsPanel.setBackground(new Color(254, 216, 177));

        if (null != powerUps) {
            powerUps.sort(Comparator.comparingInt(PowerUp::getCost));
            for (PowerUp powerUp : powerUps) {
                JPanel powerUpView = powerUpView(powerUp);
                powerUpViews.add(powerUpView);
                powerUpsPanel.add(powerUpView);
            }
        }

        powerUpsPanel.revalidate();
        powerUpsPanel.repaint();
    }

    /**
     * Method in charge of displaying all the information of a specific power up.
     *
     * @param powerUp Power up that's going to be shown.
     * @return panel with the power up view of the specific power up.
     */
    private JPanel powerUpView (PowerUp powerUp) {
        JPanel powerUpPanel = setPanelSizeInfo();

        // Panell de la imatge del power up.
        ImageIcon originalIcon = new ImageIcon(powerUp.getImage());
        JPanel panelPhoto = getImagePanel(originalIcon);
        powerUpPanel.add(panelPhoto, BorderLayout.WEST);

        // Panell del text a mostrar - Acció del powerup i cost.
        JPanel centerPanel = getTextPowerUpPanel(powerUp);
        powerUpPanel.add(centerPanel, BorderLayout.CENTER);

        // Panell del botó per comprar.
        JPanel eastPanel = getBuyPowerUpPanel(powerUp);
        powerUpPanel.add(eastPanel, BorderLayout.EAST);

        powerUpPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return powerUpPanel;
    }

    /**
     * Method that specifies the size and the layout of the panel for the generators and power-ups.
     *
     * @return The panel with the specified size and layout.
     */
    private JPanel setPanelSizeInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.setPreferredSize(new Dimension(550, 100));
        panel.setMaximumSize(new Dimension (550, 100));
        panel.setMinimumSize(new Dimension (550, 100));
        panel.setLayout(new BorderLayout());

        return panel;
    }

    /**
     * Method that retrieves the panel containing the image to be displayed on the view, for either the power up views
     * or the generators views.
     *
     * @param originalIcon The original image icon to be scaled that represents the generator/power up.
     * @return The panel containing the scaled image icon.
     */
    private JPanel getImagePanel(ImageIcon originalIcon) {
        Image scaledImage = originalIcon.getImage().getScaledInstance(78, 78, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledIcon);
        JPanel panelPhoto = new JPanel(new GridLayout(0, 1));
        panelPhoto.setPreferredSize(new Dimension(100, 100));
        panelPhoto.add(label, BorderLayout.CENTER);
        panelPhoto.setBackground(Color.WHITE);

        return panelPhoto;
    }

    /**
     * Method that extracts the text to be displayed in each power up view panel.
     *
     * @param powerUp Power up, which information is being set.
     * @return Panel with the information necessary added.
     */
    private JPanel getTextPowerUpPanel(PowerUp powerUp) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel actionPowerUpLabel = new JLabel("Multiplicador de producció de " + powerUp.getAffects() + " X2");
        centerPanel.add(actionPowerUpLabel);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(250, 100));

        JLabel costLabel = new JLabel(powerUp.getCost() + " càfes");
        if (powerUp.getCost() > coffeeCounter) {
            costLabel.setForeground(Color.RED);
        }
        centerPanel.add(costLabel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30,20,0,0));

        powerUp.setCostLabel(costLabel);

        return centerPanel;
    }

    /**
     * Method that extracts the panel containing the buy button of the specific power up view.
     *
     * @param powerUp Power up which is being created at the moment.
     * @return Panel with the button added.
     */
    private JPanel getBuyPowerUpPanel(PowerUp powerUp) {
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBackground(Color.WHITE);
        JButton buyPowerUpButton = new JButton(Strings.BUY.getValue());

        buyPowerUpButton.setPreferredSize(new Dimension(100, 40));
        buyPowerUpButton.setActionCommand("POWERUP: " + powerUp.getName().toUpperCase());

        return getBuyButtonPanel(eastPanel, buyPowerUpButton);
    }

    /**
     * Method that retrieves the panel containing the buy button.
     *
     * @param eastPanel  The panel to which the buy button panel will be added.
     * @param buyButton  The buy button component.
     * @return The panel containing the buy button and additional space components.
     */
    private JPanel getBuyButtonPanel(JPanel eastPanel, JButton buyButton) {
        buyButton.addActionListener(listener);
        buyButton.setFocusPainted(false);
        buyButton.setBackground(ViewStyles.HOME_BUTTON_BACKGROUND_COLOR.getColor());
        buyButton.setForeground(ViewStyles.HOME_BUTTON_FOREGROUND_COLOR.getColor());

        JPanel northSpace = new JPanel();
        northSpace.setPreferredSize(new Dimension(80, 30));
        northSpace.setBackground(Color.WHITE);

        JPanel southSpace = new JPanel();
        southSpace.setPreferredSize(new Dimension(80, 30));
        southSpace.setBackground(Color.WHITE);

        eastPanel.add(northSpace, BorderLayout.NORTH);
        eastPanel.add(southSpace, BorderLayout.SOUTH);
        eastPanel.add(buyButton, BorderLayout.CENTER);

        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        return eastPanel;
    }

    /**
     * Method that adds the power ups to this class.
     *
     * @param powerUps List with the power ups to be shown in the game view.
     */
    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
        createPowerUpsPanel();
    }

    /**
     * Method that refreshes the power ups cost labels after specific period of times, either to change the label to
     * black because in terms of coffees it could be bought already, or keep it the same if it couldn't.
     */
    public void refreshPowerUpsLabels() {
        for (PowerUp powerUp : powerUps) {
            JLabel costLabel = powerUp.getCostLabel();
            if (costLabel != null) {
                if (powerUp.getCost() <= coffeeCounter) {
                    costLabel.setForeground(Color.BLACK);
                } else {
                    costLabel.setForeground(Color.RED);
                }
            }
        }
    }

    /**
     * Method that makes so a pop-up can be shown when needed to display an error message.
     *
     * @param message Specific message wanted to be displayed.
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
     * Method in charge of removing a power up from the class and from the view, after it has successfully been bought.
     *
     * @param powerUpName Power up that needs to be removed.
     */
    public void updatePowerUpView(String powerUpName) {
        PowerUp powerUpToRemove = null;
        JPanel powerUpViewToRemove = null;

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            if (powerUp.getName().equalsIgnoreCase(powerUpName)) {
                powerUpToRemove = powerUp;
                powerUpViewToRemove = powerUpViews.get(i);
                break;
            }
        }

        if (null != powerUpToRemove && null != powerUpViewToRemove) {
            powerUps.remove(powerUpToRemove);
            powerUpViews.remove(powerUpViewToRemove);
            powerUpsPanel.remove(powerUpViewToRemove);

            // Actualitzem la vista dels powerups
            powerUpsPanel.revalidate();
            powerUpsPanel.repaint();
        }
    }

    /**
     * Method in charge of creating the panel with the different generators views.
     */
    private void createGeneratorsPanel() {
        generatorsPanel.removeAll();
        generatorsPanel.setBackground(new Color(254, 216, 177));

        if (null != generators) {
            for (Generator generator : generators) {
                JPanel generatorView = generatorView(generator);
                generatorsViews.add(generatorView);
                generatorsPanel.add(generatorView);
            }
        }

        generatorsPanel.revalidate();
        generatorsPanel.repaint();
    }

    /**
     * Method in charge of displaying all the information of a specific generator.
     *
     * @param generator Generator that's going to be shown.
     * @return panel with the generator view of the specific generator.
     */
    private JPanel generatorView(Generator generator) {
        JPanel generatorPanel = setPanelSizeInfo();

        // Panell de la imatge del power up.
        ImageIcon originalIcon = new ImageIcon(generator.getImage());
        JPanel panelPhoto = getImagePanel(originalIcon);
        generatorPanel.add(panelPhoto, BorderLayout.WEST);

        // Panell informació
        JPanel centerPanel = getInfoGeneratorPanel(generator);
        generatorPanel.add(centerPanel, BorderLayout.CENTER);

        // Panell botó comprar generadors
        JPanel eastPanel = getBuyGeneratorPanel(generator);
        generatorPanel.add(eastPanel, BorderLayout.EAST);

        generatorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return generatorPanel;
    }

    /**
     * Method that retrieves a panel with the information needed to be set to every generator.
     *
     * @param generator The generator object for which the information panel is generated.
     * @return A JPanel containing detailed information about the generator.
     */
    private JPanel getInfoGeneratorPanel(Generator generator) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(250, 100));

        JLabel nameLabel = new JLabel(generator.getGeneratorsType());
        centerPanel.add(nameLabel);

        JLabel costLabel = new JLabel("Cost: " + generator.getCost() + " càfes");
        if (generator.getCost() > coffeeCounter) {
            costLabel.setForeground(Color.RED);
        }
        centerPanel.add(costLabel);

        JLabel cookiesLabel = new JLabel("Producció unitat: " + generator.getProduction() + "c/s");
        centerPanel.add(cookiesLabel);

        generator.setCostLabel(costLabel);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25,20,0,0));

        return centerPanel;
    }

    /**
     * Method that extracts the panel containing the buy button of the specific generator view.
     *
     * @param generator Generator which is being created at the moment.
     * @return Panel with the button added.
     */
    private JPanel getBuyGeneratorPanel(Generator generator) {
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBackground(Color.WHITE);
        JButton buyGeneratorButton = new JButton(Strings.BUY.getValue());

        buyGeneratorButton.setPreferredSize(new Dimension(100, 40));
        buyGeneratorButton.setActionCommand("GENERATOR: " + generator.getGeneratorsType().toUpperCase());

        return getBuyButtonPanel(eastPanel, buyGeneratorButton);
    }

    /**
     * Method that adds the generators to this class.
     *
     * @param generators List with the generators to be shown in the game view.
     */
    public void setGenerators(ArrayList<Generator> generators) {
        this.generators = generators;
        createGeneratorsPanel();
    }

    /**
     * Method that refreshes the generators cost labels after specific period of times, either to change the label to
     * black because in terms of coffees it could be bought already, or keep it the same if it couldn't.
     */
    public void refreshGeneratorsLabels() {
        for (Generator generator : generators) {
            JLabel costLabel = generator.getCostLabel();
            if (costLabel != null) {
                if (generator.getCost() <= coffeeCounter) {
                    costLabel.setForeground(Color.BLACK);
                } else {
                    costLabel.setForeground(Color.RED);
                }
            }
        }

    }

    /**
     * Method in charge of updating the view of a generator with the specified name by modifying its cost label and
     * adjusting its text color based on the new cost value and the user's coffees owned.
     *
     * @param generatorName       The name of the generator to update.
     * @param newCostForGenerator The new cost for the generator.
     */
    public void updateGeneratorView(String generatorName, double newCostForGenerator) {
        for (Generator generator : generators) {
            if (generator.getGeneratorsType().equalsIgnoreCase(generatorName)) {
                JLabel costLabel = generator.getCostLabel();
                costLabel.setText("Cost: " + String.format("%.2f", newCostForGenerator) + " càfes");
                generator.setCost(newCostForGenerator);

                if (generator.getCost() < coffeeCounter) {
                    costLabel.setForeground(Color.BLACK);
                } else {
                    costLabel.setForeground(Color.RED);
                }

                costLabel.repaint();
            }
        }
    }
}
