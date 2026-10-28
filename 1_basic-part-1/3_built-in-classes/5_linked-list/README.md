# LinkedList

## List of method

- add(value): thêm phần tử mới
- addAll(linkedListA): thêm tất cả phần tử của A vào B
- retainAll(linkedListA): chỉ giữ lại phần từ có trong A

## LinkedList and Array List

- ArrayList dựa trên mảng còn LinkedList dựa trên danh sách liên kết.
  - ArrayList sử dụng mảng để lưu trữ các phần tử. Việc truy cập phần tử ngẫu nhiên thông qua chỉ mục (get(index)) được thực hiện một cách nhanh chóng vì chỉ cần truy cập trực tiếp vào vị trí của phần tử trong mảng. 
  - LinkedList sử dụng danh sách liên kết để lưu trữ các phần tử. Mỗi phần tử trong LinkedList sẽ trỏ đến phần tử tiếp theo và trước đó trong danh sách. Việc truy cập phần tử ngẫu nhiên trong LinkedList sẽ đòi hỏi việc đi qua các phần tử từ đầu đến vị trí cần truy cập, điều này có thể làm tăng độ phức tạp của thao tác truy cập so với ArrayList 
- ArrayList tiêu tốn ít bộ nhớ hơn LinkedList vì không cần lưu thêm con trỏ.
- Trong LinkedList, có các phương thức sau để thêm/xóa phần tử ở đầu hoặc cuối danh sách một cách hiệu quả hơn so với ArrayList:
  - Để thêm ở đầu danh sách: `addFirst(E e)`: Thêm phần tử vào đầu danh sách.
  - Để thêm ở cuối danh sách: `addLast(E e)`: Thêm phần tử vào cuối danh sách.
  - Để xóa ở đầu danh sách: `removeFirst()`: Xóa phần tử ở đầu danh sách.
  - Để xóa ở cuối danh sách: `removeLast()`: Xóa phần tử ở cuối danh sách.
  - Trong khi đó, trong ArrayList, để thêm/xóa phần tử ở đầu hoặc cuối danh sách, bạn phải sử dụng các phương thức như add(index, element) hoặc remove(index), điều này có thể đòi hỏi việc dịch chuyển các phần tử khác trong mảng, làm tăng độ phức tạp của thao tác.