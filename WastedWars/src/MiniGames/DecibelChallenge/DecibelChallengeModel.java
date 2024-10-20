package WastedWars.src.MiniGames.DecibelChallenge;

import WastedWars.src.Model.GameFinishListener;
import WastedWars.src.Model.MiniGame;

import javax.sound.sampled.LineUnavailableException;

public class DecibelChallengeModel implements MiniGame {
    protected static final float WIN_THRESHOLD = 80.0f; // Decibel level required to win
    private boolean isGameOver = false;
    private boolean isWon = false;

    private SoundLevelDetector soundDetector;
    private GameFinishListener gameFinishListener; // Add the listener reference

    public DecibelChallengeModel() {
        try {
            soundDetector = new SoundLevelDetector();
        } catch (LineUnavailableException e) {
            // Handle the exception by notifying the user or logging the error
            System.err.println("Error: Unable to initialize sound detector. Audio system unavailable.");
            // Optionally, set a flag or trigger alternative logic in case of failure
        }
    }

    // Start the game and begin capturing sound
    @Override
    public void startGame() {
        try {
            soundDetector.startCapturing();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetGame() {
        // Stop any ongoing sound capturing first
        if (soundDetector != null) {
            soundDetector.stopCapturing();
        }

        // Reset the game state flags
        isGameOver = false;
        isWon = false;

        // Start capturing sound again
        startGame();

        // Notify listeners that the game has been reset (if necessary)
        if (gameFinishListener != null) {
            gameFinishListener.onGameFinished();
        }
    }

    // Continuously check the sound level to determine win/loss
    public void checkSoundLevel() {
        float currentLevel = soundDetector.getSoundLevel();
        if (currentLevel >= WIN_THRESHOLD) {
            isWon = true;
            isGameOver = true;
            if (gameFinishListener != null) {
                gameFinishListener.onGameFinished(); // Notify listener of win
            }
        }
    }

    public float getSoundLevel() {
        return soundDetector.getSoundLevel();
    }

    @Override
    public boolean isOver() {
        soundDetector.stopCapturing();
        return isGameOver;
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        this.gameFinishListener = listener; // Assign the listener
    }

    @Override
    public boolean isWin() {
        return isWon;
    }
}
