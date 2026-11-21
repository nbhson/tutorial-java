public class DownloadTask extends Thread {
    private final String fileName;

    public DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        // code trong run() chay tren thread moi
        for (int i = 1; i <= 3; i++) {
            System.out.println(fileName + " - da tai " + (i * 30)
                    + "% - " + Thread.currentThread().getName());
            try {
                Thread.sleep(100); // gia lap thoi gian tai
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DownloadTask t1 = new DownloadTask("video.mp4");
        DownloadTask t2 = new DownloadTask("music.mp3");

        t1.start(); // KHONG goi run() truc tiep!
        t2.start();

        System.out.println("Main tiep tuc chay trong khi download dang xay ra");
    }
}
