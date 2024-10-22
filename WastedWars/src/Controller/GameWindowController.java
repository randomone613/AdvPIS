package WastedWars.src.Controller;

import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.GameWindow;

/**
 * Controller for handling interactions between the game model and the game window.
 */
public class GameWindowController {
    private WastedWarsModel model;
    private GameWindow gameWindow;

    /**
     * Constructs a GameWindowController with the specified model and game window.
     * @param model The WastedWarsModel instance containing game data.
     * @param gameWindow The GameWindow instance for displaying the game.
     */
    public GameWindowController(WastedWarsModel model, GameWindow gameWindow) {
        this.model = model;
        this.gameWindow = gameWindow;

        gameWindow.getFrame().setFocusable(true);
        gameWindow.getFrame().requestFocusInWindow();
    }

}
