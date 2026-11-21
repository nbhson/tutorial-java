import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> tinhTong = () -> {
            Thread.sleep(500); // gia lap tinh toan nang
            return 1 + 2 + 3;
        };

        Future<Integer> future = executor.submit(tinhTong);

        // ... lam viec khac trong khi cho ...

        Integer ketQua = future.get(); // BLOCK cho den khi co ket qua
        System.out.println(ketQua);    // 6

        executor.shutdown();
    }
}
