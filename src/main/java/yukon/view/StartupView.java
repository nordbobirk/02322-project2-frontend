package yukon.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import yukon.controller.Command;
import yukon.controller.GameController;

import java.util.List;
import java.util.Optional;

public class StartupView {

    private final VBox root;

    public StartupView() {
        Button startGameButton = new Button("Start");
        startGameButton.setOnAction(e -> startGameButtonAction());

        Button shuffleRandomButton = new Button("Random shuffle");
        shuffleRandomButton.setOnAction(e -> shuffleRandomButtonAction());

        Button shuffleSplitButton = new Button("Split shuffle");
        shuffleSplitButton.setOnAction(e -> shuffleSplitButtonAction());

        Button showCardsButton = new Button("Show");
        showCardsButton.setOnAction(e -> showCardsButtonAction());

        Button saveDeckButton = new Button("Save");
        saveDeckButton.setOnAction(e -> saveDeckButtonAction());

        Button mainMenuButton = new Button("Main menu");
        mainMenuButton.setOnAction(e -> mainMenuButtonAction());

        List<Button> buttons = List.of(startGameButton, shuffleRandomButton, shuffleSplitButton, showCardsButton, saveDeckButton, mainMenuButton);

        // apply styling to all buttons except the auto move button
        buttons.stream().filter(button -> !button.getText().contains("Show")).toList().forEach(button -> {
            button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-border-color: black;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;"));
        });

        styleShowCardsButton(showCardsButton);

        root = new VBox(new Header(buttons), new Label(GameController.getInstance().getBoard().serializedBoard), getCardColumnBox());
    }

    public VBox getRoot() {
        return root;
    }

    private HBox getCardColumnBox() {
        CardColumnView column1View = new CardColumnView(1, true, this::handleClick);
        CardColumnView column2View = new CardColumnView(2, true, this::handleClick);
        CardColumnView column3View = new CardColumnView(3, true, this::handleClick);
        CardColumnView column4View = new CardColumnView(4, true, this::handleClick);
        CardColumnView column5View = new CardColumnView(5, true, this::handleClick);
        CardColumnView column6View = new CardColumnView(6, true, this::handleClick);
        CardColumnView column7View = new CardColumnView(7, true, this::handleClick);

        return new HBox(10, column1View, column2View, column3View, column4View, column5View, column6View, column7View);
    }

    private void handleClick(int column, int index) {
        // clicks are ignored in STARTUP phase
    }

    private void styleShowCardsButton(Button showCardsButton) {
        // kinda hacky but it's fairly safe to assume that the shown boolean is the same for all cards in STARTUP
        if (GameController.getInstance().getBoard().getColumnHead(1).isShown()) {
            showCardsButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;");
            showCardsButton.setOnMouseEntered(e -> showCardsButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;"));
            showCardsButton.setOnMouseExited(e -> showCardsButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;"));
        } else {
            showCardsButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;");
            showCardsButton.setOnMouseEntered(e -> showCardsButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;"));
            showCardsButton.setOnMouseExited(e -> showCardsButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;"));
        }
    }

    private void startGameButtonAction() {
        GameController.getInstance().executeCommand(Command.START_GAME);
    }

    private void shuffleRandomButtonAction() {
        GameController.getInstance().executeCommand(Command.RANDOM_SHUFFLE);
    }

    private void shuffleSplitButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Split shuffle");
        dialog.setHeaderText("Enter the number for where you want to split the deck");
        dialog.setContentText("Number:");

        Optional<String> resultString = dialog.showAndWait();
        if (resultString.isEmpty() || resultString.get().isBlank()) {
            Util.alert("Invalid input", "Please enter a valid, positive integer less than 53", Alert.AlertType.WARNING);
            return;
        }

        int result;
        try {
            result = Integer.parseInt(resultString.get());
        } catch (NumberFormatException e) {
            Util.alert("Invalid input", "Please enter a valid, positive integer less than 53", Alert.AlertType.WARNING);
            return;
        }

        if (0 > result || result > 52) {
            Util.alert("Invalid input", "Please enter a valid, positive integer less than 53", Alert.AlertType.WARNING);
            return;
        }

        GameController.getInstance().executeCommand(Command.SPLIT_SHUFFLE, "" + result);
    }

    private void showCardsButtonAction() {
        GameController.getInstance().executeCommand(Command.SHOW_CARDS);
    }

    private void saveDeckButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save deck");
        dialog.setHeaderText("Enter the name of the save file (without the file extension)");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            // they clicked cancel
            return;
        }

        String[] argumentParts = result.get().trim().split(" ");
        if (argumentParts.length >= 1) {
            GameController.getInstance().executeCommand(Command.SAVE_DECK, argumentParts[0]);
        } else {
            GameController.getInstance().executeCommand(Command.SAVE_DECK);
        }
    }

    private void mainMenuButtonAction() {
        GameController.getInstance().updateBoard(null);
        GameController.getInstance().getRootView().transitionToView(View.MAIN_MENU);
    }

}
