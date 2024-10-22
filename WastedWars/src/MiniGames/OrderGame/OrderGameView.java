package WastedWars.src.MiniGames.OrderGame;

import WastedWars.src.Model.GameFinishListener;
import WastedWars.src.Model.MiniGame;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class OrderGameView extends JPanel implements MiniGame {
    private OrderGameModel model;
    private JPanel cardPanel;
    private JPanel slotPanel;
    private JLabel orderLabel;
    private JLabel messageLabel;

    private static final int CARD_SIZE = 60;
    private static final int SLOT_SIZE = 60;
    private static final int NUM_CARDS = 5;

    private boolean win = false;
    private boolean over = false;
    private GameFinishListener gameFinishListener;

    public OrderGameView(OrderGameModel model) {
        this.model = model;
        setLayout(null);

        orderLabel = new JLabel("Order: " + (model.isAscending() ? "Ascending" : "Descending"), SwingConstants.CENTER);
        orderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        orderLabel.setBounds(50, 0, 800, 50);
        add(orderLabel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 50, 800, 30);
        add(messageLabel);

        slotPanel = new JPanel();
        slotPanel.setLayout(new GridLayout(1, NUM_CARDS, 10, 10));
        slotPanel.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width) / 5,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 5, SLOT_SIZE * NUM_CARDS + 40, SLOT_SIZE);

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
        add(slotPanel);

        cardPanel = new JPanel(null);
        cardPanel.setBounds(0, 100, 2 * (Toolkit.getDefaultToolkit().getScreenSize().width) / 3,
                2 * (Toolkit.getDefaultToolkit().getScreenSize().height) / 3);
        cardPanel.setOpaque(false);
        add(cardPanel);

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
            do {
                xPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - CARD_SIZE);
                yPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - CARD_SIZE);
            } while (isOverlappingSlotArea(xPos, yPos));

            card.setBounds(xPos, yPos, CARD_SIZE, CARD_SIZE);
            cardPanel.add(card);
        }
    }

    private boolean isOverlappingSlotArea(int x, int y) {
        int slotX = slotPanel.getX();
        int slotY = slotPanel.getY();
        int slotWidth = slotPanel.getWidth();
        int slotHeight = slotPanel.getHeight();

        return (x + CARD_SIZE > slotX && x < slotX + slotWidth) && (y + CARD_SIZE > slotY && y < slotY + slotHeight);
    }

    @Override
    public void startGame() {
        OrderGameModel model = new OrderGameModel();
        new OrderGameView(model);
    }

    @Override
    public void resetGame(){
        messageLabel.setText("");
        orderLabel = new JLabel("Order: " + (model.isAscending() ? "Ascending" : "Descending"), SwingConstants.CENTER);

        for (JLabel slot : model.getSlotLabels()) {
            slot.setText("");
            slot.setTransferHandler(new ValueImportTransferHandler());
        }

        cardPanel.removeAll();
        cardPanel.revalidate();
        cardPanel.repaint();

        Random random = new Random();
        List<Integer> newCardValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            newCardValues.add((int) (Math.random() * 100));
        }
        model.setCardValues(newCardValues);
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
            do {
                xPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - CARD_SIZE);
                yPos = random.nextInt((Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - CARD_SIZE);
            } while (isOverlappingSlotArea(xPos, yPos));

            card.setBounds(xPos, yPos, CARD_SIZE, CARD_SIZE);
            cardPanel.add(card);
        }

        win = false;
        over = false;

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    @Override
    public boolean isWin() {
        return win;
    }

    @Override
    public boolean isOver() {
        return over;
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        this.gameFinishListener = listener;
    }

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
                comp.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        }

        @Override
        public Icon getVisualRepresentation(Transferable t) {
            JButton button = new JButton();
            try {
                String cardText = t.getTransferData(DataFlavor.stringFlavor).toString();
                button.setText(cardText);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            return button.getIcon();
        }

        @Override
        public void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JButton button = (JButton) source;
                cardPanel.remove(button);
                cardPanel.revalidate();
                cardPanel.repaint();
            }
        }
    }

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

                if (label.getText().isEmpty()) {
                    label.setText(value);
                    label.setTransferHandler(new ValueExportFromSlotHandler());
                    checkWinCondition();
                    return true;
                } else {
                    return false;
                }

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private class ValueExportFromSlotHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(((JLabel) c).getText());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JLabel label = (JLabel) source;
                label.setText("");
                label.setTransferHandler(new ValueImportTransferHandler());
            }
        }
    }

    private void checkWinCondition() {
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
                win = true;
            } else {
                messageLabel.setText("You Lose!");
                win = false;
            }
            over = true;
            if (gameFinishListener != null) {
                gameFinishListener.onGameFinished();
            }
        }
    }
}
