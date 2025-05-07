package yukon.view;

import javafx.scene.control.Alert;

public class Util {

    /**
     * Display an alert.
     *
     * @param title     the alert title
     * @param message   the alert message
     * @param alertType the type of alert
     */
    public static void alert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Converts seconds to mm:ss format
     */
    public static String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
