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
    private GameFinishListener gameFinishListener; // Listener for game finish events

    public QFASController(QFASModel model, QFASView view) {
        this.model = model;
        this.view = view;

        view.getValidateButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String userAnswer = view.getResponse();
                String trueAnswer = model.selectedAnswer;
                if (Objects.equals(userAnswer, trueAnswer)) {
                    view.setQuestionLabel("Bravo bg<3");
                    win = true;
                    over = true;
                } else {
                    view.setQuestionLabel("Loupé :( allez BOIS");
                    win = false;
                    over = true;
                }

                // Notify the listener that the game is finished
                if (gameFinishListener != null) {
                    gameFinishListener.onGameFinished();
                }
            }
        });
    }

    // Setter for the game finish listener
    public void setGameFinishListener(GameFinishListener listener) {
        this.gameFinishListener = listener;
    }

    public boolean getwin(){
        return win;
    }

    public boolean getOver(){
        return over;
    }
}
