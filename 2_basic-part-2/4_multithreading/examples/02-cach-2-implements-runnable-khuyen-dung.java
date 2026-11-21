public class DownloadTask implements Runnable {
    private final String fileName;

    public DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Dang tai: " + fileName);
    }
}

// Java 8+ co the dung lambda
Runnable task = () -> System.out.println("Cong viec nhe");
Thread t = new Thread(task);
t.start();
