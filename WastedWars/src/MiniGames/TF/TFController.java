package WastedWars.src.MiniGames.TF;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
