// Tu Java 21, HttpClient implement AutoCloseable
try (HttpClient client = HttpClient.newHttpClient()) {
    HttpResponse<String> res = client.send(
        HttpRequest.newBuilder(URI.create("https://example.com")).build(),
        HttpResponse.BodyHandlers.ofString());
    System.out.println(res.statusCode());
} // client tu dong dong, giai phong tai nguyen
