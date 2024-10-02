package WastedWars.src.View;

import WastedWars.src.Model.OrderGameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class OrderGameView extends JPanel {
    private OrderGameModel model;
    private JPanel cardPanel;
    private JPanel slotPanel;
    private JLabel orderLabel;
    private JLabel messageLabel; // To display win/loss messages

    private static final int CARD_SIZE = 60;  // Size of each card
    private static final int SLOT_SIZE = 60;  // Size of each slot
    private static final int NUM_CARDS = 5;   // Number of cards

    public OrderGameView(OrderGameModel model) {
        this.model = model;
        setLayout(null);  // Use absolute positioning for card randomness

        // Display whether ascending or descending order
        orderLabel = new JLabel("Order: " + (model.isAscending() ? "Ascending" : "Descending"), SwingConstants.CENTER);
        orderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        orderLabel.setBounds(50, 0, 800, 50);  // Position order label at the top (center)
        add(orderLabel);

        // Message label for win/loss messages
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 50, 800, 30);
        add(messageLabel);

        // Panel for slots (centered on the screen)
        slotPanel = new JPanel();
        slotPanel.setLayout(new GridLayout(1, NUM_CARDS, 10, 10));
        slotPanel.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/5, (Toolkit.getDefaultToolkit().getScreenSize().height)/5, SLOT_SIZE * NUM_CARDS + 40, SLOT_SIZE);  // Center the slot panel
        for (JLabel slot : model.getSlotLabels()) {
            slot.setPreferredSize(new Dimension(SLOT_SIZE, SLOT_SIZE)); // Adjust size of the slot
            slot.setTransferHandler(new ValueImportTransferHandler()); // Enable drag and drop
            slot.setOpaque(true); // Make it easier to see the slots
            slot.setBackground(Color.LIGHT_GRAY); // Slot background
            slot.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            slot.setFont(new Font("Arial", Font.BOLD, 14));
            slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slotPanel.add(slot);
        }
        add(slotPanel);  // Add slot panel in the center

        // Panel for cards (randomly placed around the slots)
        cardPanel = new JPanel(null);  // Absolute positioning inside card panel
        cardPanel.setBounds(0, 100, 2*(Toolkit.getDefaultToolkit().getScreenSize().width)/3, 2*(Toolkit.getDefaultToolkit().getScreenSize().height)/3);  // Set card panel bounds to cover most of the screen
        cardPanel.setOpaque(false);  // Transparent so we can see the background
        add(cardPanel);

        // Randomly position the cards around the screen
        Random random = new Random();
        for (JButton card : model.getCardValues()) {
            card.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE)); // Set fixed size for cards
            card.setTransferHandler(new ValueExportTransferHandler()); // Enable drag and drop
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handler = button.getTransferHandler();
                    handler.exportAsDrag(button, e, TransferHandler.MOVE); // Use MOVE instead of COPY
                }
            });

            // Generate random positions around the slotPanel area
            int xPos, yPos;
            do {
                xPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - CARD_SIZE);
                yPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().height)/2 - CARD_SIZE);
            } while (isOverlappingSlotArea(xPos, yPos));  // Ensure cards aren't overlapping slots

            card.setBounds(xPos, yPos, CARD_SIZE, CARD_SIZE);  // Set random position for the card
            cardPanel.add(card);
        }
    }

    // Helper method to ensure cards don't overlap the slot area
    private boolean isOverlappingSlotArea(int x, int y) {
        int slotX = slotPanel.getX();
        int slotY = slotPanel.getY();
        int slotWidth = slotPanel.getWidth();
        int slotHeight = slotPanel.getHeight();

        // Check if the card position overlaps with the slot area
        return (x + CARD_SIZE > slotX && x < slotX + slotWidth) && (y + CARD_SIZE > slotY && y < slotY + slotHeight);
    }

    // TransferHandler to handle dragging the card's value (text)
    private class ValueExportTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(((JButton) c).getText());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE; // Allow the card to be moved
        }

        @Override
        public void exportDone(JComponent c, Transferable t, int action) {
            if (action == MOVE) {
                ((JButton) c).setVisible(false); // Hide the button after it's moved
            }
        }
    }

    // TransferHandler to handle dropping the card's value onto the slot and enabling dragging from slots
    private class ValueImportTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            try {
                String value = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                JLabel label = (JLabel) support.getComponent();

                // Only allow to drop if the slot is empty
                if (label.getText().isEmpty()) {
                    label.setText(value);
                    label.setTransferHandler(new ValueExportFromSlotHandler()); // Make the slot draggable
                    checkWinCondition(); // Check if all slots are filled
                    return true;
                } else {
                    return false; // If the slot is not empty, don't allow drop
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    // TransferHandler to allow dragging from the slot (similar to card dragging)
    private class ValueExportFromSlotHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            JLabel label = (JLabel) c;
            String text = label.getText();
            return new StringSelection(text);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE; // Allow moving the value from the slot
        }

        @Override
        public void exportDone(JComponent c, Transferable t, int action) {
            if (action == MOVE) {
                JLabel label = (JLabel) c;
                label.setText(""); // Clear the slot after dragging the value out
                messageLabel.setText(""); // Clear message when a card is removed
            }
        }
    }

    // Method to check if the cards in the slots are in the correct order
    private void checkWinCondition() {
        boolean allFilled = true;
        String[] values = new String[NUM_CARDS];
        for (int i = 0; i < NUM_CARDS; i++) {
            JLabel slot = model.getSlotLabels().get(i);
            values[i] = slot.getText();
            if (values[i].isEmpty()) {
                allFilled = false; // At least one slot is empty
                break;
            }
        }

        // If all slots are filled, check for the correct order
        if (allFilled) {
            boolean isCorrectOrder = model.checkOrder(values); // Check if the order is correct
            if (isCorrectOrder) {
                messageLabel.setText("You Win!");
            } else {
                messageLabel.setText("You Lose!");
            }
        }
    }
}
