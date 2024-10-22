package WastedWars.src.MiniGames.OrderGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model for the Order Game, representing the game state and logic.
 */
public class OrderGameModel {
    private List<JButton> cardValues;
    private List<JLabel> slotLabels;
    private boolean ascendingOrder;

    public OrderGameModel() {
        cardValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomValue = (int) (Math.random() * 100);
            JButton card = new JButton(String.valueOf(randomValue));
            cardValues.add(card);
        }

        slotLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JLabel slot = new JLabel("");
            slot.setHorizontalAlignment(SwingConstants.CENTER);
            slot.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
            slotLabels.add(slot);
        }

        ascendingOrder = Math.random() > 0.5;
    }

    public List<JButton> getCardValues() {
        return cardValues;
    }

    public void setCardValues(List<Integer> values) {
        for (int i = 0; i < Math.min(cardValues.size(), values.size()); i++) {
            cardValues.get(i).setText(String.valueOf(values.get(i)));
        }
    }

    public List<JLabel> getSlotLabels() {
        return slotLabels;
    }

    public boolean isAscending() {
        return ascendingOrder;
    }

    /**
     * Checks if the values in the slots are in the correct order.
     * @param values The values to check.
     * @return true if the order is correct, false otherwise.
     */
    public boolean checkOrder(String[] values) {
        List<Integer> slotValues = new ArrayList<>();
        for (String value : values) {
            if (value.isEmpty()) {
                return false;
            }
            slotValues.add(Integer.parseInt(value));
        }

        List<Integer> sortedValues = new ArrayList<>(slotValues);
        if (ascendingOrder) {
            Collections.sort(sortedValues);
        } else {
            Collections.sort(sortedValues, Collections.reverseOrder());
        }

        return slotValues.equals(sortedValues);
    }
}
