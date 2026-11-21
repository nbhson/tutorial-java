Socket socket = new Socket();
socket.connect(new InetSocketAddress(host, port), 3000); // timeout ket noi 3s
socket.setSoTimeout(5000);       // timeout khi doc du lieu 5s
socket.setTcpNoDelay(true);      // tat Nagle's algorithm — gui ngay, do tre thap
socket.setKeepAlive(true);       // giu ket noi song khi long idle
