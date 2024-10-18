package WastedWars.src.MiniGames.DecibelChallenge;

import WastedWars.src.Model.MiniGame;

import javax.sound.sampled.LineUnavailableException;

public class DecibelChallengeModel implements MiniGame {
    protected static final float WIN_THRESHOLD = 80.0f; // Decibel level required to win
    private boolean isGameOver = false;
    private boolean isWon = false;

    private SoundLevelDetector soundDetector;

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

    }

    // Continuously check the sound level to determine win/loss
    public void checkSoundLevel() {
        float currentLevel = soundDetector.getSoundLevel();
        if (currentLevel >= WIN_THRESHOLD) {
            isWon = true;
            isGameOver = true;
        }
    }

    public float getSoundLevel(){
        return soundDetector.getSoundLevel();
    }

    @Override
    public boolean isOver() {
        soundDetector.stopCapturing();
        return isGameOver;
    }

    @Override
    public boolean isWin() {
        return isWon;
    }
}
