package WastedWars.src.MiniGames.OrderGame;

import WastedWars.src.MiniGames.OrderGame.OrderGameModel;
import WastedWars.src.Model.MiniGame;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class OrderGameView extends JPanel implements MiniGame {
    private OrderGameModel model;
    private JPanel cardPanel;
    private JPanel slotPanel;
    private JLabel orderLabel;
    private JLabel messageLabel; // To display win/loss messages
    private boolean over = false;

    private static final int CARD_SIZE = 60;  // Size of each card
    private static final int SLOT_SIZE = 60;  // Size of each slot
    private static final int NUM_CARDS = 5;   // Number of cards

    public OrderGameView(OrderGameModel model) {
        this.model = model;

        // Initialize layout
        setLayout(null);  // Using absolute positioning for randomness
        initializeUI();
    }

    private void initializeUI() {
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

        // Initialize slot and card panels
        initializeSlots();
        initializeCards();

        // Force layout to refresh after adding all components
        revalidate();
        repaint();
    }

    private void initializeSlots() {
        // Slot panel setup
        slotPanel = new JPanel();
        slotPanel.setLayout(new GridLayout(1, NUM_CARDS, 20, 20)); // Set horizontal gaps of 20 pixels

        // Set proper bounds for slot panel to avoid overlaps
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int panelWidth = SLOT_SIZE * NUM_CARDS + (20 * (NUM_CARDS - 1));
        int panelHeight = SLOT_SIZE;
        int xPosition = (screenWidth - panelWidth) / 2; // Center horizontally
        int yPosition = 200; // Position lower vertically

        slotPanel.setBounds(xPosition, yPosition, panelWidth, panelHeight);
        slotPanel.setOpaque(true);  // Ensure it's visible (non-transparent)
        slotPanel.setBackground(Color.GRAY);  // Optional: change background for visibility during debugging

        // Add slots
        for (JLabel slot : model.getSlotLabels()) {
            slot.setPreferredSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
            slot.setTransferHandler(new ValueImportTransferHandler());
            slot.setOpaque(true);
            slot.setBackground(Color.LIGHT_GRAY);
            slot.setHorizontalAlignment(SwingConstants.CENTER);
            slot.setFont(new Font("Arial", Font.BOLD, 14));
            slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            slotPanel.add(slot);
        }

        add(slotPanel);  // Add slotPanel BEFORE cardPanel to ensure it's in the background
    }

    private void initializeCards() {
        // Card panel setup
        cardPanel = new JPanel(null);  // Absolute positioning inside card panel
        cardPanel.setBounds(0, 300, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2); // Position below the slots
        cardPanel.setOpaque(false);  // Transparent so slots remain visible

        // Randomly position the cards around the screen
        Random random = new Random();
        for (JButton card : model.getCardValues()) {
            card.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            card.setTransferHandler(new ValueExportTransferHandler());
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    TransferHandler handler = button.getTransferHandler();
                    handler.exportAsDrag(button, e, TransferHandler.MOVE);
                }
            });

            int xPos, yPos;
            // Ensure cards fit on the screen without overlapping the slot panel
            do {
                xPos = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width - CARD_SIZE);
                yPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().height / 2) - CARD_SIZE) + 350; // Ensure the y position starts below the slot panel
            } while (isOverlappingSlotArea(xPos, yPos));  // Prevent overlapping with slots

            card.setBounds(xPos, yPos, CARD_SIZE, CARD_SIZE);  // Set random position for the card
            cardPanel.add(card);
        }

        add(cardPanel);  // Add card panel after slot panel to ensure cards appear above slots
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

    @Override
    public void startGame() {
        // Ensure components are added and the panel is refreshed
        initializeUI();  // Re-initialize the UI elements
        revalidate();    // Ensure the layout is recalculated
        repaint();       // Force the panel to be redrawn
    }

    @Override
    public void resetGame() {
        removeAll();     // Remove all components before resetting
        startGame();     // Start game again
    }

    @Override
    public boolean isWin() {
        return checkWinCondition();
    }

    @Override
    public boolean isOver() {
        return over;
    }

    // Method to check if the game has been won
    private boolean checkWinCondition() {
        String[] slotValues = new String[model.getSlotLabels().size()];
        boolean allFilled = true;

        for (int i = 0; i < model.getSlotLabels().size(); i++) {
            JLabel slot = model.getSlotLabels().get(i);
            if (slot.getText().isEmpty()) {
                allFilled = false;
                break;
            }
            slotValues[i] = slot.getText();
        }

        if (allFilled) {
            if (model.checkOrder(slotValues)) {
                messageLabel.setText("You Win!");
                over = true;
                return true;
            } else {
                messageLabel.setText("You Lose!");
                over = true;
                return false;
            }
        }

        return false;
    }

    // TransferHandler to handle dragging the card's value (text)
    private class ValueExportTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(((JButton) c).getText());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public void exportAsDrag(JComponent comp, InputEvent e, int action) {
            super.exportAsDrag(comp, e, action);
            if (e instanceof MouseEvent) {
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); // Change cursor to move
            }
        }

        @Override
        public Icon getVisualRepresentation(Transferable t) {
            JButton button = new JButton();
            try {
                String cardText = t.getTransferData(DataFlavor.stringFlavor).toString();
                button.setText(cardText);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace(); // Handle exceptions properly
            }
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.setBackground(Color.LIGHT_GRAY); // Set a background color for better visibility
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a border for clarity
            return button.getIcon(); // Return the icon of the button
        }

        @Override
        public void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JButton button = (JButton) source;
                cardPanel.remove(button); // Remove the card from the cardPanel
                cardPanel.revalidate();
                cardPanel.repaint();
            }
        }
    }

    // TransferHandler to handle dropping the card's value (text)
    private class ValueImportTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            JLabel slot = (JLabel) support.getComponent();
            try {
                String value = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                slot.setText(value); // Set the slot text to the value from the card
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace(); // Handle exceptions properly
            }
            return false;
        }
    }
}

