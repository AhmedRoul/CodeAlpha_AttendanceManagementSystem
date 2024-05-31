package DataAcessObject;

import Entitys.Student;
import Entitys.periodsClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Attendancedao {
    public Map<Integer ,Student> getStudentAttendanceCourse(String idCourse) throws SQLException;

    public Map<Student, List<periodsClass>> getCourseAttendance(String idCourse) throws SQLException;

    public int getIDPeriods(int idAttendance,int NumWeek);

    public boolean AddCourseAttendance(int idAttendance, char typeAttendance, int IdInstructor, int NumWeek) throws SQLException;

    public boolean updateTypeAttendance(int idPeriods, char typeAttendance, int NumWeek) throws SQLException;
    public int InsertTypeAttendance(char typeAttendance,int NumWeek) throws SQLException;
    public boolean  deleteTypeAttendance(int idPeriods)  throws SQLException;



    public boolean insertIntoRecordAttendance(int IdInstructor, int enrollAttendance, int idPeriods) throws SQLException;
}
