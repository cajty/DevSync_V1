<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.entities.Ticket" %>
<%@ page import="com.entities.Tag" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Board</title>
    <!-- Include Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .ticket-card:hover .details {
            display: block;
        }

        .ticket-card .details {
            display: none;
        }
    </style>
</head>
<body class="bg-gray-100">
<%@ include file="../component/header.jsp" %>



<!-- Modal Background -->
<div class="container mx-auto py-8">
    <div class="grid grid-cols-3 gap-4">
        <h1>tttttttttttttttttttttttttttttttttttttttttttttt</h1>
        <!-- EN COURS Column -->
        <div>
            <h2 class="text-lg font-semibold mb-4">EN COURS</h2>
            <div class="space-y-4">
                <%
                    List<Ticket> inProgressTickets = (List<Ticket>) request.getAttribute("inProgressTickets");
                    for (Ticket ticket : inProgressTickets) {
                %>
                <div class="ticket-card bg-white p-4 shadow-lg rounded-lg">
                    <div class="mb-2">
                        <p class="font-bold"><%= ticket.getTitle() %></p>
                        <p class="text-sm text-gray-500">Due: <%= ticket.getDeadline() %></p>
                        <!-- Tags Section -->

                    </div>
                    <div class="details bg-gray-100 p-2 mt-2 rounded-md">
                        <p><strong>Description:</strong> <%= ticket.getDescription() %></p>
                        <p><strong>User:</strong> <%= ticket.getUser() %></p>
                        <div class="flex flex-wrap gap-1 mt-2">
                            <% for (Tag tag : ticket.getTags()) { %>
                            <span class="bg-blue-200 text-blue-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded"><%= tag.getName() %></span>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>

        <!-- A FAIRE Column -->
        <div>
            <h2 class="text-lg font-semibold mb-4">A FAIRE</h2>
            <div class="space-y-4">
                <%
                    List<Ticket> todoTickets = (List<Ticket>) request.getAttribute("todoTickets");
                    for (Ticket ticket : todoTickets) {
                %>
                <div class="ticket-card bg-white p-4 shadow-lg rounded-lg">
                    <div class="mb-2">
                        <p class="font-bold"><%= ticket.getTitle() %></p>
                        <p class="text-sm text-gray-500">Due: <%= ticket.getDeadline() %></p>

                    </div>
                    <div class="details bg-gray-100 p-2 mt-2 rounded-md">
                        <p><strong>Description:</strong> <%= ticket.getDescription() %></p>
                        <p><strong>User:</strong> <%= ticket.getUser() %></p>
                        <form action="ticket/update" method="post">
                            <input type="hidden" name="id" value="<%= ticket.getId() %>">
                            <button type="submit" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-1 px-3 rounded-lg shadow-md transition duration-300 ease-in-out focus:ring-4 focus:ring-yellow-500 focus:outline-none">
                                Update
                            </button>
                        </form>
                        <!-- Tags Section -->
                        <div class="flex flex-wrap gap-1 mt-2">
                            <% for (Tag tag : ticket.getTags()) { %>
                            <span class="bg-blue-200 text-blue-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded"><%= tag.getName() %></span>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>

        <!-- FINI Column -->
        <div>
            <h2 class="text-lg font-semibold mb-4">FINI</h2>
            <div class="space-y-4">
                <%
                    List<Ticket> finishedTickets = (List<Ticket>) request.getAttribute("finishedTickets");
                    for (Ticket ticket : finishedTickets) {
                %>
                <div class="ticket-card bg-white p-4 shadow-lg rounded-lg">
                    <div class="mb-2">
                        <p class="font-bold"><%= ticket.getTitle() %></p>
                        <p class="text-sm text-gray-500">Completed on: <%= ticket.getDeadline() %></p>

                    </div>
                    <div class="details bg-gray-100 p-2 mt-2 rounded-md">
                        <p><strong>Description:</strong> <%= ticket.getDescription() %></p>
                        <p><strong>User:</strong> <%= ticket.getUser() %></p>
                        <!-- update form -->
                        <form action="ticket/update" method="post">
                            <input type="hidden" name="id" value="<%= ticket.getId() %>">
                            <button type="submit" class="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-1 px-3 rounded-lg shadow-md transition duration-300 ease-in-out focus:ring-4 focus:ring-yellow-500 focus:outline-none">
                                Update
                            </button>
                        </form>
                        <!-- delete form -->
                        <form action="ticket/delete" method="post">
                            <input type="hidden" name="id" value="<%= ticket.getId() %>">
                            <button type="submit" class="bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-3 rounded-lg shadow-md transition duration-300 ease-in-out focus:ring-4 focus:ring-red-500 focus:outline-none">
                                Delete
                            </button>
                        </form>
                        <!-- Tags Section -->
                        <div class="flex flex-wrap gap-1 mt-2">
                            <% for (Tag tag : ticket.getTags()) { %>
                            <span class="bg-blue-200 text-blue-800 text-xs font-semibold mr-2 px-2.5 py-0.5 rounded"><%= tag.getName() %></span>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<!-- Modal JavaScript -->
<script>
    const modal = document.getElementById("ticketModal");
    const openModalButton = document.getElementById("openModalButton");
    const closeModalButton = document.getElementById("closeModalButton");

    openModalButton.addEventListener("click", () => {
        modal.classList.remove("hidden");
    });

    closeModalButton.addEventListener("click", () => {
        modal.classList.add("hidden");
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.classList.add("hidden");
        }
    });
</script>