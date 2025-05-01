package yukon.model;

public class Board {

    private GamePhase phase;
    private String message;
    private String lastCommand;

    public Board() {
        this.phase = GamePhase.STARTUP;
        this.message = "";
        this.lastCommand = "";
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
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

}
