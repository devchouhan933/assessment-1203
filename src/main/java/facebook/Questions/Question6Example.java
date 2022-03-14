package facebook.Questions;

/*
Q.6 Explain the benefits of DAO Pattern and provide an example.

(i) It avoids duplicate code:
No need to write the same code again and again for different methods.

(ii) Code reusability:
We can call the method whenever it is required. There is no need to write the code again.

(iii) Database related operations are put in a separate class of their own.
So any changes to Dao classes don't affect the classes that use it.When we add some fields to the Domain class, we
don't need to change the DAO interface nor its caller.

(iv) Decouples the Database logic with other logic:
Whenever there is change in the database logic no need to look for the code that uses it.
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Question6Example {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDaoImpl();

        //print all students
        for (Student student : studentDao.getAllStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }


        //update student
        Student student = studentDao.getAllStudents().get(0);
        student.setName("Michael");
        studentDao.updateStudent(student);

        //get the student
        studentDao.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
    }
}
