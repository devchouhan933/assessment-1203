package day14.controller;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tweet_spring")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tweet;
    private String email;
    private Timestamp timestamp;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    public Tweet() {
    }

    public Tweet(String tweet, Timestamp timestamp, User user, String email) {
        this.tweet = tweet;
        this.timestamp = timestamp;
        this.user = user;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", tweet='" + tweet + '\'' +
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

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
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