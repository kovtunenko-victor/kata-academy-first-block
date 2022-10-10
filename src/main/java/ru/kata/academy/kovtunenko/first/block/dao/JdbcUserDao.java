package ru.kata.academy.kovtunenko.first.block.dao;

import ru.kata.academy.kovtunenko.first.block.model.User;
import ru.kata.academy.kovtunenko.first.block.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserDao.class.getName());

    public JdbcUserDao() {

    }

    public void createUsersTable() {
        try {
            executePreparedStatement("CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30), last_name VARCHAR(30), age TINYINT, PRIMARY KEY (id))");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when create user table", ex);
        }
    }

    public void dropUsersTable() {
        try {
            executePreparedStatement("DROP TABLE users");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when drop user table", ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection con = Util.getConnection()) {
            PreparedStatement st = con.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)");
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);

            st.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when save user", ex);
        }
    }

    public void removeUserById(long id) {
        try(Connection con = Util.getConnection()) {
            PreparedStatement st = con.prepareStatement("DELETE FROM users where id = 1");
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when remove user by id", ex);
        }
    }

    public List<User> getAllUsers() {
        try {
            List<User> userList = new ArrayList<>();

            try(Connection con = Util.getConnection()) {
                PreparedStatement st = con.prepareStatement("SELECT id, name, last_name, age FROM users");
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setAge(rs.getByte("age"));
                    userList.add(user);
                }
            }

            return userList;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when remove user by id", ex);
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        try {
            executePreparedStatement("TRUNCATE TABLE users");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Exception when truncate user table", ex);
        }
    }

    private void executePreparedStatement(String sqlQuery) throws SQLException {
        try(Connection con = Util.getConnection()) {
            PreparedStatement st = con.prepareStatement(sqlQuery);
            st.execute();
        }
    }
}
