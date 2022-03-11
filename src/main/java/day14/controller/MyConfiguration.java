package day14.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
    @Bean
    public Session getSessions() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Tweet.class);
        configuration.addAnnotatedClass(Follower.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }
}
