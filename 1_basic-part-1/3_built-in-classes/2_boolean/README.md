# Lớp Boolean

- Lớp `Boolean` trong Java bọc giá trị kiểu nguyên thủy `boolean` trong một đối tượng. Một đối tượng `Boolean` chứa duy nhất một field kiểu `boolean`.

## Các constructor của lớp Boolean

| # | Constructor | Mô tả |
|---|-------------|-------|
| 1 | `Boolean(boolean value)` | Tạo đối tượng `Boolean` biểu diễn giá trị `value` |
| 2 | `Boolean(String s)` | Tạo đối tượng `Boolean` mang giá trị `true` nếu chuỗi không phải `null` và bằng `"true"` (không phân biệt hoa thường); ngược lại là `false` |

## Các phương thức của lớp Boolean

| # | Phương thức | Mô tả |
|---|-------------|-------|
| 1 | `boolean booleanValue()` | Trả về giá trị của đối tượng `Boolean` dưới dạng kiểu nguyên thủy `boolean` |
| 2 | `int compareTo(Boolean b)` | So sánh thể hiện `Boolean` này với thể hiện khác |
| 3 | `boolean equals(Object obj)` | Trả về `true` khi và chỉ khi tham số khác `null` và là đối tượng `Boolean` mang cùng giá trị |
| 4 | `static boolean getBoolean(String name)` | Trả về `true` khi và chỉ khi system property có tên tương ứng tồn tại và bằng chuỗi `"true"` |
| 5 | `int hashCode()` | Trả về mã băm của đối tượng `Boolean` này |
| 6 | `int hashCode(boolean value)` | Trả về mã băm cho một giá trị `boolean` cụ thể — tương thích với `Boolean.hashCode()` |
| 7 | `static boolean logicalAnd(boolean a, boolean b)` | Trả về kết quả của phép AND logic trên hai toán hạng |
| 8 | `static boolean logicalOr(boolean a, boolean b)` | Trả về kết quả của phép OR logic trên hai toán hạng |
| 9 | `static boolean logicalXor(boolean a, boolean b)` | Trả về kết quả của phép XOR logic trên hai toán hạng |
| 10 | `static boolean parseBoolean(String s)` | Phân tích chuỗi thành `boolean` |
| 11 | `String toString()` | Trả về chuỗi biểu diễn giá trị của đối tượng `Boolean` này |
| 12 | `static String toString(boolean b)` | Trả về chuỗi biểu diễn giá trị `boolean` chỉ định |
| 13 | `static Boolean valueOf(boolean b)` | Trả về thể hiện `Boolean` biểu diễn giá trị `boolean` chỉ định |
| 14 | `static Boolean valueOf(String s)` | Trả về `Boolean` có giá trị biểu diễn bởi chuỗi chỉ định |

## Ví dụ

```java
package com.tutorialspoint;
public class BooleanDemo {
   public static void main(String[] args) {

      // create 2 Boolean objects b1, b2
      Boolean b1, b2;

      // assign values to b1, b2
      b1 = Boolean.valueOf(true);
      b2 = Boolean.valueOf(false);

      // create an int res
      int res;

      // compare b1 with b2
      res = b1.compareTo(b2);

      String str1 = "Both values are equal ";
      String str2 = "Object value is true";
      String str3 = "Argument value is true";
      if( res == 0 ) {
      	System.out.println( str1 );
      } else if( res > 0 ) {
         System.out.println( str2 );
      } else if( res < 0 ) {
         System.out.println( str3 );
      }
   }
}
```

Kết quả: vì `true` so sánh với `false` cho giá trị dương, chương trình in ra `Object value is true`.
