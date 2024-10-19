package WastedWars.src.Model;

public interface MiniGame {
    void startGame();
    boolean isWin(); // Returns true if the player wins the mini-game
    boolean isOver();

    // Add a method to set a game finish listener
    void setGameFinishListener(GameFinishListener listener);
}
