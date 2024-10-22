package WastedWars.src.Controller;

import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.GameWindow;
import WastedWars.src.View.WastedWarsView;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

public class WastedWarsController {
    private WastedWarsModel model;
    private WastedWarsView view;
    private List<String> miniGames;

    public WastedWarsController(WastedWarsModel model, WastedWarsView view) {
        this.model = model;
        this.view = view;

        this.miniGames = List.of("OrderGame", "Question For A Shot", "Twisted Fingers");//, "Decibel Challenge");

        view.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRandomMiniGame();
            }
        });

        view.getAddPlayerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addPlayer("Player" + (model.getPlayers().size() + 1));
                view.refreshPlayers();
            }
        });

        view.getFrame().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    int result = JOptionPane.showConfirmDialog(view.getFrame(),
                            "Do you really want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });

        view.getFrame().setFocusable(true);
        view.getFrame().requestFocusInWindow();
    }

    private void startRandomMiniGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(miniGames.size());
        String selectedMiniGame = miniGames.get(randomIndex);
        startMiniGame(selectedMiniGame);
    }

    private void startMiniGame(String miniGame) {
        switch (miniGame) {
            case "OrderGame":
                new GameWindow(model, this, "OrderGame");
                break;
            case "Question For A Shot":
                new GameWindow(model, this,"QFAS");
                break;
            case "Twisted Fingers":
                new GameWindow(model,this, "TF");
                break;
                /*
            case "Decibel Challenge":
                new GameWindow(model,this,  "DecibelChallenge");
                break;
                 */
            default:
                JOptionPane.showMessageDialog(view.getFrame(), "Game not available!", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
