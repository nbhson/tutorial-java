import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;

        // Mo cua lang nghe tai port 8080
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server dang lang nghe tai port " + port);

            while (true) {
                // block cho den khi co client ket noi
                Socket client = serverSocket.accept();
                System.out.println("Client da ket noi: " + client.getRemoteSocketAddress());

                // xu ly client nay tren thread rieng
                new Thread(() -> xuLyClient(client)).start();
            }
        }
    }

    static void xuLyClient(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {   // doc den khi client ngat
                System.out.println("Nhan: " + line);
                if ("exit".equalsIgnoreCase(line)) break;
                out.println("Echo: " + line);          // phan hoi lai
            }
        } catch (IOException e) {
            System.err.println("Loi client: " + e.getMessage());
        }
        // try-with-resources tu dong dong socket
    }
}
