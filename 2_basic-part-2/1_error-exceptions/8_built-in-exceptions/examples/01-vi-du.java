public class BuiltInExceptionDemo {
    public static void main(String[] args) {
        try {
            int a = 10 / 0;               // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Lỗi số học: " + e.getMessage());
        }

        try {
            int[] arr = new int[3];
            arr[5] = 1;                    // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Vượt chỉ số mảng: " + e.getMessage());
        }

        try {
            int n = Integer.parseInt("abc"); // NumberFormatException
        } catch (NumberFormatException e) {
            System.out.println("Chuỗi không phải số hợp lệ");
        }

        String s = null;
        // s.length();                    // NullPointerException nếu bỏ comment
    }
}
