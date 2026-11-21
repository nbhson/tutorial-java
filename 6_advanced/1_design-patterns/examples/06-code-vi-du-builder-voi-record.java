public record Pizza(
    String size,
    boolean cheese,
    boolean pepperoni,
    boolean mushroom,
    boolean olive
) {
    // Static method để tạo Builder
    public static Builder builder() {
        return new Builder();
    }

    // Builder inner class
    public static class Builder {
        // Bắt buộc
        private final String size;

        // Optional với default values
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushroom = false;
        private boolean olive = false;

        public Builder(String size) {
            this.size = size;
        }

        public Builder cheese(boolean value) {
            this.cheese = cheese;
            return this;  // Return this để chain method
        }

        public Builder pepperoni(boolean value) {
            this.pepperoni = pepperoni;
            return this;
        }

        public Builder mushroom(boolean value) {
            this.mushroom = mushroom;
            return this;
        }

        public Builder olive(boolean value) {
            this.olive = olive;
            return this;
        }

        public Pizza build() {
            return new Pizza(size, cheese, pepperoni, mushroom, olive);
        }
    }
}

// Sử dụng: Rõ ràng, dễ đọc!
Pizza pizza = Pizza.builder()
    .size("Large")
    .cheese(true)
    .mushroom(true)
    .build();
