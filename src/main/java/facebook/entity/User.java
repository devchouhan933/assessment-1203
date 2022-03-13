package facebook.entity;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "user_spring")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String userPostVisibility = "PUBLIC";

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", userPostVisibility='" + userPostVisibility + '\'' + '}';
    }

    public String getUserPostVisibility() {
        return userPostVisibility;
    }

    public void setUserPostVisibility(String userPostVisibility) {
        this.userPostVisibility = userPostVisibility;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}