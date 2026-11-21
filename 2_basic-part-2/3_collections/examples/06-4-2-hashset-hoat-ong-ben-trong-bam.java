class Student {
    String id;
    Student(String id) { this.id = id; }

    // CHI override equals - SAI
    @Override
    public boolean equals(Object o) {
        return o instanceof Student s && s.id.equals(this.id);
    }
    // Thieu hashCode() → hai Student cung id co the bam ra 2 bucket khac nhau
}

// ✅ dung: luon override cap doi
@Override
public int hashCode() {
    return id.hashCode();
}
