package WastedWars.src.Controller;

import WastedWars.src.Model.QFAS;
import WastedWars.src.View.QFASView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class QFASController {
    private QFAS model;
    private QFASView view;

    public QFASController(QFAS model, QFASView view) {
        this.model = model;
        this.view = view;

        view.getValidateButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String userAnswer = view.getResponse();
                String trueAnswer = model.selectedAnswer;
                if (Objects.equals(userAnswer, trueAnswer)) {
                    view.setQuestionLabel("Bravo bg<3");
                } else {
                    view.setQuestionLabel("Loupé :( allez BOIS");
                }
            }
        });
    }
}
