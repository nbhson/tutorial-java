package loop;

public class Main {
	public static void main(String[] args) {

		// example 1
		int x = 10;
		do {
			System.out.print("value of x : " + x);
			x++;
			System.out.print("\n");
		} while (x < 10);

		// example 2
		int[] numbers = { 10, 20, 30, 40, 50 };
		int index = 0;
		do {
			System.out.println("value of item : " + numbers[index]);
			index++;
		} while (index < 5);
	}
}
