<%@ page import="com.example.vehicalrentalserviceplatform.model.ActivityLogEntry" %>
<%@ page import="com.example.vehicalrentalserviceplatform.service.AdminStaffService" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Activity Log</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    AdminStaffService service = new AdminStaffService();
    List<ActivityLogEntry> logs = service.getAllActivityLogs();
%>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Admin Activity Logs</h2>
        <a href="adminDashboard.jsp" class="btn btn-outline-dark">Back to Dashboard</a>
    </div>

    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead>
                <tr>
                    <th>Timestamp</th>
                    <th>Actor</th>
                    <th>Action</th>
                    <th>Details</th>
                </tr>
                </thead>
                <tbody>
                <% if (logs.isEmpty()) { %>
                <tr>
                    <td colspan="4" class="text-center py-3">No activity logs found.</td>
                </tr>
                <% } %>
                <% for (ActivityLogEntry log : logs) { %>
                <tr>
                    <td><%= log.getTimestamp() %></td>
                    <td><%= log.getActor() %></td>
                    <td><%= log.getAction() %></td>
                    <td><%= log.getDetails() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
