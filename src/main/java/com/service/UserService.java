package com.service;

import com.repository.TicketRepository;
import com.repository.UserRepository;
import com.entities.Ticket;
import com.entities.User;

import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    public UserService() {
        this.userRepository = new UserRepository();
        this.ticketRepository = new TicketRepository();
    }

    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // Add a new user
    public void addUser(User user) {
        System.out.println("Adding user: " + user.getUsername());
        userRepository.addUser(user);
    }

    // Update an existing user
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    // Delete a user by ID
    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }



    public void resetDailyReplaceTokens() {
        userRepository.resetDailyReplaceTokens();
    }

    public void resetMonthlyDeleteTokens() {
        userRepository.resetMonthlyDeleteTokens();
    }
}