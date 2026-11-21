// kiem tra du lieu ngay trong constructor (compact form)
public record AgeRange(int minAge, int maxAge) {
    public AgeRange {
        if (minAge > maxAge) {
            throw new IllegalArgumentException(
                    "minAge (" + minAge + ") > maxAge (" + maxAge + ")");
        }
        // khong can gan this.minAge = minAge — tu dong lam
    }
}
