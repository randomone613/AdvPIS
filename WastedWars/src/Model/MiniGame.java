package WastedWars.src.Model;

public interface MiniGame {
    void startGame();
    void resetGame();
    boolean isWin(); // Returns true if the player wins the mini game
    boolean isOver();
}
