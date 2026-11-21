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
