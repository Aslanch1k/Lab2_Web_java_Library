package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class contains settings for Database Connection
 */
public class DbConnection {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/Library";
        String user = "root";
        String password = "097890";
        return DriverManager.getConnection(url, user, password);
    }
}
