package DataAcessObject.imp;

import DataAcessObject.Userdao;
import Entitys.Role;
import Entitys.User;
import Utils.DataBaseConnection;
import Utils.HibernateSessionFactoryListener;
import org.jboss.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Entitys.Role.Student;
import static Entitys.Role.instructor;

public class Userdaoimp implements Userdao {
    private static final Logger logger = Logger.getLogger(Userdaoimp.class);

    private final String STUDENTSQL="Select id_stud ,username ,email from student where Password_stud =? and ";
    private final String INSTRUCTORSQL="Select id_instr ,username ,email from instructor where Password_instr  =? and ";
    private  final String ADMINSQL="Select id_admin  ,username ,email from adminn where Password_admin  =? and ";
    private  final String ADMINSQLCHECK="Select id_admin   from adminn where email=? ;";
    private  final String INSTRUCTORSQLCHECK="Select id_instr  from instructor where email=? ; ";
    private  final String STUDENTSQLCHECK="Select id_stud  from student where email  =? and ";


    private String Sql="";
    private Connection connection;

    private PreparedStatement preparedStatement;
    private String ValueParametersPassword="";
    private String ValueParameter2="";

    public Userdaoimp (){
        connection = null;
    }

    @Override
    public User IsExist(User user) throws SQLException {
        connection=DataBaseConnection.openConnection();

        Role role=Role.getRole(user.getEmail());
        switch (Role.getRole(user.getEmail())) {
            case Admin -> Sql = ADMINSQLCHECK;
            case Student -> Sql = STUDENTSQLCHECK ;
            case instructor -> Sql = INSTRUCTORSQLCHECK ;
            }
         preparedStatement =connection.prepareStatement(Sql);
        preparedStatement.setString(1,user.getEmail());
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()) {
            if (rs.getInt(1) > 0) {
                user.setId(rs.getInt(1));
            }
        }
        DataBaseConnection.closeConnection(connection);
        user.setRole(role);
        return user;
    }

    @Override
    public User Check(User user) throws SQLException {
        Sql="";

        ///search by username
        if(user.getEmail()==null)
        {
            logger.info("user.getEmail()==null");
            //search in All table
            List<User> users=new ArrayList<>();
            ValueParametersPassword=user.getPassword();
            ValueParameter2=user.getUsername();

            users.add(AdminCheck(user));
            users.add(StudentCheck(user));
            users.add(InstructorCheck(user));
            for (User user1:users) {
                if(user1!=null){
                    return user1;
                }
            }
        }
        //search by email
        else
        {
            logger.info("user.getEmail()!=null");
            ValueParametersPassword=user.getPassword();
            ValueParameter2=user.getEmail();


            switch (Role.getRole(user.getEmail())) {

                case Admin ->
                {

                    this.Sql=ADMINSQL+"email=?;";
                    logger.info(Sql);
                    return  AdminCheck(user);
                }
                case Student ->
                {
                    this.Sql=STUDENTSQL+"email=?;";
                    return  StudentCheck(user );
                }
                case instructor ->
                {
                    this.Sql= this.INSTRUCTORSQL+"email=?;";
                    logger.info(Sql);
                   return InstructorCheck(user);
                }

            }
            return null;

        }
        return null;


    }


    @Override
    public User StudentCheck(User user) throws SQLException {

         connection=DataBaseConnection.openConnection();
        User Studentuser=null;
        //search by username
        if(Objects.equals(Sql, ""))
        {
            Sql+=STUDENTSQL+" username =?;";
        }


        preparedStatement =connection.prepareStatement(Sql);
        preparedStatement.setString(1,ValueParametersPassword);
        preparedStatement.setString(2,ValueParameter2);
        ResultSet rs=preparedStatement.executeQuery();

        while(rs.next()){
            if( rs.getInt(1)>0){
                Studentuser=(new User());
                Studentuser.setId(rs.getInt(1));
                Studentuser.setUsername(rs.getString(2));
                Studentuser.setEmail(rs.getString(3));
                Studentuser.setRole(Student);
            }
        }
        DataBaseConnection.closeConnection(connection);
        return Studentuser;
    }

    @Override
    public User InstructorCheck(User user ) throws SQLException {
        connection=DataBaseConnection.openConnection();
        User Instructoruser=null;
        if(Objects.equals(Sql, ""))
        {
            Sql+=INSTRUCTORSQL+" username =?;";
        }
        preparedStatement =connection.prepareStatement(Sql);
        preparedStatement.setString(1,ValueParametersPassword);
        preparedStatement.setString(2,ValueParameter2);
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){

            if( rs.getInt(1)>0){
                Instructoruser=(new User());
                Instructoruser.setId(rs.getInt(1));
                Instructoruser.setUsername(rs.getString(2));
                Instructoruser.setEmail(rs.getString(3));
                Instructoruser.setRole(instructor);


            }
        }
        DataBaseConnection.closeConnection(connection);
        return Instructoruser;
    }

    @Override
    public User AdminCheck(User user) throws SQLException {
         connection=DataBaseConnection.openConnection();
        User Adminuser =null;
        if(Objects.equals(Sql, ""))
        {
            Sql+=ADMINSQL+" username =?;";
        }
        logger.info(Sql);
        logger.info(ValueParametersPassword);
        logger.info(ValueParameter2);
        preparedStatement =connection.prepareStatement(Sql);
        preparedStatement.setString(1,ValueParametersPassword);
        preparedStatement.setString(2,ValueParameter2);
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){

           if( rs.getInt(1)>0){
               Adminuser=(new User());
               Adminuser.setId(rs.getInt(1));
               Adminuser.setUsername(rs.getString(2));
               Adminuser.setEmail(rs.getString(3));
               Adminuser.setRole(Role.Admin);

           }
        }
        DataBaseConnection.closeConnection(connection);

        return Adminuser;
    }

}
