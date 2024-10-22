package WastedWars.src.MiniGames.TF;

import WastedWars.src.Model.GameFinishListener;
import WastedWars.src.Model.MiniGame;

import javax.swing.*;
import java.awt.*;

public class TFView extends JPanel implements MiniGame {
    private JLabel[] keys;
    private JLabel messageLabel;
    private TFController controller;
    private TFModel model;
    private GameFinishListener finishListener;

    public TFView() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        keys = new JLabel[30];

        String[] azertyKeys = {
                "A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P",
                "Q", "S", "D", "F", "G", "H", "J", "K", "L", "M",
                "W", "X", "C", "V", "B", "N", ",", ";", ":", "!"
        };

        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                if (index < azertyKeys.length) {
                    keys[index] = new JLabel(azertyKeys[index], SwingConstants.CENTER);
                    keys[index].setFont(new Font("Arial", Font.BOLD, 18));
                    keys[index].setOpaque(true);
                    keys[index].setBackground(Color.LIGHT_GRAY);
                    keys[index].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    keys[index].setPreferredSize(new Dimension(50, 50));

                    gbc.gridx = col;
                    gbc.gridy = row;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 1.0;
                    gbc.weighty = 1.0;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    add(keys[index], gbc);
                    index++;
                }
            }
        }

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 10;
        add(messageLabel, gbc);

        addLegend(gbc);

        setFocusable(true);
        requestFocusInWindow();

        model = new TFModel(3);
        controller = new TFController(model, this);
    }

    private void addLegend(GridBagConstraints gbc) {
        JLabel leftHandLegend = new JLabel("Left Hand: ");
        leftHandLegend.setForeground(new Color(77, 148, 255));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 5;
        add(leftHandLegend, gbc);

        JLabel rightHandLegend = new JLabel("Right Hand: ");
        rightHandLegend.setForeground(new Color(255, 77, 77));
        gbc.gridx = 5;
        gbc.gridwidth = 5;
        add(rightHandLegend, gbc);
    }

    public void highlightKey(String key, boolean isRightHand) {
        for (JLabel label : keys) {
            if (label.getText().equals(key)) {
                if (isRightHand) {
                    label.setBackground(new Color(255, 77, 77));
                } else {
                    label.setBackground(new Color(77, 148, 255));
                }
            }
        }
    }

    public void resetKeys() {
        for (JLabel label : keys) {
            label.setBackground(Color.LIGHT_GRAY);
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
    public void resetGame() {
        model.resetPressedKeys();
        this.resetKeys();
        model.selectKeys(3);
        controller.setGameOver(false);
        controller.setWin(false);
        controller.updateView();
        displayMessage("");
    }


    @Override
    public boolean isWin() {
        return controller.getWin();
    }

    @Override
    public boolean isOver(){
        return controller.getGameOver();
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        this.finishListener = listener;
    }

    public void notifyGameFinish() {
        if (finishListener != null) {
            finishListener.onGameFinished();
        }
    }
}
