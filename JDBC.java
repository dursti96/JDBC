import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {


    public static void main(String[] args) {

        database database1 = new database("alex", "asdf", "jdbc");


        try {
            System.out.println(database1.SQLSelectCountAll("test"));
            System.out.println(database1.SQLCheckAutoIncrement("test"));
            System.out.println(database1.SQLInsert("test", "username", "20220311", "22"));
            String [][] selectAll = database1.SQLSelectAll("test");

            for(int i = 0; i < selectAll.length; i++){
                for(int a = 0; a < selectAll[0].length; a++){
                    System.out.print(selectAll[i][a] + "    ");
                }
                System.out.println();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
