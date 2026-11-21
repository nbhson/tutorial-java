import java.util.*;
import java.util.stream.*;

public class StreamDemo {
    record SanPham(String ten, String loai, int gia) {}

    public static void main(String[] args) {
        List<SanPham> ds = List.of(
            new SanPham("Laptop", "Dien tu", 20000),
            new SanPham("Chuot", "Dien tu", 300),
            new SanPham("Ban phim", "Dien tu", 700),
            new SanPham("Ghe", "Noi that", 1500)
        );

        // 1. filter + map + collect: loc dien tu gia < 1000, lay ten
        List<String> re = ds.stream()
                .filter(p -> p.loai().equals("Dien tu"))
                .filter(p -> p.gia() < 1000)
                .map(SanPham::ten)
                .toList();                          // [Chuot, Ban phim]

        // 2. sorted + limit: top 2 san pham re nhat
        List<SanPham> top2 = ds.stream()
                .sorted(Comparator.comparingInt(SanPham::gia))
                .limit(2)
                .toList();

        // 3. reduce: tong gia tri kho hang
        int tong = ds.stream()
                .mapToInt(SanPham::gia)
                .sum();                             // IntStream co sum san

        // 4. group: nhom theo loai
        Map<String, List<SanPham>> theoLoai = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai));

        // 5. group + dem
        Map<String, Long> soLuong = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai, Collectors.counting()));

        // 6. group + trung binh
        Map<String, Double> giaTB = ds.stream()
                .collect(Collectors.groupingBy(SanPham::loai,
                         Collectors.averagingInt(SanPham::gia)));

        // 7. anyMatch / allMatch / noneMatch
        boolean coDat = ds.stream().anyMatch(p -> p.gia() > 15000);
        boolean tatCaDuong = ds.stream().allMatch(p -> p.gia() > 0);

        // 8. min / max
        Optional<SanPham> datNhat = ds.stream()
                .max(Comparator.comparingInt(SanPham::gia));

        // 9. flatMap: lam phang danh sach cua danh sach
        List<List<Integer>> nhomDiem = List.of(List.of(8, 9), List.of(5, 7));
        List<Integer> tatCaDiem = nhomDiem.stream()
                .flatMap(List::stream)
                .toList();                          // [8, 9, 5, 7]

        // 10. toMap
        Map<String, Integer> giaTheoTen = ds.stream()
                .collect(Collectors.toMap(SanPham::ten, SanPham::gia));
    }
}
