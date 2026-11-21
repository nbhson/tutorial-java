// cac thao tac dieu khien co ban
Thread t = new Thread(task);
t.start();          // khoi dong
t.join();           // main cho t chay xong
t.join(3000);       // cho toi da 3 giay
t.interrupt();      // bao huy (khong bat buoc dung ngay!)
t.setDaemon(true);  // thread phu, JVM thoat khong cho no
t.isAlive();        // kiem tra con song khong
