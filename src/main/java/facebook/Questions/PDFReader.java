package facebook.Questions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pdfReader")
public class PDFReader implements Statement {
    @Override
    public void read() {
        System.out.println("PDF");
    }
}
