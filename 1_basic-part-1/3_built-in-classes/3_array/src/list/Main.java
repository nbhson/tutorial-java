package list;

public class Main {
	public static void main(String[] args) {
		// Example 1
		float[] myList = new float[5]; 
		myList[0] = 5.5f; 
		myList[1] = 3.0f; 
		System.out.println(myList[0]); // 5.5
		System.out.println(myList[1]); // 3.0
		System.out.println(myList[2]); // 0.0

		// Example 2
		// double[] myList2 = new double[]{1.9, 2.9, 3.4, 3.5};
		double[] myList2 = {1.9, 2.9, 3.4, 3.5}; // sorthand
		for (int i = 0; i < myList2.length; i++) {
			System.out.println(myList2[i] + " ");
		}

		// Example 3
		String[] stringList = {"1.9S", "2.9S", "3.4S", "3.5S"};
		for (String element: stringList) {
		   System.out.println(element);
		}

		// Example 4
		double[] myList3 = {1.9, 2.9, 3.4, 9};
		double[] myList4 = reverse(myList3);
		System.out.println(myList4[0]);
	}

	public static double[] reverse(double[] list) {
		double[] result = new double[list.length];
		for (int i = 0, j = result.length - 1; i < list.length; i++, j--) {
		   result[j] = list[i];
		}
		return result;
	 }
}
