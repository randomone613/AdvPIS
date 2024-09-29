package WastedWars.src.Controller;

import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.GameWindow;
import WastedWars.src.View.WastedWarsView;

import java.awt.event.*;
import javax.swing.*;

public class WastedWarsController {
    private WastedWarsModel model;
    private WastedWarsView view;

    public WastedWarsController(WastedWarsModel model, WastedWarsView view) {
        this.model = model;
        this.view = view;

        // Add ActionListeners for the mode buttons
        view.getRandomModeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameWindow(model); // Open the new game window
            }
        });

        view.getChooseModeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameWindow(model); // Open the new game window
            }
        });

        // Add player action listener
        view.getAddPlayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addPlayer("Player" + (model.getPlayers().size() + 1));
                view.refreshPlayers();
            }
        });

        // Escape key listener for exit confirmation
        view.getFrame().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Show confirmation dialog
                    int result = JOptionPane.showConfirmDialog(view.getFrame(), "Do you really want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);  // Exit the app
                    }
                }
            }
        });

        view.getFrame().setFocusable(true);
        view.getFrame().requestFocusInWindow();
    }
}
