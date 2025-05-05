package yukon.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The main game view.
 */
public class GameView extends VBox {

    public GameView() {
        getChildren().add(new Label("game view"));
    }
}
