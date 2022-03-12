package facebook.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Post")
@Table(name = "post_spring")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String post;
    private String email;
    private Timestamp timestamp;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    public Post() {
    }

    public Post(String post, Timestamp timestamp, User user, String email) {
        this.post = post;
        this.timestamp = timestamp;
        this.user = user;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", email='" + email + '\'' +
                ", timestamp=" + timestamp +
                ", user=" + user +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getpost() {
        return post;
    }

    public void setpost(String post) {
        this.post = post;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}