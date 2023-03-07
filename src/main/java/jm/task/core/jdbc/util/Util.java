package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/ppdb1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "luboshA1984!";

    private Util () {

    }

    public static Connection getConnections () {
        Connection connection;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;// реализуйте настройку соеденения с БД
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к БД");
            throw new RuntimeException(e);
        }
    }
    public static SessionFactory getFactory () {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", URL);
        properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.connection.username", USERNAME);
        properties.setProperty("hibernate.connection.password", PASSWORD);
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.show_sql", "true");
        SessionFactory factory = new Configuration().addProperties(properties)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        return factory;
    }
}
