import java.io.File;
import java.io.IOException;

public class FileDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("example.txt");

        if (file.createNewFile()) {
            System.out.println("Đã tạo: " + file.getName());
        } else {
            System.out.println("File đã tồn tại.");
        }

        System.out.println("Đường dẫn tuyệt đối: " + file.getAbsolutePath());
        System.out.println("Kích thước: " + file.length() + " bytes");
        System.out.println("Có thể ghi? " + file.canWrite());
        System.out.println("Là thư mục? " + file.isDirectory());

        // Đổi tên
        File newName = new File("renamed.txt");
        if (file.renameTo(newName)) {
            System.out.println("Đã đổi tên thành " + newName.getName());
        }

        // Xóa
        if (newName.delete()) {
            System.out.println("Đã xóa file.");
        }
    }
}
