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
