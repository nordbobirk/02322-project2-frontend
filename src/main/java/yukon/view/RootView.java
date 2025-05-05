package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class RootView extends VBox {

    private View currentView;

    public RootView() {
        currentView = View.MAIN_MENU;
        setPadding(new Insets(10));
    }

    public void transitionToView(View view) {
        switch (view) {
            case MAIN_MENU -> gotoMainMenuView();
            case STARTUP -> gotoStartupView();
            case PLAY -> gotoPlayView();
        }
    }

    public View getCurrentView() {
        return currentView;
    }

    private void gotoMainMenuView() {
        getChildren().clear();
        getChildren().add(new MainMenuView().getRoot());
        currentView = View.MAIN_MENU;
    }

    private void gotoStartupView() {
        getChildren().clear();
        getChildren().add(new StartupView());
        currentView = View.STARTUP;
    }

    private void gotoPlayView() {
        getChildren().clear();
        getChildren().add(new GameView());
        currentView = View.PLAY;
    }

}
