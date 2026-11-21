import java.io.*;
import java.net.*;

public class ModernEchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Server (virtual threads) dang chay...");

            while (true) {
                Socket client = server.accept();

                // ✅ moi client 1 virtual thread — hang chuc nghin client van nhe
                Thread.startVirtualThread(() -> xuLyClient(client));
            }
        }
    }

    static void xuLyClient(Socket client) {
        try (client;
             var in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
             var out = new PrintWriter(client.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                out.println(line.toUpperCase()); // xu ly "nang" van duoc — block I/O khong ton tai nguyen
            }
        } catch (IOException e) {
            System.err.println("Client ngat: " + e.getMessage());
        }
    }
}
