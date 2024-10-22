package WastedWars.src.View;

//import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeModel;
//import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeView;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.Timer;

public class GameWindow {
    private JFrame frame;
    private WastedWarsModel model;
    private WastedWarsController controller;
    private Player currentPlayer;
    private JPanel miniGamePanel;
    private JPanel currentPlayerPanel;
    private JPanel otherPlayersPanel;
    private JLabel sipPointsLabel;
    private JLabel playerNameLabel;
    private CardLayout cardLayout;
    private Map<String, Component> miniGamesMap;
    private int currentTurnIndex = 0;
    private final int TARGET_SIPS = 10;
    private String currentMiniGame;

    private QFASModel QFASmodel;
    private OrderGameModel OrderGamemodel;
    //private DecibelChallengeModel decibelModel;

    private JLabel timerLabel;
    private Timer countdownTimer;
    private int timeRemaining;

    /**
     * Initializes the game window, sets up the layout, and selects the initial mini-game.
     * @param model the game model containing players and game state.
     * @param controller the game controller to handle interactions.
     * @param miniGame the initial mini-game to start.
     */
    public GameWindow(WastedWarsModel model, WastedWarsController controller, String miniGame) {
        this.model = model;
        this.controller = controller;
        this.currentPlayer = model.getPlayers().get(currentTurnIndex);
        miniGamesMap = new HashMap<>();
        initializeWindow();
        startSelectedGame(miniGame);
    }

