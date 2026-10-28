# Array List

## Introduce

The Java ArrayList class provides resizable-array and implements the List interface. Following are the important points about ArrayList −

- It implements all optional list operations and it also permits all elements, includes null.

- It provides methods to manipulate the size of the array that is used internally to store the list.

> Standard Java arrays are of a fixed length. After arrays are created, they cannot grow or shrink, which means that you must know in advance how many elements an array will hold.

> Array lists are created with an initial size. When this size is exceeded, the collection is automatically enlarged. When objects are removed, the array may be shrunk. 

```java
ArrayList<Integer> list = new ArrayList<Integer>(); 
```

## Class constructors

1	ArrayList()
This constructor is used to create an empty list with an initial capacity sufficient to hold 10 elements.

2	ArrayList(Collection<? extends E> c)
This constructor is used to create a list containing the elements of the specified collection.

3   ArrayList(int initialCapacity)
This constructor is used to create an empty list with an initial capacity.

## List of method

add: thêm phần tử vào arrList
set(index): update phần tử (replace value tren index do)
get(index): lấy phần tử
remove(index): xóa phần tử của list
clear(): xóa tất cả các phần tử ra khỏi arrList
contains(value): kiểm tra phần tử có tồn tại trong list không
hasNext():  kiểm tra còn phần từ nào ở phía sau không

addAll(arrList): thêm tất cả phần tử của một arrList vào một arrList khác
retainAll(arrList): xóa những phần tử của listA không có trong listB
removeAll: xóa tất cả những phần tử của listA ra khỏi listB

indexOf: tìm phần tử đầu tiên xuất hiện trong list
lastIndexOf: tìm phần tử xuất hiện cuối cùng trong list
toArray: chuyển list thành mảng

## Sự khác nhau giữa Array và ArrayList

- Kích thước cố định: Mảng có kích thước cố định khi được khai báo, trong khi ArrayList có thể thay đổi kích thước của nó khi cần thiết.
- Kiểu dữ liệu: Mảng có thể chứa các phần tử cùng một kiểu dữ liệu cố định, trong khi ArrayList có thể chứa các phần tử của bất kỳ kiểu dữ liệu nào (với sự hỗ trợ của Generics).
- Thao tác với phần tử: ArrayList cung cấp các phương thức tiện ích để thêm, xóa, sắp xếp, tìm kiếm phần tử một cách dễ dàng hơn so với mảng.
- Tính linh hoạt: ArrayList linh hoạt hơn trong việc thêm/xóa phần tử mà không cần phải lo lắng về việc quản lý bộ nhớ như mảng.