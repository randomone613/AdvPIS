package WastedWars.src.MiniGames.TF;

import WastedWars.src.Model.GameFinishListener;
import WastedWars.src.Model.MiniGame;

import javax.swing.*;
import java.awt.*;

public class TFView extends JPanel implements MiniGame {
    private JLabel[] keys; // Labels representing keys on the keyboard
    private JLabel messageLabel;
    private TFController controller;
    private TFModel model;
    private GameFinishListener finishListener;  // Field to store the finish listener

    public TFView() {
        setLayout(new GridBagLayout()); // Using GridBagLayout for custom layout
        GridBagConstraints gbc = new GridBagConstraints();

        keys = new JLabel[30]; // Create 30 labels for AZERTY layout

        // AZERTY keyboard keys layout
        String[] azertyKeys = {
                "A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P",
                "Q", "S", "D", "F", "G", "H", "J", "K", "L", "M",
                "W", "X", "C", "V", "B", "N", ",", ";", ":", "!"
        };

        // Initialize labels for keys and add to the panel
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                if (index < azertyKeys.length) {
                    keys[index] = new JLabel(azertyKeys[index], SwingConstants.CENTER);
                    keys[index].setFont(new Font("Arial", Font.BOLD, 18));
                    keys[index].setOpaque(true); // To allow background color change
                    keys[index].setBackground(Color.LIGHT_GRAY);
                    keys[index].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    keys[index].setPreferredSize(new Dimension(50, 50)); // Size of each key

                    // Set GridBag constraints for label placement
                    gbc.gridx = col;
                    gbc.gridy = row;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 1.0; // Equal width for all labels
                    gbc.weighty = 1.0; // Equal height for all labels
                    gbc.insets = new Insets(5, 5, 5, 5); // Padding

                    add(keys[index], gbc);
                    index++;
                }
            }
        }

        // Message label for win/loss messages
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0; // Reset position for the message
        gbc.gridy = 5; // Place it below the keys
        gbc.gridwidth = 10; // Span all columns
        add(messageLabel, gbc);

        // Legend for key colors
        addLegend(gbc);

        setFocusable(true);
        requestFocusInWindow();

        model = new TFModel(3);
        controller = new TFController(model, this);
    }

    private void addLegend(GridBagConstraints gbc) {
        JLabel leftHandLegend = new JLabel("Left Hand: ");
        leftHandLegend.setForeground(new Color(77, 148, 255));
        gbc.gridx = 0; // Reset position for the legend
        gbc.gridy = 6; // Place it below the message
        gbc.gridwidth = 5; // Span half of the columns
        add(leftHandLegend, gbc);

        JLabel rightHandLegend = new JLabel("Right Hand: ");
        rightHandLegend.setForeground(new Color(255, 77, 77));
        gbc.gridx = 5; // Position on the right half
        gbc.gridwidth = 5; // Span the other half
        add(rightHandLegend, gbc);
    }

    public void highlightKey(String key, boolean isRightHand) {
        for (JLabel label : keys) {
            if (label.getText().equals(key)) {
                if (isRightHand) {
                    label.setBackground(new Color(255, 77, 77)); // Highlight right-hand keys in red
                } else {
                    label.setBackground(new Color(77, 148, 255)); // Highlight left-hand keys in blue
                }
            }
        }
    }

    public void resetKeys() {
        for (JLabel label : keys) {
            label.setBackground(Color.LIGHT_GRAY); // Reset label color
        }
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }

    @Override
    public void startGame() {
        TFModel model = new TFModel(3);
        TFView view = new TFView();
        TFController controller = new TFController(model, view);
    }

    @Override
    public boolean isWin() {
        return controller.getwin();
    }

    @Override
    public boolean isOver(){
        return controller.getGameOver();
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        this.finishListener = listener;  // Store the listener
    }

    // Method to notify that the game is finished
    public void notifyGameFinish() {
        if (finishListener != null) {
            finishListener.onGameFinished();
        }
    }
}
