package activeRecord;

import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    private DBConnection dbConnection;

    @BeforeEach
    public void init() throws SQLException {
        dbConnection = new DBConnection();
    }

}