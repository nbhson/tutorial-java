# Networking (Lập trình mạng trong Java)

> Mọi ứng dụng hiện đại đều "nói chuyện" qua mạng: gọi REST API, kết nối database, gửi tin nhắn realtime. Bài này đi từ nguyên lý socket thô nhất đến `HttpClient` hiện đại của Java 21 và WebSocket.

## 1. Mô hình cơ bản: client — server qua TCP

**Tầng 1 — Trực giác:** TCP socket giống **cuộc gọi điện thoại**: một bên gọi (client), một bên nghe máy (server), kết nối được thiết lập rồi hai bên nói chuyện tới khi gác máy. UDP thì giống **gửi thiệp**: nhanh, rẻ, nhưng không đảm bảo đến nơi và không theo thứ tự.

**Tầng 2 — Định nghĩa:**

- **IP address** — địa chỉ máy trên mạng (ví dụ `142.250.4.100`).
- **Port** — "cửa số" của tiến trình trên máy đó (0–65535; dưới 1024 cần quyền admin: 80 HTTP, 443 HTTPS).
- **TCP socket** — kênh truyền 2 chiều, đảm bảo dữ liệu đến đúng, đúng thứ tự.
- **UDP datagram** — gói tin độc lập, không đảm bảo, dùng cho game realtime, stream video.

> **Tóm gọn 1 câu:** TCP là gọi điện (bắt máy mới nói, nói thì chắc chắn tới), UDP là gửi thiệp (rẻ, nhanh, nhưng lạc thì mất) — chọn kênh theo yêu cầu độ tin cậy.

**Tầng 3 — Vì sao Java lập trình viên cần hiểu socket dù đã có framework?** Vì mọi tầng cao hơn (HTTP client, JPA connection pool, gRPC) đều đứng trên socket. Hiểu socket giúp bạn debug timeout, connection reset, port đã bận (EADDRINUSE) — những lỗi không framework nào che được.

## 2. TCP Server — ServerSocket

Mục này dựng bên "nghe máy": server mở cổng, đứng đợi và nhận cuộc gọi từ client.

> 📄 **Code ví dụ:** [`examples/01-2-tcp-server-serversocket.java`](examples/01-2-tcp-server-serversocket.java)

**Điểm cần chú ý:**

- `accept()` **blocking** — server đứng đợi cho tới khi có client.
- `try (client; ...)` — từ Java 9, biến đã khai báo bên ngoài cũng có thể được dùng trong try-with-resources để tự đóng.
- Mỗi client nên chạy trên thread riêng (hoặc virtual thread từ Java 21) nếu không server chỉ phục vụ được một client tại một thời điểm.

> **Tóm gọn 1 câu:** `ServerSocket` là tổng đài: `accept()` đứng đợi cuộc gọi, mỗi cuộc gọi thì nhả ra một socket riêng — muốn phục vụ nhiều người cùng lúc thì mỗi người một thread.

## 3. TCP Client — Socket

Mục này dựng bên "gọi máy": client chủ động kết nối tới server và hai bên bắt đầu trao đổi.

> 📄 **Code ví dụ:** [`examples/02-3-tcp-client-socket.java`](examples/02-3-tcp-client-socket.java)

### Cấu hình socket quan trọng

> 📄 **Code ví dụ:** [`examples/03-cau-hinh-socket-quan-trong.java`](examples/03-cau-hinh-socket-quan-trong.java)

❌ **Pitfall:** Không đặt timeout → thread treo **vĩnh viễn** nếu server im lặng. Trong production, luôn set cả connect timeout lẫn read timeout.

❌ **Pitfall:** `PrintWriter` nuốt exception — `out.println()` có lỗi không báo ngay. Kiểm tra `out.checkError()` hoặc dùng `BufferedWriter` + `flush()` + bắt `IOException`.

> **Tóm gọn 1 câu:** Client `Socket` đơn giản, nhưng hai dòng cấu hình timeout là mạng lưới an toàn — không có chúng, thread của bạn có thể treo vĩnh viễn chờ server im lặng.

## 4. UDP — DatagramSocket

Mục này nói về "gửi thiệp": nhanh, rẻ, không bắt tay — và vì sao game/livestream vẫn cần nó dù không đảm bảo.

> 📄 **Code ví dụ:** [`examples/04-4-udp-datagramsocket.java`](examples/04-4-udp-datagramsocket.java)

**Vì sao vẫn cần UDP khi TCP tốt hơn mọi mặt?** Vì TCP có chi phí: bắt tay 3 bước (handshake), cơ chế truyền lại (retransmission), kiểm soát lưu lượng. Với game FPS hoặc livestream, gói tin muộn 500ms thì **thà mất còn hơn chờ** — UDP cho phép bạn quyết định chính sách mất gói.

> **Tóm gọn 1 câu:** UDP là "gửi thiệp số lượng lớn": không đảm bảo, không xếp hàng — đúng thứ mà game và livestream cần, vì gói muộn còn vô dụng hơn gói mất.

## 5. HttpClient — chuẩn hiện đại (Java 11+, hoàn thiện ở Java 21)

