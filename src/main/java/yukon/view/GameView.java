package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView {

    private final VBox root;
    private final TextArea boardArea;
    private final TextField commandField;
    private final Button sendButton;

    public GameView() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        Text title = new Text("Yukon Solitaire");
        boardArea = new TextArea();
        boardArea.setEditable(false);
        boardArea.setStyle("-fx-font-family: monospace;");

        commandField = new TextField();
        commandField.setPromptText("Enter command (e.g. move A1 D2)");

        sendButton = new Button("Send");

        root.getChildren().addAll(title, boardArea, commandField, sendButton);
    }

    public VBox getRoot() {
        return root;
    }

    public TextArea getBoardArea() {
        return boardArea;
    }

    public TextField getCommandField() {
        return commandField;
    }

    public Button getSendButton() {
        return sendButton;
    }
}
