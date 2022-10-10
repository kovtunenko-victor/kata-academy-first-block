package ru.kata.academy.kovtunenko.first.block.dao;

import ru.kata.academy.kovtunenko.first.block.model.User;
import ru.kata.academy.kovtunenko.first.block.util.Util;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(HibernateUserDao.class.getName());

    public HibernateUserDao() {

    }

    public void createUsersTable() {
        executeNativeQuery("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT, PRIMARY KEY (id)) AUTO_INCREMENT = 1");
    }

    public void dropUsersTable() {
        executeNativeQuery("DROP TABLE users");
    }

    public void saveUser(String name, String lastName, byte age) {
        EntityManager em = null;
        try {
            em = Util.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(new User(name, lastName, age));
            em.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if(em != null) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Exception when save user", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void removeUserById(long id) {
        EntityManager em = null;
        try {
            em = Util.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(User.class, id));
            em.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if(em != null) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Exception when remove user by id", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<User> getAllUsers() {
        EntityManager em = null;
        try {
            em = Util.getEntityManagerFactory().createEntityManager();
            return em.createQuery("from User", User.class).getResultList();
        } catch (IllegalStateException | PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Exception when get users list", ex);
            return Collections.emptyList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void cleanUsersTable() {
        executeNativeQuery("TRUNCATE TABLE users");
    }

    private void executeNativeQuery(String sql) throws IllegalStateException, PersistenceException {
        EntityManager em = null;
        try {
            em = Util.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.createNativeQuery(sql).executeUpdate();
            em.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException ex) {
            if(em != null) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Exception when execute native sql [" + sql + "]", ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
