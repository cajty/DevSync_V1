package com.servlet;

import com.entities.User;
import com.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserProfileServlet", value = "/user-profile")
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserProfileServlet.class.getName());
    private static final String PROFILE_JSP = "/profile.jsp";
    private static final int FORBIDDEN_ERROR = HttpServletResponse.SC_FORBIDDEN;
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || !isInteger(idParam)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            return;
        }

        int userId = Integer.parseInt(idParam);

        User user = userService.getUserById(userId);
        if (user == null) {
            logger.warning("User with ID: " + userId + " not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }
        System.out.println("User found: " + user.getUsername());
        request.setAttribute("user", user);
        request.getRequestDispatcher(PROFILE_JSP).forward(request, response);
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
