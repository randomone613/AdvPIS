package WastedWars.src.Controller;

import WastedWars.src.Model.TF;
import WastedWars.src.View.TFView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TFController {
    private TF model;
    private TFView view;
    private boolean gameOver = false; // To track game end state

    public TFController(TF model, TFView view) {
        this.model = model;
        this.view = view;

        // Initialize the view with keys and add key listeners
        initialize();
    }

    private void initialize() {
        view.addKeyListener(new KeyPressListener());
        updateView();
    }

    private void updateView() {
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

            // If the pressed key is not part of the required keys, the game is lost
            if (!model.getRequiredKeys().contains(pressedKey)) {
                view.displayMessage("You Lose!");
                gameOver = true; // Mark the game as over
                return;
            }

            // Add the pressed key to the model (if it hasn't already been pressed)
            model.addPressedKey(pressedKey);

            // After each correct key press, check if the game is won
            checkWinCondition();
        }
    }

    private void checkWinCondition() {
        if (model.isGameWon()) {
            view.displayMessage("You Win!");
            gameOver = true; // Mark the game as over
        }
    }
}
