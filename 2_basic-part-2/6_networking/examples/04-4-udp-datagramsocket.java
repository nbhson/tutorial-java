import java.net.*;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(9090)) {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // block cho goi tin

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("UDP nhan tu " + packet.getAddress()
                        + ":" + packet.getPort() + " → " + msg);

                // phan hoi ve dung dia chi nguoi gui
                byte[] reply = ("OK " + msg).getBytes();
                socket.send(new DatagramPacket(reply, reply.length,
                        packet.getAddress(), packet.getPort()));
            }
        }
    }
}

public class UdpClient {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(2000); // cho toi da 2 giay

            byte[] data = "ping".getBytes();
            InetAddress addr = InetAddress.getByName("localhost");

            socket.send(new DatagramPacket(data, data.length, addr, 9090));

            byte[] buffer = new byte[1024];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(reply);
                System.out.println("Nhan: " + new String(reply.getData(), 0, reply.getLength()));
            } catch (SocketTimeoutException e) {
                System.out.println("Goi tin mat trong duong — UDP khong bao dam!"); // binh thuong voi UDP
            }
        }
    }
}
