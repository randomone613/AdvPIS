package WastedWars.src;

import WastedWars.src.Model.TF;
import WastedWars.src.View.TFView;
import WastedWars.src.Controller.TFController;

import javax.swing.*;

public class TFTest {
    public static void main(String[] args) {
        int keyCount = 3; // Number of random keys to select

        // Create instances of the model, view, and controller
        TF TFModel = new TF(keyCount);
        TFView View = new TFView();
        TFController TFController = new TFController(TFModel, View);

        // Set up the JFrame
        JFrame frame = new JFrame("Captcha Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(View);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the window
    }
}
