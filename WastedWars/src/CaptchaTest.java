package WastedWars.src;

import WastedWars.src.Model.Captcha;
import WastedWars.src.View.CaptchaView;
import WastedWars.src.Controller.CaptchaController;

import javax.swing.*;

public class CaptchaTest {
    public static void main(String[] args) {
        int keyCount = 3; // Number of random keys to select

        // Create instances of the model, view, and controller
        Captcha captchaModel = new Captcha(keyCount);
        CaptchaView captchaView = new CaptchaView();
        CaptchaController captchaController = new CaptchaController(captchaModel, captchaView);

        // Set up the JFrame
        JFrame frame = new JFrame("Captcha Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(captchaView);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the window
    }
}
