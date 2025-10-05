import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/employee_db"; 
    private static final String USER = "postgres"; // your PostgreSQL username
    private static final String PASSWORD = "Rakikris003&@"; // your PostgreSQL password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // This driver comes with most Java installations when pgjdbc is on classpath
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // If you get driver not found error: add PostgreSQL JDBC driver jar to classpath
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}
