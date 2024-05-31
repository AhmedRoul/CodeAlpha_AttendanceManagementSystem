package DataAcessObject.imp;

import DataAcessObject.coursedao;
import Entitys.Course;
import Entitys.Instructor;
import Entitys.Student;
import Utils.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class coursedaoimp implements coursedao {
    @Override
    public List<Course> get() throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="SELECT *, (SELECT COUNT(*) FROM Attendance a WHERE a.ID_course = cr.id_Course)" +
                "FROM course cr;";
        Statement statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return Course.setData(resultSet);
    }

    @Override
    public List<Course> get(int idInstructor) throws SQLException {
        String sql ="SELECT cr.id_Course,cr.name_c,cr.year_c , (SELECT COUNT(*) FROM Attendance a WHERE a.ID_course = cr.id_Course) " +
                "  FROM  Teach_course tc ,course cr where tc.id_instr_Teach=? and tc.id_Course_Teach=cr.id_Course;";
        Connection connection= DataBaseConnection.openConnection();
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setInt(1,idInstructor);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Course.setData(resultSet);

    }


    @Override
    public Course getCourse(String  id) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="SELECT *, (SELECT COUNT(*) FROM Attendance a WHERE a.ID_course = cr.id_Course)" +
                "FROM course cr where cr.id_Course=?;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Course.setData(resultSet).get(0);
    }



    @Override
    public List<Student> getStudentRegistertionCourse(String id) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql ="select s.id_stud  ,s.FirstName ,s.lastname ,s.email  from Attendance  a ,Student  s where a.ID_course=?"+
                          " and a.Id_student  =s.id_stud  ;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Student> Students=new ArrayList<>();


        while (resultSet.next()){
            Student student=new Student();
            student.setId(resultSet.getInt(1));
            student.setFirstName(resultSet.getString(2));
            student.setLastName(resultSet.getString(3));
            student.setEmail(resultSet.getString(4));
            Students.add(student);
        }
        return Students;
    }

    @Override
    public List<Instructor> getInstructorsTeachCourse(String id) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql ="select i.id_instr ,i.FirstName ,i.lastname  from Teach_course t,instructor i where t.id_Course_Teach  =?" +
                " and t.id_instr_Teach=i.id_instr ;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Instructor> Instructors=new ArrayList<>();

        while (resultSet.next()){
            Instructor instructor=new Instructor();
            instructor.setId(resultSet.getInt(1));
            instructor.setFirstName(resultSet.getString(2));
            instructor.setLastName(resultSet.getString(3));
            Instructors.add(instructor);
        }
        return Instructors;
    }

    @Override
    public boolean update(Course course) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql ="update course set name_c= ? ,year_c =? where id_Course=?;";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,course.getName());
        preparedStatement.setString(2,course.getYear());
        preparedStatement.setString(3,course.getId());
        int row =preparedStatement.executeUpdate();
        return row>0;
    }


}
