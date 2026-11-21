interface CoTheIn {
    void in(String taiLieu);

    // default method: hop ly khi no la HANH VI CHUNG cua TAT CA implementer
    default void inNhieu(String... taiLieus) {
        for (String tl : taiLieus) in(tl);   // dung lai in() — khong phai method "thua"
    }
}
