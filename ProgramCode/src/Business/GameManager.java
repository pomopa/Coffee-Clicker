package Business;


import Business.Entities.Game;
import Business.Entities.Generator;
import Business.Entities.PowerUp;
import Business.Exceptions.BusinessException;
import Persistence.MySqlDAO;
import Persistence.Exceptions.PersistenceException;
import Persistence.SqlDAO;
import Presentation.Controllers.GameController;
import java.util.ArrayList;

/**
 * Manages the game logic and interaction with the database.
 */
public class GameManager {

    private SqlDAO sqlDAO;
    // Declaracions pel joc que s'està jugant actualment.
    private String username;
    private Game currentGame;
    private ArrayList<Generator> currentGenerators;
    private ArrayList<PowerUp> currentPowerUps;
    private  GameController gameController;

    // Declaracions per tractar amb els threads
    private volatile boolean running;
    private Thread clockThread;
    private Thread saveGameThread;
    private Thread gameTimeThread;

    // Declaracions amb informació relativa a clicks i producció actual
    private long coffeeClick;
    private int clickIncrease;

    /**
     * Default constructor for the class Game Manager.
     * @param mySqlDAO Class that implements the necessary functions to talk to the database.
     */
    public GameManager(MySqlDAO mySqlDAO){
        sqlDAO = mySqlDAO;
        running = true;
        username = "";
        clickIncrease = 1;
    }

    /**
     * Method that updates the username value with the current user playing the game.
     *
     * @param username String containing the user's name.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method that retrieves the username of the user playing the game.
     *
     * @return Username of the user playing the game.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method in charge of closing the connection with the database.
     *
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void closeConnection() throws BusinessException {
        try {
            sqlDAO.disconnect();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Method that retrieves all the games from the database.
     *
     * @return List of the games stored in the database.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public ArrayList<Game> getAllFinishedGames() throws BusinessException {
        ArrayList<Game> historicGames = new ArrayList<>();
        ArrayList<Game> aux = null;
        try {
            aux = sqlDAO.readAllGames();
            for (Game game : aux) {
                if (!game.isOngoingGame()) {
                    historicGames.add(game);
                }
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

        return historicGames;
    }

    /**
     * Method that retrieves all the usernames stored in the database.
     *
     * @return List of the usernames stored in the database.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public ArrayList<String> getUserNames() throws BusinessException {
        try {
            return sqlDAO.readAllUsers();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Method that retrieves all the information related to the games stored in the Historic table of the database.
     *
     * @return List of games stored in the Historic table of the database.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public ArrayList<Game> getAllHistoric() throws BusinessException {
        try {
            return sqlDAO.readAllHistoric();
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Method that retrieves the current Power Ups of the current game.
     *
     * @return Current Power Ups of the current game.
     */
    public ArrayList<PowerUp> getCurrentPowerUps() {
        return currentPowerUps;
    }

    /**
     * Method that retrieves the current Generators of the current game.
     *
     * @return Current Generators of the current game.
     */
    public ArrayList<Generator> getCurrentGenerators() {
        return currentGenerators;
    }

    /**
     * Method that retrieves and sets the information of the current game.
     *
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void setGameInfo() throws BusinessException {
        try {
            currentGame = sqlDAO.readCurrentGame(username);
            currentGenerators = sqlDAO.readAllGeneratorFromGame(currentGame.getGameName());
            currentPowerUps = sqlDAO.readAllPowerUpFromGame(currentGame.getGameName());
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * Method that repeatedly updates the info of the current game into the database.
     *
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void saveGame() throws BusinessException {
        if (null != currentGame) {
            try {
                sqlDAO.updateGame(currentGame);
                for (Generator g : currentGenerators) {
                    sqlDAO.updateGenerator(currentGame.getGameName(), g);
                }
            } catch (PersistenceException e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    /**
     * Method that finalises the current game that the user was playing.
     *
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public void finalizeGame() throws BusinessException {
        currentGame = null;
        clickIncrease = 1;
        try {
            sqlDAO.endGameCurrentGame(username);
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Method that handles the clock of the game, ticking every 1 second.
     * It increments the game's time by one second in each tick and runs
     * continuously until it is stopped.
     *
     * @throws BusinessException if there is an issue with the business logic
     */
    public void clockGameTime() throws BusinessException{
        gameTimeThread = new Thread(() -> {
            running = true;
            while (running) {

                try {
                    currentGame.setTimeOnGame(currentGame.getTimeOnGame() + 1);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //En aquest cas l'excepció no es controla, donat que per la lògica del joc és necessari
                    // que s'interrompi forçadament, tot es gestiona mitjançat la variable "running".
                }
            }
        });
        gameTimeThread.start();
    }


