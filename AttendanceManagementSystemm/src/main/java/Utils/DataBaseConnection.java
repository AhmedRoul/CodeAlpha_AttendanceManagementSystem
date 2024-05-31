package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String DRIVER = "org.postgresql.Driver";
    private static   final String USERNAME = "postgres";
    private static  final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static  java.sql.Connection connection = null;
    public static Connection openConnection() {
        //check the connection
        if(connection != null) {
            return connection;
        }else {

            try {
                //load the driver
                Class.forName(DRIVER);
                //get the connection
                connection = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);

            }catch(Exception e) {
                e.printStackTrace();
            }
            //return connection
            return connection;
        }

    }
    public static void closeConnection(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                System.err.println("Error closing connection: " + e.getMessage());
//            }
//        }
    }
}
