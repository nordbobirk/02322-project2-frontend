package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class RootView extends VBox {

    public RootView() {
        setPadding(new Insets(10));
    }

    public void transitionToView(View view) {
        switch (view) {
            case MAIN_MENU -> gotoMainMenuView();
            case STARTUP -> gotoStartupView();
            case PLAY -> gotoPlayView();
        }
    }

    private void gotoMainMenuView() {
        getChildren().clear();
        getChildren().add(new MainMenuView().getRoot());
    }

    private void gotoStartupView() {
        getChildren().clear();
        getChildren().add(new StartupView().getRoot());
    }

    private void gotoPlayView() {
        getChildren().clear();
        getChildren().add(new GameView().getRoot());
    }

}
