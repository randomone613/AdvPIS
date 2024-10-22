package WastedWars.src;

import WastedWars.src.Controller.WastedWarsController;
import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.WastedWarsView;

public class WastedWarsApp {
    public static void main(String[] args) {
        WastedWarsModel model = new WastedWarsModel();

        WastedWarsView view = new WastedWarsView(model);

        new WastedWarsController(model, view);

        view.show();
    }
}
