package WastedWars.src;

import WastedWars.src.MiniGames.TF.TFModel;
import WastedWars.src.MiniGames.TF.TFView;
import WastedWars.src.MiniGames.TF.TFController;

import javax.swing.*;

public class TFTest {
    public static void main(String[] args) {
        int keyCount = 3; // Number of random keys to select

        // Create instances of the model, view, and controller
        TFModel model = new TFModel(keyCount);
        TFView View = new TFView();
        TFController TFController = new TFController(model, View);

        // Set up the JFrame
        JFrame frame = new JFrame("Captcha Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(View);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the window
    }
}
