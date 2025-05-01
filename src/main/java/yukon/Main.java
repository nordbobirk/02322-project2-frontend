package yukon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yukon.controller.GameController;

public class Main extends Application {

    private GameController gameController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        gameController = new GameController();
        Scene scene = new Scene(gameController.getGameView());
        primaryStage.setTitle("Yukon Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        gameController.disconnect();
    }
}