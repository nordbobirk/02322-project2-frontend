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

    // TODO remove serializedBoard
    public String serializedBoard;

    public Board(String serializedBoard) {
        this.serializedBoard = serializedBoard;

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
     * Get the head of a column based in its index.
     *
     * @param i the index of the column (1-7)
     * @return the head of the column
     */
    public Card getColumnHead(int i) {
        if (1 > i || i > 7) {
            return null;
        }
        return columns.get(i - 1);
    }

    public void setColumnHead(Card card, int i) {
        columns.set(i - 1, card);
    }

    /**
     * Get the head of a foundation based on its index.
     *
     * @param i the index of the foundation (1-4)
     * @return the head of the foundation
     */
    public Card getFoundationHead(int i) {
        if (1 > i || i > 4) {
            return null;
        }
        return foundations.get(i - 1);
    }

}
