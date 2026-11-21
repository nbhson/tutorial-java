// Gia dinh ArrayList co 3 truong:
// Object[] elementData; // mang luu du lieu thuc su
// int size;             // so phan tu dang giu
// int capacity;         // do dai hien tai cua elementData

add("A")  → elementData[0] = "A", size = 1
add("B")  → elementData[1] = "B", size = 2
add("C")  → size == capacity?
              - neu chua day: them binh thuong
              - neu day: tao mang moi capacity * 1.5, copy toan bo, roi them
