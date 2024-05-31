package DataAcessObject.imp;

import DataAcessObject.Attendancedao;
import Entitys.Student;
import Entitys.periodsClass;
import Utils.DataBaseConnection;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class AttendancedaoImp implements Attendancedao {
    private static final Logger logger = Logger.getLogger(AttendancedaoImp.class);

    @Override
    public Map<Integer, Student> getStudentAttendanceCourse(String idCourse) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select  s.id_stud ,S.firstName ,S.lastName ,a.enroll_no " +
                " from Student s , Attendance a   " +
                " where a.Id_student =s.id_stud  and  a.ID_course =?  ORDER BY s.id_stud  ASC ;";
        PreparedStatement preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,idCourse);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<Integer,Student> studentMap=new TreeMap<>(Comparator.comparingInt(a -> a));
        while (resultSet.next()) {

            int idStudent = resultSet.getInt(1);

            Student student = new Student();

            student.setId(idStudent);
            student.setFirstName(resultSet.getString(2));
            student.setLastName(resultSet.getString(3));
            student.setID_attendance(resultSet.getInt(4));
            studentMap.put(idStudent, student);
        }
        return studentMap;
    }

    @Override
    public Map<Student, List<periodsClass>> getCourseAttendance(String idCourse) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql ="select  s.id_stud ,p.id_periods,p.state_Student ,p.week " +
                " from Student s , Attendance a,record_Attendance ra ,periods p  " +
                " where a.Id_student =s.id_stud and a.enroll_no=ra.enroll_no_rd  " +
                " and p.id_periods=ra.id_periods_rd   and  a.ID_course =? ;";
        PreparedStatement preparedStatement  =connection.prepareStatement(sql);
        preparedStatement.setString(1,idCourse);
        ResultSet resultSet = preparedStatement.executeQuery();


        Map<Integer, List<periodsClass>> Attendance=new TreeMap<>();

        while (resultSet.next()){

           int idStudent= resultSet.getInt(1);


            periodsClass periodsClass=new periodsClass();
            periodsClass.setId(resultSet.getInt(2));
            periodsClass.setState_Student(resultSet.getString(3).charAt(0));
            periodsClass.setWeek(resultSet.getInt(4));
            List<Entitys.periodsClass> list;

            if(!Attendance.containsKey(idStudent)){

                list = new ArrayList<>();

            }
           else{
                list = Attendance.get(idStudent);
            }
            list.add(periodsClass);
            Attendance.put(idStudent, list);

        }

        return ADDMissingElements(Attendance,idCourse);
    }

    @Override
    public int getIDPeriods(int idAttendance, int NumWeek) {
        Connection connection= DataBaseConnection.openConnection();
        String sql="select p.id_periods  from periods p,record_Attendance ra where p.id_periods=ra.id_periods_rd and  ra.enroll_no_rd  =? and p.week=?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idAttendance);
            preparedStatement.setObject(2,NumWeek);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
              return   resultSet.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean AddCourseAttendance(int idAttendance, char typeAttendance, int IdInstructor, int NumWeek) throws SQLException {

        int  idPeriods = getIDPeriods(idAttendance,NumWeek);

        logger.info("idPeriods :"+idPeriods);
        logger.info("idAttendance"+idAttendance);
        if(NumWeek>0) {
            if (idPeriods == 0 && (typeAttendance == 'P' || typeAttendance == 'A')) {
                logger.info("con :1");
                idPeriods = this.InsertTypeAttendance(typeAttendance, NumWeek);
                return this.insertIntoRecordAttendance(IdInstructor, idAttendance, idPeriods);
            }
            if (idPeriods > 0 && (typeAttendance == 'N')) {
                logger.info("con :2");
                return deleteTypeAttendance(idPeriods);
            }
            if (idPeriods > 0 && (typeAttendance == 'P' || typeAttendance == 'A')) {
                logger.info("con :3");
                return this.updateTypeAttendance(idPeriods, typeAttendance, NumWeek);
            }
            logger.info("con :4");
        }
        logger.info("con :5");
        return false;

    }

    @Override
    public boolean updateTypeAttendance(int idPeriods, char typeAttendance, int NumWeek) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql="update  periods set week=?,state_Student =?  WHERE id_periods = ?;";
        PreparedStatement preparedStatement =connection.prepareStatement(sql);
        preparedStatement.setInt(1,NumWeek);
        preparedStatement.setObject(2,typeAttendance);
        preparedStatement.setInt(3,idPeriods);
        int row = preparedStatement.executeUpdate();
        return row>0;
    }

    @Override
    public int InsertTypeAttendance(char typeAttendance, int NumWeek) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql="INSERT INTO periods (id_periods, week, state_Student)" +
                "VALUES (NEXTVAL('periods_SEQ'), ?, ?)" +
                "RETURNING id_periods;";
        PreparedStatement preparedStatement =connection.prepareStatement(sql);
        preparedStatement.setObject(2,typeAttendance);
        preparedStatement.setInt(1,NumWeek);

        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id_periods = resultSet.getInt("id_periods");
           return id_periods;
        }
        return 0;
    }

    @Override
    public boolean deleteTypeAttendance(int idPeriods) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();

        String sql="DELETE FROM periods WHERE id_periods = ?;";
        PreparedStatement preparedStatement =connection.prepareStatement(sql);
        preparedStatement.setInt(1,idPeriods);
        int row = preparedStatement.executeUpdate();
        return row>0;
    }

    @Override
    public boolean insertIntoRecordAttendance(int IdInstructor, int enrollAttendance, int idPeriods) throws SQLException {
        Connection connection= DataBaseConnection.openConnection();
        String sql="insert into record_Attendance(id_periods_rd, enroll_no_rd, id_instr_rd)" +
                " VALUES(?,?,?);";
        PreparedStatement preparedStatement =connection.prepareStatement(sql);
        preparedStatement.setInt(1,idPeriods);
        preparedStatement.setInt(2,enrollAttendance);
        preparedStatement.setInt(3,IdInstructor);

        int row = preparedStatement.executeUpdate();
        return row>0;
    }

    private  Map<Student, List<periodsClass>> ADDMissingElements(Map<Integer, List<periodsClass>> dic,String idCourse) throws SQLException {
        Map<Student, List<periodsClass>> studentListMap=new LinkedHashMap<>();

        Map<Integer,Student> student=getStudentAttendanceCourse(idCourse);
        boolean flagStudentIsEmpty=false;

        if(dic.size()==0)
            flagStudentIsEmpty=true;

        for (Map.Entry<Integer,Student> entry : student.entrySet()) {

            if (flagStudentIsEmpty)
                studentListMap.put(entry.getValue(), SortList(new ArrayList<>()));
            else
                studentListMap.put(entry.getValue(), SortList(dic.get(entry.getKey())));

        }

        return  studentListMap;
    }
    private  List<periodsClass> SortList(List<periodsClass> list){

        List<periodsClass>  completeList =new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            periodsClass pc = new periodsClass();
            pc.setWeek( i);
            pc.setId(0);
            pc.setState_Student('N');

            completeList.add(pc);
        }
        if(list==null||list.size()==0){
            return completeList;
        }
        list.sort(Comparator.comparingInt(periodsClass::getWeek));
        int index=0;
        for (periodsClass pc : completeList) {
            if (index < list.size() && list.get(index).getWeek() == pc.getWeek() ) {
                pc.setId(list.get(index).getId());
                pc.setState_Student( list.get(index).getState_Student());
                index++;
            }
        }
        return completeList;
    }

}
