package com.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.config.DbConnection;
import com.entities.User;

import java.util.List;

import static java.lang.System.out;

public class UserDAO {
    private EntityManager entityManager;

    public UserDAO() {
        this.entityManager = DbConnection.getInstance().getEntityManager();
    }


    public User getUserById(int userId) {
        try {
            // Enable logging of when the method is called
            System.out.println("Attempting to retrieve user by ID...");

            // Wrap with transaction if needed
            User user = entityManager.find(User.class, userId);

            // Log the user found
            System.out.println("User retrieved: " + user);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            // Enable logging of when the method is called
            System.out.println("Attempting to retrieve all users...");

            // Wrap with transaction if needed
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = query.getResultList();



            // Log the number of users found
            System.out.println("Number of users retrieved: " + users.size());

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or empty list in case of error
        }
    }



    // Add a new user
    public void addUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                out.println("Error in addUser method");
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }


    public void updateUser(User user) {
        try {
            entityManager.getTransaction().begin();

            // Fetch the existing user from the database by ID
            User existingUser = entityManager.find(User.class, user.getId());

            if (existingUser != null) {
                // Merge the changes into the existing user entity
                entityManager.merge(user);
            } else {
                // Handle the case where the user doesn't exist (optional)
                System.out.println("User with ID " + user.getId() + " not found!");
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }


    // Delete a user by ID
    public void deleteUser(int userId) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to close the EntityManager when needed
    public void cleanup() {
        DbConnection.getInstance().closeEntityManager();
    }
}