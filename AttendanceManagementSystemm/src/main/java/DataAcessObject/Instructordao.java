package DataAcessObject;

import Entitys.Instructor;
import Entitys.Student;

import java.sql.SQLException;
import java.util.List;

public interface Instructordao {
    List<Instructor> get() throws SQLException;
    List<Instructor> get(List<Integer>integers) throws SQLException;
    boolean save(Instructor c) throws SQLException;

    Instructor getInstructor(int id) throws SQLException;
    Instructor getInstructor(String  email) throws SQLException;

    boolean update(Instructor c) throws SQLException;

    boolean delete(int id) throws SQLException;
}
