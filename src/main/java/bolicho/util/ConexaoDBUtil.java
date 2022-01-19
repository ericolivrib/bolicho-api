package bolicho.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDBUtil {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5433/bolicho";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection connection;
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
