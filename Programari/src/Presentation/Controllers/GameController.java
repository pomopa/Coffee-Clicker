package Presentation.Controllers;

import Business.Exceptions.BusinessException;
import Business.GameManager;
import Presentation.Views.Frames.Enums.Strings;
import Presentation.Views.Frames.GameView;
import Presentation.Views.ViewException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller class responsible for handling user actions in the game view.
 */
public class GameController implements ActionListener {

    private MainControllerEvent mc;
    private GameView gameView;
    private GameManager gameManager;

    /**
     * Default constructor for the class GameController.
     *
     * @param mc Interface in charge of letting the main controller know when a view needs to be changed onto another.
     * @param gameView View of the game.
     * @param gameManager Manager in charge of the logic, specially when talking with the database is necessary.
     */
    public GameController(MainControllerEvent mc, GameView gameView, GameManager gameManager) {
        this.mc = mc;
        this.gameView = gameView;
        this.gameManager = gameManager;
        this.gameManager.setGameController(this);
    }

    /**
     * Method in charge of the logic when any button is pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case GameView.BACK_BUTTON:
                try {
                    gameManager.saveGame();
                    gameManager.stopThread();
                    mc.switchView(GameView.BACK_BUTTON);
                } catch (BusinessException ex) {
                    ViewException.error(ex.getMessage());
                }
                break;

            case GameView.END_GAME_BUTTON:
                try {
                    gameManager.saveGame();
                    gameManager.finalizeGame();
                    gameManager.stopThread();
                    mc.switchView(GameView.END_GAME_BUTTON);
                } catch (BusinessException ex) {
                    ViewException.error(ex.getMessage());
                }
                break;

            case GameView.USER_BUTTON:
                mc.switchView(GameView.USER_BUTTON);
                break;

            case GameView.COFFEE_CLICK:
                gameManager.sumCoffeeClick();
                break;

            default:
                if (actionCommand.startsWith("POWERUP: ")) {
                    String prefix = "POWERUP: ";
                    String powerUpName = actionCommand.substring(prefix.length());
                    int error = 0;
                    try {
                        error = gameManager.checkPossibilityToBuyPowerUp(actionCommand);
                    } catch (BusinessException ex) {
                        ViewException.error(ex.getMessage());
                    }

                    switch (error) {
                        case 0:
                            gameView.updatePowerUpView(powerUpName);
                            break;

                        case 1:
                            gameView.errorPopUp(Strings.NO_GENERATOR.getValue());
                            break;

                        case 2:
                            gameView.errorPopUp(Strings.NOT_ENOUGH_COFFEES.getValue());
                            break;
                    }
                }

                if (actionCommand.startsWith("GENERATOR: ")) {
                    String prefix = "GENERATOR: ";
                    String generatorName = actionCommand.substring(prefix.length());
                    boolean possible = gameManager.checkPossibilityToBuyGenerator(actionCommand);

                    if (!possible) {
                        gameView.errorPopUp(Strings.NOT_ENOUGH_COFFEES.getValue());
                    } else {
                        gameView.updateGeneratorView(generatorName.toLowerCase(), gameManager.getNewCostForGenerator(generatorName));
                    }
                }

                break;
        }
    }

    /**
     * Method that asks the Game View to set a specific number as the new number of coffees owned.
     * @param coffeeCounter New value for the number of coffees owned.
     */
    public void setCoffeeCounter(long coffeeCounter) {
        gameView.setCoffeeCounter(coffeeCounter);
    }

    /**
     * Method that asks the Game View to set a specific number as the new number of coffees per second generated.
     * @param coffeesPerSecond New value for the number of coffees per second generated.
     */
    public void setCoffeesPerSecond(long coffeesPerSecond) {
        gameView.setCoffeesPerSecond(coffeesPerSecond);
    }

    /**
     * Method that asks the Game View to update visually the number of Coffees currently owned by the user.
     */
    public void updateNumberCoffeesPanel(){
        gameView.updateNumberCoffeesPanel();
    }

    /**
     * Method that asks the Game View to update visually the Coffees per second.
     */
    public void updateCoffeesPerSecondPanel(){
        gameView.updateCoffeesPerSecondPanel();
    }

    /**
     * Method that asks the Game View to update the power ups cost labels.
     */
    public void updatePowerUps() {
        gameView.refreshPowerUpsLabels();
    }

    /**
     * Method that gets the Game View to update the data of the generators for the table.
     *
     * @param generatorsData List with the information to be shown on the table.
     */
    public void updateTableData(ArrayList<String[]> generatorsData) {
        gameView.setTableData(generatorsData);
    }

    /**
     * Method that gets the Game View to update the Generators Table.
     */
    public void updateGeneratorsTable() {
        gameView.refreshTable();
    }

    /**
     * Method that asks the Game View to update the generators cost labels.
     */
    public void updateGenerators() {
        gameView.refreshGeneratorsLabels();
    }

}
