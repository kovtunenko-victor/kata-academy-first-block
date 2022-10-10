package ru.kata.academy.kovtunenko.first.block.util;

import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.Session;
import org.hibernate.service.ServiceRegistry;
import ru.kata.academy.kovtunenko.first.block.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static final String SERVER_ADDRESS = "31.31.199.29";
    private static final String SERVER_PORT = "3306";
    private static final String DATA_BASE = "test";
    private static final String USER = "root";
    private static final String PASSWORD = "reporter2015";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mysql://%s:%s/%s", SERVER_ADDRESS, SERVER_PORT, DATA_BASE),
                USER, PASSWORD);
    }

    public static Session getOpenSession() {
        if (sessionFactory == null) {
            Configuration cfg = new Configuration();
            Properties prop = new Properties();
            prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            prop.put(Environment.URL, String.format("jdbc:mysql://%s:%s/%s", SERVER_ADDRESS, SERVER_PORT, DATA_BASE));
            prop.put(Environment.USER, USER);
            prop.put(Environment.PASS, PASSWORD);
            prop.put(Environment.SHOW_SQL, "false");
            prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            prop.put(Environment.HBM2DDL_AUTO, "none");

            cfg.setProperties(prop);
            cfg.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory.openSession();
    }
}
