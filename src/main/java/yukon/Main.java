package yukon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yukon.controller.GameController;
import yukon.view.Constants;
import yukon.view.View;

public class Main extends Application {

    private GameController gameController;

    /**
     * Main method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX entry point. Sets up the game controller and root scene.
     * @param primaryStage primary stage passed by JavaFx
     */
    @Override
    public void start(Stage primaryStage) {
        gameController = GameController.getInstance();
        Scene scene = new Scene(gameController.getRootView(), Constants.WIDTH, Constants.HEIGHT);
        gameController.getRootView().transitionToView(View.MAIN_MENU);
        primaryStage.setTitle(Constants.TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Stop the game. This handles disconnecting from the backend.
     * This is called automatically by JavaFX upon exiting the game.
     */
    @Override
    public void stop() {
        gameController.disconnect();
    }
}