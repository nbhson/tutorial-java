record Diem(int x, int y) {}
record Circle(Diem tam, double r) {}

static double dienTich(Object shape) {
    return switch (shape) {
        case Circle(var t, double r) -> Math.PI * r * r;  // destructure record ngay trong case
        case Diem d                  -> 0;
        default                      -> throw new IllegalArgumentException();
    };
}
