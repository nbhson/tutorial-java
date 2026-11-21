import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        // Ket noi — neu server chua chay se nem ConnectException
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedReader keyboard = new BufferedReader(
                     new InputStreamReader(System.in))) {

            System.out.println("Da ket noi " + host + ":" + port);

            String userInput;
            while ((userInput = keyboard.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server tra loi: " + in.readLine());
                if ("exit".equalsIgnoreCase(userInput)) break;
            }
        } catch (UnknownHostException e) {
            System.err.println("Khong tim thay host: " + host);
        } catch (IOException e) {
            System.err.println("Loi I/O: " + e.getMessage());
        }
    }
}
