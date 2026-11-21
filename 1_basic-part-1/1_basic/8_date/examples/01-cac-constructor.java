import java.util.Date;

public class DateDemo {
   public static void main(String args[]) {
      Date date = new Date();          // thời điểm hiện tại
      System.out.println(date.toString());

      Date d2 = new Date(0L);          // 01/01/1970 00:00:00 GMT
      System.out.println(d2);
   }
}
