package yukon.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import yukon.controller.GameController;

public class WinView {

    private final VBox root;

    public WinView() {
        Label title = new Label("You won!");
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.BLACK);

        Label time = new Label("It only took you " + Util.formatTime(GameController.getInstance().getSecondsElapsed()));
        time.setFont(Font.font("Arial", 24));
        time.setTextFill(Color.BLACK);

        Button backButton = new Button("Back to main menu");
        backButton.setOnAction(e -> GameController.getInstance().getRootView().transitionToView(View.MAIN_MENU));

        backButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-border-color: black;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;"));
        backButton.setPrefWidth(200);

        root = new VBox(20, title, time, backButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");
        root.setPrefHeight(Constants.HEIGHT);
    }

    public VBox getRoot() {
        return root;
    }
}
