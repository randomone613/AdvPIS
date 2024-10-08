package WastedWars.src.MiniGames;

import WastedWars.src.Model.MiniGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SampleMiniGame extends JPanel implements MiniGame {
    private boolean winConditionMet = false; // Tracks if the win condition is met

    public SampleMiniGame() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Sample Mini Game", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        // Add logic to play the mini game here
        // For demonstration, we randomly determine win/loss
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            winConditionMet = new Random().nextBoolean(); // Randomly decide win or lose
            JOptionPane.showMessageDialog(this, winConditionMet ? "You Win!" : "You Lose!");
        });
        add(playButton, BorderLayout.SOUTH);
    }

    @Override
    public void startGame() {
        // Logic to start the mini game
        winConditionMet = false; // Reset win condition at the start
    }

    @Override
    public void resetGame() {
        winConditionMet = false; // Reset win condition for replay
    }

    @Override
    public boolean isWin() {
        return winConditionMet; // Return the current win condition
    }
}
