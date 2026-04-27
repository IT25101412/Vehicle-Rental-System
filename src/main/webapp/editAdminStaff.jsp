<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Admin/Staff</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-7">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-warning">
                    <h4 class="mb-0">Edit Admin / Staff Account</h4>
                </div>
                <div class="card-body">
                    <form action="updateAdminStaff" method="POST">
                        <input type="hidden" name="userId" value="<%= request.getParameter("userId") %>">

                        <div class="mb-3">
                            <label class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="fullName" value="<%= request.getParameter("fullName") %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" class="form-control" name="username" value="<%= request.getParameter("username") %>" required>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Role</label>
                                <select class="form-select" name="role" required>
                                    <option value="ADMIN" <%= "ADMIN".equals(request.getParameter("role")) ? "selected" : "" %>>ADMIN</option>
                                    <option value="STAFF" <%= "STAFF".equals(request.getParameter("role")) ? "selected" : "" %>>STAFF</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Employee Type</label>
                                <select class="form-select" name="employeeType" required>
                                    <option value="FULL_TIME" <%= "FULL_TIME".equals(request.getParameter("employeeType")) ? "selected" : "" %>>FULL_TIME</option>
                                    <option value="PART_TIME" <%= "PART_TIME".equals(request.getParameter("employeeType")) ? "selected" : "" %>>PART_TIME</option>
                                    <option value="CONTRACT" <%= "CONTRACT".equals(request.getParameter("employeeType")) ? "selected" : "" %>>CONTRACT</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-4">
                            <label class="form-label">New Password (Optional)</label>
                            <input type="password" class="form-control" name="password" placeholder="Leave blank to keep current password">
                        </div>

                        <button type="submit" class="btn btn-success">Update Account</button>
                        <a href="adminDashboard.jsp" class="btn btn-outline-secondary ms-2">Cancel</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
