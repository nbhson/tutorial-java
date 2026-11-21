static double area(Shape shape) {
    // khong can default! compiler BAO LOI neu thieu mot case
    return switch (shape) {
        case Circle c    -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        case Triangle t  -> 0;
    };
}

// sau nay ban them Square vao Shape:
// → khong sua switch nay → LOI BIEN DICH ngay lap tuc, khong phai bug nua dem
