package WastedWars.src.Model;

/**
 * Interface representing a mini-game in the Wasted Wars application.
 * Each mini-game must implement the methods defined in this interface.
 */
public interface MiniGame {
    void startGame();
    void resetGame();
    boolean isWin();
    boolean isOver();

    /**
     * Sets a listener that will be notified when the game finishes.
     * @param listener The GameFinishListener to be set.
     */
    void setGameFinishListener(GameFinishListener listener);
}
