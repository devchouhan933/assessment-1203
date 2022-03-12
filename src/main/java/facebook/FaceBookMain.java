package facebook;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FaceBookMain {
    @Autowired
    static Session session;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FaceBookMain.class, args);
        System.out.println(session);
    }
}