public record KhachHang(String ten, String email, int tuoi) {
    public KhachHang {
        if (ten == null || ten.isBlank()) throw new IllegalArgumentException("Ten rong");
        if (!email.contains("@"))         throw new IllegalArgumentException("Email sai");
        if (tuoi < 0 || tuoi > 150)       throw new IllegalArgumentException("Tuoi sai");
    }
}
