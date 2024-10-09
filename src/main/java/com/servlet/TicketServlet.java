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

@WebServlet(name = "ticket", value = "/ticket/*")
public class TicketServlet extends HttpServlet {

    private TicketService ticketservice;
    private TagService tagService;

    @Override
    public void init() {
        ticketservice = new TicketService();
        tagService = new TagService();
    }

    private void returnTicketHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ticket> tickets = ticketservice.getAllTickets();

            // Separate tickets into categories based on their status
            List<Ticket> inProgressTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.IN_PROGRESS))
                    .collect(Collectors.toList());

            List<Ticket> todoTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.CLOSED))
                    .collect(Collectors.toList());

            List<Ticket> finishedTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.RESOLVED))
                    .collect(Collectors.toList());

            // Set these lists as request attributes
            request.setAttribute("inProgressTickets", inProgressTickets);
            request.setAttribute("todoTickets", todoTickets);
            request.setAttribute("finishedTickets", finishedTickets);
            request.setAttribute("tags", tagService.getAll());

            // Forward to JSP
            request.getRequestDispatcher("/view/show_ticket.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve tickets");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        returnTicketHome(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        System.out.println("\n\n\n\n\nPath info: " + pathInfo);

        boolean status = false;

        Ticket ticket = null ;
        List<Integer> tags = null;
        if(pathInfo.equals("/add") || pathInfo.equals("/update")){
            ticket = requestToTicket(request);
            String[] tagIds = request.getParameterValues("tags");
            tags= tagIds != null ?
                    Arrays.stream(tagIds).map(Integer::parseInt).collect(Collectors.toList())
                    : new ArrayList<>();
        }



        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }


        switch (pathInfo) {
            case "/add":
                status = handleAdd(ticket, tags);
                break;
            case "/update":
                ticket.setId(Long.parseLong(request.getParameter("id")));
                status = handleUpdate(ticket);
                break;
            case "/delete":
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    Long ticketId = Long.parseLong(idParam);
                    status = handleDelete(ticketId);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing ticket ID");
                    return;
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid patFFh");
                return;
        }

        if (status) {
            response.sendRedirect(request.getContextPath() + "/ticket");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Operation failed");
        }
    }

    private boolean handleAdd(Ticket ticket, List<Integer> tagIds) {
        try {
            ticketservice.addTicket(ticket, tagIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false;
        }
    }

    private boolean handleUpdate(Ticket ticket) {
        try {
            ticketservice.updateTicket(ticket);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return false;
        }
    }

    private boolean handleDelete(Long ticketId) {
        try {
            ticketservice.deleteTicket(ticketId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ticket requestToTicket(HttpServletRequest request) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String deadlineParam = request.getParameter("deadline");

        // Validate inputs
        if (title == null || description == null || deadlineParam == null) {
            throw new IllegalArgumentException("Missing parameters");
        }

        LocalDate deadline = LocalDate.parse(deadlineParam);
        TaskValidator.validateTaskDate(deadline);

        return new Ticket(title, description, deadline, TicketStatus.RESOLVED);
    }
}