package WastedWars.src.View;

import WastedWars.src.Model.QFAS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QFASView extends JFrame{

    private JLabel titleLabel;
    private JLabel questionLabel;
    private JTextField reponseField;
    private JButton validateButton = new JButton("Validate");;

    private QFAS model;

    public QFASView(QFAS model) {
        this.model = model;
        setTitle("Question For a Shot!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2*(Toolkit.getDefaultToolkit().getScreenSize().width)/3,
                2*(Toolkit.getDefaultToolkit().getScreenSize().height)/3);

        // title
        JLabel titleLabel = new JLabel("Question For a Shot!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.titleLabel = titleLabel;
        add(this.titleLabel, BorderLayout.NORTH);

        // question
        String question = this.model.selectedQuestion;
        JLabel questLabel = new JLabel(question,SwingConstants.CENTER);
        questLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        this.questionLabel = questLabel;
        add(this.questionLabel, BorderLayout.CENTER);

        // Panneau pour le champ de réponse et le bouton
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        reponseField = new JTextField(15);
        inputPanel.add(reponseField);
        inputPanel.add(validateButton);
        add(inputPanel, BorderLayout.SOUTH);
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
}

