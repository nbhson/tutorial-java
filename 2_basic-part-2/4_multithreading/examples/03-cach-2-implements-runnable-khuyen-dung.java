t.run();   // ✗ chay NGAY TRONG main thread, khong ton tai thread moi!
t.start(); // ✅ tao thread moi, JVM goi run() tren thread do
