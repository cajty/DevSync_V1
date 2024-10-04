<%--
  Created by IntelliJ IDEA.
  User: YouCode
  Date: 10/4/2024
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.entities.User" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .form-control:disabled {
            background-color: #e9ecef;
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
<div class="container">
    <h1 class="mt-5">User Profile</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form method="post" action="updateProfile">
                <input type="hidden" name="_method" value="PUT" id="methodValue">

                <%
                    // Retrieve the 'user' object from the session
                    User user = (User) session.getAttribute("user");
                    if (user == null) {
                        // Display error message directly in HTML
                %>
                <div class="alert alert-danger" role="alert">
                    Error: User not found in session!
                </div>
                <%
                    }
                %>

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username" value="<%= (user != null) ? user.getUsername() : "" %>" disabled>
                </div>

                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" value="<%= (user != null) ? user.getFirstName() : "" %>" disabled>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" value="<%= (user != null) ? user.getLastName() : "" %>" disabled>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="<%= (user != null) ? user.getEmail() : "" %>" disabled>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" value="<%= (user != null) ? user.getPassword() : "" %>" disabled>
                </div>

                <div class="form-group">
                    <label for="role">Role</label>
                    <input type="text" class="form-control" id="role" name="role" value="<%= (user != null && user.getRole() != null) ? user.getRole().name() : "" %>" disabled> <!-- Assuming UserRole is an enum or object -->
                </div>

                <div class="form-group text-center">
                    <button type="button" id="editBtn" class="btn btn-primary">Edit</button>
                    <button type="submit" id="saveBtn" class="btn btn-success" style="display: none;">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    document.getElementById('editBtn').addEventListener('click', function() {
        // Enable form fields
        document.getElementById('firstName').disabled = false;
        document.getElementById('lastName').disabled = false;
        document.getElementById('email').disabled = false;
        document.getElementById('password').disabled = false;

        // Hide 'Edit' button and show 'Save' button
        document.getElementById('editBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline-block';
    });
</script>
</body>
</html>
