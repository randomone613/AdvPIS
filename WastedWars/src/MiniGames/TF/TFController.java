package WastedWars.src.MiniGames.TF;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TFController {
    private TFModel model;
    private TFView view;
    private boolean gameOver = false; // To track game end state
    private boolean win;

    public TFController(TFModel model, TFView view) {
        this.model = model;
        this.view = view;

        // Initialize the view with keys and add key listeners
        initialize();
    }

    private void initialize() {
        view.addKeyListener(new KeyPressListener());
        updateView();
    }

    public void updateView() {
        view.resetKeys(); // Reset any previous highlights
        for (String key : model.getRequiredKeys()) {
            boolean isRightHand = model.isRightHand(key);
            view.highlightKey(key, isRightHand); // Highlight required keys
        }
    }

    private class KeyPressListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver) {
                return; // If the game is over, no further input is processed
            }

            String pressedKey = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();

            // If the key is correct but has already been pressed, do nothing
            if (model.getPressedKeys().contains(pressedKey)) {
                return; // Ignore repeated key presses
            }

            // If the pressed key is not part of the required keys, the game is lost
            if (!model.getRequiredKeys().contains(pressedKey)) {
                view.displayMessage("You Lose!"); // Display loss message
                gameOver = true; // Mark the game as over
                win = false;
                view.notifyGameFinish();
                return;
            }

            // Add the pressed key to the model
            model.addPressedKey(pressedKey);

            // Update the view after each key press
            updateView();

            // Check if all required keys are held down simultaneously
            checkWinCondition();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (gameOver) {
                return;
            }

            String releasedKey = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
            // Remove the released key from pressedKeys
            model.removePressedKey(releasedKey);

            // Recheck the state of the game if a key is released
            checkWinCondition();
        }
    }

    private void checkWinCondition() {
        // Only win if all required keys are currently pressed
        if (model.areAllKeysPressed()) {
            view.displayMessage("You Win!");
            gameOver = true;
            win = true;
            view.notifyGameFinish();
        }
    }


    public boolean getWin(){
        return win;
    }
    public void setWin(boolean b){
        win = b;
    }

    public boolean getGameOver(){
        return gameOver;
    }
    public void setGameOver(boolean b){
        gameOver = b;
    }
}
