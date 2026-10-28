package loop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// while example 1
		int x = 10;
		while( x < 20 ) {
			System.out.println("value of x : " + x );
			x++;
		}
		
		// while example 2
		int [] numbers = {10, 20, 30, 40, 50};
		int index = 0;
		while( index < 5 ) {
			System.out.println("value of item : " + numbers[index] );
			index++;
		}
	}
}
