package yukon.view;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import yukon.controller.GameController;
import yukon.model.Card;

public class FoundationStackView extends Pane {

    private static final double CARD_WIDTH = 80;
    private static final double CARD_HEIGHT = 120;
    private final CardClickCallback cardClickCallback;
    private final int column;
    private Button topCardButton;
    private boolean selected;

    public FoundationStackView(int column, CardClickCallback cardClickCallback) {
        this.cardClickCallback = cardClickCallback;
        this.column = column;
        this.selected = false;
        renderStack();
    }

    private void renderStack() {
        getChildren().clear();
        Card columnHead = GameController.getInstance().getBoard().getColumnHead(column);

        if (columnHead == null) {
            topCardButton = createEmptyStackButton();
            getChildren().add(topCardButton);
            return;
        }

        Card topCard = GameController.getInstance().getBoard().getColumnHead(column).getTail();
        topCardButton = createTopCardButton(topCard);
        getChildren().add(topCardButton);
    }

    private Button createEmptyStackButton() {
        Button btn = new Button();
        btn.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        btn.setStyle(getDefaultStyle());
        btn.setCursor(Cursor.HAND);
        btn.setOnAction(e -> toggleSelection());
        return btn;
    }

    private Button createTopCardButton(Card card) {
        String label = formatCard(card);
        Button btn = new Button(label);
        btn.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        btn.setStyle(getDefaultStyle());
        btn.setCursor(Cursor.HAND);
        btn.setOnAction(e -> toggleSelection());
        return btn;
    }

    private void toggleSelection() {
        if (selected) {
            clearHighlighting();
            selected = false;
        } else {
            highlight();
            selected = true;
            cardClickCallback.call(column, 0);
        }
    }

    private void highlight() {
        topCardButton.setStyle(getHighlightedStyle());
    }

    private void clearHighlighting() {
        topCardButton.setStyle(getDefaultStyle());
    }

    private String getDefaultStyle() {
        return String.join("",
                "-fx-background-color: white;",
                "-fx-border-color: black;",
                "-fx-border-width: 1;",
                "-fx-font-weight: bold;",
                "-fx-font-size: 16;",
                "-fx-alignment: center;",
                "-fx-text-fill: black;"
        );
    }

    private String getHighlightedStyle() {
        return String.join("",
                "-fx-background-color: lightblue;",
                "-fx-border-color: darkblue;",
                "-fx-border-width: 3;",
                "-fx-font-weight: bold;",
                "-fx-font-size: 16;",
                "-fx-alignment: center;",
                "-fx-text-fill: black;"
        );
    }

    private String formatCard(Card card) {
        char rank = yukon.model.Card.numberToChar(card.getNumber());
        char suit = yukon.model.Suit.toChar(card.getSuit());
        return "" + rank + suit;
    }

}
