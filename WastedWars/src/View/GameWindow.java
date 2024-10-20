package WastedWars.src.View;

import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeModel;
import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeView;
import WastedWars.src.MiniGames.OrderGame.OrderGameModel;
import WastedWars.src.MiniGames.OrderGame.OrderGameView;
import WastedWars.src.MiniGames.QFAS.QFASModel;
import WastedWars.src.MiniGames.QFAS.QFASView;
import WastedWars.src.MiniGames.TF.TFView;
import WastedWars.src.Model.Player;
import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.Model.MiniGame;
import WastedWars.src.Controller.WastedWarsController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;

public class GameWindow {
    private JFrame frame;
    private WastedWarsModel model;
    private WastedWarsController controller;
    private Player currentPlayer;
    private JPanel miniGamePanel; // Panel for mini games
    private JPanel currentPlayerPanel;
    private JPanel otherPlayersPanel;
    private JLabel sipPointsLabel;
    private JLabel playerNameLabel;
    private CardLayout cardLayout; // CardLayout for switching mini games
    private Map<String, Component> miniGamesMap; // Store the mini-games by their name
    private int currentTurnIndex = 0; // Index of the current player turn
    private final int TARGET_SIPS = 10; // Target sips to win
    private String currentMiniGame;

    private QFASModel QFASmodel;
    private OrderGameModel OrderGamemodel;
    private DecibelChallengeModel decibelModel;

    private JLabel timerLabel; // Label to display the countdown timer
    private Timer countdownTimer; // Timer for countdown functionality
    private int timeRemaining; // Time remaining in seconds

    // New constructor for specific mini-game
    public GameWindow(WastedWarsModel model, WastedWarsController controller, String miniGame) {
        this.model = model;
        this.controller = controller;
        this.currentPlayer = model.getPlayers().get(currentTurnIndex); // Get the first player
        miniGamesMap = new HashMap<>();  // Initialize the mini-game map
        initializeWindow();
        startSelectedGame(miniGame); // Start the specified mini-game immediately
    }

