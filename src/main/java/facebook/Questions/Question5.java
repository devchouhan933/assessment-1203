package facebook.Questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class Question5 {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Question5.class, args);
        //    statement.Read();
    }

}

@RestController
class Rest {
    @Autowired
    @Qualifier("processReader")
    Statement processReader;

    @Autowired
    @Qualifier("pdfReader")
    Statement pdfReader;

    @Autowired
    @Qualifier("docReader")
    Statement docReader;

    @Autowired
    @Qualifier("csvReader")
    Statement csvReader;

    @GetMapping("/get")
    public void readStatement() {
        csvReader.read();
    }
}