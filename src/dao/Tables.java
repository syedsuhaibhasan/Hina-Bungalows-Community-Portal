package dao;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class Tables {
         public static void main(String [] args){
        Connection con = null;
        Statement st = null;  
        try
        {
            con = ConnectionProvider.getcon();
            st = con.createStatement();
            if (!(tableExists(st,"user_data"))) {
                st.executeUpdate("CREATE TABLE user_data("
                        + " id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "name TEXT NOT NULL," 
                        + "ownership BOOLEAN," 
                        + "email VARCHAR(50) NOT NULL UNIQUE,"
                        + "pwd_hash VARCHAR(255) NOT NULL,"
                        + "house_no INT NOT NULL UNIQUE,"
                        + "maintainence_paid BOOLEAN,"
                        + " updated_at DATETIME,"
                        + "last_login_at DATETIME"
                        + ");" );
            }
            if (!(tableExists(st,"admin_data"))) {
                st.executeUpdate("CREATE TABLE admin_data("
                        + "id INT PRIMARY KEY,"
                        + "email VARCHAR(50) NOT NULL UNIQUE,"
                        + "pwd_hash VARCHAR(255) NOT NULL,"
                        + "ke_bill BLOB,"
                        + "kwsb_bill BLOB"
                        + ");");
            }
        }catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
      
    public static boolean tableExists(Statement st, String tableName) throws Exception{
    ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" +tableName+ "'");
    return resultSet.next();
    }
   }
