package facebook.dao;

import facebook.entity.User;

import java.util.List;

public interface UserDao {
    List<User> readAll();

    void create(User user);

    List<User> read();

    User readByEmail(String email);
}

