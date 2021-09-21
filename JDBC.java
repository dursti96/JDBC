import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {


    public static void main(String[] args) {

        database database1 = new database("alex", "asdf", "jdbc");



        try {
            //database1.SQLSelectAll("user");
            database1.SQLInsert("user",2,"a","a","a");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }


    }




}
