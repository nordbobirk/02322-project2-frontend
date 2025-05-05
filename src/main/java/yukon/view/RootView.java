package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class RootView extends VBox {

    public RootView() {
        setPadding(new Insets(10));
    }

    public void gotoMainMenuView() {
        getChildren().clear();
        getChildren().add(new MainMenuView());
    }

    public void gotoStartupView() {
        getChildren().clear();
        getChildren().add(new StartupView());
    }

    public void gotoPlayView() {
        getChildren().clear();
        getChildren().add(new GameView());
    }

}
