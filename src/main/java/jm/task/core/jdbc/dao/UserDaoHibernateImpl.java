package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;

import javax.persistence.Id;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory = Util.getFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            String createTable = "CREATE TABLE IF NOT EXISTS users (Id INT AUTO_INCREMENT PRIMARY KEY, " +
                                   "name VARCHAR (20) NOT NULL, lastName VARCHAR (20) NOT NULL, " +
                                    "age TINYINT NOT NULL);";
            session.createSQLQuery(createTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            String dropTable = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(dropTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (
            Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> userList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            userList = session.createQuery("FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (User userToPtint: userList) {
            System.out.println(userToPtint.toString());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Успешно удалено");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
