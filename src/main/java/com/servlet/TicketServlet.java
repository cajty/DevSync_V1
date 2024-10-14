package com.servlet;

import com.entities.Ticket;
import com.entities.TicketStatus;
import com.service.TagService;
import com.service.TicketService;
import com.validation.TaskValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "IN_PROGRESS", value = "/ticket/*")
public class TicketServlet extends HttpServlet {



    @Override
    public void init() {
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/user_ticket.jsp").forward(request, response);
    }




}