package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView {

    private final VBox root;

    public GameView() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        Text title = new Text("Yukon Solitaire");

        root.getChildren().addAll(title);
    }

    public VBox getRoot() {
        return root;
    }
}
