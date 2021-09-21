import java.sql.*;

public class database {

    private String dBUserName;
    private String dBPassword;
    private String dBName;


    public database(String dBUserName, String dBPassword, String dBName) {
        this.dBName = dBName;
        this.dBUserName = dBUserName;
        this.dBPassword = dBPassword;
    }


    /** select * from ?; println result; only for tables with 4 columns */
    public void SQLSelectAll(String tableName) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dBName, this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName);
        StringBuilder stringBuilder = new StringBuilder();
        while(resultSet.next()) {
            stringBuilder.append(resultSet.getInt(1)).append(" ");
            stringBuilder.append(resultSet.getString(2)).append(" ");
            stringBuilder.append(resultSet.getString(3)).append(" ");
            stringBuilder.append(resultSet.getString(4 )).append(" ").append("\n");
        }
        System.out.println(stringBuilder);
    }


    /** insert into table; values need to be in the right order; only tables with 4 values */
    public void SQLInsert(String tableName, int value1, String value2, String value3, String value4) throws SQLException, ClassNotFoundException {

        // first query for column names
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "INFORMATION_SCHEMA", this.dBUserName, this.dBPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "' AND TABLE_SCHEMA = '" + this.dBName + "' ORDER BY ORDINAL_POSITION");

        StringBuilder stringBuilder = new StringBuilder();
        while(resultSet.next()) {

            stringBuilder.append(resultSet.getString(1 )).append("\n");
        }
        System.out.println(stringBuilder);

        // second query, put in column names:
    }

}
