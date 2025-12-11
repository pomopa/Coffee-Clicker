package Business.Entities;


/**
 * Represents a game entity.
 */
public class Game {
    private String gameName;
    private boolean ongoingGame;
    private long numResources;
    private int timeOnGame;
    private String userName;


    /**
     * Default constructor for the class Game, with the ongoing game parameter.
     *
     * @param gameName       The unique identifier of the game.
     * @param ongoingGame  Indicates whether the game is ongoing or not.
     * @param numResources The number of resources available in the game.
     * @param timeOnGame   The duration of time spent on the game.
     * @param userName     The username associated with the game.
     */
    public Game(String gameName, boolean ongoingGame, long numResources, int timeOnGame, String userName) {
        this.gameName = gameName;
        this.ongoingGame = ongoingGame;
        this.numResources = numResources;
        this.timeOnGame = timeOnGame;
        this.userName = userName;
    }

    /**
     * Default constructor for the class Game, without the ongoing game parameter.
     *
     * @param gameName The unique identifier of the game.
     * @param numResources The number of resources available in the game.
     * @param timeOnGame The duration of time spent on the game.
     * @param userName The username associated with the game.
     */
    public Game(String gameName, long numResources, int timeOnGame, String userName) {
        this.gameName = gameName;
        this.ongoingGame = false;
        this.numResources = numResources;
        this.timeOnGame = timeOnGame;
        this.userName = userName;
    }

    /**
     * Checks if the game is ongoing.
     *
     * @return True if the game is ongoing, false otherwise.
     */
    public boolean isOngoingGame() {
        return ongoingGame;
    }

    /**
     * Returns the number of resources available in the game.
     *
     * @return The number of resources available in the game.
     */
    public long getNumResources() {
        return numResources;
    }

    /**
     * Returns the duration of time spent on the game.
     *
     * @return The duration of time spent on the game.
     */
    public int getTimeOnGame() {
        return timeOnGame;
    }

    /**
     * Sets the duration of time spent on the game.
     *
     * @param timeOnGame The duration of time spent on the game.
     */
    public void setTimeOnGame(int timeOnGame) {
        this.timeOnGame = timeOnGame;
    }

    /**
     * Returns the username associated with the game.
     *
     * @return The username associated with the game.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the game name.
     *
     * @return The game name.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the number of resources available in the game.
     *
     * @param numResources The number of resources available in the game.
     */
    public void setNumResources(long numResources) {
        this.numResources = numResources;
    }
}