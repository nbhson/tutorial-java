import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("Java");
        list.add("C++");
        list.add("PHP");
        list.add("Java");
        System.out.println(list); // [Java, C++, PHP, Java]
        
        LinkedList<String> listA = new LinkedList<String>();
        listA.addAll(list);
        System.out.println("listA: " + listA); // listA: [Java, C++, PHP, Java]
        
        LinkedList<String> listB = new LinkedList<String>();
        listB.add("Java");
        listA.retainAll(listB);
        System.out.println("listA:"+ listA); // listA:[Java, Java] 
        
        list.removeAll(listB);
        System.out.println("list:" + list); // list:[C++, PHP]
        
        System.out.println(list.get(1)); // PHP
        
        list.set(1, "Python");
        System.out.println(list); // [C++, Python]
        
        list.remove(1);
        System.out.println(list); // [C++]
        
        System.out.println(list.contains("C++")); // true

        // -------------------------------------- //
        ArrayList<String> arrList = new ArrayList<>();
        arrList.addAll(list);
        System.out.println(arrList);

        LinkedList<Object> listObj = new LinkedList<>();
        listObj.add(null);
        listObj.add(26);
        System.out.println(listObj);
	}
}
