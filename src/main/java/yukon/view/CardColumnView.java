package yukon.view;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import yukon.controller.GameController;
import yukon.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardColumnView extends Pane {

    private static final double CARD_WIDTH = 80;
    private static final double CARD_HEIGHT = 120;
    private static final double VERTICAL_OFFSET = 30;

    private final boolean interactable;
    private final List<Button> cardButtons = new ArrayList<>();
    private final List<Card> cardList = new ArrayList<>();
    private final int column;
    private Integer selectedIndex = null;

    private final SelectColumnCallback selectColumnCallback;
    private final SelectEmptyColumnCallback selectEmptyColumnCallback;

    public CardColumnView(int column, boolean interactable, SelectColumnCallback selectColumnCallback, SelectEmptyColumnCallback selectEmptyColumnCallback) {
        this.selectColumnCallback = selectColumnCallback;
        this.selectEmptyColumnCallback = selectEmptyColumnCallback;
        this.interactable = interactable;
        this.column = column;
        renderColumn();
    }

    private void renderColumn() {
        getChildren().clear();
        cardButtons.clear();
        cardList.clear();

        Card columnHead = GameController.getInstance().getBoard().getColumnHead(column);
        if (columnHead == null) {
            Button emptyColumnButton = createEmptyColumnButton();
            getChildren().add(emptyColumnButton);
            cardButtons.add(emptyColumnButton);
            return;
        }

        double yOffset = 0;
        int index = 0;
        Card current = columnHead;

        while (current != null) {
            Button cardButton = createCardButton(current, index);
            cardButton.setLayoutY(yOffset);
            getChildren().add(cardButton);
            cardButtons.add(cardButton);
            cardList.add(current);

            yOffset += VERTICAL_OFFSET;
            current = current.getNext();
            index++;
        }
    }

    private Button createEmptyColumnButton() {
        Button btn = new Button();
        btn.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        btn.setStyle(getDefaultStyle(false, true));
        btn.setCursor(Cursor.HAND);
        btn.setOnAction(e -> selectEmptyColumnCallback.selectEmptyColumn(column));
        return btn;
    }

    private Button createCardButton(Card card, int index) {
        String label = formatCard(card);
        Button btn = new Button(label);
        btn.setPrefSize(CARD_WIDTH, CARD_HEIGHT);

        boolean isTail = (card.getNext() == null);
        btn.setStyle(getDefaultStyle(card.isShown(), isTail));

        if (interactable && card.isShown()) {
            btn.setCursor(Cursor.HAND);
            btn.setOnAction(e -> toggleSelection(index));
        } else {
            btn.setDisable(true);
        }

        return btn;
    }

    private void toggleSelection(int index) {
        if (selectedIndex != null && selectedIndex == index) {
            // Deselect
            clearHighlighting();
            selectedIndex = null;
        } else {
            // New selection
            highlightFromIndex(index);
            selectedIndex = index;
            selectColumnCallback.selectColumn(column, index);
        }
    }

    private void highlightFromIndex(int startIndex) {
        for (int i = 0; i < cardButtons.size(); i++) {
            Button btn = cardButtons.get(i);
            Card card = getCardAtIndex(i);
            boolean isTail = card != null && card.getNext() == null;
            boolean isShown = card != null && card.isShown();
            if (i >= startIndex) {
                btn.setStyle(getHighlightedStyle(isTail));
            } else {
                btn.setStyle(getDefaultStyle(isShown, isTail));
            }
        }
    }

    private void clearHighlighting() {
        for (int i = 0; i < cardButtons.size(); i++) {
            Card card = getCardAtIndex(i); // you'll add this helper
            boolean isTail = card != null && card.getNext() == null;
            cardButtons.get(i).setStyle(getDefaultStyle(card != null && card.isShown(), isTail));
        }
    }


    private String getDefaultStyle(boolean shown, boolean isTail) {
        String alignment = isTail ? "center;" : "top-left;";
        return String.join("",
                "-fx-background-color: ", shown ? "white;" : "gray; -fx-opacity: 1;",
                "-fx-border-color: black;",
                "-fx-border-width: 1;",
                "-fx-font-weight: bold;",
                "-fx-font-size: 16;",
                "-fx-alignment: ", alignment
        );
    }


    private String getHighlightedStyle(boolean isTail) {
        String alignment = isTail ? "center;" : "top-left;";
        return String.join("",
                "-fx-background-color: lightblue;",
                "-fx-border-color: darkblue;",
                "-fx-border-width: 3;",
                "-fx-font-weight: bold;",
                "-fx-font-size: 16;",
                "-fx-alignment: ", alignment
        );
    }

    private String formatCard(Card card) {
        if (!card.isShown()) return "";
        char rank = yukon.model.Card.numberToChar(card.getNumber());
        char suit = yukon.model.Suit.toChar(card.getSuit());
        return rank + String.valueOf(suit);
    }

    private Card getCardAtIndex(int index) {
        return index >= 0 && index < cardList.size() ? cardList.get(index) : null;
    }

}
