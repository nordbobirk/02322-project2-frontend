package yukon.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class Footer extends VBox {

    public Footer(List<Node> children) {
        HBox nodeRow = new HBox(10);
        nodeRow.getChildren().addAll(children);

        nodeRow.setFillHeight(false);
        nodeRow.setPadding(new Insets(0, 0, 5, 0));

        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(separator, Priority.NEVER);

        getChildren().addAll(separator, nodeRow);

        setPrefWidth(Constants.WIDTH);
        setPrefHeight(Constants.HEIGHT / 40.0);
    }
}