    private void initializeWindow() {
        frame = new JFrame("Wasted Wars - Mini Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize CardLayout for mini games
        cardLayout = new CardLayout();
        miniGamePanel = new JPanel(cardLayout);

        // Add mini games to the card layout
        QFASmodel = new QFASModel();
        QFASView qfasView = new QFASView(QFASmodel);
        miniGamePanel.add(qfasView, "QFAS"); // Add QFAS mini-game
        miniGamesMap.put("QFAS", qfasView);

        OrderGamemodel = new OrderGameModel();
        OrderGameView orderGameView = new OrderGameView(OrderGamemodel);
        miniGamesMap.put("OrderGame", orderGameView);
        miniGamePanel.add(orderGameView, "OrderGame"); // Add OrderGame mini-game

        TFView tfView = new TFView();
        miniGamesMap.put("TF", tfView);
        miniGamePanel.add(tfView, "TF"); // Add TF mini-game

        decibelModel = new DecibelChallengeModel();
        DecibelChallengeView decibelView = new DecibelChallengeView(decibelModel);
        miniGamesMap.put("DecibelChallenge", decibelView);
        miniGamePanel.add(decibelView, "DecibelChallenge");

        // Position the mini-game panel at the top-left (2/3 width and height)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.67;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(miniGamePanel, gbc);

        // Create a panel for the current player
        currentPlayerPanel = new JPanel(new GridLayout(3, 1)); // Changed to GridLayout with 3 rows
        currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Player icon placeholder
        JButton quitGame = new JButton("Quit Game");
        quitGame.setPreferredSize(new Dimension(100, 100));
        quitGame.setBackground(new Color(210, 77, 255)); // Placeholder color
        quitGame.setFocusPainted(false);

        // Add ActionListener to the quitGame button
        quitGame.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                stopAndResetTimer(); // Stop and reset the countdown timer
                resetGame(); // Reset the game
                frame.dispose(); // Close the game window
            }
        });


        // Add Quit Game button to the first row of the GridLayout
        currentPlayerPanel.add(quitGame);

        // Timer Label
        timerLabel = new JLabel("Time Remaining: 30s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerPanel.add(timerLabel); // Add timer label to the second row

        // Current Player name
        playerNameLabel = new JLabel(currentPlayer.getUsername(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentPlayerPanel.add(playerNameLabel); // Add player name to the third row

        // Placeholder for sip points
        sipPointsLabel = new JLabel("Sip: " + currentPlayer.getSip(), SwingConstants.CENTER);
        sipPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPlayerPanel.add(sipPointsLabel); // Optionally keep or remove, depending on layout preferences

        // Position the current player panel on the right
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.33;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(currentPlayerPanel, gbc);

        // Create a panel for the other players
        otherPlayersPanel = new JPanel(new GridLayout(1, model.getPlayers().size() - 1));
        otherPlayersPanel.setBorder(BorderFactory.createTitledBorder("Other Players"));
        otherPlayersPanel.setPreferredSize(new Dimension(800, 100));

        // Add other players (excluding the current player)
        for (Player player : model.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel playerPanel = new JPanel(new BorderLayout());
                playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Player's name
                JLabel playerName = new JLabel(player.getUsername(), SwingConstants.CENTER);
                playerName.setFont(new Font("Arial", Font.BOLD, 16));
                playerPanel.add(playerName, BorderLayout.CENTER);

                // Sip count placeholder
                JLabel sipCount = new JLabel("Sip: 0", SwingConstants.CENTER); // Placeholder for sip points
                sipCount.setFont(new Font("Arial", Font.PLAIN, 14));
                playerPanel.add(sipCount, BorderLayout.SOUTH);

                otherPlayersPanel.add(playerPanel);
            }
        }

        // Position the other players panel at the bottom
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(otherPlayersPanel, gbc);

        // Display the window
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void startMiniGame() {
        // Stop the previous timer before starting a new one
        stopAndResetTimer(); // Ensure no other timers are running

        Component selectedComponent = getCurrentMiniGameComponent();
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;

            // Reset the game before starting to ensure clean state
            miniGame.resetGame();

            // Dialog for player's turn
            JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + "'s turn! Play the mini-game.");

            // Initialize time remaining and start the countdown
            countdownTimer = new Timer(1000, e -> {
                timeRemaining--;
                updateTimerLabel();

                if (timeRemaining <= 0) {
                    ((Timer) e.getSource()).stop(); // Stop the countdown
                    if (!miniGame.isOver()) {
                        JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " took too long! Mini game lost. You gain 1 sip.");
                        currentPlayer.setSip(currentPlayer.getSip() + 1);
                        nextTurn();
                    }
                }
            });

            countdownTimer.start(); // Start the timer

            // Add a listener for when the game finishes
            miniGame.setGameFinishListener(() -> {
                if (countdownTimer.isRunning()) {
                    countdownTimer.stop();
                }
                processOutcome();
            });
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time Remaining: " + timeRemaining + "s");
    }

    public void startSelectedGame(String miniGame) {
        currentMiniGame = miniGame;  // Track the current mini-game

        // Revalidate and repaint to ensure layout and UI are refreshed
        cardLayout.show(miniGamePanel, miniGame);
        miniGamePanel.revalidate();
        miniGamePanel.repaint();

        startMiniGame(); // Start the mini-game logic
    }

    private void startTurn() {
        String[] miniGameNames = {"OrderGame", "QFAS", "TF", "DecibelChallenge"};

        // Select a random mini-game
        String selectedGame = miniGameNames[new Random().nextInt(miniGameNames.length)];
        currentMiniGame = selectedGame;  // Track the current mini-game

        // Show the selected mini-game immediately
        cardLayout.show(miniGamePanel, selectedGame);
        frame.revalidate();  // Ensure the frame refreshes
        frame.repaint();

        // Start the selected mini-game
        startMiniGame();
    }

    public void processOutcome() {
        Component selectedComponent = getCurrentMiniGameComponent();  // Get the active mini-game

        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;

            // Check if the mini-game is over
            if (miniGame.isOver()) {
                // Check win condition
                if (miniGame.isWin()) {
                    JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " wins the mini game! Other players gain 1 sip.");
                    for (Player player : model.getPlayers()) {
                        if (!player.equals(currentPlayer)) {
                            player.setSip(player.getSip()+1);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " loses the mini game! They gain 1 sip.");
                    currentPlayer.setSip(currentPlayer.getSip()+1);
                }
                frame.repaint();
            }

            // Move to the next player's turn
            nextTurn();

            // Check for game end
            checkGameEnd();
        }
    }

    private void nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % model.getPlayers().size();
        currentPlayer = model.getPlayers().get(currentTurnIndex);

        // Now update the entire window for the new player turn
        updateWindow();
    }


    private void updateCurrentPlayerPanel() {
        // Update the timer label and current player info
        sipPointsLabel.setText("Sip: " + currentPlayer.getSip());
        playerNameLabel.setText(currentPlayer.getUsername());
        updateTimerLabel(); // Reset or update the timer label if needed

        // Repaint and revalidate the current player panel
        currentPlayerPanel.revalidate();
        currentPlayerPanel.repaint();
    }

    private void updateOtherPlayersPanel() {
        otherPlayersPanel.removeAll(); // Clear the existing panel

        // Add the previous current player to the other players' panel
        for (Player player : model.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel playerPanel = new JPanel(new BorderLayout());
                playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Player's name
                JLabel playerName = new JLabel(player.getUsername(), SwingConstants.CENTER);
                playerName.setFont(new Font("Arial", Font.BOLD, 16));
                playerPanel.add(playerName, BorderLayout.CENTER);

                // Sip count
                JLabel sipCount = new JLabel("Sip: " + player.getSip(), SwingConstants.CENTER);
                sipCount.setFont(new Font("Arial", Font.PLAIN, 14));
                playerPanel.add(sipCount, BorderLayout.SOUTH);

                otherPlayersPanel.add(playerPanel);
            }
        }

        otherPlayersPanel.revalidate(); // Refresh the other players panel
        otherPlayersPanel.repaint();
    }

    public void updateMiniGamePanel(String s){
        cardLayout.show(miniGamePanel, s);
    }

    public void updateWindow() {
        // Update the current player panel
        updateCurrentPlayerPanel();

        // Update the other players panel
        updateOtherPlayersPanel();

        updateMiniGamePanel(currentMiniGame);

        // Show the next mini-game (either random or selected by the user)
        if (controller.getIsRandom()) {
            startTurn(); // Randomly select and start a new mini-game
        } else {
            String[] miniGameOptions = controller.getMiniGames().toArray(new String[0]);
            String selectedGame = (String) JOptionPane.showInputDialog(frame,
                    "Choose a Mini Game", "Choose Mini Game",
                    JOptionPane.QUESTION_MESSAGE, null, miniGameOptions, miniGameOptions[0]);
            startSelectedGame(selectedGame);
        }

        // Reset the timer to 30 seconds for the new player
        timeRemaining = 30; // Reset time
        updateTimerLabel(); // Update the timer label
    }


    // Helper method to get the currently visible mini-game component
    private Component getCurrentMiniGameComponent() {
        return miniGamesMap.get(currentMiniGame);  // Fetch the component based on the current mini-game key
    }

    // Check if any player has reached the target sips
    private void checkGameEnd() {
        for (Player player : model.getPlayers()) {
            if (player.getSip() >= TARGET_SIPS) {
                JOptionPane.showMessageDialog(frame, player.getUsername() + " has reached " + TARGET_SIPS + " sips and loses the game!");
                frame.dispose(); // Close the game window
                resetGame();
                return;
            }
        }
    }

    public void resetGame() {
        stopAndResetTimer(); // Stop and reset the timer when resetting the game
        for (Player player : model.getPlayers()) {
            player.setSip(0); // Reset all players' sip points
        }
        currentPlayer = model.getPlayers().get(0); // Reset to the first player
        currentTurnIndex = 0;
    }

    private void stopAndResetTimer() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop(); // Stop the existing timer if it's running
        }
        timeRemaining = 30; // Reset the time remaining
        updateTimerLabel(); // Update the timer label with the new value
    }

    public JFrame getFrame() {
        return frame;
    }
}
