package WastedWars.src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CaptchaView extends JPanel {
    private JButton[] keys; // Buttons representing keys on the keyboard
    private JLabel messageLabel;

    public CaptchaView() {
        setLayout(new GridBagLayout()); // Using GridBagLayout for custom layout
        GridBagConstraints gbc = new GridBagConstraints();

        keys = new JButton[30]; // Create 30 keys for AZERTY layout

        // AZERTY keyboard keys layout
        String[] azertyKeys = {
                "A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P",
                "Q", "S", "D", "F", "G", "H", "J", "K", "L", "M",
                "W", "X", "C", "V", "B", "N", ",", ";", ":", "!"
        };

        // Initialize buttons for keys and add to the panel
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                if (index < azertyKeys.length) {
                    keys[index] = new JButton(azertyKeys[index]);
                    keys[index].setFont(new Font("Arial", Font.BOLD, 18));
                    keys[index].setBackground(Color.LIGHT_GRAY);
                    keys[index].setFocusable(false); // Make the buttons non-focusable

                    // Set GridBag constraints for button placement
                    gbc.gridx = col;
                    gbc.gridy = row;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 1.0; // Equal width for all buttons
                    gbc.weighty = 1.0; // Equal height for all buttons
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
        for (JButton button : keys) {
            if (button.getText().equals(key)) {
                if (isRightHand) {
                    button.setBackground(new Color(255, 77, 77)); // Highlight right-hand keys in red
                } else {
                    button.setBackground(new Color(77, 148, 255)); // Highlight left-hand keys in blue
                }
            }
        }
    }

    public void resetKeys() {
        for (JButton button : keys) {
            button.setBackground(Color.LIGHT_GRAY); // Reset button color
        }
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }

    public void addKeyListener(ActionListener listener) {
        for (JButton button : keys) {
            button.addActionListener(listener);
        }
    }
}
