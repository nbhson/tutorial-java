# L — Liskov Substitution Principle (Nguyên tắc thay thế Liskov)

> **Mọi nơi dùng lớp cha phải chạy đúng khi thay bằng lớp con.** Lớp con không được "làm hỏng lời hứa" của lớp cha — nếu hỏng, kế thừa đó là sai từ thiết kế.

## 1. Định nghĩa

**Tầng 1 — Trực giác:** Nếu một "chim cánh cụt" kế thừa "chim" nhưng **không bay được**, thì mọi đoạn code viết cho "chim" với giả định `bay()` hoạt động sẽ nổ tung khi gặp cánh cụt. Đó là vi phạm LSP — cánh cụt không nên kế thừa "chim bay".

**Tầng 2 — Định nghĩa (Barbara Liskov, 1987):** *Nếu S là subtype của T, thì object kiểu T có thể được thay bằng object kiểu S mà không làm thay đổi tính đúng đắn của chương trình.*

**Tầng 3 — Vì sao?** Đa hình chỉ có ích khi caller có thể **tin tưởng hợp đồng** của lớp cha:

- Preconditions: lớp con không được **thắt chặt** điều kiện vào.
- Postconditions: lớp con không được **lỏng lẻo** kết quả ra.
- Invariants: trạng thái của lớp con phải vẫn đúng như lớp cha hứa.

Nếu lớp con phá hợp đồng, caller buộc phải `instanceof` để phòng hờ → mất lợi ích đa hình, sinh code rác.

## 2. Ví dụ ❌ vi phạm kinh điển

```java
// ✗ hinh vuong ke thua hinh chu nhat — nghe logic nhung pha hop dong
class ChuNhat {
    protected int w, h;

    public void setW(int w) { this.w = w; }
    public void setH(int h) { this.h = h; }
    public int dienTich()   { return w * h; }
}

class Vuong extends ChuNhat {
    @Override
    public void setW(int w) { this.w = w; this.h = w; } // dong bo 2 canh
    @Override
    public void setH(int h) { this.w = h; this.h = h; }
}

public class Demo {
    static void test(ChuNhat cn) {
        cn.setW(5);
        cn.setH(4);
        // Hop dong voi ChuNhat: w=5, h=4 → dien tich = 20
        if (cn.dienTich() != 20) {
            System.out.println("SAI! Dien tich = " + cn.dienTich()); // Vuong cho 16!
        }
    }

    public static void main(String[] args) {
        test(new ChuNhat()); // OK
        test(new Vuong());   // ✗ VI PHAM LSP — pha ky vọng của caller
    }
}
```

**Vì sao sai?** Caller của `ChuNhat` có quyền kỳ vọng "setW không ảnh hưởng h". `Vuong` phá kỳ vọng đó → mọi code generic xử lý `ChuNhat` đều có thể sai.

## 3. Ví dụ vi phạm khác — làm rỗng method cha

```java
// ✗ da dạng: class con "làm rỗng" method của cha
abstract class NguoiChoi {
    abstract void danhCo();
}

class MayTinh implements NguoiChoi {
    public void danhCo() { /* AI danh co */ }
}

class NguoiXem implements NguoiChoi {
    public void danhCo() {
        throw new UnsupportedOperationException(); // ✗ caller không thể tin NguoiChoi nữa
    }
}
```

Cái gì `throw UnsupportedOperationException` trong override = gần như chắc chắn vi phạm LSP (và ISP — xem bài 4).

## 4. Ví dụ ✅ tuân thủ — trong pizza-app

```java
// PizzaStore abstract hứa: "orderPizza(type) sẽ chạy đủ 4 bước prepare→box"
// Mọi subclass đều giữ đúng hợp đồng đó:

public class HuePizzaStore extends PizzaStore {
    @Override
    protected Pizza CreatePizza(String type) {
        switch (type) {
            case "cheese": return new HueCheesePizza();
            case "greek":  return new HueGreekPizza();
            default:       return new HuePepperonPizza();
        }
    }
}

// Caller hoàn toàn an tâm:
PizzaStore store = new HuePizzaStore();   // có thể thay SGPizzaStore, DNPizzaStore
store.orderPizza("cheese");               // hợp đồng không đổi theo subclass
```

Không subclass nào `throw` lung tung, không subclass nào "quên" gọi `prepare()`. Đó là LSP đúng chuẩn: **thay thế được, hành vi tương thích**.

## 5. Bài toán vuông/chữ nhật — giải đúng bằng thiết kế

✅ Khi hành vi **khác bản chất**, đừng ép kế thừa — tách abstraction chung nhỏ hơn:

```java
interface Hinh {
    double dienTich();
}

record ChuNhat(double w, double h) implements Hinh {
    public double dienTich() { return w * h; }
}

record Vuong(double canh) implements Hinh {   // không extends ChuNhat!
    public double dienTich() { return canh * canh; }
}
```

Cả hai cùng thỏa hợp đồng `dienTich()` — không ai phá lời hứa của ai.

## 6. Dấu hiệu nhận biết vi phạm

- 🔴 Override method rồi `throw UnsupportedOperationException` / để trống.
- 🔴 Override làm method **ném exception dễ hơn** cha (thắt chặt preconditions).
- 🔴 Override trả kết quả **hẹp/khác** hợp đồng cha (ví dụ trả null khi cha hứa luôn có).
- 🔴 Caller bắt buộc `instanceof` trước khi gọi để "phòng" một số subclass.
- 🔴 Tên class con chứa "Nhưng..." (VinhNhưngKhongDi...).

❌ **Pitfall:** "Compile được" ≠ đúng LSP. LSP là về **hành vi**, không phải chữ ký method. Java chỉ kiểm tra chữ ký; LSP phải do bạn tự bảo vệ bằng thiết kế và test.

## 7. Bài tập

1. Cho `Chim { bay(); }` và `CanhCut extends Chim` không bay được. Refactor theo 2 cách: (a) tách `CoTheBay` interface, (b) đổi hierarchy. So sánh.
2. Tìm một override trong dự án của bạn mà subclass làm "ngược" kỳ vọng của cha. Sửa lại cho đúng hợp đồng hoặc tách abstraction.
3. Viết test generic `void kiemTra(Hinh h)` hoạt động đúng cho mọi `Hinh` — chứng minh design mới tuân thủ LSP.

## 8. Tóm tắt

- LSP: lớp con thay lớp cha mà **không phá hợp đồng** (pre/post-conditions, invariants).
- Kế thừa sai (Vuong extends ChuNhat) trông hợp lý về "tự nhiên" nhưng phá hành vi.
- `throw UnsupportedOperationException` trong override = mùi code LSP/ISP.
- Fix: tách abstraction nhỏ hơn phản ánh đúng hành vi, hoặc composition.
