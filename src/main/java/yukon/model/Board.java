package yukon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private GamePhase phase;
    private boolean autoMoveEnabled;
    private String message;
    private String lastCommand;

    private final List<Card> columns;
    private final List<Card> foundations;

    public Board() {
        this.phase = GamePhase.STARTUP;
        this.autoMoveEnabled = false;
        this.message = "";
        this.lastCommand = "";
        columns = new ArrayList<>(Collections.nCopies(7, null));
        foundations = new ArrayList<>(Collections.nCopies(4, null));
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public boolean isAutoMoveEnabled() {
        return autoMoveEnabled;
    }

    public void setAutoMoveEnabled(boolean autoMoveEnabled) {
        this.autoMoveEnabled = autoMoveEnabled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    /**
     * Get the head of a column/foundation based on its index.
     *
     * @param i the index of the column (1-11)
     * @return the head of the column
     */
    public Card getColumnHead(int i) {
        if (i > 0 && i < 8) {
            return columns.get(i - 1);
        }

        if (i > 7 && i < 12) {
            return foundations.get(i - 1 - 7);
        }

        return null;
    }

    /**
     * Set the head of a column/foundation based on its index.
     * @param newHead the card to set as the head
     * @param i the column index
     */
    public void setColumnHead(Card newHead, int i) {
        if (i > 0 && i < 8) {
            columns.set(i - 1, newHead);
        }

        if (i > 7 && i < 12) {
            foundations.set(i - 1 - 7, newHead);
        }
    }

}
