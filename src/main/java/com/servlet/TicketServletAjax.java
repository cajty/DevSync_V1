package com.servlet;

import com.entities.Tag;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ticket", value = "/ticket/*")
public class TicketServletAjax extends HttpServlet {

    private TicketService ticketservice;
    private TagService tagService;

    @Override
    public void init() {
        ticketservice = new TicketService();
        tagService = new TagService();
    }


    private void returnTicketHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Set<Tag> tags = new HashSet<>(tagService.getAll());

            List<Ticket> tickets = ticketservice.getAllTickets();

            System.out.println("\n\n\n\n" + tickets.size() + "\n\n\n\n");
            List<Ticket> resolvedTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.RESOLVED))
                    .collect(Collectors.toList());

            List<Ticket> inProgressTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.IN_PROGRESS))
                    .collect(Collectors.toList());
            System.out.println("\n\n\n\n" + inProgressTickets + "\n\n\n\n");
                System.out.println("\n\n\n\n" + tickets.size() + "\n\n\n\n");
            List<Ticket> replacedTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.REPLACED))
                    .collect(Collectors.toList());

            List<Ticket> closedTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus().equals(TicketStatus.CLOSED))
                    .collect(Collectors.toList());

            // Build JSON response manually
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            jsonBuilder.append("\"resolvedTickets\":").append(listToJson(resolvedTickets)).append(",");
            jsonBuilder.append("\"inProgressTickets\":").append(listToJson(inProgressTickets)).append(",");
            jsonBuilder.append("\"replacedTickets\":").append(listToJson(replacedTickets)).append(",");
            jsonBuilder.append("\"closedTickets\":").append(listToJson(closedTickets)).append(",");
            jsonBuilder.append("\"tags\":").append(tagsToJson(tags));
            jsonBuilder.append("}");

            // Set the response content type and write the JSON data
            response.setContentType("application/json");
            response.getWriter().write(jsonBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve tickets");
        }
    }

    private String listToJson(List<Ticket> tickets) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            jsonBuilder.append("{")
                    .append("\"id\":").append(ticket.getId()).append(",")
                    .append("\"title\":\"").append(ticket.getTitle()).append("\",")
                    .append("\"description\":\"").append(ticket.getDescription()).append("\",")
                    .append("\"deadline\":\"").append(ticket.getDeadline()).append("\",")
                    .append("\"status\":\"").append(ticket.getStatus()).append("\",")
                    .append("\"user\":\"").append(ticket.getUser()).append("\",")
                    .append("\"tags\":").append(tagsToJson(ticket.getTags()))
                    .append("}");
            if (i < tickets.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
private String tagsToJson(Set<Tag> tags) {
    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("[");
    int i = 0;
    for (Tag tag : tags) {
        jsonBuilder.append("{")
                .append("\"id\":").append(tag.getId()).append(",")
                .append("\"name\":\"").append(tag.getName()).append("\"")
                .append("}");
        if (i < tags.size() - 1) {
            jsonBuilder.append(",");
        }
        i++;
    }
    jsonBuilder.append("]");
    return jsonBuilder.toString();
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
                System.out.println("\n\n\n\n\n\nID param: " + idParam + "\n\n\n\n\n\n");

                if (idParam != null) {
                    Long ticketId = Long.parseLong(idParam);
                    status = handleDelete(ticketId);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing ticket ID");
                    return;
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
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