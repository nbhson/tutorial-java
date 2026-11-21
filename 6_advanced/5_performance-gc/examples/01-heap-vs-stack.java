public class MemoryDemo {
    
    // Instance variables - stored in HEAP
    private String name;    // Reference (4/8 bytes) → points to object in Heap
    
    public void calculate() {
        // Local variables - stored in STACK
        int x = 10;         // Primitive: value stored directly
        String s = "Hello"; // Reference: points to String in Heap
        
        // Object created inside method → stored in Heap
        List<String> list = new ArrayList<>();
        list.add("Item");
    }
}
