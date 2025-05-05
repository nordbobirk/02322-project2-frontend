package yukon.controller;

import javafx.scene.control.Alert;
import yukon.model.Board;
import yukon.util.GameParser;
import yukon.view.RootView;
import yukon.view.Util;

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
     * Set a new board instance.
     *
     * @param newBoard new board instance
     */
    public void updateBoard(Board newBoard) {
        board = newBoard;
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
    private void sendMessage(String message) {
        if (message.isEmpty()) {
            return;
        }

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
            GameParser.parseGame(response);
        } catch (Exception e) {
            e.printStackTrace();
            Util.alert("An error occurred while parsing the game state", "Failed to parse serialized game state, see stack trace in console.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Get the root of the main game view.
     *
     * @return main game view root
     */
    public RootView getRootView() {
        return rootView;
    }

    public Board getBoard() {
        return board;
    }

}
