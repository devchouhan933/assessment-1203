package facebook.Questions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
        @Qualifier("docReader")
public class DocReader implements Statement {
    @Override
    public void read() {
        System.out.println("DOC");
    }
}
