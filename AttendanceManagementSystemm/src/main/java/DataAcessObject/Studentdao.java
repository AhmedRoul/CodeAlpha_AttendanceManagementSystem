package DataAcessObject;

import Entitys.Student;

import java.sql.SQLException;
import java.util.List;

public interface Studentdao {

    List<Student> get() throws SQLException;
    List<Student> get(List<Integer> list) throws SQLException;

    boolean save(Student c) throws SQLException;

    Student getStudent(int id) throws SQLException;

    boolean update(Student c) throws SQLException;

    boolean delete(int id) throws SQLException;

}
