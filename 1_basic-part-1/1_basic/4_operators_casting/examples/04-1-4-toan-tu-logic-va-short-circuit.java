int age = 17;
boolean coTheDieuXe = age >= 18 && coGiayPhep; // && : ca hai dung moi dung

// SHORT-CIRCUIT: && dung ngay khi ve trai sai; || dung ngay khi ve trai dung
if (list != null && list.size() > 0) { }  // ✅ an toan — list null thi khong goi size()
if (list.size() > 0 && list != null) { }  // ✗ NPE khi list null — dung thu tu
