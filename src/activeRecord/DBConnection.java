package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBConnection {

    private Properties connectionProps;
    private static Connection connect;
    private String userName, password, serverName, portNumber, tableName, dbName, urlDB;

    public DBConnection(String userName, String password, String serverName, String portNumber, String tableName, String dbName) throws SQLException {
        this.userName = userName;
        this.password = password;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.tableName = tableName;
        this.dbName = dbName;

        connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        this.connect = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized Connection getConnection() {
        if (connect == null) new DBConnection();
    }

    public void setNomDB(String nomDB) {
    }
}
