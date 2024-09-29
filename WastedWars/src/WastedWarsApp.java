package WastedWars.src;

import WastedWars.src.Controller.WastedWarsController;
import WastedWars.src.Model.WastedWarsModel;
import WastedWars.src.View.WastedWarsView;

public class WastedWarsApp {
    public static void main(String[] args) {
        // Initialize Model
        WastedWarsModel model = new WastedWarsModel();

        // Initialize View
        WastedWarsView view = new WastedWarsView(model);

        // Initialize Controller
        new WastedWarsController(model, view);

        // Launch the application
        view.show();
    }
}
