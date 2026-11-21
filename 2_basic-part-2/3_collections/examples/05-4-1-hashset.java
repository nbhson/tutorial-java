import java.util.HashSet;
import java.util.Set;

public class SetDemo {
    public static void main(String[] args) {
        Set<String> emails = new HashSet<>();
        emails.add("an@gmail.com");
        emails.add("binh@gmail.com");
        emails.add("an@gmail.com");         // bi tu choi, tra ve false

        System.out.println(emails.size());  // 2 - khong trung lap
        System.out.println(emails.contains("an@gmail.com")); // true, O(1)

        // Phep tap hop
        Set<String> nhom1 = Set.of("a", "b", "c");
        Set<String> nhom2 = new HashSet<>(Set.of("b", "c", "d"));

        Set<String> giao = new HashSet<>(nhom1);   // copy de tranh sua Set.of
        giao.retainAll(nhom2);                     // {b, c}

        Set<String> hop = new HashSet<>(nhom1);
        hop.addAll(nhom2);                         // {a, b, c, d}
    }
}
