package facebook.Questions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("csvReader")
public class CSVReader implements Statement {
    @Override
    public void read() {
        System.out.println("CSV");
    }
}