/*
package WastedWars.src.View;

import WastedWars.src.Model.OrderGameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        slotPanel.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width) / 5,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 5, SLOT_SIZE * NUM_CARDS + 40, SLOT_SIZE);  // Center the slot panel

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
        cardPanel.setBounds(0, 100, 2 * (Toolkit.getDefaultToolkit().getScreenSize().width) / 3,
                2 * (Toolkit.getDefaultToolkit().getScreenSize().height) / 3);  // Set card panel bounds to cover most of the screen
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
                xPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - CARD_SIZE);
                yPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - CARD_SIZE);
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
            return MOVE;
        }

        @Override
        public void exportAsDrag(JComponent comp, InputEvent e, int action) {
            super.exportAsDrag(comp, e, action);
            if (e instanceof MouseEvent) {
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); // Change cursor to move
            }
        }

        @Override
        public Icon getVisualRepresentation(Transferable t) {
            JButton button = new JButton();
            try {
                String cardText = t.getTransferData(DataFlavor.stringFlavor).toString();
                button.setText(cardText);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace(); // Handle exceptions properly
            }
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.setBackground(Color.LIGHT_GRAY); // Set a background color for better visibility
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a border for clarity
            return button.getIcon(); // Return the icon of the button
        }

        @Override
        public void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JButton button = (JButton) source;
                cardPanel.remove(button); // Remove the card from the cardPanel
                cardPanel.revalidate();    // Refresh the card panel
                cardPanel.repaint();       // Repaint the card panel
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

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace(); // Handle exceptions properly
            }
            return false;
        }
    }

    // TransferHandler to allow dragging from the slot (similar to card dragging)
    private class ValueExportFromSlotHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(((JLabel) c).getText()); // Transfer the text of the slot
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE; // Allow moving
        }

        @Override
        public void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JLabel label = (JLabel) source;
                label.setText(""); // Clear the slot when the value is moved
                label.setTransferHandler(new ValueImportTransferHandler()); // Reset the transfer handler for the slot
            }
        }
    }

    // Method to check if the game has been won
    private void checkWinCondition() {
        // Create an array to hold slot values
        String[] slotValues = new String[model.getSlotLabels().size()];
        boolean allFilled = true;

        for (int i = 0; i < model.getSlotLabels().size(); i++) {
            JLabel slot = model.getSlotLabels().get(i);
            if (slot.getText().isEmpty()) {
                allFilled = false; // If any slot is empty, set the flag to false
                break; // Exit loop if any slot is empty
            }
            slotValues[i] = slot.getText(); // Store the slot text
        }

        // If all slots are filled, check the order
        if (allFilled) {
            if (model.checkOrder(slotValues)) { // Pass the slot values to checkOrder
                messageLabel.setText("You Win!");
            } else {
                messageLabel.setText("You Lose!"); // Notify the user of loss
            }
        }
    }
}
 */