package WastedWars.src.MiniGames.DecibelChallenge;

import javax.swing.*;
import java.awt.*;

public class CustomProgressBar extends JProgressBar {
    private float winThreshold; // The win threshold in decibels

    public CustomProgressBar(int min, int max, float winThreshold) {
        super(min, max);
        this.winThreshold = winThreshold;
        setOrientation(SwingConstants.VERTICAL); // Keep the bar vertical
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

        // Correctly draw the progress percentage upright
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());

        // Create a string for the percentage
        String progressText = getValue() + "%";

        // Measure text to center it
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(progressText);
        int textHeight = fm.getHeight();

        // Calculate the x position (centered horizontally)
        int x = (getWidth() - textWidth) / 2;

        // Ensure y position stays within the bar and doesn't go out of bounds
        int y = Math.max(textHeight, barHeight - progressHeight + (textHeight / 2));
        y = Math.min(y, barHeight - 5); // Prevent text from going below the bar

        // Draw the text upright at the calculated position
        g2d.drawString(progressText, x, y);

        // Dispose of the Graphics2D object
        g2d.dispose();
    }
}
