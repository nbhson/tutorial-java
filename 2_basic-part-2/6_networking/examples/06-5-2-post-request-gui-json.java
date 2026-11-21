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
