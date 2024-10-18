package WastedWars.src.MiniGames.DecibelChallenge;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class DecibelChallengeView extends JPanel {
    private DecibelChallengeModel model;
    private CustomProgressBar volumeBar; // Use CustomProgressBar instead of JProgressBar
    private Timer timer;

    public DecibelChallengeView(DecibelChallengeModel model) {
        this.model = model;
        setLayout(new BorderLayout());

        // Initialize the custom volume bar
        volumeBar = new CustomProgressBar(0, 100, DecibelChallengeModel.WIN_THRESHOLD);
        volumeBar.setStringPainted(true);
        volumeBar.setForeground(Color.RED);
        volumeBar.setPreferredSize(new Dimension(300, 50));

        JLabel instructions = new JLabel("Scream loud enough to win!", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.BOLD, 18));

        add(instructions, BorderLayout.NORTH);
        add(volumeBar, BorderLayout.CENTER);
    }

    public void startGame() {
        // Start a timer to update the volume bar
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateVolumeBar();
                model.checkSoundLevel();

                if (model.isOver()) {
                    endGame();
                }
            }
        }, 0, 100); // Update every 100ms
    }

    private void updateVolumeBar() {
        float soundLevel = model.getSoundLevel();
        int progress = (int) Math.min(100, soundLevel); // Convert sound level to a percentage
        volumeBar.setValue(progress);
        volumeBar.setString("Volume: " + progress + " dB");
    }

    private void endGame() {
        timer.cancel();
        if (model.isWin()) {
            JOptionPane.showMessageDialog(this, "You won the Decibel Challenge!");
        } else {
            JOptionPane.showMessageDialog(this, "You lost! Scream louder next time.");
        }
    }

    // Custom progress bar class to add a target threshold line
    private class CustomProgressBar extends JProgressBar {
        private float winThreshold; // The win threshold in decibels

        public CustomProgressBar(int min, int max, float winThreshold) {
            super(min, max);
            this.winThreshold = winThreshold;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Calculate the position of the win threshold
            int barWidth = getWidth();
            int barHeight = getHeight();
            int thresholdPosition = (int) ((winThreshold / getMaximum()) * barWidth);

            // Draw a vertical line to indicate the win threshold
            g.setColor(Color.GREEN);  // Color of the threshold indicator
            g.drawLine(thresholdPosition, 0, thresholdPosition, barHeight);

            // Draw a label to indicate the threshold level
            g.setColor(Color.BLACK);
            g.drawString("Target: " + (int) winThreshold + " dB", thresholdPosition - 30, barHeight / 2);
        }
    }
}