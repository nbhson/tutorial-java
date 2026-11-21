interface Hinh {
    double dienTich();
}

record ChuNhat(double w, double h) implements Hinh {
    public double dienTich() { return w * h; }
}

record Vuong(double canh) implements Hinh {   // không extends ChuNhat!
    public double dienTich() { return canh * canh; }
}
