package yukon.controller;

/**
 * The exception thrown when communication with the TCP server fails.
 */
public class ServerCommunicationException extends RuntimeException {
    public ServerCommunicationException(String message) {
        super(message);
    }
}
