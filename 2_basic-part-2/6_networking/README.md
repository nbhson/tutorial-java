# Networking (Lập trình mạng trong Java)

> Mọi ứng dụng hiện đại đều "nói chuyện" qua mạng: gọi REST API, kết nối database, gửi tin nhắn realtime. Bài này đi từ nguyên lý socket thô nhất đến `HttpClient` hiện đại của Java 21 và WebSocket.

## 1. Mô hình cơ bản: client — server qua TCP

**Tầng 1 — Trực giác:** TCP socket giống **cuộc gọi điện thoại**: một bên gọi (client), một bên nghe máy (server), kết nối được thiết lập rồi hai bên nói chuyện tới khi gác máy. UDP thì giống **gửi thiệp**: nhanh, rẻ, nhưng không đảm bảo đến nơi và không theo thứ tự.

**Tầng 2 — Định nghĩa:**

- **IP address** — địa chỉ máy trên mạng (ví dụ `142.250.4.100`).
- **Port** — "cửa số" của tiến trình trên máy đó (0–65535; dưới 1024 cần quyền admin: 80 HTTP, 443 HTTPS).
- **TCP socket** — kênh truyền 2 chiều, đảm bảo dữ liệu đến đúng, đúng thứ tự.
- **UDP datagram** — gói tin độc lập, không đảm bảo, dùng cho game realtime, stream video.

**Tầng 3 — Vì sao Java lập trình viên cần hiểu socket dù đã có framework?** Vì mọi tầng cao hơn (HTTP client, JPA connection pool, gRPC) đều đứng trên socket. Hiểu socket giúp bạn debug timeout, connection reset, port đã bận (EADDRINUSE) — những lỗi không framework nào che được.

## 2. TCP Server — ServerSocket

```java
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
```

**Điểm cần chú ý:**

- `accept()` **blocking** — server đứng đợi cho tới khi có client.
- `try (client; ...)` — từ Java 9, biến đã khai báo bên ngoài cũng có thể được dùng trong try-with-resources để tự đóng.
- Mỗi client nên chạy trên thread riêng (hoặc virtual thread từ Java 21) nếu không server chỉ phục vụ được một client tại một thời điểm.

## 3. TCP Client — Socket

```java
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
```

### Cấu hình socket quan trọng

```java
Socket socket = new Socket();
socket.connect(new InetSocketAddress(host, port), 3000); // timeout ket noi 3s
socket.setSoTimeout(5000);       // timeout khi doc du lieu 5s
socket.setTcpNoDelay(true);      // tat Nagle's algorithm — gui ngay, do tre thap
socket.setKeepAlive(true);       // giu ket noi song khi long idle
```

❌ **Pitfall:** Không đặt timeout → thread treo **vĩnh viễn** nếu server im lặng. Trong production, luôn set cả connect timeout lẫn read timeout.

❌ **Pitfall:** `PrintWriter` nuốt exception — `out.println()` có lỗi không báo ngay. Kiểm tra `out.checkError()` hoặc dùng `BufferedWriter` + `flush()` + bắt `IOException`.

## 4. UDP — DatagramSocket

```java
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
```

**Vì sao vẫn cần UDP khi TCP tốt hơn mọi mặt?** Vì TCP có chi phí: bắt tay 3 bước (handshake), cơ chế truyền lại (retransmission), kiểm soát lưu lượng. Với game FPS hoặc livestream, gói tin muộn 500ms thì **thà mất còn hơn chờ** — UDP cho phép bạn quyết định chính sách mất gói.

## 5. HttpClient — chuẩn hiện đại (Java 11+, hoàn thiện ở Java 21)

`HttpURLConnection` cũ đã lỗi thời. Từ Java 11, Java có **HttpClient** hiện đại: hỗ trợ HTTP/2, bất đồng bộ, và từ **Java 21** có **autoCloseable** (dùng được với try-with-resources).

### 5.1. GET request — đồng bộ

```java
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class HttpGetDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)      // uu tien HTTP/2, tu fallback ve 1.1
                .connectTimeout(Duration.ofSeconds(5))   // timeout ket noi
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/octocat"))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))         // timeout cho toan bo request
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode()); // 200
        System.out.println("Body: "   + response.body());
    }
}
```

### 5.2. POST request — gửi JSON

```java
public class HttpPostDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String json = """
                {
                    "title": "Hoc Java 21",
                    "body": "Noi dung bai viet",
                    "userId": 1
                }
                """; // text block tu Java 15 — khong can + hoac \n

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode()); // 201 Created
        System.out.println(response.body());
    }
}
```

### 5.3. Bất đồng bộ — sendAsync

```java
public class HttpAsyncDemo {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req1 = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/octocat")).build();
        HttpRequest req2 = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/torvalds")).build();

        // 2 request chay song song
        CompletableFuture<HttpResponse<String>> f1 = client.sendAsync(req1, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> f2 = client.sendAsync(req2, HttpResponse.BodyHandlers.ofString());

        // cho ca hai xong
        CompletableFuture.allOf(f1, f2).join();

        System.out.println(f1.join().body());
        System.out.println(f2.join().body());
    }
}
```

### 5.4. Download file — xử lý body dạng stream

```java
import java.nio.file.*;

public class HttpDownloadDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.com/bao-cao.pdf"))
                .build();

        // BodyHandlers.ofFile — ghi truc tiep ra file, khong nhet vao bo nho
        HttpResponse<Path> response = client.send(request,
                HttpResponse.BodyHandlers.ofFile(Paths.get("bao-cao.pdf")));

        System.out.println("Da tai ve: " + response.body());
    }
}
```

**Các `BodyHandler` thường dùng:**

