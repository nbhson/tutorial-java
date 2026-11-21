// ============ Cách 2: Thread-safe với Double-Checked Locking ============
public class Logger {

    private static volatile Logger instance;  // volatile: đảm bảo thread-safe

    private Logger() {
        // Private constructor: không cho tạo từ bên ngoài
    }

    public static Logger getInstance() {
        if (instance == null) {                    // Check 1 (không lock)
            synchronized (Logger.class) {
                if (instance == null) {            // Check 2 (có lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[" + java.time.LocalTime.now() + "] " + message);
    }
}

// Sử dụng:
Logger logger = Logger.getInstance();
logger.info("Xin chào!");
