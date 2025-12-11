package Presentation.Views.Frames;

import Business.Entities.Game;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.Helpers.BackButton;
import Presentation.Views.Frames.Helpers.CustomErrorDialog;
import Presentation.Views.Frames.Helpers.UserButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Represents the StatisticsView frame, which displays statistics and graphs based on user and game data.
 */
public class StatisticsView extends JFrame {

    /**
     * Represents the action command for the back button.
     */
    public static final String BACK_BUTTON = "BACK_BUTTON";

    /**
     * Represents the action command for the graph button.
     */
    public static final String GRAPH_BUTTON = "GRAPH_BUTTON";

    /**
     * Represents the action command for the user button.
     */
    public static final String USER_BUTTON = "USER_BUTTON";

    /**
     * Necessary button to get to the user's view.
     */
    private JButton userButton;

    /**
     * Necessary button to get to the previous view.
     */
    private JButton backButton;

    /**
     * Necessary button to generate the graphic of a specific user's game.
     */
    private JButton generateGraphButton;

    /**
     * Information related to all the users who have registered.
     */
    private JComboBox<String> usersComboBox;

    /**
     * Information related to the games of a specific user.
     */
    private JComboBox<String> gamesComboBox;

    /**
     * String necessary to store the selected user.
     */
    private String selectedUser;

    /**
     * String necessary to store the selected game.
     */
    private String selectedGame;

    /**
     * List with the users registered.
     */
    private  ArrayList<String> userNames;

    /**
     * List with all the games from user registered.
     */
    private ArrayList<Game> allGames;

    /**
     * List with the different times in which the information has been updated, every 1 minute.
     */
    private ArrayList<Integer> time;

    /**
     * List with the different number coffees the user owned at the specific times saved in theTime list.
     */
    private ArrayList<Long> numCoffee;

    /**
     * Panel necessary to contain the graphic.
     */
    private JPanel graphPanel;


    /**
     * Default constructor for the class Statistics View.
     */
    public StatisticsView() {
        usersComboBox = new JComboBox<>();
        gamesComboBox = new JComboBox<>();
        selectedGame = null;
    }

