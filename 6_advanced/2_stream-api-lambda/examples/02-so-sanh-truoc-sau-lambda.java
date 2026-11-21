// ============ TRƯỚC Java 8: Anonymous Inner Class ============
Comparator<String> comparator = new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
};

// ============ SAU Java 8: Lambda ============
Comparator<String> comparator = (s1, s2) -> s1.length() - s2.length();

// ============ Method Reference (thậm chí ngắn hơn) ============
Comparator<String> comparator = Comparator.comparingInt(String::length);
