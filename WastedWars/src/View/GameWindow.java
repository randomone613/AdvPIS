package WastedWars.src.View;

import WastedWars.src.Model.Player;
import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.Model.MiniGame;
import WastedWars.src.MiniGames.SampleMiniGame; // Import your sample mini game class

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameWindow {
    private JFrame frame;
    private WastedWarsModel model;
    private Player currentPlayer;
    private JPanel miniGamePanel; // Panel for mini games
    private CardLayout cardLayout; // CardLayout for switching mini games
    private int currentTurnIndex = 0; // Index of the current player turn
    private final int TARGET_SIPS = 10; // Target sips to win

    public GameWindow(WastedWarsModel model) {
        this.model = model;
        this.currentPlayer = model.getPlayers().get(currentTurnIndex); // Get the first player
        initializeWindow();
        startTurn(); // Start the first turn
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
        miniGamePanel.add(new SampleMiniGame(), "SampleMiniGame");
        // Add other mini games here, e.g. miniGamePanel.add(new AnotherMiniGame(), "AnotherMiniGame");

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
        JPanel currentPlayerPanel = new JPanel(new BorderLayout());
        currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Player icon placeholder
        JButton quitGame = new JButton("Quit Game");
        quitGame.setPreferredSize(new Dimension(100, 100));
        quitGame.setBackground(new Color(134, 0, 178)); // Placeholder color
        quitGame.setFocusPainted(false);

        // Add ActionListener to the quitGame button
        quitGame.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose(); // Close the game window
            }
        });

        currentPlayerPanel.add(quitGame, BorderLayout.NORTH);

        // Current Player name
        JLabel playerNameLabel = new JLabel(currentPlayer.getUsername(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentPlayerPanel.add(playerNameLabel, BorderLayout.CENTER);

        // Placeholder for sip points
        JLabel sipPointsLabel = new JLabel("Sip: " + currentPlayer.getSip(), SwingConstants.CENTER);
        sipPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPlayerPanel.add(sipPointsLabel, BorderLayout.SOUTH);

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
        JPanel otherPlayersPanel = new JPanel(new GridLayout(1, model.getPlayers().size() - 1));
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

    private void startTurn() {
        // Select a random mini game
        String[] miniGameNames = {"SampleMiniGame", /* other mini game names here */ };
        String selectedGame = miniGameNames[new Random().nextInt(miniGameNames.length)];
        cardLayout.show(miniGamePanel, selectedGame); // Show the selected mini game

        // Start the selected mini game
        MiniGame miniGame = (MiniGame) miniGamePanel.getComponent(0); // Assuming the first game is the selected one
        miniGame.startGame();

        // Show current player's turn in a dialog
        JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + "'s turn! Play the mini game.");
    }

    // Method to process the outcome of the mini game
    public void processOutcome() {
        MiniGame miniGame = (MiniGame) miniGamePanel.getComponent(0); // Get the active mini game

        // Update the sip counts based on the outcome
        if (miniGame.isWin()) {
            // Current player wins
            currentPlayer.addSip(1); // Add sip to current player
            JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " wins the mini game! They gain 1 sip.");
            for (Player player : model.getPlayers()) {
                if (!player.equals(currentPlayer)) {
                    player.addSip(1); // Add sip to other players
                }
            }
        } else {
            // Current player loses
            currentPlayer.addSip(1); // Add sip to current player
            JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " loses the mini game! They gain 1 sip.");
        }

        // Check for game end
        checkGameEnd();

        // Move to the next player's turn
        currentTurnIndex = (currentTurnIndex + 1) % model.getPlayers().size();
        currentPlayer = model.getPlayers().get(currentTurnIndex);
        startTurn(); // Start the next turn
    }

    // Check if any player has reached the target sips
    private void checkGameEnd() {
        for (Player player : model.getPlayers()) {
            if (player.getSip() >= TARGET_SIPS) {
                JOptionPane.showMessageDialog(frame, player.getUsername() + " has reached " + TARGET_SIPS + " sips and wins the game!");
                frame.dispose(); // Close the game window
                return;
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
