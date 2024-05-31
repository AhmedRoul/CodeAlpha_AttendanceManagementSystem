package DataAcessObject;

import Entitys.Course;
import Entitys.Instructor;
import Entitys.Student;

import java.sql.SQLException;
import java.util.List;

public interface coursedao {

    List<Course> get() throws SQLException;
    List<Course> get(int idInstructor) throws SQLException;
    Course getCourse(String id) throws SQLException;

    List<Student> getStudentRegistertionCourse(String id) throws SQLException;
    List<Instructor> getInstructorsTeachCourse(String id) throws SQLException;




}
