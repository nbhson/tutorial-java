int x = 10;
x += 5;   // x = x + 5 → 15
x -= 3;   // 12
x *= 2;   // 24
x /= 4;   // 6
x %= 4;   // 2

// luu y: toan tu gan ghep an trong ep kieu
byte b = 10;
b += 1;            // OK — ngam dinh ep ve byte
b = (byte) (b + 1); // vi b + 1 la int, gan vao byte phai ep kieu
