package yukon.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import yukon.controller.Command;
import yukon.controller.GameController;

import java.util.List;
import java.util.Optional;

public class MainMenuView {

    private final VBox root;

    public MainMenuView() {
        Label title = new Label("Yukon Solitaire");
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.BLACK);

        Button loadSavedDeckButton = new Button("Load a saved deck");
        loadSavedDeckButton.setOnAction(e -> loadSavedDeckButtonAction());

        Button loadDefaultDeckButton = new Button("Load default deck");
        loadDefaultDeckButton.setOnAction(e -> loadDefaultDeckButtonAction());

        Button loadSavedGameButton = new Button("Load a saved game");
        loadSavedGameButton.setOnAction(e -> loadSavedGameButtonAction());

        Button loadDefaultGameButton = new Button("Load default game");
        loadDefaultGameButton.setOnAction(e -> loadDefaultGameAction());

        Button quitButton = new Button("Quit the game");
        quitButton.setOnAction(e -> Platform.exit());

        List<Button> buttons = List.of(loadSavedDeckButton, loadDefaultDeckButton, loadSavedGameButton, loadDefaultGameButton, quitButton);
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-border-color: black;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;"));
            button.setPrefWidth(200);
        }

        root = new VBox(20, title, loadSavedDeckButton, loadDefaultDeckButton, loadSavedGameButton, loadDefaultGameButton, quitButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");
        root.setPrefHeight(Constants.HEIGHT);
    }

    public VBox getRoot() {
        return root;
    }

    private void loadSavedDeckButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load saved deck");
        dialog.setHeaderText("Enter the name of the save file (without the file extension)");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty() || result.get().isBlank()) {
            Util.alert("Invalid input", "Please enter a valid file name", Alert.AlertType.ERROR);
            return;
        }

        String[] argumentParts = result.get().trim().split(" ");
        if (argumentParts.length >= 1) {
            GameController.getInstance().executeCommand(Command.LOAD_DECK, argumentParts[0]);
        } else {
            GameController.getInstance().executeCommand(Command.LOAD_DECK);
        }
    }

    private void loadDefaultDeckButtonAction() {
        GameController.getInstance().executeCommand(Command.LOAD_DECK);
    }

    private void loadSavedGameButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load saved game");
        dialog.setHeaderText("Enter the name of the save file (without the file extension)");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty() || result.get().isBlank()) {
            Util.alert("Invalid input", "Please enter a valid file name", Alert.AlertType.ERROR);
            return;
        }

        String[] argumentParts = result.get().trim().split(" ");
        if (argumentParts.length >= 1) {
            GameController.getInstance().executeCommand(Command.LOAD_GAME, argumentParts[0]);
            GameController.getInstance().startAndResetTimer();
        } else {
            GameController.getInstance().executeCommand(Command.LOAD_GAME);
            GameController.getInstance().startAndResetTimer();
        }
    }

    private void loadDefaultGameAction() {
        GameController.getInstance().executeCommand(Command.LOAD_GAME);
        GameController.getInstance().startAndResetTimer();
    }

}
