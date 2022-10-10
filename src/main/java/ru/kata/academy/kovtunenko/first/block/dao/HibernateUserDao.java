package ru.kata.academy.kovtunenko.first.block.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.kata.academy.kovtunenko.first.block.model.User;
import ru.kata.academy.kovtunenko.first.block.util.Util;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(HibernateUserDao.class.getName());

    public HibernateUserDao() {

    }

    public void createUsersTable() {
        executeSql("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT, PRIMARY KEY (id)) AUTO_INCREMENT = 1");
    }

    public void dropUsersTable() {
        executeSql("DROP TABLE users");
    }

    public void saveUser(String name, String lastName, byte age) {
        Transaction tran = null;

        try (Session session = Util.getOpenSession()) {
            tran = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tran.commit();
        } catch (Exception ex) {
            if (tran != null) {
                tran.rollback();
            }

            LOGGER.log(Level.SEVERE, "Exception when save user", ex);
        }
    }

    public void removeUserById(long id) {
        Transaction tran = null;

        try (Session session = Util.getOpenSession()) {
            User user = session.get(User.class, id);
            tran = session.beginTransaction();
            session.delete(user);
            tran.commit();
        } catch (Exception ex) {
            if (tran != null) {
                tran.rollback();
            }

            LOGGER.log(Level.SEVERE, "Exception when remove user by id", ex);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = Util.getOpenSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception when get users list", ex);
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        executeSql("TRUNCATE TABLE users");
    }

    private void executeSql(String sql) {
        Transaction tran = null;

        try (Session session = Util.getOpenSession()) {
            tran = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tran.commit();
        } catch (Exception ex) {
            if (tran != null) {
                tran.rollback();
            }

            LOGGER.log(Level.SEVERE, "Exception when execute native sql [" + sql + "]", ex);
        }
    }
}
