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
    private boolean isRandomMode = false; // To track if we are in random mode

    public WastedWarsController(WastedWarsModel model, WastedWarsView view) {
        this.model = model;
        this.view = view;

        // Assuming we have a list of available mini-games in the model
        this.miniGames = List.of("OrderGame", "Question For A Shot", "Twisted Fingers", "Decibel Challenge"); // List of mini-games

        // Random Mode button action
        view.getRandomModeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRandomMode = true;
                startRandomMiniGame();
            }
        });

        // Choose Mode button action
        view.getChooseModeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRandomMode = false;
                chooseMiniGame();
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

    // Start a random mini-game
    private void startRandomMiniGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(miniGames.size());
        String selectedMiniGame = miniGames.get(randomIndex);
        startMiniGame(selectedMiniGame);
    }

    // Open a dialog to choose a mini-game
    private void chooseMiniGame() {
        String[] miniGameOptions = miniGames.toArray(new String[0]); // Convert to array for JComboBox

        // Show a dialog with a dropdown (JComboBox) for mini-game selection
        String selectedGame = (String) JOptionPane.showInputDialog(view.getFrame(),
                "Choose a Mini Game", "Choose Mini Game",
                JOptionPane.QUESTION_MESSAGE, null, miniGameOptions, miniGameOptions[0]);

        if (selectedGame != null) {
            startMiniGame(selectedGame);
        }
    }

    // Start the chosen mini-game
    private void startMiniGame(String miniGame) {
        switch (miniGame) {
            case "OrderGame":
                new GameWindow(model, this, "OrderGame");
                System.out.println("Starting mini-game: " + miniGame);
                break;
            case "Question For A Shot":
                new GameWindow(model, this,"QFAS"); // Match the key used in CardLayout
                System.out.println("Starting mini-game: " + miniGame);
                break;
            case "Twisted Fingers":
                new GameWindow(model,this, "TF"); // Use "TF" instead of "Twisted Fingers"
                System.out.println("Starting mini-game: " + miniGame);
                break;
            case "Decibel Challenge":
                new GameWindow(model,this,  "DecibelChallenge");
                System.out.println("Starting mini-game: " + miniGame);
                break;
            default:
                JOptionPane.showMessageDialog(view.getFrame(), "Game not available!", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public boolean getIsRandom(){
        return isRandomMode;
    }

    public List<String> getMiniGames(){
        return miniGames;
    }
}
