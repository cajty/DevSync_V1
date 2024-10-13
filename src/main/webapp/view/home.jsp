<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.entities.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .popup {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
            width: 80%;
            max-width: 450px;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            border: 1px solid #ccc;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.7);
            display: none; /* Hidden by default */
        }



        .popup.show{
            display: block; /* Show the popup and overlay */
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #f5f5f5;
            border: none;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            text-align: center;
            line-height: 30px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-show {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-show:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<%@ include file="../component/header.jsp" %>
<div class="container">
    <h1 class="mt-5">User List</h1>
    <button onclick="openPopup()" class="btn-show btn-primary">Add User</button>
    <table class="table table-bordered mt-3">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            for (User user : users) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getUsername() %></td>
            <td><%= user.getEmail() %></td>
            <td>
                <form action="user" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= user.getId() %>">
                    <input type="hidden" name="_method" value="DELETE">
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
                <form action="user-profile" method="GET" style="display:inline;">
                    <input type="hidden" name="id" value="<%= user.getId() %>">
                    <button type="submit" class="btn btn-primary btn-sm">Edit</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <!-- Button to open the popup -->


    <!-- Overlay -->
    <div id="overlay" class="overlay" onclick="closePopup()"></div>

    <!-- Popup with form -->
    <div id="popup" class="popup card">
        <button onclick="closePopup()" class="close-btn">x</button>
        <h2 class="mt-5">Add User</h2>
        <form action="user" method="post">
            <!-- Simulate PUT -->
            <input type="hidden" name="_method" value="POST" id="methodValue">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" class="form-control" id="firstName" name="firstName" required>
            </div>
            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" class="form-control" id="lastName" name="lastName" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <button type="submit" class="btn btn-primary">Add User</button>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function openPopup() {
        document.getElementById('popup').classList.add('show');
        document.getElementById('overlay').classList.add('show');
    }

    function closePopup() {
        document.getElementById('popup').classList.remove('show');
        document.getElementById('overlay').classList.remove('show');
    }
</script>
</body>
</html>
