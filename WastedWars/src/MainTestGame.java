package WastedWars.src;

import WastedWars.src.MiniGames.OrderGame.OrderGameModel;
import WastedWars.src.MiniGames.OrderGame.OrderGameView;

import javax.swing.*;
import java.awt.*;

public class MainTestGame {
    public static void main(String[] args) {
        // Initialize the model and view
        OrderGameModel model = new OrderGameModel();
        OrderGameView view = new OrderGameView(model);

        // Create the frame to display the game
        JFrame frame = new JFrame("Order Game Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2*(Toolkit.getDefaultToolkit().getScreenSize().width)/3,
                2*(Toolkit.getDefaultToolkit().getScreenSize().height)/3);
        frame.add(view);  // Add the game view to the frame

        // Show the frame
        frame.setVisible(true);
    }
}
