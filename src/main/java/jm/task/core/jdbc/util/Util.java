package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
}
