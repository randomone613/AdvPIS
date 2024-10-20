package WastedWars.src.MiniGames.QFAS;

import WastedWars.src.Model.MiniGame;
import WastedWars.src.Model.GameFinishListener;

import javax.swing.*;
import java.awt.*;

public class QFASView extends JPanel implements MiniGame {

    private JLabel titleLabel;
    private JLabel questionLabel;
    private JTextField reponseField;
    private JButton validateButton = new JButton("Validate");
    private QFASModel model;
    private QFASController controller;

    public QFASView(QFASModel model) {
        this.model = model;

        // Set the layout manager for the JPanel
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("Question For a Shot!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Question
        String question = this.model.selectedQuestion;
        questionLabel = new JLabel(question, SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(questionLabel, BorderLayout.CENTER);

        // Panel for the response field and validate button
        JPanel inputPanel = new JPanel(new FlowLayout());
        reponseField = new JTextField(15);
        inputPanel.add(reponseField);
        inputPanel.add(validateButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Initialize the controller
        controller = new QFASController(model, this);
    }

    public JButton getValidateButton() {
        return validateButton;
    }

    public void setQuestionLabel(String label) {
        questionLabel.setText(label);
    }

    public String getResponse() {
        return reponseField.getText().trim();
    }

    @Override
    public void startGame() {
        // Recreate the controller and view to start fresh
        new QFASController(model, new QFASView(model));
    }

    @Override
    public void resetGame() {
        // 1. Reset the model's state (select new question/answer)
        model.select_question();

        // 2. Clear the response field and reset the question label
        reponseField.setText(""); // Clear user input
        questionLabel.setText(model.selectedQuestion); // Set new question

        // 3. Reset the controller's state (flags like win/over)
        controller.setOver(false); // Reset game over status
        controller.setWin(false); // Reset win status
    }

    @Override
    public boolean isWin() {
        return controller.getwin();
    }

    @Override
    public boolean isOver(){
        return controller.getOver();
    }

    @Override
    public void setGameFinishListener(GameFinishListener listener) {
        controller.setGameFinishListener(listener); // Pass listener to the controller
    }
}
