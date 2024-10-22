package WastedWars.src.MiniGames.DecibelChallenge;

import WastedWars.src.Model.GameFinishListener;
import WastedWars.src.Model.MiniGame;

import javax.sound.sampled.LineUnavailableException;

public class DecibelChallengeModel implements MiniGame {
    protected static final float WIN_THRESHOLD = 80.0f; // Decibel level required to win
    private boolean isGameOver = false;
    private boolean isWon = false;

    private SoundLevelDetector soundDetector;
    private GameFinishListener gameFinishListener;

    public DecibelChallengeModel() {
        try {
            soundDetector = new SoundLevelDetector();
            startGame();
        } catch (LineUnavailableException e) {
            System.err.println("Error: Unable to initialize sound detector. Audio system unavailable.");
        }
    }

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
        if (soundDetector != null) {
            soundDetector.stopCapturing();
        }

        isGameOver = false;
        isWon = false;

        startGame();

        if (gameFinishListener != null) {
            gameFinishListener.onGameFinished();
        }
    }

    public void checkSoundLevel() {
        float currentLevel = soundDetector.getSoundLevel();
        if (currentLevel >= WIN_THRESHOLD) {
            isWon = true;
            isGameOver = true;
            if (gameFinishListener != null) {
                gameFinishListener.onGameFinished();
            }
        }
    }

    public float getSoundLevel() {
        return soundDetector.getSoundLevel();
    }

    @Override
    public boolean isOver() {
        if (isGameOver) {
            soundDetector.stopCapturing();
        }
        return isGameOver;
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        this.gameFinishListener = listener;
    }

    @Override
    public boolean isWin() {
        return isWon;
    }
}
