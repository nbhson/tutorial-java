import java.util.*;

public class WordCount {
    public static void main(String[] args) {
        String text = "an bun pho an com bun an";
        String[] words = text.split(" ");

        // Cach 1: du loi
        Map<String, Integer> dem1 = new HashMap<>();
        for (String w : words) {
            if (dem1.containsKey(w)) {
                dem1.put(w, dem1.get(w) + 1);
            } else {
                dem1.put(w, 1);
            }
        }

        // Cach 2: gon hon - merge
        Map<String, Integer> dem2 = new HashMap<>();
        for (String w : words) {
            dem2.merge(w, 1, Integer::sum); // neu co roi cong them 1, chua co thi 1
        }

        System.out.println(dem2); // {an=3, bun=2, pho=1, com=1}
    }
}
