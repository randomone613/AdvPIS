package WastedWars.src.Controller;

import WastedWars.src.Model.Captcha;
import WastedWars.src.View.CaptchaView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CaptchaController {
    private Captcha model;
    private CaptchaView view;

    public CaptchaController(Captcha model, CaptchaView view) {
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
            String pressedKey = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
            model.addPressedKey(pressedKey);
            checkWinCondition();
        }
    }

    private void checkWinCondition() {
        if (model.isGameWon()) {
            view.displayMessage("You Win!");
        }
    }
}