| Handler | Trả về | Dùng khi |
|---------|--------|----------|
| `ofString()` | `String` | JSON/text nhỏ |
| `ofFile(path)` | `Path` | File lớn — tránh tràn bộ nhớ |
| `ofByteArray()` | `byte[]` | Nhị phân nhỏ |
| `ofInputStream()` | `InputStream` | Xử lý stream thủ công |
| `discarding()` | `Void` | Chỉ cần status code |

### 5.5. Java 21: AutoCloseable

```java
// Tu Java 21, HttpClient implement AutoCloseable
try (HttpClient client = HttpClient.newHttpClient()) {
    HttpResponse<String> res = client.send(
        HttpRequest.newBuilder(URI.create("https://example.com")).build(),
        HttpResponse.BodyHandlers.ofString());
    System.out.println(res.statusCode());
} // client tu dong dong, giai phong tai nguyen
```

❌ **Pitfall:** Tạo `HttpClient` mới cho **mỗi request** — mất connection pool, chậm hẳn. ✅ `HttpClient` là **thread-safe và immutable**: tạo 1 instance dùng chung toàn ứng dụng.

❌ **Pitfall:** Gọi `.body()` trên `HttpResponse<String>` của response khổng lồ (ví dụ 500MB) → OutOfMemory. Body lớn → dùng `ofFile` hoặc `ofInputStream`.

## 6. WebSocket — giao tiếp 2 chiều realtime

**Tầng 1 — Trực giác:** HTTP bình thường là **hỏi—đáp**: client hỏi, server trả, xong. WebSocket giống **cuộc gọi luôn mở**: hai bên nói chuyện bất cứ lúc nào mà không cần "gọi lại" — nền tảng của chat, bảng giá chứng khoán, game online.

**Tầng 2 — Cách dùng trong Java (từ Java 11):**

```java
import java.net.URI;
import java.net.http.*;
import java.util.concurrent.*;

public class WebSocketDemo {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create("wss://echo.websocket.events"),
                        new WebSocket.Listener() {
                            @Override
                            public CompletionStage<?> onText(WebSocket ws,
                                    CharSequence data, boolean last) {
                                System.out.println("Nhan: " + data);
                                return WebSocket.Listener.super.onText(ws, data, last);
                            }

                            @Override
                            public CompletionStage<?> onClose(WebSocket ws,
                                    int statusCode, String reason) {
                                System.out.println("Dong ket noi: " + statusCode);
                                return WebSocket.Listener.super.onClose(ws, statusCode, reason);
                            }

                            @Override
                            public void onError(WebSocket ws, Throwable error) {
                                System.err.println("Loi: " + error.getMessage());
                            }
                        })
                .join();

        // gui tin nhan
        webSocket.sendText("Xin chao server!", true); // true = tin nhan hoan chinh

        // gui ping de giu ket noi
        webSocket.sendPing(java.nio.ByteBuffer.wrap(new byte[]{1}));

        Thread.sleep(2000); // cho nhan phan hoi
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Tam biet");
    }
}
```

**Điểm chính:**

- Giao tiếp WebSocket là **sự kiện (event-driven)**: bạn cài đặt `Listener` với các callback `onText`, `onBinary`, `onClose`, `onError`.
- `sendText(data, true)` — tham số `last` cho phép gửi một tin nhắn lớn thành nhiều mảnh (fragment).
- WebSocket **không có khái niệm request/response** — server có thể đẩy tin bất cứ lúc nào.

## 7. Server với virtual threads (Java 21) — kết hợp mọi thứ

```java
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
```

**Vì sao đây là mô hình server được yêu thích nhất hiện nay?** Vì code vẫn là dạng "đọc—viết" tuần tự dễ hiểu, nhưng nhờ virtual thread, mỗi kết nối có thread riêng mà không tốn 1MB stack như platform thread.

## 8. Bài tập

1. Viết server TCP chấp nhận tính toán: client gửi biểu thức như `5 + 3`, server trả kết quả. Cho phép 2 client đồng thời (thread/virtual thread).
2. Thêm tính năng chat: nhiều client kết nối vào một server, tin nhắn của client này được broadcast cho tất cả client khác (gợi ý: lưu tập hợp `CopyOnWriteArrayList<PrintWriter>`).
3. Dùng `HttpClient` gọi API công khai (ví dụ `https://api.exchangerate.host/latest`), parse JSON thủ công, in tỉ giá USD→VND.
4. Gọi 5 URL song song bằng `sendAsync` + `CompletableFuture.allOf`, in thời gian tổng — so sánh với gọi tuần tự.
5. Viết ứng dụng gửi file qua UDP: chia file thành các datagram nhỏ, client ghép lại (bỏ qua cơ chế truyền lại — thử nghiệm trên localhost).
6. Viết WebSocket client kết nối tới một dịch vụ giá coin thật (ví dụ Binance WebSocket), in giá cập nhật realtime ra console.

## 9. Tóm tắt

- TCP = kênh tin cậy 2 chiều (`ServerSocket` + `Socket`); UDP = gói tin không đảm bảo (`DatagramSocket`).
- Luôn đặt **connect timeout** và **read timeout**; luôn đóng socket bằng try-with-resources.
- Server nhiều client = mỗi client một thread — và từ Java 21, dùng **virtual thread** là lựa chọn mặc định.
- `HttpClient` (Java 11+) thay thế hoàn toàn `HttpURLConnection`: HTTP/2, `sendAsync`, `BodyHandlers` đa dạng; tạo **một** instance dùng chung.
- Từ Java 21, `HttpClient` là `AutoCloseable` — dùng được try-with-resources.
- WebSocket = kênh 2 chiều realtime, lập trình theo event listener, khác mô hình request/response của HTTP.
