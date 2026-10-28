import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Main {
	public static void main(String[] args) {
		
		// Array List
		ArrayList<String> originList = new ArrayList<String>();
		originList.add("Java");
		originList.add("C++");
		originList.add("PHP");
        System.out.println(originList );
        System.out.println(originList.size());
        System.out.println(originList.get(0));
        
        // sử dụng Iterator - hiển thị các phần tử của list
        Iterator<String> iterator = originList.iterator();
        while (iterator.hasNext()) {
            System.out.println((String) iterator.next());
        }
        
        // sử dụng ListIterator - hiển thị các phần tử của list
        ListIterator<String> listIterator = originList.listIterator();
        while (listIterator.hasNext()) {
            System.out.println((String) listIterator.next());
        }
		
        ArrayList<String> listA = new ArrayList<String>();
        // thêm các phần tử của originList vào listA
        listA.addAll(originList);
        System.out.println(listA); // [Java, C++, PHP]
        
        
        ArrayList<String> listB = new ArrayList<String>();
        listB.add("Java");
        // xóa những phần tử không thuộc listB ra khỏi listA
        listA.retainAll(listB); 
        // listB.addAll(listA); // (question: when we use ArrayList & LinkedList)
        
        System.out.println("listA: " + listA); // [Java]
        System.out.println("listB: " + listB); // [Java]
		        
        // xóa những phần tử thuộc listB khỏi originList
        originList.removeAll(listB);
        System.out.println("removeAll: " + originList); // [C++, PHP]
        
        // update
        originList.set(1, "Python");
        System.out.println("set: " + originList); // [C++, Python]
        
        // remove 1 phần tử trong list
        originList.remove(0);
        System.out.println("remove: " + originList); // [Python]
        
        // kiểm tra xem PHP có tồn tại trong list hay không?
        System.out.println(originList.contains("Python")); // true
        
        // xóa tất cả phần tử trong list
        originList.clear();
        System.out.println("clear: " + originList);  // []

        originList.add(0, "No value");
        // originList.add(1, "No value"); // error
        System.out.println("clear: " + originList);  // [No value]

        System.out.println(originList.get(0) instanceof String); // true

        // ----------------------------------------------- //

        ArrayList<Object> listObj = new ArrayList<>();
        listObj.add("Son Nguyen");
        listObj.add(26);
        listObj.add(true);
        System.out.println(listObj);
	}
}
