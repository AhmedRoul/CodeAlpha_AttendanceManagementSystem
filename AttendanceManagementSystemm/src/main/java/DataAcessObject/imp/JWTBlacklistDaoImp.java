package DataAcessObject.imp;

import DataAcessObject.JWTBlacklistDao;
import Utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JWTBlacklistDaoImp implements JWTBlacklistDao {
    @Override
    public boolean IsExist(String Token) {
        Connection connection= DataBaseConnection.openConnection();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("select Count(*) from JWTBlackList  where token=?;");
            prepareStatement.setString(1,Token);
            ResultSet resultSet = prepareStatement.executeQuery();
            while(resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    return true;
                }
            }
            return  false;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean InsertToken(String Token,String email) {
        Connection connection= DataBaseConnection.openConnection();
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("insert into JWTBlackList values (?,?);");
            prepareStatement.setString(1,Token);
            prepareStatement.setString(2,email);
            int row  = prepareStatement.executeUpdate();
            return  row==1;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }
}
