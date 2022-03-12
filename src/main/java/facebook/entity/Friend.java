package facebook.entity;

import javax.persistence.*;
@Entity(name = "Friend")
@Table(name = "friend_spring")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "friend_id")
    private Integer friendId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public Friend(Integer id, Integer friendId, User user, boolean blocked) {
        this.id = id;
        this.friendId = friendId;
        this.user = user;
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    private boolean blocked  = false;
    public Friend(Integer friendId, User user) {
        this.friendId = friendId;
        this.user = user;
    }

    public Friend() {
    }

    public Friend(Integer id, Integer friendId, User user) {
        this.id = id;
        this.friendId = friendId;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Follower{" + "id=" + id + ", friendId=" + friendId + ", user=" + user + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}