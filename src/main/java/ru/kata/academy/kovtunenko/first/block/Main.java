package ru.kata.academy.kovtunenko.first.block;

import ru.kata.academy.kovtunenko.first.block.service.UserService;
import ru.kata.academy.kovtunenko.first.block.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        String userName;
        UserService service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser(userName = "Viktor", "Kovtunenko", (byte)32);
        System.out.printf("User with name %s inserted into db%s", userName, System.lineSeparator());

        service.saveUser(userName = "Vladimir", "Ovsanikov", (byte)55);
        System.out.printf("User with name %s inserted into db%s", userName, System.lineSeparator());

        service.saveUser(userName = "Andrey", "Popov", (byte)32);
        System.out.printf("User with name %s inserted into db%s", userName, System.lineSeparator());

        service.saveUser(userName = "Timur", "Husainov", (byte)37);
        System.out.printf("User with name %s inserted into db%s", userName, System.lineSeparator());


        service.getAllUsers().forEach(System.out::println);

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
