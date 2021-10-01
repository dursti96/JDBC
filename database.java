import java.sql.*;
import java.util.ArrayList;

public class database {

    private String dBUserName;
    private String dBPassword;
    private String dBName;


    public database(String dBUserName, String dBPassword, String dBName) {
        this.dBName = dBName;
        this.dBUserName = dBUserName;
        this.dBPassword = dBPassword;
    }



    /** select * from ?; return values are Strings */
    public String[][] SQLSelectAll(String tableName) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dBName, this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName);

        int rowCount = SQLSelectCountAll(tableName);
        int columnCount = resultSet.getMetaData().getColumnCount();
        String[][] result = new String[rowCount][columnCount];
        int a = 0;  // row number

        while(resultSet.next()) {
            for(int i = 0; i < columnCount; i++){

                result[a][i] = resultSet.getObject(i+1).toString();
            }
            a++;
        }
        return result;
    }


    /** select count All */
    public Integer SQLSelectCountAll(String tableName) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dBName, this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from " + tableName);

        //get result
        resultSet.next();
        Integer result = resultSet.getInt("count(*)");

        return result;
    }


    /** get names of columns from table */
    private ArrayList<String> SQLGetColumnNames(String tableName) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "INFORMATION_SCHEMA", this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "' AND TABLE_SCHEMA = '" + this.dBName + "' ORDER BY ORDINAL_POSITION");

        ArrayList<String> result = new ArrayList<>();

        while(resultSet.next()) {
            result.add(resultSet.getString("COLUMN_NAME"));
        }

        return result;
    }

    /** get column names of all columns where auto increment is on */
    public ArrayList<String> SQLCheckAutoIncrement(String tableName) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "INFORMATION_SCHEMA", this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "' AND TABLE_SCHEMA = '" + this.dBName + "' AND EXTRA like '%auto_increment%' ORDER BY ORDINAL_POSITION");

        ArrayList<String> result = new ArrayList<>();

        while(resultSet.next()) {
            result.add(resultSet.getString("COLUMN_NAME"));
        }

        return result;
    }


    /** insert into tableName values (?,?,?,...); Integers,Dates,... have to be written as String; Date-Format: "YYYYMMDD"
     * if a column is auto increment, no parameter is needed; example:
     * if column names: "ID", "Username", "Password"; "ID" auto increments; then SQLInsert(example-username, example-password)*/
    public boolean SQLInsert(String tableName, String ... data) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dBName, this.dBUserName, this.dBPassword);

        // get prepared statement with varying number of parameters
        String sql = "insert into " + tableName + " (";
        ArrayList<String> columnNames = SQLGetColumnNames(tableName);

        // add column names to prepared statement, except auto increment columns
        for (int i = 0; i < columnNames.size() -1; i++){
            if(!SQLCheckAutoIncrement(tableName).contains(columnNames.get(i))){
                sql = sql + columnNames.get(i) + ", ";
            }
        }

        sql = sql + columnNames.get(columnNames.size()-1);
        sql = sql + ") values (";

        // add placeholder for insert values to prepared statement
        for(int i = 0; i < data.length-1; i++){
            sql = sql + "?,";
        }
        sql = sql + "?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // set parameters of data
        for(int i = 0; i < data.length; i++){
            preparedStatement.setString(i+1,data[i]);
        }

        int rowsUpdated = preparedStatement.executeUpdate();

        if(rowsUpdated == 0){
            return false;
        }
        else{
            return true;
        }
    }


}
