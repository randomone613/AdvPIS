package WastedWars.src.MiniGames.QFAS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import WastedWars.src.Model.GameFinishListener;

public class QFASController {
    private QFASModel model;
    private QFASView view;
    private boolean win;
    private boolean over = false;
    private GameFinishListener gameFinishListener;

    public QFASController(QFASModel model, QFASView view) {
        this.model = model;
        this.view = view;

        view.getValidateButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String userAnswer = view.getResponse();
                String trueAnswer = model.selectedAnswer;
                if (Objects.equals(userAnswer, trueAnswer)) {
                    view.setQuestionLabel("Good guess !");
                    win = true;
                    over = true;
                } else {
                    view.setQuestionLabel("Wrong ! Too bad, take a shot !");
                    win = false;
                    over = true;
                }

                if (gameFinishListener != null) {
                    gameFinishListener.onGameFinished();
                }
            }
        });
    }

    public void setGameFinishListener(GameFinishListener listener) {
        this.gameFinishListener = listener;
    }

    public boolean getwin(){
        return win;
    }
    public void setWin(boolean b){
        win = b;
    }

    public boolean getOver(){
        return over;
    }
    public void setOver(boolean b){
        over = b;
    }
}
