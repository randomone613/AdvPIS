package WastedWars.src;

import WastedWars.src.MiniGames.QFAS.QFASController;
import WastedWars.src.MiniGames.QFAS.QFASModel;
import WastedWars.src.MiniGames.QFAS.QFASView;

public class MainTestQFAS {
    public static void main(String[] args) {

        QFASModel model = new QFASModel();
        QFASView view = new QFASView(model);
        new QFASController(model, view);
        view.setVisible(true);
    }
}
