package day14.controller;

import java.util.List;

public interface FollowerDao {
// dis
    void create(Follower follower);

    List<Follower> readByEmail(String email);
}
