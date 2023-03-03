package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE users (Id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR (20) NOT NULL, lastName VARCHAR (20) NOT NULL, age TINYINT NOT NULL);";

        Connection connection = null;
        Statement statement = null;

        try {
            connection = Util.getConnections();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(createTable);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        finally {
            closing(connection, statement);
        }

    }



    public void dropUsersTable() {
        String dropTable = "DROP TABLE users";
        Connection connection = null;
        Statement statement = null;
        try { connection = Util.getConnections();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            closing(connection, statement);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO users (name, lastName, age )VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnections();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(saveUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            closing(connection, preparedStatement);
        }
    }

    public void removeUserById(long id) {
        String removeUserById = "DELETE FROM users WHERE Id = " + id;
        Connection connection =null;
        Statement statement =null;
        try {
            connection = Util.getConnections();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(removeUserById);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
        finally {
            closing(connection, statement);
        }
    }

    public List<User> getAllUsers() {
        String getAllUsers = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Util.getConnections();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        } finally {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            closing(connection, statement);
        }
        for (User userToPrint : users) {
            System.out.println(userToPrint.toString());
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE TABLE users";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnections();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(cleanUsersTable);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }finally {
            closing(connection, statement);
        }
    }

    private void closing(Connection connection, Statement statement) {
        if (statement!=null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
