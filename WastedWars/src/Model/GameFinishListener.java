package WastedWars.src.Model;

/**
 * Interface for listening to game finish events in the Wasted Wars application.
 * Implement this interface to define actions that should be taken when a game finishes.
 */
public interface GameFinishListener {
    void onGameFinished();
}