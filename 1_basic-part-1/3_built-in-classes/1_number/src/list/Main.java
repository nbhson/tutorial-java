package list;

public class Main {
	public static void main(String[] args) {
		Integer x = 1; // boxes int to an Integer object
		x =  x + 99;   // unboxes the Integer to a int
		System.out.println(x); 
		System.out.println(x.floatValue()); 

		String a = "20";
		int b = Integer.parseInt(a);
		System.out.println(b);

		String c = Integer.toString(b);
		System.out.println(c);

		Number n = 10;
		System.out.println(n instanceof Integer); // (question: the different between Number & Integer? real use case?)
	}
}
