package Entitys;

import DataAcessObject.imp.AttendancedaoImp;
import lombok.*;
import org.jboss.logging.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {
   private static final Logger logger = Logger.getLogger(Course.class);
   String  id;
   String name;
   String year;
   int NumStudent;

   public  static List<Course> setData(ResultSet resultSet) throws SQLException {
      List<Course> Courses=new ArrayList<>();
      while (resultSet.next()){

         Course  course =new Course();
         course.setId(resultSet.getString("id_Course"));
         course.setName(resultSet.getString("name_c"));
         course.setYear(resultSet.getString("year_c"));
         course.setNumStudent(resultSet.getInt(4));
         Courses.add(course);

         logger.info(course.toString());
      }
      return Courses;

   }

   @Override
   public String toString() {
      return "Course{" +
              "id='" + id + '\'' +
              ", name='" + name + '\'' +
              ", year='" + year + '\'' +
              ", NumStudent=" + NumStudent +
              '}';
   }
}