    /**
     * Initializes the game window layout, creates panels for mini-games, player status, and other players.
     * Sets up the user interface components and makes the window visible.
     */
    private void initializeWindow() {
        frame = new JFrame("Wasted Wars - Mini Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        cardLayout = new CardLayout();
        miniGamePanel = new JPanel(cardLayout);

        QFASmodel = new QFASModel();
        QFASView qfasView = new QFASView(QFASmodel);
        miniGamePanel.add(qfasView, "QFAS");
        miniGamesMap.put("QFAS", qfasView);

        OrderGamemodel = new OrderGameModel();
        OrderGameView orderGameView = new OrderGameView(OrderGamemodel);
        miniGamesMap.put("OrderGame", orderGameView);
        miniGamePanel.add(orderGameView, "OrderGame");

        TFView tfView = new TFView();
        miniGamesMap.put("TF", tfView);
        miniGamePanel.add(tfView, "TF");

        /*
        decibelModel = new DecibelChallengeModel();
        DecibelChallengeView decibelView = new DecibelChallengeView(decibelModel);
        miniGamesMap.put("DecibelChallenge", decibelView);
        miniGamePanel.add(decibelView, "DecibelChallenge");
         */

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.67;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(miniGamePanel, gbc);

        currentPlayerPanel = new JPanel(new GridLayout(3, 1));
        currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton quitGame = new JButton("Quit Game");
        quitGame.setPreferredSize(new Dimension(100, 100));
        quitGame.setBackground(new Color(210, 77, 255));
        quitGame.setFocusPainted(false);

        quitGame.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                stopAndResetTimer();
                resetGame();
                frame.dispose();
            }
        });


        timerLabel = new JLabel("Time Remaining: 30s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerPanel.add(timerLabel);

        currentPlayerPanel.add(quitGame);

        playerNameLabel = new JLabel(currentPlayer.getUsername(), SwingConstants.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentPlayerPanel.add(playerNameLabel);

        sipPointsLabel = new JLabel("Sip: " + currentPlayer.getSip(), SwingConstants.CENTER);
        sipPointsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPlayerPanel.add(sipPointsLabel);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.33;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(currentPlayerPanel, gbc);

        otherPlayersPanel = new JPanel(new GridLayout(1, model.getPlayers().size() - 1));
        otherPlayersPanel.setBorder(BorderFactory.createTitledBorder("Other Players"));
        otherPlayersPanel.setPreferredSize(new Dimension(800, 100));

        for (Player player : model.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel playerPanel = new JPanel(new BorderLayout());
                playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel playerName = new JLabel(player.getUsername(), SwingConstants.CENTER);
                playerName.setFont(new Font("Arial", Font.BOLD, 16));
                playerPanel.add(playerName, BorderLayout.CENTER);

                JLabel sipCount = new JLabel("Sip: 0", SwingConstants.CENTER);
                sipCount.setFont(new Font("Arial", Font.PLAIN, 14));
                playerPanel.add(sipCount, BorderLayout.SOUTH);

                otherPlayersPanel.add(playerPanel);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(otherPlayersPanel, gbc);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Starts a mini-game for the current player, and sets up a countdown timer.
     * This function resets the mini-game state, displays a message to the current player, and handles the countdown timer for the mini-game.
     */
    public void startMiniGame() {
        stopAndResetTimer();

        Component selectedComponent = getCurrentMiniGameComponent();
        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;

            miniGame.resetGame();

            JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + "'s turn! Play the mini-game.");

            countdownTimer = new Timer(1000, e -> {
                timeRemaining--;
                updateTimerLabel();

                if (timeRemaining <= 0) {
                    ((Timer) e.getSource()).stop();
                    if (!miniGame.isOver()) {
                        JOptionPane.showMessageDialog(frame, currentPlayer.getUsername() + " took too long! Mini game lost. You gain 1 sip.");
                        currentPlayer.setSip(currentPlayer.getSip() + 1);
                        nextTurn();
                    }
                }
            });

            countdownTimer.start();

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
        currentMiniGame = miniGame;

        cardLayout.show(miniGamePanel, miniGame);
        miniGamePanel.revalidate();
        miniGamePanel.repaint();

        startMiniGame();
    }

    /**
     * Starts a turn for the next player and randomly selects a new mini-game.
     * This function rotates between players and selects a mini-game for the current player to play.
     */
    private void startTurn() {
        String[] miniGameNames = {"OrderGame", "QFAS", "TF"};//, "DecibelChallenge"};

        String selectedGame = miniGameNames[new Random().nextInt(miniGameNames.length)];
        currentMiniGame = selectedGame;

        cardLayout.show(miniGamePanel, selectedGame);
        frame.revalidate();
        frame.repaint();

        startMiniGame();
    }

    /**
     * Processes the outcome of the mini-game (win or loss).
     * Updates player sips and moves to the next turn.
     */
    public void processOutcome() {
        Component selectedComponent = getCurrentMiniGameComponent();

        if (selectedComponent instanceof MiniGame) {
            MiniGame miniGame = (MiniGame) selectedComponent;

            if (miniGame.isOver()) {
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

            nextTurn();

            checkGameEnd();
        }
    }

    private void nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % model.getPlayers().size();
        currentPlayer = model.getPlayers().get(currentTurnIndex);

        updateWindow();
    }


    private void updateCurrentPlayerPanel() {
        sipPointsLabel.setText("Sip: " + currentPlayer.getSip());
        playerNameLabel.setText(currentPlayer.getUsername());
        updateTimerLabel();

        currentPlayerPanel.revalidate();
        currentPlayerPanel.repaint();
    }

    private void updateOtherPlayersPanel() {
        otherPlayersPanel.removeAll();

        for (Player player : model.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel playerPanel = new JPanel(new BorderLayout());
                playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel playerName = new JLabel(player.getUsername(), SwingConstants.CENTER);
                playerName.setFont(new Font("Arial", Font.BOLD, 16));
                playerPanel.add(playerName, BorderLayout.CENTER);

                JLabel sipCount = new JLabel("Sip: " + player.getSip(), SwingConstants.CENTER);
                sipCount.setFont(new Font("Arial", Font.PLAIN, 14));
                playerPanel.add(sipCount, BorderLayout.SOUTH);

                otherPlayersPanel.add(playerPanel);
            }
        }

        otherPlayersPanel.revalidate();
        otherPlayersPanel.repaint();
    }

    public void updateMiniGamePanel(String s){
        cardLayout.show(miniGamePanel, s);
    }

    /**
     * Updates the game window, refreshing the current player's status and mini-game panel.
     * This function is called at the beginning of a player's turn to reset the display and game state.
     */
    public void updateWindow() {
        updateCurrentPlayerPanel();

        updateOtherPlayersPanel();

        updateMiniGamePanel(currentMiniGame);

        startTurn();

        timeRemaining = 30;
        updateTimerLabel();
    }


    private Component getCurrentMiniGameComponent() {
        return miniGamesMap.get(currentMiniGame);
    }

    /**
     * Checks if the game has reached the end condition, where any player has reached the target number of sips.
     * If a player reaches the target, the game ends and is reset.
     */
    private void checkGameEnd() {
        for (Player player : model.getPlayers()) {
            if (player.getSip() >= TARGET_SIPS) {
                JOptionPane.showMessageDialog(frame, player.getUsername() + " has reached " + TARGET_SIPS + " sips and loses the game!");
                frame.dispose();
                resetGame();
                return;
            }
        }
    }

    public void resetGame() {
        stopAndResetTimer();
        for (Player player : model.getPlayers()) {
            player.setSip(0);
        }
        currentPlayer = model.getPlayers().get(0);
        currentTurnIndex = 0;
    }

    private void stopAndResetTimer() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        timeRemaining = 30;
        updateTimerLabel();
    }

    public JFrame getFrame() {
        return frame;
    }
}
