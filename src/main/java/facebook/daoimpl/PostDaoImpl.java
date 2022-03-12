package facebook.daoimpl;

import facebook.dao.PostDao;
import facebook.entity.Post;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostDaoImpl implements PostDao {
    @Autowired
    Session session;

    @Override
    public void savePost(Post post) {
        session.beginTransaction();
        session.persist(post);
        session.getTransaction().commit();
    }

    @Override
    public List<Post> findAllPost() {
        String SQL = "From Post";
        List<Post> resultList = session.createQuery(SQL, Post.class).getResultList();
        if (resultList.size() > 0) {
            return resultList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Post> findAllPostByEmail(String email) {
        String SQL = "From Post where email = '" + email + "'";
        List<Post> posts = session.createQuery(SQL, Post.class).getResultList();
        if (posts.size() > 0) {
            return posts;
        }
        return new ArrayList<>();
    }
}
