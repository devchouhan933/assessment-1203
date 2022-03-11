package day14.controller;

import java.util.List;


public interface UserDao {
    List<User> readAll();

    void create(User user);

    List<Object[]> read();

    List<User> readByEmail(String email);
}
