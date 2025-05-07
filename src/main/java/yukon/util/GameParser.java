package yukon.util;

import yukon.controller.GameController;
import yukon.model.Board;
import yukon.model.Card;
import yukon.model.GamePhase;
import yukon.model.Suit;
import yukon.view.View;

public class GameParser {

    /**
     * Parse a serialized game and set the newly created board
     * as the board instance in the game controller.
     *
     * @param serializedGame serialized game state
     * @return the view that should be shown
     */
    public static View parseGame(String serializedGame) {
        GameController gameController = GameController.getInstance();

        if (serializedGame.length() <= 10) {
            // no cards meaning the ongoing game was just quit'ed
            gameController.updateBoard(null);
            return View.MAIN_MENU;
        }

        Board newBoard = new Board(serializedGame);
        newBoard.setPhase(getGamePhase(serializedGame));
        newBoard.setAutoMoveEnabled(getAutoMoveEnabled(serializedGame));

        if (newBoard.getPhase() == GamePhase.PLAY) {
            parseColumn(serializedGame, 1, newBoard);
            parseColumn(serializedGame, 2, newBoard);
            parseColumn(serializedGame, 3, newBoard);
            parseColumn(serializedGame, 4, newBoard);
            parseColumn(serializedGame, 5, newBoard);
            parseColumn(serializedGame, 6, newBoard);
            parseColumn(serializedGame, 7, newBoard);

            parseColumn(serializedGame, 8, newBoard);
            parseColumn(serializedGame, 9, newBoard);
            parseColumn(serializedGame, 10, newBoard);
            parseColumn(serializedGame, 11, newBoard);

            gameController.updateBoard(newBoard);
            return View.PLAY;
        }

        if (newBoard.getPhase() == GamePhase.STARTUP) {
            parseStartupDeck(serializedGame, newBoard);

            gameController.updateBoard(newBoard);
            return View.STARTUP;
        }

        throw new GameParsingException("i dont even know man, shit's fucked");
    }

    /**
     * Parse a startup deck in the STARTUP phase.
     *
     * @param serializedGame serialized game state
     * @param newBoard       new board instance
     */
    private static void parseStartupDeck(String serializedGame, Board newBoard) {
        String startupDeckData = serializedGame.substring(serializedGame.indexOf("@") + 1);
        String[] cardStrings = startupDeckData.split(",");

        int index = 0;
        for (String cardString : cardStrings) {
            Card newCard = parseCard(cardString.length() == 3 ? cardString : cardString.substring(0, 3));
            int columnIndex = (index % 7) + 1;
            Card columnHead = newBoard.getColumnHead(columnIndex);
            if (columnHead == null) {
                newBoard.setColumnHead(newCard, columnIndex);
            } else {
                columnHead.getTail().setNext(newCard);
            }
            index++;
        }
    }

    /**
     * Get the game phase from the serialized game state.
     *
     * @param serializedGame serialized game state
     * @return game phase
     */
    private static GamePhase getGamePhase(String serializedGame) {
        String phaseString = serializedGame.substring(0, serializedGame.indexOf("$"));
        if (phaseString.equals("PLAY")) return GamePhase.PLAY;
        if (phaseString.equals("STARTUP")) return GamePhase.STARTUP;
        throw new GameParsingException("Unknown phase");
    }

    /**
     * Get the auto move enabled option from the serialized game state.
     *
     * @param serializedGame serialized game state
     * @return auto move enabled option
     */
    private static boolean getAutoMoveEnabled(String serializedGame) {
        String autoMoveEnabledString = serializedGame.substring(serializedGame.indexOf("$") + 1, serializedGame.indexOf("@"));
        if (autoMoveEnabledString.equals("0")) return false;
        if (autoMoveEnabledString.equals("1")) return true;
        throw new GameParsingException("Unknown auto move enabled option");
    }

    /**
     * Parse a column on a serialized game in the PLAY phase. Columns
     * 1-11 correspond to C1-C7 and F1-F4. This creates and sets the card
     * stacks in the board instance.
     *
     * @param serializedGame serialized game state
     * @param col            column index
     * @param newBoard       new board instance
     */
    private static void parseColumn(String serializedGame, int col, Board newBoard) {
        String columnData = getColumnData(serializedGame, col);
        String[] cardStrings;
        try {
            cardStrings = columnData.substring(columnData.indexOf(":") + 1, columnData.length() - 1).split(",");
        } catch (Exception e) {
            cardStrings = new String[0];
        }

        for (String cardString : cardStrings) {
            Card head = newBoard.getColumnHead(col);

            if (head == null) {
                newBoard.setColumnHead(parseCard(cardString), col);
                continue;
            }

            newBoard.getColumnHead(col).getTail().setNext(parseCard(cardString));
        }
    }

    /**
     * Parse a serialized card into a card object.
     *
     * @param cardString card serialization
     * @return card object
     */
    private static Card parseCard(String cardString) {
        if (cardString.length() != 3) {
            throw new GameParsingException(String.format("Unknown card string (%s), weird length!", cardString));
        }
        return new Card(parseSuit(cardString.charAt(1)), parseNumber(cardString.charAt(0)), cardString.charAt(2) == '1', null);
    }

    /**
     * Parse a serialized card number into its corresponding
     * integer value.
     *
     * @param number number char
     * @return number int
     */
    private static int parseNumber(char number) {
        return switch (number) {
            case 'A' -> 1;
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            default -> number - 48;
        };
    }

    /**
     * Parse the serialized card suit into a Suit enum value.
     *
     * @param suit suit char
     * @return Suit enum value
     */
    private static Suit parseSuit(char suit) {
        return switch (suit) {
            case 'C' -> Suit.CLUBS;
            case 'H' -> Suit.HEARTS;
            case 'S' -> Suit.SPADES;
            case 'D' -> Suit.DIAMONDS;
            default -> throw new GameParsingException("Unknown suit");
        };
    }

    /**
     * Get a substring of the serialized game state containing all the data
     * pertaining to a specific column by its index (1-11).
     *
     * @param serializedGame serialized game state
     * @param col            column index (1-11)
     * @return serialized game state substring
     */
    private static String getColumnData(String serializedGame, int col) {
        if (col < 1 || col > 11) {
            throw new GameParsingException("Column index must be between 1 and 11");
        }

        String prefix;
        if (col <= 7) {
            prefix = "C" + col + ":";
        } else {
            prefix = "F" + (col - 7) + ":";
        }

        int startIndex = serializedGame.indexOf(prefix);
        if (startIndex == -1) {
            throw new GameParsingException("Couldn't find column " + prefix);
        }

        int nextCol = col + 1;
        if (nextCol > 11) {
            return serializedGame.substring(startIndex);
        }

        String nextColPrefix;
        if (nextCol <= 7) {
            nextColPrefix = "C" + nextCol + ":";
        } else {
            nextColPrefix = "F" + (nextCol - 7) + ":";
        }

        int endIndex = serializedGame.indexOf(nextColPrefix, startIndex);
        return serializedGame.substring(startIndex, endIndex);
    }

}
