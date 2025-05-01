package yukon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yukon.controller.GameController;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController();
        Scene scene = new Scene(controller.getGameView(), 256, 256);
        primaryStage.setTitle("Yukon Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}