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
<!-- Trigger Button for Modal -->
<div class="flex justify-center mt-10">
    <button id="openModalButton" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md shadow-md transition duration-300 ease-in-out">
        Create New Ticket
    </button>
</div>
<!-- Modal Background -->
<div id="ticketModal" class="fixed inset-0 hidden bg-gray-900 bg-opacity-70 flex items-center justify-center modal">
    <div class="bg-white rounded-lg shadow-lg max-w-lg w-full">
        <div class="flex justify-between items-center p-4 border-b">
            <h3 class="text-lg font-bold text-gray-800">Create a New Ticket</h3>
            <button id="closeModalButton" class="text-gray-500 hover:text-gray-800 focus:outline-none">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
                </svg>
            </button>
        </div>

        <!-- Modal Body (Form) -->
        <div class="p-6 bg-gray-100 rounded-lg shadow-lg max-h-[80vh] overflow-y-auto">
            <form action="ticket/add" method="post">
                <!-- Title Input -->
                <div class="mb-6">
                    <label for="title" class="block text-gray-600 text-sm font-semibold mb-2">Title</label>
                    <input type="text" id="title" name="title" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out" placeholder="Enter ticket title">
                </div>

                <!-- Description Input -->
                <div class="mb-6">
                    <label for="description" class="block text-gray-600 text-sm font-semibold mb-2">Description</label>
                    <textarea id="description" name="description" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out" placeholder="Enter ticket description" rows="4"></textarea>
                </div>

                <!-- Deadline Input -->
                <div class="mb-6">
                    <label for="deadline" class="block text-gray-600 text-sm font-semibold mb-2">Deadline</label>
                    <input type="date" id="deadline" name="deadline" required class="shadow-md border border-gray-300 rounded-lg w-full py-3 px-4 text-gray-800 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition duration-200 ease-in-out">
                </div>

                <!-- Tags Input (2 tags per line, scrollable) -->
                <div class="mb-6">
                    <label class="block text-gray-600 text-sm font-semibold mb-2">Tags</label>
                    <div class="grid grid-cols-2 gap-4 max-h-32 overflow-y-auto border border-gray-300 rounded-lg p-4 shadow-inner">
                        <% List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                            for (Tag tag : tags) { %>
                        <div class="flex items-center">
                            <input type="checkbox" id="tag_<%= tag.getId() %>" name="tags" value="<%= tag.getId() %>" class="h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500 mr-2">
                            <label for="tag_<%= tag.getId() %>" class="text-gray-800"><%= tag.getName() %></label>
                        </div>
                        <% } %>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="flex justify-end">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg shadow-md transition duration-300 ease-in-out focus:ring-4 focus:ring-blue-500 focus:outline-none">
                        Create Ticket
                    </button>
                </div>
            </form>
        </div>

    </div>
</div>
</div>
<div class="container mx-auto py-8">
    <div class="grid grid-cols-3 gap-4">
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