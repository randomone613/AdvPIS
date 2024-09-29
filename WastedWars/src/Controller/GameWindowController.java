package WastedWars.src.Controller;

import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.GameWindow;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindowController {
    private WastedWarsModel model;
    private GameWindow gameWindow;

    public GameWindowController(WastedWarsModel model, GameWindow gameWindow) {
        this.model = model;
        this.gameWindow = gameWindow;

        // Adding KeyListener to the game window
        gameWindow.getFrame().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    handleEscapeKey();
                }
            }
        });

        // Ensure the window is focusable for key events
        gameWindow.getFrame().setFocusable(true);
        gameWindow.getFrame().requestFocusInWindow();
    }

    // Handle the ESC key press to confirm exit
    private void handleEscapeKey() {
        int result = JOptionPane.showConfirmDialog(
                gameWindow.getFrame(),
                "Do you really want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            gameWindow.getFrame().dispose();  // Close the game window
        }
    }
}
