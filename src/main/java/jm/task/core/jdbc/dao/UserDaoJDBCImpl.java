package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static long idUD = 0;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE users (Id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR (20) NOT NULL, lastName VARCHAR (20) NOT NULL, age TINYINT NOT NULL);";

        try (Statement statement = Util.getConnections().createStatement()) {
            statement.execute(createTable);
        }catch (SQLException e) {
            System.out.println("Таблица существует");
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE users";
        try (Statement statement = Util.getConnections().createStatement()) {
            statement.execute(dropTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, lastName, age )VALUES (?,?,?)";
        idUD++;
        try (PreparedStatement preparedStatement = Util.getConnections().prepareStatement(saveUser)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeUserById  = "DELETE FROM users WHERE Id = " + id;
        try (Statement statement = Util.getConnections().createStatement()) {
            statement.execute(removeUserById);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String getAllUsers = "SELECT * FROM users";
        List <User> users = new ArrayList<>();
        try (Statement statement = Util.getConnections().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User userToPrint: users) {
            System.out.println(userToPrint.toString());
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE TABLE users";
        try (Statement statement = Util.getConnections().createStatement()) {
            statement.execute(cleanUsersTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
