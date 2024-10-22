package WastedWars.src.MiniGames.TF;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller for managing the Twisted Fingers (TF) game, handling user interactions
 * and updating the view based on the model's state.
 */
public class TFController {
    private TFModel model;
    private TFView view;
    private boolean gameOver = false;
    private boolean win;

    public TFController(TFModel model, TFView view) {
        this.model = model;
        this.view = view;

        initialize();
    }

    private void initialize() {
        view.addKeyListener(new KeyPressListener());
        updateView();
    }

    /**
     * Updates the view to highlight the required keys for the current game state.
     */
    public void updateView() {
        view.resetKeys();
        for (String key : model.getRequiredKeys()) {
            boolean isRightHand = model.isRightHand(key);
            view.highlightKey(key, isRightHand);
        }
    }

    private class KeyPressListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver) {
                return;
            }

            String pressedKey = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();

            if (model.getPressedKeys().contains(pressedKey)) {
                return;
            }

            if (!model.getRequiredKeys().contains(pressedKey)) {
                view.displayMessage("You Lose!");
                gameOver = true;
                win = false;
                view.notifyGameFinish();
                return;
            }

            model.addPressedKey(pressedKey);

            updateView();

            checkWinCondition();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (gameOver) {
                return;
            }

            String releasedKey = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
            model.removePressedKey(releasedKey);

            checkWinCondition();
        }
    }

    /**
     * Checks if the player has pressed all required keys to win the game.
     */
    private void checkWinCondition() {
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
