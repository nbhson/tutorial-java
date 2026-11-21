Stream<SanPham> s = ds.stream();
s.filter(p -> p.gia() > 100).toList();
s.count(); // IllegalStateException: stream has already been operated upon
