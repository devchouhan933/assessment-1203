package facebook.daoimpl;

import facebook.dao.FriendDao;
import facebook.entity.Friend;
import facebook.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendDaoImpl implements FriendDao {

    @Autowired
    Session session;

    @Override
    public List<Friend> readAll(Integer userId) {
        String SQL = "from Friend where user_id = " + userId;
        List<Friend> resultList = session.createQuery(SQL, Friend.class).getResultList();
        if (resultList.size() > 0) {
            return resultList;
        }
        return new ArrayList<>();
    }

    @Override
    public void create(Friend friend) {
        session.beginTransaction();
        session.persist(friend);
        session.getTransaction().commit();
    }

    @Override
    public void createMultiple(int id, Friend friend) {
        User user = session.get(User.class, id);
        session.beginTransaction();
        friend.setUser(user);
        session.persist(friend);
        session.getTransaction().commit();
    }

    @Override
    public void blockFriend(Integer id) {

        String SQL = "UPDATE `Friend`\n" +
                "SET\n" +
                "`blocked` = "+ true+
                " WHERE `id` = "+ id;

    }

}