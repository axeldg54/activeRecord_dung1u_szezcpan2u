package activeRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    private Connection connection1;
    private Connection connection2;

    @BeforeEach
    public void init() throws SQLException {
        connection1 = DBConnection.getConnection();
        connection2 = DBConnection.getConnection();
    }

    @Test
    public void test_connectionUnique_ok() {
        assertEquals(connection1, connection2);
    }

    @Test
    public void test_typeConnection_ok() throws SQLException {
        Connection connection3 = null;
        assertEquals(connection3, null);
        connection3 = DBConnection.getConnection();
        assertTrue(connection1 instanceof java.sql.Connection);
    }

    @Test
    public void test_setDbName_ok() throws SQLException {
        Connection connection = DBConnection.getConnection();
        DBConnection.setNomDB("testpersonne2");
        Connection connection2 = DBConnection.getConnection();
        assertNotEquals(connection, connection2);
    }
}