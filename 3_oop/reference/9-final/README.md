# Final 

Mục này nói về từ khóa "niêm phong" của Java: `final` — dán nhãn "không được sửa" lên biến, phương thức hoặc lớp tùy nơi bạn đặt. Đọc để biết dùng final ở đâu sẽ bảo vệ được gì.

Trong Java, từ khóa final được sử dụng để chỉ định rằng một biến, phương thức hoặc lớp không thể bị thay đổi hoặc ghi đè. Dưới đây là các cách sử dụng chính của final

Ví von: `final` như dây niêm phong trên hàng hóa — ai cắt dây là bị "la" ngay khi compile, không ai âm thầm thay thế được.

## Final Variables:

Mục này nói về mức "niêm phong" phổ biến nhất: biến final gán một lần là chốt luôn, không ai gán lại được.

Khi một biến được khai báo là final, giá trị của nó không thể thay đổi sau khi được gán lần đầu tiên. Điều này có nghĩa là bạn không thể gán lại giá trị cho biến đó.

Ví von: giống như khắc tên lên đá — khắc xong là không tẩy được.

> 📄 **Code ví dụ:** [`examples/01-final-variables.java`](examples/01-final-variables.java)

## Final Methods:

Mục này nói về phương thức "cấm ghi đè": hành vi của nó được bảo vệ nguyên vẹn qua mọi thế hệ lớp con.

Khi một phương thức được khai báo là final, nó không thể bị ghi đè (overridden) trong các lớp con. Điều này hữu ích khi bạn muốn bảo vệ một phương thức khỏi việc bị thay đổi hành vi trong các lớp kế thừa.

Ví von: như "công thức bí mật" của một quán lâu đời — con cháu nối nghiệp được học và dùng, nhưng không được phép đổi công thức.

> 📄 **Code ví dụ:** [`examples/02-final-methods.java`](examples/02-final-methods.java)

##  Final Classes:

Mục này nói về mức niêm phong cao nhất: lớp final "đóng cửa thừa kế" — không lớp nào được làm con của nó nữa.

Khi một lớp được khai báo là final, nó không thể được kế thừa. Điều này có nghĩa là không thể tạo ra một lớp con từ lớp final.

Ví von: như một di sản đã được trân trọng niêm yết — ai cũng được ngắm và dùng, nhưng không ai được "cải tạo thành dòng họ mới" từ nó.

> 📄 **Code ví dụ:** [`examples/03-final-classes.java`](examples/03-final-classes.java)

Tóm tắt:
- Biến final: Không thể thay đổi giá trị sau khi gán.
- Phương thức final: Không thể bị ghi đè trong lớp con.
- Lớp final: Không thể được kế thừa.

> **Tóm gọn 1 câu:** `final` là "dây niêm phong" — dán ở biến thì chốt giá trị, dán ở method thì cấm ghi đè, dán ở class thì đóng cửa thừa kế.
