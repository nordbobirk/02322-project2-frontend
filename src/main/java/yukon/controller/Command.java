package yukon.controller;

public enum Command {
    LOAD_DECK, LOAD_GAME, SHOW_CARDS, SPLIT_SHUFFLE, RANDOM_SHUFFLE, SAVE_DECK, SAVE_GAME, QUIT_GAME, START_GAME, AUTO_MOVE, UNDO, REDO;

    public static String getCommandString(Command command) {
        return switch (command) {
            case LOAD_DECK -> "ld";
            case LOAD_GAME -> "l";
            case SHOW_CARDS -> "sw";
            case SPLIT_SHUFFLE -> "si";
            case RANDOM_SHUFFLE -> "sr";
            case SAVE_DECK -> "sd";
            case SAVE_GAME -> "s";
            case QUIT_GAME -> "q";
            case START_GAME -> "p";
            case AUTO_MOVE -> "am";
            case UNDO -> "u";
            case REDO -> "r";
        };
    }

}
