package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private Properties connectionProps;
    private static Connection connect;
    private String userName = "root";
    private String password = "";
    private String serverName = "localhost";
    private String portNumber = "3306";
    private static String dbName = "testpersonne";

    public DBConnection() throws SQLException {
        connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        connect = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connect == null) new DBConnection();
        return connect;
    }

    public static void setNomDB(String nomDB) {
        if(nomDB != null && !dbName.equals(nomDB)) {
            dbName = nomDB;
            connect = null;
        }
    }

    public static String getDbName() {
        return dbName;
    }
}
