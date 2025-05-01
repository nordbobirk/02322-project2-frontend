package yukon.controller;

import javafx.application.Platform;
import yukon.view.GameView;

import java.io.IOException;

public class GameController {

    private final GameView gameView;
    private TcpClient client;

    public GameController() {
        gameView = new GameView();

        try {
            client = new TcpClient("localhost", 12345);
        } catch (IOException e) {
            // FIXME
            System.out.println("failed to connect to backend");
        }

        gameView.getSendButton().setOnAction(e -> sendMessage());
        gameView.getCommandField().setOnAction(e -> sendMessage());
    }

    public void disconnect() {
        try {
            client.close();
            System.out.println("disconnected from server");
        } catch (IOException e) {
            System.out.println("failed to close server connection");
        }
    }

    private void sendMessage() {
        String message = gameView.getCommandField().getText().trim();

        if (message.isEmpty()) {
            return;
        }

        try {
            client.sendMessage(message);
            refreshBoard();
        } catch (Exception ex) {
            gameView.getBoardArea().setText("Error sending command: " + ex.getMessage());
        }
        gameView.getCommandField().clear();
    }

    private void refreshBoard() {
        try {
            String response = client.receiveResponse();
            // TODO: parse and render better once format is decided
            Platform.runLater(() -> gameView.getBoardArea().setText(response));
        } catch (Exception e) {
            Platform.runLater(() -> gameView.getBoardArea().setText("Error receiving response: " + e.getMessage()));
        }
    }

    public javafx.scene.layout.Pane getGameView() {
        return gameView.getRoot();
    }

}
