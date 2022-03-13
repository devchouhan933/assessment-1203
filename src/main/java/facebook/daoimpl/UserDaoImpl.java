package facebook.daoimpl;

import facebook.dao.UserDao;
import facebook.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {


    @Autowired
    Session session;

    @Override
    public List<User> readAll() {
        return session.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void create(User user) {
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> read() {
        List<User> resultList = session.createQuery("From User", User.class).getResultList();
        if (!resultList.isEmpty()) return resultList;
        return new ArrayList<>();
    }

    @Override
    public User readByEmail(String email) {
        String SQL = "From User where email = '" + email + "'";
        List<User> resultList = session.createQuery(SQL, User.class).getResultList();
        if (resultList.size() > 0) {
            User singleResult = resultList.get(0);
            return singleResult;
        }
        return null;

        /* Query query = session.createQuery("from " +
                "User where email=:email");
        query.setParameter("email", email);

        return (User) query.getSingleResult();*/
    }

    @Override
    public void updateVisibility(String visibility, Integer user_id) {
        session.beginTransaction();
        String SQL = "UPDATE User SET userPostVisibility =:visibility   WHERE id = :user_id";
        Query query = session.createQuery(SQL);
        query.setParameter("visibility", visibility);
        query.setParameter("user_id", user_id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
    }

}