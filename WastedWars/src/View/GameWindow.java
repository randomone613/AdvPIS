package WastedWars.src.View;

import WastedWars.src.Model.Player;
import WastedWars.src.Model.WastedWarsModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame frame;
    private WastedWarsModel model;
    private Player currentPlayer; // Holds the reference to the current player

    public GameWindow(WastedWarsModel model) {
        this.model = model;
        this.currentPlayer = model.getPlayers().get(0); // Assuming player 1 is the first to play
        initializeWindow();
    }

    private void initializeWindow() {
        frame = new JFrame("Wasted Wars - Mini Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the frame undecorated before making it visible
        frame.setUndecorated(true);

        // Set the layout to GridBagLayout for more control
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a panel for the top-left (Mini Game placeholder)
        JPanel miniGamePanel = new JPanel();
        miniGamePanel.setBackground(Color.LIGHT_GRAY);
        miniGamePanel.setPreferredSize(new Dimension(
                2 * (Toolkit.getDefaultToolkit().getScreenSize().width) / 3,
                2 * (Toolkit.getDefaultToolkit().getScreenSize().height) / 3
        ));
        miniGamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel miniGameLabel = new JLabel("Mini Game", SwingConstants.CENTER);
        miniGameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        miniGamePanel.add(miniGameLabel);

        // Position the mini-game panel at the top-left (2/3 width and height)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.67;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(miniGamePanel, gbc);

        // Create a panel for the current player (on the right side)
        JPanel currentPlayerPanel = new JPanel(new BorderLayout());
        currentPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Player icon placeholder
        JButton quitGame = new JButton("Quit Game");
        quitGame.setPreferredSize(new Dimension(100, 100));
        quitGame.setBackground(new Color(134, 0, 178)); // Placeholder color

        // Remove the border around the button
        quitGame.setFocusPainted(false); // No focus border when clicked

        // Add ActionListener to the quitGame button
        quitGame.addActionListener(e -> {
            // Confirm before closing the window
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

        // Placeholder for sip points (glass icon)
        JLabel glassIconLabel = new JLabel("Glass Icon Placeholder", SwingConstants.CENTER);
        glassIconLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPlayerPanel.add(glassIconLabel, BorderLayout.SOUTH);

        // Position the current player panel on the right
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.33;
        gbc.weighty = 0.67;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(currentPlayerPanel, gbc);

        // Create a panel for the other players (bottom 1/3 of the screen)
        JPanel otherPlayersPanel = new JPanel(new GridLayout(1, model.getPlayers().size() - 1));
        otherPlayersPanel.setBorder(BorderFactory.createTitledBorder("Other Players"));
        otherPlayersPanel.setPreferredSize(new Dimension(800, 100));

        // Add the other players (excluding the current player) to the panel
        for (Player player : model.getPlayers()) {
            if (!player.equals(currentPlayer)) { // Exclude the current player
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setVisible(true); // Now it's visible
    }

    public JFrame getFrame() {
        return frame;
    }
}
