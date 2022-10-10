package ru.kata.academy.kovtunenko.first.block.service;

import ru.kata.academy.kovtunenko.first.block.dao.HibernateUserDao;
import ru.kata.academy.kovtunenko.first.block.dao.UserDao;
import ru.kata.academy.kovtunenko.first.block.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao dao = new HibernateUserDao();

    public void createUsersTable() {
        dao.createUsersTable();
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}