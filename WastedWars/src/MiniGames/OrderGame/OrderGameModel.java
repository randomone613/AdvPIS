package WastedWars.src.MiniGames.OrderGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderGameModel {
    private List<JButton> cardValues;  // Cards that the player will sort
    private List<JLabel> slotLabels;   // Slots where cards are dropped
    private boolean ascendingOrder;     // Whether the sorting is ascending or descending

    public OrderGameModel() {
        // Initialize game with 5 cards having random values
        cardValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomValue = (int) (Math.random() * 100);  // Random number between 0 and 99
            JButton card = new JButton(String.valueOf(randomValue));
            cardValues.add(card);
        }

        // Initialize 5 empty slots
        slotLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel slot = new JLabel("");
            slot.setHorizontalAlignment(SwingConstants.CENTER);
            slot.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
            slotLabels.add(slot);
        }

        // Randomly decide if the game is ascending or descending
        ascendingOrder = Math.random() > 0.5;
    }

    // Getter for the card values (JButton)
    public List<JButton> getCardValues() {
        return cardValues;
    }

    // Getter for the slot labels (JLabel)
    public List<JLabel> getSlotLabels() {
        return slotLabels;
    }

    // Getter for the order (ascending or descending)
    public boolean isAscending() {
        return ascendingOrder;
    }

    // Check if the slots have been filled correctly and return the result
    public boolean checkOrder(String[] values) {
        List<Integer> slotValues = new ArrayList<>();
        for (String value : values) {
            if (value.isEmpty()) {
                return false; // Slot is empty
            }
            slotValues.add(Integer.parseInt(value));
        }

        // Check if the list is ordered correctly
        List<Integer> sortedValues = new ArrayList<>(slotValues);
        if (ascendingOrder) {
            Collections.sort(sortedValues);
        } else {
            Collections.sort(sortedValues, Collections.reverseOrder());
        }

        return slotValues.equals(sortedValues);  // Compare current slots with sorted version
    }
}
