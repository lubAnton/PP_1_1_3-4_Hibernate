package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov" , (byte) 50);
        userService.saveUser("Oleg", "Petrov", (byte) 55);
        userService.saveUser("Olga", "Ivanova", (byte) 36);
        userService.saveUser("Artem", "Petrov", (byte) 5);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();// реализуйте алгоритм здесь
    }
}
