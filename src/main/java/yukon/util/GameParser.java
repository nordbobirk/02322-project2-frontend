package yukon.util;

import yukon.controller.GameController;
import yukon.model.Board;
import yukon.model.GamePhase;

public class GameParser {

    public static void parseGame(String serializedGame) {
        GameController gameController = GameController.getInstance();

        Board newBoard = new Board();
        newBoard.setPhase(getGamePhase(serializedGame));
        newBoard.setAutoMoveEnabled(getAutoMoveEnabled(serializedGame));

        gameController.updateBoard(newBoard);
    }

    private static GamePhase getGamePhase(String serializedGame) {
        String phaseString = serializedGame.substring(0, serializedGame.indexOf("$"));
        if (phaseString.equals("PLAY")) return GamePhase.PLAY;
        if (phaseString.equals("STARTUP")) return GamePhase.STARTUP;
        throw new GameParsingException("Unknown phase");
    }

    private static boolean getAutoMoveEnabled(String serializedGame) {
        String autoMoveEnabledString = serializedGame.substring(serializedGame.indexOf("$") + 1, serializedGame.indexOf("@"));
        if (autoMoveEnabledString.equals("0")) return false;
        if (autoMoveEnabledString.equals("1")) return true;
        throw new GameParsingException("Unknown auto move enabled option");
    }

    private static void parseColumn(String serializedGame, int col) {

    }

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

        int endIndex = serializedGame.indexOf(';', startIndex);
        if (endIndex == -1) {
            // might be at the end of the string without semicolon
            endIndex = serializedGame.length();
        }

        return serializedGame.substring(startIndex, endIndex);
    }

}
