<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.entities.User" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fa;
        }

        .container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-top: 50px;
        }

        h1 {
            color: #343a40;
            margin-bottom: 30px;
        }

        .form-control:disabled {
            background-color: #e9ecef;
        }

        .btn {
            width: 100%;
            margin-top: 10px;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .btn-success {
            background-color: #28a745;
            border: none;
        }

        .btn-success:hover {
            background-color: #218838;
        }

        .alert {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center">User Profile</h1>
    <div class="row justify-content-center">
        <div class="col-md-8">
            <form method="post" action="user">
                <input type="hidden" name="_method" value="PUT" id="methodValue">
                <%
                    User user = (User) request.getAttribute("user");
                    if (user == null) {
                %>




                <div class="alert alert-danger" role="alert">
                    Error: User not found in session!
                </div>
                <%
                    }
                %>


                    <input name="id" type="hidden"  value="<%= (user != null) ?  user.getId() : "" %>">

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
                    <label for="emails">Email</label>
                    <input type="email" class="form-control" id="emails" name="email" value="<%= (user != null) ? user.getEmail() : "" %>" disabled>
                </div>
                <input type="hidden" name="email" value="<%= (user != null) ? user.getEmail() : "" %>">


                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" value="<%=(user != null && user.getPassword() != null) ? user.getPassword() : ""%>" placeholder="Enter new password" disabled>
                </div>

                <div class="form-group">
                    <label for="role">Role</label>
                    <input type="text" class="form-control" id="role" name="role" value="<%= (user != null && user.getRole() != null) ? user.getRole().name() : "" %>" disabled>
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
        document.getElementById('username').disabled = false;
        document.getElementById('firstName').disabled = false;
        document.getElementById('lastName').disabled = false;
        document.getElementById('password').disabled = false;

        // Hide 'Edit' button and show 'Save' button
        document.getElementById('editBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline-block';
    });
</script>
</body>
</html>
