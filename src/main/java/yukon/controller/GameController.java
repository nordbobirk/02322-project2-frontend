package yukon.controller;

import javafx.application.Platform;
import yukon.model.Board;
import yukon.util.GameParser;
import yukon.view.GameView;

import java.io.IOException;

public class GameController {

    /**
     * Singleton instance
     */
    private static GameController instance;

    /**
     * Game view instance
     */
    private final GameView gameView;

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
        gameView = new GameView();

        try {
            client = new TcpClient("localhost", 12345);
        } catch (IOException e) {
            throw new ServerCommunicationException("Failed to establish a connection with the server");
        }

        gameView.getSendButton().setOnAction(e -> sendMessage());
        gameView.getCommandField().setOnAction(e -> sendMessage());
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
     * Tell the TCP client to send a message to the server,
     * getting the message from the command field in the main
     * game view.
     */
    private void sendMessage() {
        String message = gameView.getCommandField().getText().trim();

        if (message.isEmpty()) {
            return;
        }

        try {
            client.sendMessage(message);
            awaitResponse();
        } catch (Exception ex) {
            gameView.getBoardArea().setText("Error sending command: " + ex.getMessage());
        }
        gameView.getCommandField().clear();
    }

    /**
     * Wait for a response from the server.
     */
    private void awaitResponse() {
        try {
            String response = client.receiveResponse();
            Platform.runLater(() -> gameView.getBoardArea().setText(response));
            GameParser.parseGame(response);
        } catch (Exception e) {
            Platform.runLater(() -> gameView.getBoardArea().setText("Error receiving response: " + e.getMessage()));
        }
    }

    /**
     * Get the root of the main game view.
     *
     * @return main game view root
     */
    public javafx.scene.layout.Pane getGameView() {
        return gameView.getRoot();
    }

}
