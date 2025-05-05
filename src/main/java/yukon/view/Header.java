package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class Header extends VBox {

    public Header(List<Button> buttons) {
        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(buttons);

        buttonRow.setFillHeight(false);
        buttonRow.setPadding(new Insets(0, 0, 5, 0));

        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(separator, Priority.NEVER);

        getChildren().addAll(buttonRow, separator);

        setPrefWidth(Constants.WIDTH);
        setPrefHeight(Constants.HEIGHT / 20.0);
    }
}
