import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        List<String> monAn = new ArrayList<>();

        // Them phan tu
        monAn.add("Pho");
        monAn.add("Bun bo");
        monAn.add("Com tam");

        // Truy cap theo index - dac quyen cua List
        System.out.println(monAn.get(0));      // Pho
        System.out.println(monAn.size());      // 3
        System.out.println(monAn.contains("Pho")); // true

        // Chen vao giua
        monAn.add(1, "Banh xeo");              // Pho, Banh xeo, Bun bo, Com tam

        // Thay the
        monAn.set(2, "Bun cha");               // thay Bun bo bang Bun cha

        // Xoa
        monAn.remove("Com tam");               // xoa theo gia tri
        monAn.remove(0);                       // xoa theo index

        // Duyet
        for (String m : monAn) {
            System.out.println(m);
        }

        // Duyet voi index
        for (int i = 0; i < monAn.size(); i++) {
            System.out.println(i + ": " + monAn.get(i));
        }
    }
}
