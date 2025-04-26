import model.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnection_Success() {

        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "Connection should not be null.");
            assertFalse(connection.isClosed(), "Connection should be open.");
        } catch (SQLException e) {
            fail("Exception occurred while getting the connection: " + e.getMessage());
        }
    }

    @Test
    void testGetConnection_InvalidCredentials() {

        String invalidUrl = "jdbc:postgresql://localhost:5432/nothing";
        String invalidUser = "nothing";
        String invalidPassword = "nothing";

        SQLException thrown = assertThrows(SQLException.class, () -> {
            DriverManager.getConnection(invalidUrl, invalidUser, invalidPassword);
        });

        assertNotNull(thrown, "SQLException should be thrown for invalid credentials.");
    }
}

