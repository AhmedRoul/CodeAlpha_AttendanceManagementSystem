package DataAcessObject.imp;

import DataAcessObject.Studentdao;
import Entitys.Student;
import Utils.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class    Studentdaoimp  implements Studentdao {


    private final String columns=" id_stud ,FirstName ,LastName ,email ,username ,Birthday ,CurrentLevel";
    private final String[] columnsArr= new String[]{"id_stud","FirstName" ,"LastName","email","username","Birthday","CurrentLevel"};
    @Override
    public List<Student> get() throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from student;";
        Statement  statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return  Student.setData(resultSet);
    }
    public List<Student> get(List<Integer>integers) throws SQLException{
        String ids = String.join(",", integers.stream().map(String::valueOf).collect(Collectors.toList()));
        String query = "SELECT id_stud ,FirstName ,LastName ,email ,CurrentLevel FROM Student WHERE id_stud IN (" + ids + ");";
        Connection connection= DataBaseConnection.openConnection();
        Statement  statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Student>students=new ArrayList<>();
        while(resultSet.next()){
            Student student=new Student();
            student.setId(resultSet.getInt(1));
            student.setFirstName(resultSet.getString(2));
            student.setLastName(resultSet.getString(3));
            student.setEmail(resultSet.getString(4));
            student.setCurrentLevel(resultSet.getString(5));
            students.add(student);
        }
        return students;

    }

    @Override
    public boolean save(Student c) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String Sql="insert into student (FirstName, LastName, email, username, Birthday, RegisterDay, Password_stud, CurrentLevel)" +
                "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement  preparedStatement  =connection.prepareStatement(Sql);

        preparedStatement.setString(1,c.getFirstName());
        preparedStatement.setString(2,c.getLastName());
        preparedStatement.setString(3,c.getEmail());
        preparedStatement.setString(4,c.getUsername());
        preparedStatement.setDate(5, (Date) c.getBirthday());
        preparedStatement.setDate(6, (Date) c.getRegisterDay());
        preparedStatement.setString(7,c.getPassword());
        preparedStatement.setString(8,c.getCurrentLevel());
        int row = preparedStatement.executeUpdate();

        return row>0;
    }

    @Override
    public Student getStudent(int id) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from student where id_stud=?;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Student.setData(resultSet).get(0);
    }

    @Override
    public boolean update(Student c) throws SQLException {
        String sql ="update student  set FirstName=? , lastname=? where id_stud=?;";
        Connection connection= DataBaseConnection.openConnection();
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,c.getFirstName());
        preparedStatement.setString(2,c.getLastName());
        preparedStatement.setInt(3,c.getId());
        int row = preparedStatement.executeUpdate();
        return row>0;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql ="delete from  student  where id_stud=?;";
        Connection connection= DataBaseConnection.openConnection();
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        int row = preparedStatement.executeUpdate();
        return row>0;
    }

}