    /**
     * Returns the main panel of the StatisticsView frame.
     * @return The JPanel representing the StatisticsView frame.
     */
    public JPanel getStatisticsView () {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(getTopPanel(), BorderLayout.NORTH);
        mainPanel.add(statisticsPanel(), BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * Creates the statistics panel.
     * @return The JPanel containing the statistics.
     */
    private JPanel statisticsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(graphic(),BorderLayout.CENTER);
        p.add(selectionButtons(), BorderLayout.SOUTH);
        p.setBorder(new EmptyBorder(10, 100, 0, 100));
        p.setBackground(Color.WHITE);
        return p;
    }

    /**
     * Creates the selection buttons panel.
     * @return The JPanel containing the selection buttons.
     */
    private JPanel selectionButtons() {
        JPanel p = new JPanel(new BorderLayout());

        usersComboBox.setPreferredSize(new Dimension(300, 30));
        usersComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        usersComboBox.setBackground(Color.WHITE);
        gamesComboBox.setPreferredSize(new Dimension(300, 30));
        gamesComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        gamesComboBox.setBackground(Color.WHITE);

        generateGraphButton  = new JButton("Generar gràfic");
        generateGraphButton.setBackground(Color.BLACK);
        generateGraphButton.setFont(new Font("Arial", Font.PLAIN, 14));
        generateGraphButton.setForeground(Color.WHITE);
        generateGraphButton.setActionCommand(GRAPH_BUTTON);

        p.add(generateGraphButton, BorderLayout.CENTER);
        p.add(usersComboBox, BorderLayout.WEST);
        p.add(gamesComboBox, BorderLayout.EAST);

        p.setBorder(new EmptyBorder(10, 200, 100, 200));
        p.setBackground(Color.WHITE);
        return p;
    }

    /**
     * Generates the graph panel.
     * @return The JPanel containing the graph.
     */
    public JPanel graphic() {

        graphPanel = new JPanel();
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("resources/Estadistiques.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        ImageIcon icon = new ImageIcon("resources/Estadistiques.jpg");
        imagePanel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        graphPanel.add(imagePanel);
        return graphPanel;
    }

    /**
     * Generates the graph based on time and coffee consumption data.
     */
    public void generateGraph() {
        if (selectedGame != null && time != null && numCoffee != null && !time.isEmpty() && !numCoffee.isEmpty()) {

            graphPanel.removeAll();
            graphPanel.add(printGraph());
            graphPanel.revalidate();
            graphPanel.repaint();
        } else {
            graphPanel.removeAll();

            JPanel imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon icon = new ImageIcon("resources/EstadistiquesError.jpg");
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            ImageIcon icon = new ImageIcon("resources/EstadistiquesError.jpg");
            imagePanel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            graphPanel.add(imagePanel);
            graphPanel.revalidate();
            graphPanel.repaint();
        }
    }

    /**
     * Prints the graph panel with time and coffee consumption data.
     * @return The JPanel representing the graph.
     */
    private JPanel printGraph(){

        int maxTime = time.stream().mapToInt(Integer::intValue).max().orElse(0);
        long maxCoffee = numCoffee.stream().mapToLong(Long::longValue).max().orElse(0);
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(3));
                g.setColor(Color.BLUE);


                g.drawLine(60, getHeight() - 50, getWidth() - 60, getHeight() - 50);
                g.drawLine(60, getHeight() - 50, 60, 50);

                int xText = (getWidth() - g.getFontMetrics().stringWidth("Temps de partida")) / 2;
                g.drawString("Temps de partida (min)", xText, getHeight() - 20);
                g.drawString("Nombre de cafès", 0, 30);

                g.setColor(Color.BLACK);
                int numOfX;
                if (maxTime / 60 >= 10) {
                    numOfX = 10;
                } else {
                    numOfX = maxTime / 60;
                }
                for (int i = 0; i <= numOfX; i++) {
                    int x = 60 + (int) (((double) i / numOfX) * (getWidth() - 100));
                    g.drawString(String.valueOf((int)((double)maxTime * i / numOfX)/60), x, getHeight() - 35);
                }

                for (int i = 0; i <= 10; i++) {
                    int y = getHeight() - 50 - (int) (((double) i / 10) * (getHeight() - 100));
                    g.drawString(String.valueOf((int)((double)maxCoffee * i / 10)), 0, y);
                }

                g.setColor(Color.RED);
                for (int i = 0; i < time.size(); i++) {
                    int x = 60 + (int) (((double) time.get(i) / maxTime) * (getWidth() - 100));
                    int y = getHeight() - 50 - (int) (((double) numCoffee.get(i) / maxCoffee) * (getHeight() - 100));
                    g.fillOval(x - 3, y - 3, 6, 6);
                    if (i > 0) {
                        int prevX = 60 + (int) (((double) time.get(i - 1) / maxTime) * (getWidth() - 100));
                        int prevY = getHeight() - 50 - (int) (((double) numCoffee.get(i - 1) / maxCoffee) * (getHeight() - 100));
                        g.drawLine(prevX, prevY, x, y);
                    }
                }
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 300);
            }
        };
        p.setBackground(Color.WHITE);
        return p;
    }

    /**
     * Creates the top panel with user and back buttons.
     * @return The component representing the top panel.
     */
    private Component getTopPanel() {
        JPanel p = new JPanel(new BorderLayout());
        backButton = new BackButton();
        backButton.setActionCommand(BACK_BUTTON);

        userButton = new UserButton();
        userButton.setActionCommand(USER_BUTTON);

        p.add(userButton, BorderLayout.EAST);
        p.add(backButton, BorderLayout.WEST);

        p.setBorder(new EmptyBorder(10, 15, 10, 25));
        //p.setBackground(Color.GRAY);
        p.setBackground(new Color(175, 130, 96));

        return p;
    }

    /**
     * Registers the controller ActionListener to handle button clicks.
     * @param listener The ActionListener to register.
     */
    public void registerController(ActionListener listener) {
        userButton.addActionListener(listener);
        backButton.addActionListener(listener);
        generateGraphButton.addActionListener(listener);

        usersComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selectedUser = (String) usersComboBox.getSelectedItem();
                ArrayList<String> gamesForUser = new ArrayList<>();

                for (int i = 0; i < allGames.size(); i++) {
                    if (allGames.get(i).getUserName().equals(selectedUser)) {
                        gamesForUser.add(allGames.get(i).getGameName());
                    }
                }

                DefaultComboBoxModel<String> gamesModel = (DefaultComboBoxModel<String>) gamesComboBox.getModel();
                gamesModel.removeAllElements();

                if (!gamesForUser.isEmpty()) {
                    DefaultComboBoxModel<String> gameModel = new DefaultComboBoxModel<>(gamesForUser.toArray(new String[0]));
                    gamesComboBox.setModel(gameModel);
                } else {
                    gamesForUser.addFirst("No té cap partida finalitzada");
                    DefaultComboBoxModel<String> gameModel = new DefaultComboBoxModel<>(gamesForUser.toArray(new String[0]));
                    gamesComboBox.setModel(gameModel);
                }

                selectedGame = (String) gamesComboBox.getSelectedItem();
            }
        });

        gamesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGame = (String) gamesComboBox.getSelectedItem();
            }
        });
    }

    /**
     * Sets the usernames and all games data for the StatisticsView.
     * @param userNames The list of usernames.
     * @param allGames The list of all games.
     */
    public void setInfo(ArrayList<String> userNames,ArrayList<Game> allGames) {
        this.userNames = userNames;
        this.allGames = allGames;

        userNames.addFirst("Selecciona un usuari");
        String[] initialGame = {"Selecciona un usuari primer"};

        DefaultComboBoxModel<String> modelUsers = new DefaultComboBoxModel<>(userNames.toArray(new String[0]));
        usersComboBox.setModel(modelUsers);

        DefaultComboBoxModel<String> initialGameModel = new DefaultComboBoxModel<>(initialGame);
        gamesComboBox.setModel(initialGameModel);
    }

    /**
     * Gets the selected game from the games combo box.
     * @return The selected game.
     */
    public String getSelectedGame() {
        return selectedGame;
    }

    /**
     * Sets the time data for the graph.
     * @param time The list of time data.
     */
    public void setTime(ArrayList<Integer> time) {
        this.time = time;
    }

    /**
     * Sets the coffee consumption data for the graph.
     * @param numCoffee The list of coffee consumption data.
     */
    public void setNumCoffee(ArrayList<Long> numCoffee) {
        this.numCoffee = numCoffee;
    }

    /**
     * Displays an error popup dialog with the given message.
     * @param message The error message to display.
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
     * Sets the selected game.
     * @param selectedGame The selected game to set.
     */
    public void setSelectedGame(String selectedGame) {
        this.selectedGame = selectedGame;
    }

}
