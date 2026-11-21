import java.util.concurrent.CompletableFuture;

public class AsyncDemo {
    public static void main(String[] args) {
        // chay bat dong bo, roi bien doi ket qua
        CompletableFuture
            .supplyAsync(() -> "du lieu thoi")   // chay tren pool ForkJoin
            .thenApply(String::toUpperCase)      // bien doi ket qua
            .thenApply(s -> s + "!")
            .thenAccept(System.out::println);    // tieu thu ket qua

        // ket hop 2 bat dong bo doc lap
        CompletableFuture<Integer> giaGoc = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Integer> thue = CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Integer> tong = giaGoc.thenCombine(thue, Integer::sum);
        System.out.println(tong.join()); // 110

        // dung 3 request song song, cho tat ca xong
        CompletableFuture.allOf(
            CompletableFuture.runAsync(AsyncDemo::goiApiA),
            CompletableFuture.runAsync(AsyncDemo::goiApiB),
            CompletableFuture.runAsync(AsyncDemo::goiApiC)
        ).join();
    }

    static void goiApiA() { /* ... */ }
    static void goiApiB() { /* ... */ }
    static void goiApiC() { /* ... */ }
}
