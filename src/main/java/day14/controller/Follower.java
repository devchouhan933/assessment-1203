package day14.controller;

import javax.persistence.*;

@Entity(name = "Follower")
@Table(name = "follower_spring")
class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String follower;
    private String email;

    public Follower() {
    }

    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", follower='" + follower + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}