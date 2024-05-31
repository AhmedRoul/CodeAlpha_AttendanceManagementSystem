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
public class Instructor {
    int id;
    String FirstName ;
    String LastName ;
    String email;
    String username ;
    String Password ;
    Date Birthday ;
    String Details;


    public static List<Instructor> setData(ResultSet resultSet) throws SQLException {
        List<Instructor> Instructors=new ArrayList<>();
        while (resultSet.next()){
            Instructor Ins =new Instructor();

            Ins.setId(resultSet.getInt("id_instr"));
            Ins.setFirstName(resultSet.getString("FirstName"));
            Ins.setLastName(resultSet.getString("LastName"));
            Ins.setEmail(resultSet.getString("email"));
            Ins.setUsername(resultSet.getString("username"));
            Ins.setBirthday(resultSet.getDate("Birthday"));
            //Ins.setDetails(resultSet.getString("Details"));
            Instructors.add(Ins);

        }
        return Instructors;
    }
}
