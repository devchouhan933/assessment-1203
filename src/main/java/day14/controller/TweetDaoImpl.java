package day14.controller;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class TweetDaoImpl implements TweetDao {
    @Autowired
    Session session;

    @Override
    public void create(Tweet tweet) {

    }

    @Override
    public List<Tweet> readByEmail(String email) {
        Query query = session.createQuery("from Tweet where email=:email");
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public List<Tweet> readByUserId(Integer userid) {
        Query query = session.createQuery("from Tweet where userId:userId");
        query.setParameter("userId", userid);
        return query.getResultList();
    }
}
