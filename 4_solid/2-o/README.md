# O — Open/Closed Principle (Mở cho mở rộng, đóng cho sửa đổi)

> **Code nên MỞ để mở rộng nhưng ĐÓNG để sửa đổi.** Muốn thêm hành vi mới? Thêm class mới — đừng chạm vào code cũ đã chạy ổn định.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Cầu electrical có **ổ cắm chuẩn**. Muốn thêm thiết bị mới, bạn cắm thêm — không phải đục tường đi lại dây điện. OCP thiết kế code theo cách đó: có "ổ cắm" (interface), thiết bị mới (class mới) cắm vào là chạy.

**Tầng 2 — Định nghĩa (Bertrand Meyer):** *"Software entities should be open for extension, but closed for modification."* — Mở rộng được (thêm hành vi mới) nhưng đóng với việc sửa (không phải thay code cũ).

**Tầng 3 — Vì sao?** Mỗi lần sửa code cũ là một lần **rủi ro gây regression** ở phần đã test, đã chạy production. OCP hướng mọi thay đổi về phía **thêm code mới** — code cũ không đổi, không cần retest toàn bộ.

## 2. Ví dụ ❌ vi phạm

```java
// ✗ moi khi them loai pizza moi phai sua switch nay — sua code da chay
class PizzaStore {
    Pizza orderPizza(String type) {
        Pizza pizza;
        if (type.equals("cheese")) {
            pizza = new DNCheesePizza();
        } else if (type.equals("greek")) {
            pizza = new DNGreekPizza();
        } else if (type.equals("pepperoni")) {
            pizza = new DNPepperonPizza();
        } else {
            throw new IllegalArgumentException("Khong biet loai: " + type);
        }
        pizza.prepare(); pizza.bake(); pizza.cut(); pizza.box();
        return pizza;
    }
}
```

**Vấn đề:** thêm pizza "seafood" cho Đà Nẵng, "chicken" cho Sài Gòn → phải mở file `PizzaStore` sửa if-else liên tục. Mỗi sửa là một lần nguy cơ phá luồng order đang chạy đúng.

## 3. Ví dụ ✅ tuân thủ — chính là cách pizza-app đã làm

```java
// ✅ abstract class "dong" voi sua doi, "mo" voi ke thua
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    protected abstract Pizza CreatePizza(String type); // "o cam" — diem mo rong
}

// Them khu vuc moi = TAO CLASS MOI, khong sua PizzaStore
public class NghePizzaStore extends PizzaStore {
    @Override
    protected Pizza CreatePizza(String type) {
        return switch (type) {
            case "cheese" -> new NgheCheesePizza();
            case "greek"  -> new NgheGreekPizza();
            default       -> new NghePepperonPizza();
        };
    }
}
```

Xem file thực tế `6-pizza-app/src/Store/DNPizzaStore.java`, `SGPizzaStore.java`, `HuePizzaStore.java` — ba store mở rộng cùng một "ổ cắm" mà không ai sửa `PizzaStore`.

Đây chính là pattern **Factory Method**: phần **bất biến** (quy trình order) nằm ở lớp cha, phần **có thể thay đổi** (cách tạo pizza) được đẩy xuống lớp con.

## 4. OCP với interface — mở rộng theo hướng đa hình

```java
// ✅ vi du khac: giam gia theo thue bao
interface KhuyenMai {
    double giamGia(double tongTien);
}

class KhuyenMaiVip implements KhuyenMai {
    public double giamGia(double t) { return t * 0.1; }
}

class KhuyenMaiBlackFriday implements KhuyenMai {
    public double giamGia(double t) { return t > 1_000_000 ? 200_000 : 0; }
}

class MayTinhHoaDon {
    private final List<KhuyenMai> khuyenMais;   // mo rong bang cach them phan tu
    MayTinhHoaDon(List<KhuyenMai> km) { this.khuyenMais = km; }

    double thanhToan(double tongTien) {
        double giam = khuyenMais.stream().mapToDouble(k -> k.giamGia(tongTien)).sum();
        return tongTien - giam;
    }
}
```

Thêm khuyến mãi mới = viết class mới + thêm vào list. `MayTinhHoaDon` không bị sửa một ký tự nào.

## 5. Dấu hiệu nhận biết vi phạm

- 🔴 `if-else`/`switch` kiểm tra type/loại xuất hiện ở **nhiều nơi** trong hệ thống.
- 🔴 Thêm một loại mới (loại tài khoản, phương thức thanh toán...) phải sửa 3-5 file cũ.
- 🔴 Class "gốc" liên tục được chạm vào trong git log.

❌ **Pitfall 1 — Áp dụng OCP sớm thái quá:** với 2 case, if-else đơn giản vẫn tốt hơn một hệ interface. Chỉ "đóng" khi bạn **đã thấy** yêu cầu thay đổi lặp lại (rule of three).

❌ **Pitfall 2 — Dùng kế thừa vô tội vạ để "mở rộng":** nhiều tầng `extends` chồng lên nhau khiến logic khó lần. OCP thường đạt được gọn hơn bằng **composition + interface** (strategy) hơn là thừa kế sâu.

❌ **Pitfall 3 — Sửa "nhẹ nhàng" bên trong class cũ vẫn tính là vi phạm OCP:** ví dụ sửa private method của `PizzaStore` cho khu vực mới — dù không đổi API, bạn vẫn đụng vào code cũ đã test.

## 6. Bài tập

1. Cho code điều hướng ưu đãi theo rank khách hàng bằng if-else (5 loại). Refactor sang OCP với interface `UuDai`. Đếm số file phải sửa khi thêm loại thứ 6 — trước và sau.
2. Trong pizza-app, thêm thành phố mới (ví dụ `CantHoPizzaStore` + 3 loại pizza) **không sửa bất kỳ file cũ nào**. Chạy lại `Main` để chứng minh.
3. Nhận diện một chỗ trong dự án của bạn mà mỗi lần thêm tính năng lại phải sửa switch. Chuyển sang strategy/interface.

## 7. Tóm tắt

- OCP: mở cho **mở rộng**, đóng cho **sửa đổi**.
- Công cụ chính: abstract class/interface + kế thừa hoặc composition; factory method trong pizza-app là ví dụ mẫu.
- Thêm tính năng = thêm code mới; code cũ đã test không bị chạm vào.
- Đừng lạm dụng: OCP có chi phí (nhiều interface/class), áp dụng ở nơi thay đổi thường xuyên.
