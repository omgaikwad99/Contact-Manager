import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
    private static final Logger logger = LogManager.getLogger(DBHelper.class);

    private String jdbcUrl;
    private String userName;
    private String password;

    public DBHelper() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error occurred while loading database properties: " + e.getMessage());
        }

        jdbcUrl = properties.getProperty("jdbcUrl");
        userName = properties.getProperty("userName");
        password = properties.getProperty("password");

        System.setProperty("log4j.configurationFile", "log4j2.properties");
    }

    public Connection establishDBConnection() {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, userName, password);
            return connection;
        } catch (SQLException e) {
            logger.error("Error occurred while connecting to the database: " + e.getMessage());
        }
        return null;
    }
}
