package WastedWars.src.MiniGames.DecibelChallenge;

import javax.swing.*;
import java.awt.*;

public class CustomProgressBar extends JProgressBar {
    private float winThreshold;

    public CustomProgressBar(int min, int max, float winThreshold) {
        super(min, max);
        this.winThreshold = winThreshold;
        setOrientation(SwingConstants.VERTICAL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int barHeight = getHeight();
        int progressHeight = (int) ((getValue() / (float) getMaximum()) * barHeight);

        g.setColor(getForeground());
        g.fillRect(0, barHeight - progressHeight, getWidth(), progressHeight);

        int thresholdPosition = (int) ((winThreshold / getMaximum()) * barHeight);
        if (thresholdPosition >= 0 && thresholdPosition <= barHeight) {
            g.setColor(Color.RED);
            g.drawLine(0, barHeight - thresholdPosition, getWidth(), barHeight - thresholdPosition);
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());

        String progressText = getValue() + "%";

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(progressText);
        int textHeight = fm.getHeight();

        int x = (getWidth() - textWidth) / 2;

        int y = Math.max(textHeight, barHeight - progressHeight + (textHeight / 2));
        y = Math.min(y, barHeight - 5);

        g2d.drawString(progressText, x, y);

        g2d.dispose();
    }
}
