package list;

public class Main {
	public static void main(String[] args) {
		// create 2 Boolean objects b1, b2
		Boolean b1, b2;

		// assign values to b1, b2
		b1 = Boolean.valueOf(false);
		b2 = Boolean.valueOf(true);

		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b1 == b2);
		System.out.println(b1 != b2);

		// create an int res
		int res;

		// compare b1 with b2
		res = b1.compareTo(b2);

		String str1 = "Both values are equal ";
		String str2 = "Object value is true";
		String str3 = "Argument value is true";

		if (res == 0) {
			System.out.println(str1); // same value
		} else if (res > 0) {
			System.out.println(str2); // different value true-false
		} else if (res < 0) {
			System.out.println(str3); // different value false-true
		}
	}
}
