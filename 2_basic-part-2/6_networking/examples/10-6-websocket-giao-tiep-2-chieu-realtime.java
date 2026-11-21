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
