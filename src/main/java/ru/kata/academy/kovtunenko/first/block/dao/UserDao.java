package ru.kata.academy.kovtunenko.first.block.dao;

import ru.kata.academy.kovtunenko.first.block.model.User;

import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
