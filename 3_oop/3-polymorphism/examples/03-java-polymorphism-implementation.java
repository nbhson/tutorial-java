interface Vegetarian{}
class Animal{}
public class Deer extends Animal implements Vegetarian{
	public static void main(String[] args) {
		Deer d = new Deer();
		Animal a = d;
		Vegetarian v = d;
		Object o = d;
		
		System.out.println(d instanceof Deer); // true
		System.out.println(a instanceof Deer); // true
		System.out.println(v instanceof Deer); // true
		System.out.println(o instanceof Deer); // true
	}	
}
