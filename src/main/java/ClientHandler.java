import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    //respond command tika
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                switch (inputLine.toUpperCase()) {
                    case "TIME":
                        out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        break;
                    case "DATE":
                        out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        break;
                    case "UPTIME":
                        out.println(ServerMain.getUptime() + " seconds");
                        break;
                    case "HELP": // time ,date,uptime,bye commanda nemeinm help
                        out.println("Available commands: TIME, DATE, UPTIME, BYE, HELP");
                        break;
                    case "BYE":
                        out.println("Byee Byee !");
                        return;
                    default:
                        out.println("Unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

