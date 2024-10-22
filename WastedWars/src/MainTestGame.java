package WastedWars.src;

import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeModel;
import WastedWars.src.MiniGames.DecibelChallenge.DecibelChallengeView;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to test DecibelChallenge.
 */
public class MainTestGame {
    public static void main(String[] args) {
        DecibelChallengeModel model = new DecibelChallengeModel();
        DecibelChallengeView view = new DecibelChallengeView(model);

        view.startGame();

        JFrame frame = new JFrame("Decibel Challenge Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2 * (Toolkit.getDefaultToolkit().getScreenSize().width) / 3,
                2 * (Toolkit.getDefaultToolkit().getScreenSize().height) / 3);

        frame.add(view);

        frame.setVisible(true);
    }
}
