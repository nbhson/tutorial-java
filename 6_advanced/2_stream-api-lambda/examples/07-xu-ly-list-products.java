public record Product(String name, String category, double price, int stock) {}

public class StreamExamples {

    public static void main(String[] args) {
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 25000, 10),
            new Product("Phone", "Electronics", 15000, 5),
            new Product("T-Shirt", "Clothing", 150, 100),
            new Product("Shoes", "Clothing", 500, 50),
            new Product("Book", "Books", 80, 200)
        );

        // 1. Lọc sản phẩm giá > 100, sắp xếp theo giá
        List<Product> affordable = products.stream()
            .filter(p -> p.price() < 1000)
            .sorted(Comparator.comparingDouble(Product::price))
            .toList();
        System.out.println("Sản phẩm giá < 500: " + affordable);

        // 2. Lấy danh sách tên sản phẩm (uppercase)
        List<String> names = products.stream()
            .map(Product::name)
            .toList();

        // 3. Tính tổng giá trị tồn kho
        double totalValue = products.stream()
            .mapToDouble(p -> p.price() * p.quantity())
            .sum();

        // 4. Group by category
        Map<String, List<Product>> byCategory = products.stream()
            .collect(Collectors.groupingBy(Product::category));

        // 5. Tìm sản phẩm có giá cao nhất
        Optional<Product> mostExpensive = products.stream()
            .max(Comparator.comparingDouble(Product::price));

        // 6. Đếm theo category
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::category,
                Collectors.counting()
            ));

        // 7. Partition (chia 2 nhóm theo điều kiện)
        Map<Boolean, List<Product>> partition = products.stream()
            .collect(Collectors.partitioningBy(p -> p.price() < 1000));

        // 8. Chuỗi nối từ danh sách tên
        String nameStr = products.stream()
            .map(Product::name)
            .collect(Collectors.joining(", ", "[", "]"));
        // "[Laptop, T-Shirt, Shoes, Book]"
    }
}
