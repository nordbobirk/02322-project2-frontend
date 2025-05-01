package yukon.controller;

import java.io.*;
import java.net.Socket;

public class TcpClient {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TcpClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendMessage(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }

    public String receiveResponse() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

}
