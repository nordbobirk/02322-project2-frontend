package yukon.util;

import yukon.controller.GameController;
import yukon.model.Card;

public class MoveSerializer {

    public static String serializeMove(int selectionColumn, int selectionIndex, int destinationColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append(selectionColumn > 7 ? "F" : "C");
        sb.append(selectionColumn > 7 ? selectionColumn - 7 : selectionColumn);

        Card columnHead = GameController.getInstance().getBoard().getColumnHead(selectionColumn);
        if (columnHead == null) {
            throw new MoveSerializationException("Column head for column " + selectionColumn + " was null");
        }

        if (selectionIndex == Card.getColumnSize(columnHead) - 1 || selectionColumn > 7) {
            continueSerializationOfMoveOfLastCard(sb, destinationColumn);
        } else {
            continueSerializationOfMoveOfInternalCard(sb, selectionColumn, selectionIndex, destinationColumn);
        }

        return sb.toString();
    }

    private static void continueSerializationOfMoveOfLastCard(StringBuilder sb, int destinationColumn) {
        sb.append("->");
        sb.append(destinationColumn > 7 ? "F" : "C");
        sb.append(destinationColumn > 7 ? destinationColumn - 7 : destinationColumn);
    }

    private static void continueSerializationOfMoveOfInternalCard(StringBuilder sb, int selectionColumn, int selectionIndex, int destinationColumn) {
        Card columnHead = GameController.getInstance().getBoard().getColumnHead(selectionColumn);
        Card selectedCard = Card.getNthCard(columnHead, selectionIndex);

        if (selectedCard == null) {
            throw new MoveSerializationException("Failed to find the selected card");
        }

        sb.append(":");
        sb.append(selectedCard.toString().charAt(0));
        sb.append(selectedCard.toString().charAt(1));
        sb.append("->");
        sb.append(destinationColumn > 7 ? "F" : "C");
        sb.append(destinationColumn > 7 ? destinationColumn - 7 : destinationColumn);
    }

}
