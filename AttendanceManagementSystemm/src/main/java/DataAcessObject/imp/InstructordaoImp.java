package DataAcessObject.imp;

import DataAcessObject.Instructordao;
import Entitys.Instructor;
import Entitys.Student;
import Utils.DataBaseConnection;
import org.jboss.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InstructordaoImp implements Instructordao {
    private static final Logger logger = Logger.getLogger(InstructordaoImp.class);
    private final String columns=" id_instr ,FirstName ,LastName ,email ,username ,Birthday ";//+details
    @Override
    public List<Instructor> get() throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from instructor;";
        Statement statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return  Instructor.setData(resultSet);
    }

    @Override
    public List<Instructor> get(List<Integer> integers) throws SQLException {
        String ids = String.join(",", integers.stream().map(String::valueOf).collect(Collectors.toList()));
        String query = "SELECT "+columns+" FROM instructor WHERE id_instr IN (" + ids + ");";
        Connection connection= DataBaseConnection.openConnection();
        Statement  statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Instructor>instructors=new ArrayList<>();

        return Instructor.setData(resultSet);
    }

    @Override
    public boolean save(Instructor c) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String Sql="insert into instructor (FirstName, LastName, email, username, Birthday,  Password_stud, details)" +
                "VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement preparedStatement  =connection.prepareStatement(Sql);

        preparedStatement.setString(1,c.getFirstName());
        preparedStatement.setString(2,c.getLastName());
        preparedStatement.setString(3,c.getEmail());
        preparedStatement.setString(4,c.getUsername());
        preparedStatement.setDate  (5, (Date) c.getBirthday());
        preparedStatement.setString(6,c.getPassword());
        preparedStatement.setString(7,c.getDetails());
        int row = preparedStatement.executeUpdate();

        return row>0;
    }

    @Override
    public Instructor getInstructor(int id) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from instructor where id_instr =?;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Instructor.setData(resultSet).get(0);
    }

    @Override
    public Instructor getInstructor(String email) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from instructor where email =?;";
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Instructor.setData(resultSet).get(0);
    }

    @Override
    public boolean update(Instructor c) throws SQLException {
        String sql ="update instructor  set FirstName=? , lastname=? where id_instr=?;";
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
        String sql ="delete from  instructor  where id_instr=?;";
        Connection connection= DataBaseConnection.openConnection();
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        int row = preparedStatement.executeUpdate(sql);
        return row>0;
    }
}
