package DataAcessObject.imp;

import DataAcessObject.Admindao;
import Entitys.Student;
import Utils.DataBaseConnection;
import Entitys.Admin;
import org.jboss.logging.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class Admindaoimp  implements Admindao {
    private static final Logger logger = Logger.getLogger(Admindaoimp.class);
    private final String columns=" id_admin ,FirstName ,LastName ,email ,username ";
    private final String[] columnsArr= new String[]{"id_admin","FirstName" ,"LastName","email","username"};

    @Override
    public List<Entitys.Admin> get() throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from adminn;";
        Statement statement  =connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return Admin.setData(resultSet);
    }

    @Override
    public boolean save(Admin c) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String Sql="insert into student (FirstName, LastName, email, username, RegisterDay, Password_admin)" +
                "VALUES (?,?,?,?,?,?);";
        PreparedStatement preparedStatement  =connection.prepareStatement(Sql);

        preparedStatement.setString(1,c.getFirstName());
        preparedStatement.setString(2,c.getLastName());
        preparedStatement.setString(3,c.getEmail());
        preparedStatement.setString(4,c.getUsername());
        preparedStatement.setDate(5, (Date) c.getRegisterDay());
        preparedStatement.setString(6, c.getPassword());


        int row = preparedStatement.executeUpdate();

        return row>0;
    }

    @Override
    public Admin getAdmin(String email  ) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select "+columns+" from adminn where email=?;";
        logger.info(email);
        PreparedStatement  preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();
        return Admin.setData(resultSet).get(0);
    }

    @Override
    public boolean update(Admin c) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
