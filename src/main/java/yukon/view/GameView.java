package yukon.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import yukon.controller.Command;
import yukon.controller.GameController;
import yukon.util.MoveSerializer;

import java.util.List;
import java.util.Optional;

/**
 * The main game view.
 */
public class GameView extends VBox {

    private Integer selectionCol = null;
    private Integer selectionIndex = null;

    public GameView() {
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            quitButtonAction();
        });

        Button undoButton = new Button("Undo");
        undoButton.setOnAction(e -> undoButtonAction());

        Button redoButton = new Button("Redo");
        redoButton.setOnAction(e -> redoButtonAction());

        Button autoMoveButton = new Button("Automove");
        autoMoveButton.setOnAction(e -> autoMoveButtonAction());

        Button saveGameButton = new Button("Save");
        saveGameButton.setOnAction(e -> saveGameButtonAction());

        List<Button> buttons = List.of(quitButton, undoButton, redoButton, autoMoveButton, saveGameButton);

        // apply styling to all buttons except the auto move button
        buttons.stream().filter(button -> !button.getText().contains("Automove")).toList().forEach(button -> {
            button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: black; -fx-border-color: black;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;"));
        });

        styleAutoMoveButton(autoMoveButton);

        getChildren().add(new Header(buttons));
        getChildren().add(new Label(GameController.getInstance().getBoard().serializedBoard));

        HBox cardColumnBox = getCardColumnBox();
        getChildren().add(cardColumnBox);
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
        if (selectionCol != null && selectionIndex != null && selectionCol != column) {
            GameController.getInstance().sendMessage(MoveSerializer.serializeMove(selectionCol, selectionIndex, column));
            return;
        }

        selectionCol = column;
        selectionIndex = index;
    }

    private void styleAutoMoveButton(Button autoMoveButton) {
        if (GameController.getInstance().getBoard().isAutoMoveEnabled()) {
            autoMoveButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;");
            autoMoveButton.setOnMouseEntered(e -> autoMoveButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;"));
            autoMoveButton.setOnMouseExited(e -> autoMoveButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;"));
        } else {
            autoMoveButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;");
            autoMoveButton.setOnMouseEntered(e -> autoMoveButton.setStyle("-fx-background-color: green; -fx-text-fill: black; -fx-border-color: black;"));
            autoMoveButton.setOnMouseExited(e -> autoMoveButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-color: black;"));
        }
    }

    private void quitButtonAction() {
        GameController.getInstance().executeCommand(Command.QUIT_GAME);
    }

    private void undoButtonAction() {
        GameController.getInstance().executeCommand(Command.UNDO);
    }

    private void redoButtonAction() {
        GameController.getInstance().executeCommand(Command.REDO);
    }

    private void autoMoveButtonAction() {
        boolean isAutoMoveEnabled = GameController.getInstance().getBoard().isAutoMoveEnabled();
        GameController.getInstance().executeCommand(Command.AUTO_MOVE, isAutoMoveEnabled ? "n" : "y");
    }

    private void saveGameButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save game");
        dialog.setHeaderText("Enter the name of the save file (without the file extension)");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            // they clicked cancel
            return;
        }

        String[] argumentParts = result.get().trim().split(" ");
        if (argumentParts.length >= 1) {
            GameController.getInstance().executeCommand(Command.SAVE_GAME, argumentParts[0]);
        } else {
            GameController.getInstance().executeCommand(Command.SAVE_GAME);
        }
    }
}
