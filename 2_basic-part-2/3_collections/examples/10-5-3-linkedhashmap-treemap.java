// LinkedHashMap: giu dung thu tu chen → danh cho cache LRU don gian
Map<String, Integer> cache = new LinkedHashMap<>(16, 0.75f, true); // true = access-order
cache.put("a", 1);
cache.put("b", 2);
cache.get("a");            // "a" duoc dung → duoi len gan day
System.out.println(cache.keySet()); // [b, a]

// TreeMap: sap xep theo key, hon hop bien doi
Map<String, Integer> diemSo = new TreeMap<>();
diemSo.put("An", 8); diemSo.put("Binh", 9);
System.out.println(diemSo);   // {An=8, Binh=9} - theo thu tu alphabet
