# Java - Deleting Files in Java

To delete a file in Java, you can use the File.delete() method. This method deletes the files or directory from the given path.

Nói bằng tiếng Việt: để xóa file trong Java, bạn dùng phương thức `File.delete()` — xóa file hoặc thư mục tại đường dẫn cho trước. Ví von: `delete()` như nút "dọn vào thùng rác" — bấm xong là mất, không có hộp thoại xác nhận, nên hãy chắc chắn trước khi gọi. Phương thức trả về `boolean`: `true` nếu xóa thành công, `false` nếu file không tồn tại hoặc đang bị khóa.

## Syntax

Mục này cho xem cú pháp gọi `delete()` — chỉ một dòng, nhưng trả về kết quả nên bạn cần kiểm tra.

> 📄 **Code ví dụ:** [`examples/01-syntax.java`](examples/01-syntax.java)

## Deleting File from Current Directory

Mục này xóa một file ở thư mục hiện tại — tình huống phổ biến nhất trong thực tế.

> 📄 **Code ví dụ:** [`examples/02-deleting-file-from-current-directory.java`](examples/02-deleting-file-from-current-directory.java)

## Deleting All Files From Given Directory

Mục này nâng lên một bậc: xóa **tất cả** file trong một thư mục — cần duyệt qua từng file rồi gọi `delete()` cho từng cái.

> 📄 **Code ví dụ:** [`examples/03-deleting-all-files-from-given-directory.java`](examples/03-deleting-all-files-from-given-directory.java)

> **Tóm gọn 1 câu:** Xóa file = gọi `File.delete()` và kiểm tra `boolean` trả về — xóa được là nhờ file tồn tại và không ai đang "giữ" nó.
