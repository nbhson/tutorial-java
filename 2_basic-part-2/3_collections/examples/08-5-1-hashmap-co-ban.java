import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> tuoi = new HashMap<>();

        // Them / cap nhat
        tuoi.put("An", 25);
        tuoi.put("Binh", 30);
        tuoi.put("An", 26);              // key trung → ghi de, tra ve 25 (gia tri cu)

        // Doc
        System.out.println(tuoi.get("An"));         // 26
        System.out.println(tuoi.get("Chi"));        // null - khong co key nay

        // Doc an toan
        System.out.println(tuoi.getOrDefault("Chi", 0)); // 0
        System.out.println(tuoi.containsKey("Binh"));    // true

        // Cap nhat gia tri dung dang
        tuoi.putIfAbsent("Chi", 28);     // chi them khi chua co
        tuoi.computeIfAbsent("Dung", k -> 40); // tinh gia tri khi thieu

        // Xoa
        tuoi.remove("Binh");

        // Duyet - cach chuan
        for (Map.Entry<String, Integer> e : tuoi.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }

        // Duyet rieng key / rieng value
        for (String ten : tuoi.keySet())   { System.out.println(ten); }
        for (int t : tuoi.values())        { System.out.println(t); }

        // Duyet voi forEach (Java 8+)
        tuoi.forEach((ten, t) -> System.out.println(ten + " = " + t));
    }
}
