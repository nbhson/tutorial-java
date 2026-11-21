// Task.java - Thêm field user
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Trong class Task, có getter:
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