    /**
     * Method that handles the clock that updates the information of the game into the database every 1 minute.
     *
     * @throws BusinessException If an unexpected error occurs during the clock operation.
     */
    public void clockSaveGame() throws BusinessException {
        saveGameThread = new Thread(() -> {
            running = true;
            while (running) {
                try {
                    saveGame();
                    Thread.sleep(60000);
                } catch (BusinessException | InterruptedException e) {
                    //En aquest cas l'excepció no es controla, donat que per la lògica del joc és necessari
                    // que s'interrompi forçadament, tot es gestiona mitjançat la variable "running".
                }

            }
        });
        saveGameThread.start();
    }

    /**
     * Method that handles the clock that updates the view of the Game every 0,25 seconds.
     *
     *  @throws BusinessException If an unexpected error occurs during the clock operation.
     */
    public void clock() throws BusinessException {
        coffeeClick = 0;
        clickIncrease = getClickIncrease();
        gameController.setCoffeeCounter(currentGame.getNumResources());
        clockThread = new Thread(() -> {
            running = true;
            while (running) {

                try {
                    gameLogic();
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    //En aquest cas l'excepció no es controla, donat que per la lògica del joc és necessari
                    // que s'interrompi forçadament, tot es gestiona mitjançat la variable "running".
                }
            }
        });
        clockThread.start();
    }

    /**
     * Safely stops the running threads.
     *
     * Method that sets the 'running' variable to false just to indicate that the threads should stop.
     * Then, if the 'clockThread', 'saveGameThread', and 'gameTimeThread' are active, it interrupts them.
     * After interrupting the threads, it waits for them to finish by calling 'join()' on each one.
     * Finally, it resets the coffee counter to 0 and the click increase to 1.
     *
     * @throws BusinessException If an unexpected error occurs during the clock operation.
     */
    public void stopThread() throws BusinessException{
        running = false;
        if (clockThread != null && saveGameThread != null && gameTimeThread != null) {
            saveGameThread.interrupt();
            gameTimeThread.interrupt();
            clockThread.interrupt();
            running = false;
            try {
                saveGameThread.join();
                gameTimeThread.join();
                clockThread.join();
            } catch (InterruptedException e) {
                throw new BusinessException(e.getMessage());
            }

        }
        gameController.setCoffeeCounter(0);
        clickIncrease = 1;
    }

    /**
     * Method called upon repeatedly to update the information displayed on the Game View.
     */
    private void gameLogic() {
        if (null != currentGame) {
            long productionGenerators = getProductionFromGenerators()/4;
            currentGame.setNumResources(currentGame.getNumResources() + coffeeClick + productionGenerators);
            coffeeClick = 0;

            gameController.setCoffeeCounter(currentGame.getNumResources());
            gameController.setCoffeesPerSecond(getProductionFromGenerators());

            //Actualitzacions
            gameController.updateTableData(getGeneratorsData());
            gameController.updateNumberCoffeesPanel();
            gameController.updateCoffeesPerSecondPanel();
            gameController.updateGeneratorsTable();
            gameController.updatePowerUps();
            gameController.updateGenerators();
        }
    }

    /**
     * Method that retrieves the production generated by all the generators bought on the current games.
     *
     * @return Production generated by all the generators bought on the current games.
     */
    private long getProductionFromGenerators() {
        long sumProduction = 0;

        for (Generator generator : currentGenerators) {
            sumProduction += (long) (generator.getProduction() * generator.getNumGenerator());
        }

        return sumProduction;
    }

    /**
     * Method that adds up the clicks of the coffee done by the user.
     */
    public void sumCoffeeClick() {
        coffeeClick = (coffeeClick + 1) * clickIncrease;
    }

    /**
     * Method that checks for the possibility of buying a specific Power Up, and does so if possible.
     *
     * @param actionCommand ActionCommand triggered when clicking on the buy button of the Power Up.
     * @return -1 if a mistake has been encountered on the way, 0 if the Power Up could be bought without problem,
     *          1 if it couldn't be bought because there was no generator found to better upgrade,
     *          and 2 if it couldn't be bought because the user doesn't have enough money.
     * @throws BusinessException If an error occurs while retrieving the games due to business logic constraints.
     */
    public int checkPossibilityToBuyPowerUp(String actionCommand) throws BusinessException {
        int behaviour = -1;
        String prefix = "POWERUP: ";
        String powerUpName = actionCommand.substring(prefix.length());

        try {
            for (PowerUp powerUp : currentPowerUps) {
                if (powerUp.getName().equalsIgnoreCase(powerUpName)) {
                    if (powerUp.getCost() <= currentGame.getNumResources()) {
                        if (actionCommand.startsWith("POWERUP: INCREMENT CLICKER")) {
                            clickIncrease++;
                            currentGame.setNumResources(currentGame.getNumResources() - powerUp.getCost());
                            powerUp.setPurchased(true);
                            sqlDAO.updatePowerUpPurchased(currentGame.getGameName(), powerUp);
                            behaviour = 0;
                            break;
                        } else {
                            boolean generatorFound = false;
                            for (Generator generator : currentGenerators) {
                                if (generator.getGeneratorsType().equals(powerUp.getAffects()) && generator.getNumGenerator() > 0) {
                                    generator.setProduction(generator.getProduction() * 2);
                                    powerUp.setPurchased(true);
                                    currentGame.setNumResources(currentGame.getNumResources() - powerUp.getCost());
                                    sqlDAO.updatePowerUpPurchased(currentGame.getGameName(), powerUp);
                                    behaviour = 0;
                                    generatorFound = true;
                                    break;
                                }
                            }

                            if (!generatorFound) {
                                behaviour = 1;
                            }
                        }
                    } else {
                        behaviour = 2;
                    }
                    break;
                }
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e.getMessage());
        }

        return behaviour;
    }

