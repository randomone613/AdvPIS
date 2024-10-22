package WastedWars.src.Model;

public interface MiniGame {
    void startGame();
    void resetGame();
    boolean isWin();
    boolean isOver();

    void setGameFinishListener(GameFinishListener listener);
}
