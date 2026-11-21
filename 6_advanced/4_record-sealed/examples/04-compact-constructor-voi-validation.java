public record Range(int min, int max) {
    public Task {
        if (min > max) {
            throw new IllegalArgumentException("min phải <= max");
        }
    }
}
