package ru.kata.academy.kovtunenko.first.block.dao;

import org.hibernate.Session;
import ru.kata.academy.kovtunenko.first.block.model.User;
import ru.kata.academy.kovtunenko.first.block.util.Util;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(HibernateUserDao.class.getName());

    public void createUsersTable() {
        executeSql("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT, PRIMARY KEY (id)) AUTO_INCREMENT = 1");
    }

    public void dropUsersTable() {
        executeSql("DROP TABLE users");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getOpenSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException | IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "Exception when save user", ex);
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.getOpenSession()) {
            User user = session.get(User.class, id);
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException | IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "Exception when remove user by id", ex);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = Util.getOpenSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (IllegalStateException | PersistenceException | IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "Exception when get users list", ex);
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        executeSql("TRUNCATE TABLE users");
    }

    private void executeSql(String sql) {
        try (Session session = Util.getOpenSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException | IllegalArgumentException ex) {
            LOGGER.log(Level.SEVERE, "Exception when execute native sql [" + sql + "]", ex);
        }
    }
}
