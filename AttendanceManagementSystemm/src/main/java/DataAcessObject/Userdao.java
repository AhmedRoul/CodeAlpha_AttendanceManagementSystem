package DataAcessObject;

import Entitys.User;

import java.sql.SQLException;

public interface Userdao {

    public User IsExist(User user)throws SQLException;

    public User Check(User user) throws SQLException;
    public User StudentCheck(User user) throws SQLException;
    public User InstructorCheck(User user) throws SQLException;
    public User AdminCheck(User user) throws SQLException;

}