    /**
     * Method that checks for the possibility of buying a specific Generator, and does so if possible.
     *
     * @param actionCommand ActionCommand triggered when clicking on the buy button of the Generator.
     * @return true if possible, false otherwise.
     */
    public boolean checkPossibilityToBuyGenerator(String actionCommand) {
        boolean possible = false;
        String prefix = "GENERATOR: ";
        String generatorName = actionCommand.substring(prefix.length());

        for (Generator generator : currentGenerators) {
            if (generator.getGeneratorsType().equalsIgnoreCase(generatorName)) {
                if (generator.getCost() <= currentGame.getNumResources()) {
                    possible = true;
                    currentGame.setNumResources((long) (currentGame.getNumResources() - generator.getCost()));
                    generator.setNumGenerator(generator.getNumGenerator() + 1);
                    generator.setCost(generator.getBaseCost() * Math.pow(generator.getIncremental(), generator.getNumGenerator()));
                }
                break;
            }
        }

        return possible;
    }

    /**
     * Method that retrieves the updated cost of a specific generator.
     *
     * @param generatorName Name of the Generator that cost needs updating.
     * @return u¡Updated cost of a specific generator.
     */
    public double getNewCostForGenerator(String generatorName) {
        for (Generator generator : currentGenerators) {
            if (generator.getGeneratorsType().equalsIgnoreCase(generatorName)) {
               return generator.getCost();
            }
        }

        return -1;
    }

    /**
     * Method that adapts the current generators into the generators data list to be displayed on the table of the Game View.
     *
     * @return Adapted generators data list to be displayed on the table of the Game View.
     */
    private ArrayList<String[]> getGeneratorsData(){
        ArrayList<String[]> generatorsData = new ArrayList<>();

        long totalProductionGenerators = getProductionFromGenerators();

        for (Generator generator : currentGenerators) {

            if(generator.getNumGenerator()>0){
                String[] data = new String[5];
                data[0] = generator.getGeneratorsType();
                data[1] = String.valueOf(generator.getNumGenerator());
                data[2] = generator.getProduction() + " c/s";
                long totalProductionGenerator = (long) (generator.getNumGenerator() * generator.getProduction());

                data[3] = totalProductionGenerator + " c/s";
                if (totalProductionGenerators > 0) {
                    data[4] = String.valueOf((totalProductionGenerator * 100L) / totalProductionGenerators) + " %";
                } else {
                    data[4] = String.valueOf(0);
                }
                generatorsData.add(data);
            }
        }

        return generatorsData;
    }

    /**
     * Method that sets the Game Controller into the Game Manager.
     *
     * @param gameController Game Controller that will be set.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Method that gathers how many times has the click increase power up been bought.
     *
     * @return The times the click increase power up has been bought.
     */
    private int getClickIncrease() {
        int clicksPowerUpsStillAvailable = 0;
        for (PowerUp powerUp : currentPowerUps) {
            if (powerUp.getType().equalsIgnoreCase("ClickIncrease")) {
                clicksPowerUpsStillAvailable++;
            }
        }

        return 6 - clicksPowerUpsStillAvailable;
    }

    /**
     * Clears the memory by dereferencing objects and invoking the garbage collector.
     * This method releases the memory associated with various game-related objects,
     * resets user-specific variables, and prompts the garbage collector to reclaim
     * any unreachable memory.
     */
    public void cleanRam() {
        currentGame = null;
        if (currentGenerators != null) {
            currentGenerators.clear();
            currentGenerators = null;
        }
        if (currentPowerUps != null) {
            currentPowerUps.clear();
            currentPowerUps = null;
        }
        username = "";
        coffeeClick = 0;
        clickIncrease = 1;
        System.gc();
    }
}
