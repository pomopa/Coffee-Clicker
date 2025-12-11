package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.Entities.Game;
import Business.GameManager;
import Presentation.Views.ViewException;
import Presentation.Views.Frames.StatisticsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller responsible for managing interactions and data flow related to statistics view.
 * This class handles user actions such as navigating back, viewing user statistics, and generating game statistics graphs.
 * It interacts with GameManager to retrieve game data and with MainControllerEvent to switch between views.
 */
public class StatisticsController implements ActionListener {

    private StatisticsView statisticsView;
    private GameManager gameManager;
    private MainControllerEvent mc;

    private ArrayList<Game> allHistoric;

    /**
     * Default constructor for the class Statistics Controller.
     *
     * @param gameManager The GameManager instance for managing game data.
     * @param statisticsView The StatisticsView instance for displaying statistics.
     * @param mc The MainControllerEvent instance for navigating between views.
     */
    public StatisticsController(GameManager gameManager, StatisticsView statisticsView, MainControllerEvent mc) {
        this.gameManager = gameManager;
        this.statisticsView = statisticsView;
        this.mc = mc;
    }

    /**
     * Updates the list of all historical games.
     */
    private void updateAllHistoric(){
        try {
            this.allHistoric = gameManager.getAllHistoric();
        } catch (BusinessException e) {
            ViewException.error(e.getMessage());
        }
    }

    /**
     * Responds to user actions performed in the statistics view.
     * @param e The ActionEvent representing the user action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case StatisticsView.BACK_BUTTON:
                statisticsView.setTime(null);
                statisticsView.setNumCoffee(null);
                statisticsView.setSelectedGame(null);
                statisticsView.generateGraph();
                mc.switchView("BACK_BUTTON");
                break;
            case StatisticsView.USER_BUTTON:
                mc.switchView(StatisticsView.USER_BUTTON);
                break;
            case StatisticsView.GRAPH_BUTTON:
                String selectedGame = statisticsView.getSelectedGame();
                if(selectedGame != null){
                    ArrayList<Integer> time = new ArrayList<>();
                    ArrayList<Long> numCoffee = new ArrayList<>();
                    updateAllHistoric();

                    for (int i = 0; i < allHistoric.size(); i++) {
                        if(allHistoric.get(i).getGameName().equals(selectedGame)){
                            time.add(allHistoric.get(i).getTimeOnGame());
                            numCoffee.add(allHistoric.get(i).getNumResources());
                        }
                    }
                    statisticsView.setTime(time);
                    statisticsView.setNumCoffee(numCoffee);
                    statisticsView.setSelectedGame(selectedGame);
                    statisticsView.generateGraph();

                } else {
                    statisticsView.errorPopUp("Selecciona la informació dels dos camps abans de clicar el botó");
                }
                break;
        }

    }
}
