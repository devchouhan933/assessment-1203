package facebook.dao;

import facebook.entity.Post;

import java.util.List;

public interface PostDao {
    void savePost(Post post);

    List<Post> findAllPost();

    List<Post> findAllPostByEmail(String email);
}
