<%@ page import="com.example.vehicalrentalserviceplatform.model.Admin" %>
<%@ page import="com.example.vehicalrentalserviceplatform.model.ActivityLogEntry" %>
<%@ page import="com.example.vehicalrentalserviceplatform.service.AdminStaffService" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    AdminStaffService service = new AdminStaffService();
    List<Admin> admins = service.getAllAdmins();
    List<ActivityLogEntry> logs = service.getAllActivityLogs();
%>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Admin Dashboard</h2>
        <div>
            <a href="adminRegistration.jsp" class="btn btn-primary">Register Staff/Admin</a>
            <a href="adminActivityLog.jsp" class="btn btn-outline-dark">View Activity Logs</a>
        </div>
    </div>

    <% String message = request.getParameter("message"); %>
    <% if (message != null && !message.isEmpty()) { %>
    <div class="alert alert-info"><%= message %></div>
    <% } %>

    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <h6 class="text-muted">Total Accounts</h6>
                    <h3><%= admins.size() %></h3>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm border-0">
                <div class="card-body">
                    <h6 class="text-muted">Activity Logs</h6>
                    <h3><%= logs.size() %></h3>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow-sm border-0">
        <div class="card-header">
            <h5 class="mb-0">Admins and Staff Accounts</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-striped mb-0">
                <thead>
                <tr>
                    <th>User ID</th>
                    <th>Full Name</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Employee Type</th>
                    <th>Password (Masked)</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <% if (admins.isEmpty()) { %>
                <tr>
                    <td colspan="7" class="text-center py-3">No staff/admin accounts found.</td>
                </tr>
                <% } %>
                <% for (Admin admin : admins) { %>
                <tr>
                    <td><%= admin.getUserId() %></td>
                    <td><%= admin.getFullName() %></td>
                    <td><%= admin.getUsername() %></td>
                    <td><%= admin.getRole() %></td>
                    <td><%= admin.getEmployeeType() %></td>
                    <td><%= admin.getMaskedPasswordHash() %></td>
                    <td>
                        <form action="deleteAdminStaff" method="POST" style="display:inline;">
                            <input type="hidden" name="userId" value="<%= admin.getUserId() %>">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
