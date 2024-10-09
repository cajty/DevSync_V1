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

    private void returnThatDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", userService.getAllUsers());
        request.getRequestDispatcher("/view/home.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        returnThatDashboard(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");

        boolean status = false;

        if ("POST".equalsIgnoreCase(method)) {
            User User = requestToUser(request);
            status = handleAdd(User);
        }

        if ("PUT".equalsIgnoreCase(method)) {


            User User = requestToUser(request);

            User.setId( Long.parseLong(request.getParameter("id")));
            status = handleUpdate(User);
        }


        if ("DELETE".equalsIgnoreCase(method)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            status  =  handleDelete(userId);

        }

        if(status){
            returnThatDashboard(request, response);
        }
    }

    private boolean handleUpdate(User User){
        try {
            userService.updateUser(User);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean handleAdd(User User){
        try {
            userService.addUser(User);
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




















































































//package com.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import com.entities.User;
//import com.entities.UserRole;
//import com.service.UserService;
//
//import java.io.IOException;
//
//@WebServlet(name = "UserServlet", value = "/user")
//public class UserServlet extends HttpServlet {
//    private UserService userService;
//
//    @Override
//    public void init() {
//        userService = new UserService();
//    }
//
//    private void returnThatDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setAttribute("users", userService.getAllUsers());
//        request.getRequestDispatcher("/home.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        returnThatDashboard(request, response);
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String method = request.getParameter("_method");
//
//        boolean status = false;
//
//        switch (method.toUpperCase()) {
//            case "POST":
//                User userToAdd = requestToUser(request);
//                status = handleAdd(userToAdd);
//                break;
//            case "PUT":
//                User userToUpdate = requestToUser(request);
//                userToUpdate.setId(Long.parseLong(request.getParameter("id")));
//                status = handleUpdate(userToUpdate);
//                break;
//            case  "DELETE":
//                int userId = Integer.parseInt(request.getParameter("id"));
//                status = handleDelete(userId);
//            default:
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid method");
//                return;
//        }
//        if(status){
//            returnThatDashboard(request,response);
//        }
//    }
//
//    private boolean handleUpdate(User User){
//        try {
//            userService.updateUser(User);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private boolean handleAdd(User User){
//        try {
//            userService.addUser(User);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private boolean handleDelete(int userId){
//        try {
//            userService.deleteUser(userId);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//    private User requestToUser(HttpServletRequest request) {
//        System.out.println("requestToUser");
//        return new User(
//                request.getParameter("username"),
//                request.getParameter("password"),
//                request.getParameter("firstName"),
//                request.getParameter("lastName"),
//                request.getParameter("email"),
//                UserRole.USER
//        );
//    }
//}
