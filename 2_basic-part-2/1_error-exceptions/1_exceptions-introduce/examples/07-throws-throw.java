public void divideNumbers(int a, int b) throws ArithmeticException {
    if (b == 0) {
        throw new ArithmeticException("Không thể chia cho 0");
    }
    int result = a / b;
}
