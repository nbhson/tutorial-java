// Cú pháp cơ bản
(parameters) -> expression
(parameters) -> { statements; }

// Ví dụ
Runnable r1 = () -> System.out.println("Hello");           // Không tham số
Consumer<String> c1 = s -> System.out.println(s);           // 1 tham số
BinaryOperator<Integer> add = (a, b) -> a + b;             // 2 tham số
Comparator<String> cmp = (s1, s2) -> s1.length() - s2.length();
