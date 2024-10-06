package WastedWars.src;

import WastedWars.src.Controller.QFASController;
import WastedWars.src.Model.QFAS;
import WastedWars.src.View.QFASView;

public class MainTestQFAS {
    public static void main(String[] args) {

        QFAS model = new QFAS();
        QFASView view = new QFASView(model);
        new QFASController(model, view);
        view.setVisible(true);
    }
}
