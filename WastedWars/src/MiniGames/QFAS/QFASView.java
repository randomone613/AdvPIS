package WastedWars.src.MiniGames.QFAS;

import WastedWars.src.Model.MiniGame;
import WastedWars.src.Model.GameFinishListener;

import javax.swing.*;
import java.awt.*;

/**
 * View component for the QFAS game, displaying the user interface for the game.
 */
public class QFASView extends JPanel implements MiniGame {

    private JLabel titleLabel;
    private JLabel questionLabel;
    private JTextField reponseField;
    private JButton validateButton = new JButton("Validate");
    private QFASModel model;
    private QFASController controller;

    public QFASView(QFASModel model) {
        this.model = model;

        setLayout(new BorderLayout());

        titleLabel = new JLabel("Question For a Shot!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        String question = this.model.selectedQuestion;
        questionLabel = new JLabel(question, SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(questionLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        reponseField = new JTextField(15);
        inputPanel.add(reponseField);
        inputPanel.add(validateButton);
        add(inputPanel, BorderLayout.SOUTH);

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
        new QFASController(model, new QFASView(model));
    }

    @Override
    public void resetGame() {
        model.select_question();

        reponseField.setText("");
        questionLabel.setText(model.selectedQuestion);

        controller.setOver(false);
        controller.setWin(false);
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
        controller.setGameFinishListener(listener);
    }
}
