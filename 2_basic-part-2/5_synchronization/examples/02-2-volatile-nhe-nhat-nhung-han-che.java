volatile int count = 0;

void increment() {
    count++; // van SAI voi nhieu thread! doc → +1 → ghi van la 3 buoc
}
