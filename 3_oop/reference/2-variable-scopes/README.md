# Variable Scopes

File này nói về "phạm vi sống" của biến: biến nào được nhìn thấy ở đâu và sống được bao lâu. Đọc để hiểu vì sao cùng một tên biến ở chỗ khác nhau lại cho kết quả khác nhau — một trong những nguồn lỗi kinh điển của người mới.

## Scope of Java Instance Variables

Mục này nói về biến instance — biến "đi theo từng object": sống lâu bao nhiêu thì do object sống bấy nhiêu.

variable. The general scope of an instance variable is throughout the class except in static methods. The lifetime of an instance variable is until the object stays in memory.

Ví von: instance variable như đồ đạc trong từng căn hộ — ai ở căn hộ đó đều dùng được mọi thứ trong nhà, và đồ đạc "biến mất" khi căn hộ bị thu hồi.

## Example: Scope of Java Instance Variables

> 📄 **Code ví dụ:** [`examples/01-example-scope-of-java-instance-variables.java`](examples/01-example-scope-of-java-instance-variables.java)

> Biến instance được khai báo trong một lớp và ngoài tất cả các phương thức và khối. Phạm vi của biến instance là toàn bộ lớp, ngoại trừ các phương thức tĩnh. Thời gian tồn tại của biến instance là cho đến khi đối tượng còn tồn tại trong bộ nhớ.

## Scope of Java Local Variables

Mục này nói về biến cục bộ — biến "sống ngắn nhất": sinh ra trong một khối code và chết ngay khi khối đó kết thúc.

- Một biến được khai báo bên trong một lớp, bên ngoài tất cả các khối và được đánh dấu là static được gọi là biến lớp. 
- Vòng đời của một biến lớp là cho đến khi kết thúc chương trình hoặc miễn là lớp được tải trong bộ nhớ.

> 📄 **Code ví dụ:** [`examples/02-scope-of-java-local-variables.java`](examples/02-scope-of-java-local-variables.java)

> Biến cục bộ được khai báo trong một phương thức, khối hoặc constructor. Phạm vi của biến cục bộ chỉ trong khối mà nó được khai báo và tồn tại cho đến khi khối đó kết thúc.

Ví von: biến cục bộ như tờ giấy nháp trên bàn làm bài — dùng xong là gấp lại vứt đi, không ai nhìn thấy tờ giấy của bạn từ phòng khác.

## Scope of Java Class (Static) Variables

Mục này nói về biến static — biến "của cả tòa nhà": chỉ có một bản duy nhất mà mọi object cùng nhìn thấy, sống đến tận lúc chương trình tắt.

Tất cả các biến khác không phải là biến thể hiện và biến lớp được coi là biến cục bộ bao gồm các tham số trong phương thức. Phạm vi của biến cục bộ nằm trong khối mà nó được khai báo và thời gian tồn tại của biến cục bộ là cho đến khi điều khiển rời khỏi khối mà nó được khai báo.

> 📄 **Code ví dụ:** [`examples/03-scope-of-java-class-static-variables.java`](examples/03-scope-of-java-class-static-variables.java)

> biến lớp được khai báo với từ khóa `static` và có phạm vi trong toàn bộ lớp. Thời gian tồn tại của biến lớp là cho đến khi chương trình kết thúc hoặc lớp không còn được tải trong bộ nhớ.

Ví von: biến static như biển hiệu treo trước chung cư — cả tòa chỉ có một cái, sáng đèn từ lúc dọn vào (lớp được tải) đến lúc tòa bị dỡ (chương trình kết thúc).

> **Tóm gọn 1 câu:** Ba vòng đời của biến: static sống trọn đời chương trình (biển hiệu tòa nhà), instance sống theo object (đồ trong căn hộ), local sống theo khối code (tờ giấy nháp).
