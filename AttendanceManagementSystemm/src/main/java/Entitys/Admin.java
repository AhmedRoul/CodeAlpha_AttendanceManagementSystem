package Entitys;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Admin {
    int Id;
    String FirstName ;
    String LastName;
    String email ;
    String  username;
    Date RegisterDay ;
    String password ;

    public static List<Admin> setData(ResultSet resultSet) throws SQLException {
        List<Admin> admins=new ArrayList<>();
        while (resultSet.next()){
            Admin admin =new Admin();
            admin.setId(resultSet.getInt("id_admin"));
            admin.setFirstName(resultSet.getString("FirstName"));
            admin.setLastName(resultSet.getString("LastName"));
            admin.setEmail(resultSet.getString("email"));
            admin.setUsername(resultSet.getString("username"));
            admins.add(admin);
        }
        return admins;
    }
}
