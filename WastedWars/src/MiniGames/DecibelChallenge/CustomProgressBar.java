package WastedWars.src.MiniGames.DecibelChallenge;

import javax.swing.*;
import java.awt.*;

public class CustomProgressBar extends JProgressBar {
    private float winThreshold; // The win threshold in decibels

    public CustomProgressBar(int min, int max, float winThreshold) {
        super(min, max);
        this.winThreshold = winThreshold;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the position of the win threshold relative to the bar's width
        int barWidth = getWidth();
        int thresholdPosition = (int) ((winThreshold / getMaximum()) * barWidth);

        // Draw the threshold line if within bounds
        if (thresholdPosition >= 0 && thresholdPosition <= barWidth) {
            g.setColor(Color.RED);  // Color of the threshold indicator
            g.drawLine(thresholdPosition, 0, thresholdPosition, getHeight());
        }

        // Draw a label to indicate the threshold level
        g.setColor(Color.BLACK);
        g.drawString("Target: " + (int) winThreshold + " dB", thresholdPosition - 30, getHeight() / 2);
    }
}
