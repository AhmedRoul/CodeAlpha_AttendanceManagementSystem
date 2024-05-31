package Entitys;


import Utils.HibernateSessionFactoryListener;
import lombok.*;
import org.jboss.logging.Logger;

import javax.persistence.*;
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

public class Student   {

    private int id;
    private  String FirstName;
    private  String LastName;
    private String email;
    private String username;
    private Date Birthday;
    private Date RegisterDay;
    private String Password;
    int ID_attendance;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass()!= obj.getClass())
            return false;
        Student other = (Student) obj;
        return getId() == other.getId();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }

    private String CurrentLevel;

    public static List<Student> setData(ResultSet resultSet) throws SQLException {
        List<Student> students=new ArrayList<>();
        while (resultSet.next()){
            Student student =new Student();
            student.setId(resultSet.getInt("id_stud"));
            student.setFirstName(resultSet.getString("FirstName"));
            student.setLastName(resultSet.getString("LastName"));
            student.setEmail(resultSet.getString("email"));
            student.setUsername(resultSet.getString("username"));
            student.setBirthday(resultSet.getDate("Birthday"));
            student.setCurrentLevel(resultSet.getString("CurrentLevel"));
            students.add(student);

        }
        return students;
    }

}
