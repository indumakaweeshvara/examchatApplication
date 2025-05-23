
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerMain {
    private static final int PORT = 12345;
    private static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getUptime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}

