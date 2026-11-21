// WIDENING — tu dong
int i = 100;
long l = i;        // ✅ tu dong int → long
double d = i;      // ✅ tu dong int → double (100.0)

// NARROWING — phai ep kieu ro rang
double pi = 3.99;
int n = (int) pi;  // 3 — MAT PHAN THAP PHAN (khong lam tron!)
long big = 5_000_000_000L;
int n2 = (int) big; // tran — ket qua sai vo dinh
