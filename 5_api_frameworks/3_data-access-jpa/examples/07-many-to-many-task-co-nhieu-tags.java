// Task.java - Cập nhật
@Entity
public class Task {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "task_tags",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getTasks().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getTasks().remove(this);
    }
}
