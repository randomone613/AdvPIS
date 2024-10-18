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
    private String currentMiniGame;

    private QFASModel QFASmodel;
    private OrderGameModel OrderGamemodel;
    private DecibelChallengeModel decibelModel;

    // Original constructor
    public GameWindow(WastedWarsModel model) {
        this.model = model;
        this.currentPlayer = model.getPlayers().get(currentTurnIndex); // Get the first player
        initializeWindow();
        startTurn(); // Start the first turn
    }

    // New constructor for specific mini-game
    public GameWindow(WastedWarsModel model, String miniGame) {
        this.model = model;
        this.currentPlayer = model.getPlayers().get(currentTurnIndex); // Get the first player
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
        miniGamePanel.add(new QFASView(QFASmodel), "QFAS"); // Add QFAS mini-game

        OrderGamemodel = new OrderGameModel();
        miniGamePanel.add(new OrderGameView(OrderGamemodel), "OrderGame"); // Add OrderGame mini-game

        miniGamePanel.add(new TFView(), "TF"); // Add TF mini-game

        decibelModel = new DecibelChallengeModel();
        miniGamePanel.add(new DecibelChallengeView(decibelModel), "DecibelChallenge");

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
        String[] miniGameNames = {"OrderGame", "QFAS", "TF"};

        // Select a random mini-game
        String selectedGame = miniGameNames[new Random().nextInt(miniGameNames.length)];
        currentMiniGame = selectedGame;  // Track the current mini-game

        // Show the selected mini-game
        cardLayout.show(miniGamePanel, selectedGame);

        // Start the selected mini-game
        Component selectedComponent = getCurrentMiniGameComponent();
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;
            miniGame.startGame();  // Start the mini-game logic
        }

        JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + "'s turn! Play the mini-game.");

        if (selectedComponent instanceof MiniGame){
            MiniGame miniGame = (MiniGame) selectedComponent;
            if (miniGame.isOver()){
                processOutcome();
            }
        }
    }

    private void startSelectedGame(String miniGame) {
        currentMiniGame = miniGame;  // Track the current mini-game
        cardLayout.show(miniGamePanel, miniGame);

        // Start the selected mini-game
        Component selectedComponent = getCurrentMiniGameComponent();
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGameInstance = (MiniGame) selectedComponent;
            miniGameInstance.startGame();  // Start the mini-game logic
        }

        JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + "'s turn! Play the mini-game.");
    }


    public void processOutcome() {
        Component selectedComponent = getCurrentMiniGameComponent();  // Get the active mini-game
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;

            // Check win condition
            if (miniGame.isWin()) {
                // Current player wins - other players gain 1 sip
                JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " wins the mini game! Other players gain 1 sip.");
                for (Player player : model.getPlayers()) {
                    if (!player.equals(currentPlayer)) {
                        player.addSip(1);
                    }
                }
            } else {
                // Current player loses - they gain 1 sip
                JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " loses the mini game! They gain 1 sip.");
                currentPlayer.addSip(1);
            }
            frame.repaint();
        }

        // Check for game end
        checkGameEnd();

        // Move to the next player's turn
        currentTurnIndex = (currentTurnIndex + 1) % model.getPlayers().size();
        currentPlayer = model.getPlayers().get(currentTurnIndex);
        startTurn(); // Start the next turn
    }

    // Helper method to get the currently visible mini-game component
    private Component getCurrentMiniGameComponent() {
        for (Component comp : miniGamePanel.getComponents()) {
            if (miniGamePanel.isAncestorOf(comp) && miniGamePanel.getComponentZOrder(comp) == 0) {
                // Compare with currentMiniGame to ensure it's the right component
                if (comp instanceof MiniGame && currentMiniGame.equals(comp.getName())) {
                    return comp;
                }
            }
        }
        return null;
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

    public boolean isMiniGameWon() {
        Component selectedComponent = getCurrentMiniGameComponent();
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;
            return miniGame.isWin();
        }
        return false;
    }

    public JFrame getFrame() {
        return frame;
    }
}
