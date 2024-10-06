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

        // Ensure the window is focusable for key events
        gameWindow.getFrame().setFocusable(true);
        gameWindow.getFrame().requestFocusInWindow();
    }

}