Mục này thay thế "điện thoại dây cũ": HTTP client hiện đại của Java — hỗ trợ HTTP/2, gọi bất đồng bộ, và cách chọn đúng cách đọc response.

`HttpURLConnection` cũ đã lỗi thời. Từ Java 11, Java có **HttpClient** hiện đại: hỗ trợ HTTP/2, bất đồng bộ, và từ **Java 21** có **autoCloseable** (dùng được với try-with-resources).

### 5.1. GET request — đồng bộ

> 📄 **Code ví dụ:** [`examples/05-5-1-get-request-ong-bo.java`](examples/05-5-1-get-request-ong-bo.java)

### 5.2. POST request — gửi JSON

> 📄 **Code ví dụ:** [`examples/06-5-2-post-request-gui-json.java`](examples/06-5-2-post-request-gui-json.java)

### 5.3. Bất đồng bộ — sendAsync

> 📄 **Code ví dụ:** [`examples/07-5-3-bat-ong-bo-sendasync.java`](examples/07-5-3-bat-ong-bo-sendasync.java)

### 5.4. Download file — xử lý body dạng stream

> 📄 **Code ví dụ:** [`examples/08-5-4-download-file-xu-ly-body-dang.java`](examples/08-5-4-download-file-xu-ly-body-dang.java)

**Các `BodyHandler` thường dùng:**

| Handler | Trả về | Dùng khi |
|---------|--------|----------|
| `ofString()` | `String` | JSON/text nhỏ |
| `ofFile(path)` | `Path` | File lớn — tránh tràn bộ nhớ |
| `ofByteArray()` | `byte[]` | Nhị phân nhỏ |
| `ofInputStream()` | `InputStream` | Xử lý stream thủ công |
| `discarding()` | `Void` | Chỉ cần status code |

> **Cách đọc bảng này:** cột **Handler** là cách bạn "đặt cách nhận hàng" cho response, đọc cột **Dùng khi** để chọn — nguyên tắc chung: response càng lớn càng phải tránh `ofString()` để không tràn bộ nhớ.

### 5.5. Java 21: AutoCloseable

> 📄 **Code ví dụ:** [`examples/09-5-5-java-21-autocloseable.java`](examples/09-5-5-java-21-autocloseable.java)

❌ **Pitfall:** Tạo `HttpClient` mới cho **mỗi request** — mất connection pool, chậm hẳn. ✅ `HttpClient` là **thread-safe và immutable**: tạo 1 instance dùng chung toàn ứng dụng.

❌ **Pitfall:** Gọi `.body()` trên `HttpResponse<String>` của response khổng lồ (ví dụ 500MB) → OutOfMemory. Body lớn → dùng `ofFile` hoặc `ofInputStream`.

> **Tóm gọn 1 câu:** `HttpClient` là chiếc điện thoại thông minh thay máy dây cũ: tạo một cái dùng chung cả app, chọn `BodyHandler` theo cỡ hàng nhận, và nhớ timeout cho mọi cuộc gọi.

## 6. WebSocket — giao tiếp 2 chiều realtime

Mục này nói về kênh "cuộc gọi luôn mở" — khác căn bản với mô hình hỏi-đáp của HTTP.

**Tầng 1 — Trực giác:** HTTP bình thường là **hỏi—đáp**: client hỏi, server trả, xong. WebSocket giống **cuộc gọi luôn mở**: hai bên nói chuyện bất cứ lúc nào mà không cần "gọi lại" — nền tảng của chat, bảng giá chứng khoán, game online.

**Tầng 2 — Cách dùng trong Java (từ Java 11):**

> 📄 **Code ví dụ:** [`examples/10-6-websocket-giao-tiep-2-chieu-realtime.java`](examples/10-6-websocket-giao-tiep-2-chieu-realtime.java)

**Điểm chính:**

- Giao tiếp WebSocket là **sự kiện (event-driven)**: bạn cài đặt `Listener` với các callback `onText`, `onBinary`, `onClose`, `onError`.
- `sendText(data, true)` — tham số `last` cho phép gửi một tin nhắn lớn thành nhiều mảnh (fragment).
- WebSocket **không có khái niệm request/response** — server có thể đẩy tin bất cứ lúc nào.

> **Tóm gọn 1 câu:** WebSocket = cuộc gọi không gác máy: hai bên nói bất cứ lúc nào, lập trình theo listener (onText, onClose...) thay vì gửi yêu cầu chờ phản hồi.

## 7. Server với virtual threads (Java 21) — kết hợp mọi thứ

Mục này ghép tất cả kiến thức của bài: tổng đài ServerSocket + mỗi cuộc gọi một virtual thread nhẹ như không.

> 📄 **Code ví dụ:** [`examples/11-7-server-voi-virtual-threads-java-21-ket.java`](examples/11-7-server-voi-virtual-threads-java-21-ket.java)

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

> **Tóm gọn 1 câu:** Networking trong Java là quản các cuộc gọi: TCP/UDP là đường dây, `HttpClient` là tổng đài hiện đại, WebSocket là đường dây nóng luôn mở — và từ Java 21, virtual thread làm mọi cuộc gọi đều có người trực mà không tốn kém.
