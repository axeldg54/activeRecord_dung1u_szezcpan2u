package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBConnection {

    private Properties connectionProps;
    private static Connection connect;
    private String userName, password, serverName, portNumber, tableName, dbName;
    public DBConnection(String userName, String password, String serverName, String portNumber, String tableName, String dbName) throws SQLException {
        if (connect == null) {
            this.userName = userName;
            this.password = password;
            this.serverName = serverName;
            this.portNumber = portNumber;
            this.tableName = tableName;
            this.dbName = dbName;

            connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            connect = DriverManager.getConnection(urlDB,connectionProps);
        }
    }
    public static synchronized Connection getConnection() {
        return connect;
    }

    public void setNomDB(String nomDB) {}
}
