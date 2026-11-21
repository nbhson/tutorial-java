import java.util.concurrent.*;

// Hang doi giua producer va consumer — khong can tu viet wait/notify
BlockingQueue<String> hangDoi = new ArrayBlockingQueue<>(100);

new Thread(() -> {           // producer
    try {
        hangDoi.put("don hang 1"); // block neu queue day
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

new Thread(() -> {           // consumer
    try {
        String don = hangDoi.take(); // block neu queue trong
        System.out.println("Xu ly: " + don);
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

// Collection thread-safe:
ConcurrentHashMap<String, Integer> dem = new ConcurrentHashMap<>();
dem.merge("an", 1, Integer::sum);  // nguyen tu, an toan giua cac thread

CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();
listeners.add("a"); // moi lan ghi tao ban sao — tot cho doc nhieu ghi it
