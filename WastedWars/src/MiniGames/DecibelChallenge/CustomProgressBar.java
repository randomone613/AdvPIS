package WastedWars.src.MiniGames.DecibelChallenge;

import javax.swing.*;
import java.awt.*;

public class CustomProgressBar extends JProgressBar {
    private float winThreshold; // The win threshold in decibels

    public CustomProgressBar(int min, int max, float winThreshold) {
        super(min, max);
        this.winThreshold = winThreshold;
        setOrientation(SwingConstants.VERTICAL); // Set the orientation to vertical
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the height of the bar based on the current value
        int barHeight = getHeight();
        int progressHeight = (int) ((getValue() / (float) getMaximum()) * barHeight);

        // Draw the filled portion of the progress bar
        g.setColor(getForeground());
        g.fillRect(0, barHeight - progressHeight, getWidth(), progressHeight);

        // Draw the threshold line
        int thresholdPosition = (int) ((winThreshold / getMaximum()) * barHeight);
        if (thresholdPosition >= 0 && thresholdPosition <= barHeight) {
            g.setColor(Color.RED);
            g.drawLine(0, barHeight - thresholdPosition, getWidth(), barHeight - thresholdPosition);
        }

        // Draw a label to indicate the threshold level
        g.setColor(Color.BLACK);
        g.drawString("Target: " + (int) winThreshold + " dB", 5, barHeight - thresholdPosition - 5);
    }
}