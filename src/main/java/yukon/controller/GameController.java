package yukon.controller;

import yukon.view.GameView;

import java.io.IOException;

public class GameController {

    private final GameView gameView;
    private TcpClient client;

    public GameController() {
        gameView = new GameView();

        try {
            client = new TcpClient("", 0);
        } catch (IOException e) {
            System.out.println("failed to connect to backend");
        }
    }

    public javafx.scene.layout.Pane getGameView() {
        return gameView.getRoot();
    }

}
