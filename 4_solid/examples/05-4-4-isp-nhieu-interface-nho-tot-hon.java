// ✗ một interface ép implement những method không cần
interface MayIn { in(); scan(); fax(); }

// ✅ tách nhỏ
interface CoTheIn  { in(); }
interface CoTheScan{ scan(); }
// MayInInOnly chỉ implements CoTheIn
