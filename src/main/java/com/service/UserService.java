package com.service;

import com.repository.TicketRepository;
import com.repository.UserRepository;
import com.entities.Ticket;
import com.entities.User;

import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private TicketRepository ticketDAO;

    public UserService() {
        this.userRepository = new UserRepository();
    }


    public User getUserById(int userId) {
        User user = userRepository.getUserById(userId);
        return user;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = userRepository.getAllUsers();


        return users;
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




    public Ticket canReplace(Long userId, Long ticketId){
        int userReplaceTokens = userRepository.getUserReplaceTokens(userId);
        Ticket ticket = ticketDAO.getTicketById(ticketId);
       if(  userReplaceTokens > 0 && ticket.getCanReplaceTicket()){
           return ticket;
       }
         return null;

    };
    boolean CanDelete(Long userId){
        int userDeleteTokens = userRepository.getUserDeleteTokens(userId);
        return userDeleteTokens > 0;
    };



    boolean replaceTicketWithToken(Long userId, Long ticketId){
        if(canReplace(userId,ticketId) != null){
//
            return true;

        }
            return false;
    };


    boolean deleteTicketWithToken(Long userId, Long ticketId){
        if(CanDelete(userId)){
            if( userRepository.useDeleteToken(userId)){

                return true;

            }
        }
            return false;
    };



























    void resetDailyReplaceTokens(){
        userRepository.resetDailyReplaceTokens();
    };
    void resetMonthlyDeleteTokens(){
        userRepository.resetMonthlyDeleteTokens();
    };

}