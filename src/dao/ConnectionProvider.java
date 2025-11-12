
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionProvider{
        private static final String DB_NAME = "communityPortal";
        private static final String DB_URL = "jdbc:mysql://localhost:3306/";
        private static final String DB_USERNAME = "root" ;
        private static final String DB_PASSWORD = "123456";
        
        public static Connection getcon(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if (!isDatabaseExists(con,DB_NAME)){
             createDatabase(con,DB_NAME);
            }
             con = DriverManager.getConnection(DB_URL+DB_NAME, DB_USERNAME, DB_PASSWORD);
             return con;
            }
        catch (Exception ex) {
             System.out.println(ex.getMessage());
              return null;
            }
       }
        
        private static boolean isDatabaseExists(Connection con, String dbName) throws SQLException{
        Statement st = con.createStatement();
        return st.executeQuery("SHOW DATABASES LIKE'"+dbName+"'").next();
        }
        
        private static void createDatabase(Connection con, String dbName) throws SQLException {
            Statement st = con.createStatement();
            st.executeUpdate("CREATE DATABASE "+dbName);
            System.out.println("Database "+ dbName +" created successfully.");
        }
}
