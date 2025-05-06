package yukon.controller;

import javafx.scene.control.Alert;
import yukon.model.Board;
import yukon.util.GameParser;
import yukon.view.RootView;
import yukon.view.Util;
import yukon.view.View;

import java.io.IOException;

public class GameController {

    /**
     * Singleton instance
     */
    private static GameController instance;

    /**
     * Root view
     */
    private final RootView rootView;

    /**
     * TCP client instance
     */
    private final TcpClient client;

    /**
     * Board instance
     */
    private Board board;

    /**
     * Set up the main game view and TCP client.
     */
    private GameController() {
        rootView = new RootView();

        try {
            client = new TcpClient("localhost", 12345);
        } catch (IOException e) {
            throw new ServerCommunicationException("Failed to establish a connection with the server");
        }
    }

    /**
     * Singleton getter
     *
     * @return singleton instance
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Get the root of the main game view.
     *
     * @return main game view root
     */
    public RootView getRootView() {
        return rootView;
    }

    /**
     * Get the current board instance.
     *
     * @return board instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set a new board instance.
     *
     * @param newBoard new board instance
     */
    public void updateBoard(Board newBoard) {
        board = newBoard;
    }

    /**
     * Execute a command with the given argument.
     *
     * @param command command
     * @param arg     argument
     */
    public void executeCommand(Command command, String arg) {
        arg = arg.isBlank() ? "" : arg;
        sendMessage(Command.getCommandString(command) + " " + arg);
    }

    /**
     * Execute a command with no argument.
     *
     * @param command command
     */
    public void executeCommand(Command command) {
        sendMessage(Command.getCommandString(command));
    }

    /**
     * Tell the TCP client to close the connection.
     */
    public void disconnect() {
        try {
            client.close();
        } catch (IOException e) {
            throw new ServerCommunicationException("Failed to close the connection with the server");
        }
    }

    /**
     * Tell the TCP client to send a message to the server.
     */
    public void sendMessage(String message) {
        if (message.isEmpty()) {
            return;
        }

        System.out.println("Sending message: " + message);

        try {
            client.sendMessage(message);
            awaitResponse();
        } catch (Exception e) {
            e.printStackTrace();
            Util.alert("Error while sending message to backend", "An error occurred while sending a message to the backend. Message: " + message + ". See stacktrace in console.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Wait for a response from the server.
     */
    private void awaitResponse() {
        try {
            String response = client.receiveResponse();
            System.out.println("Received response: " + response);
            parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            Util.alert("An error occurred while parsing the game state", "Failed to parse serialized game state, see stack trace in console.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Parse server response.
     *
     * @param response server response
     */
    private void parseResponse(String response) {
        View newView = GameParser.parseGame(response);
        getRootView().transitionToView(newView);
    }

}
