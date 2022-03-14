package facebook.Questions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("processReader")
public class ProcessData implements Statement {

    @Override
    public void read() {
        System.out.println("process data");
    }
}
