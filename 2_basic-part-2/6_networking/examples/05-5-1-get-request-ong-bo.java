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
