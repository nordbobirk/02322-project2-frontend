package yukon.controller;

import java.io.*;
import java.net.Socket;

/**
 * Client for communication with the TCP server containing the game backend.
 */
public class TcpClient {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    /**
     * Setup a new TCP client using the given host and port.
     * @param host the host
     * @param port the port
     * @throws IOException if setup fails
     */
    public TcpClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Send a message to the server.
     * @param message the message to send to the server
     * @throws IOException if sending fails
     */
    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.flush();
    }

    /**
     * Awaits a response from the server.
     * @return the response
     * @throws IOException if reading response fails
     */
    public String receiveResponse() throws IOException {
        return in.readLine();
    }

    /**
     * Close the connection with the server.
     * @throws IOException if closing fails
     */
    public void close() throws IOException {
        socket.close();
    }

}
