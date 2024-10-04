package com.service;

import com.dao.UserDAO;
import com.entities.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }


    public User getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        return user;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();


        return users;
    }

    // Add a new user
    public void addUser(User user) {
        System.out.println("Adding user: " + user.getUsername());
        userDAO.addUser(user);
    }

    // Update an existing user
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    // Delete a user by ID
    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }
}