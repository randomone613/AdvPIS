package WastedWars.src.MiniGames.OrderGame;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.List;

public class OrderGameController {
    private OrderGameModel model;
    private OrderGameView view;

    public OrderGameController(OrderGameModel model, OrderGameView view) {
        this.model = model;
        this.view = view;
    }

    private void initializeDragAndDrop() {
        List<JButton> cardButtons = model.getCardValues();
        List<JLabel> slotLabels = model.getSlotLabels();

        // Drag-and-Drop for cards
        for (JButton card : cardButtons) {
            card.setTransferHandler(new TransferHandler("text"));
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JComponent comp = (JComponent) e.getSource();
                    TransferHandler handler = comp.getTransferHandler();
                    handler.exportAsDrag(comp, e, TransferHandler.COPY);
                }
            });
        }

        // Drop functionality for slots
        for (JLabel slot : slotLabels) {
            slot.setTransferHandler(new TransferHandler("text") {
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
                        String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                        JLabel slot = (JLabel) support.getComponent();
                        slot.setText(data);  // Display card value on slot
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }
    }
}
