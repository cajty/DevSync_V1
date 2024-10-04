package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.entities.User;
import com.entities.UserRole;
import com.service.UserService;

import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        returnThatDashboard(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");

        boolean status = false;

        if ("PUT".equalsIgnoreCase(method)) {
            User newUser = requestToUser(request);
            status = handleAdd(newUser);
        }


        if ("DELETE".equalsIgnoreCase(method)) {
            int userId = Integer.parseInt(request.getParameter("id"));
             status  =  handleDelete(userId);

        }

        if(status){
            returnThatDashboard(request, response);
        }
    }

    private boolean handleAdd(User newUser){
        try {
            userService.addUser(newUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean handleDelete(int userId){
        try {
            userService.deleteUser(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



private void returnThatDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userService.getAllUsers());
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }


    private User requestToUser(HttpServletRequest request) {
        System.out.println("requestToUser");
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setRole(UserRole.USER);
        return user;
    }
}
