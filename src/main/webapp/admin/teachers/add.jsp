<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.setAttribute(
        "pageTitle",
        "Add Teacher"
    );

    String errorMessage =
        (String) request.getAttribute(
            "errorMessage"
        );
%>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        FoxBrain - Add Teacher
    </title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="container">


                <div class="page-header">

                    <div>

                        <h2>
                            Add Teacher
                        </h2>

                        <p>
                            Create a teacher account and
                            faculty profile.
                        </p>

                    </div>

                    <div>

                        <a href="<%= request.getContextPath() %>/admin/teachers"
                           class="btn btn-secondary">

                            ← Back

                        </a>

                    </div>

                </div>


                <% if (errorMessage != null) { %>

                    <div class="alert error">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <form method="post"
                      action="<%= request.getContextPath() %>/admin/teachers?action=create">


                    <!-- ACCOUNT -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Account Information
                                </h3>

                                <p>
                                    Login account for the teacher.
                                </p>

                            </div>

                        </div>


                        <div class="form-grid">


                            <div class="form-group">

                                <label>
                                    Username *
                                </label>

                                <input type="text"
                                       name="username"
                                       required
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label>
                                    Email *
                                </label>

                                <input type="email"
                                       name="email"
                                       required
                                       maxlength="150">

                            </div>


                            <div class="form-group">

                                <label>
                                    Password *
                                </label>

                                <input type="password"
                                       name="password"
                                       required>

                            </div>


                            <div class="form-group">

                                <label>
                                    Phone
                                </label>

                                <input type="text"
                                       name="phone"
                                       maxlength="30">

                            </div>


                            <div class="form-group">

                                <label>
                                    First Name *
                                </label>

                                <input type="text"
                                       name="firstName"
                                       required
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label>
                                    Last Name
                                </label>

                                <input type="text"
                                       name="lastName"
                                       maxlength="100">

                            </div>

                        </div>

                    </div>


                    <!-- PROFESSIONAL -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Professional Information
                                </h3>

                                <p>
                                    Teacher employment details.
                                </p>

                            </div>

                        </div>


                        <div class="form-grid">


                            <div class="form-group">

                                <label>
                                    Employee Number *
                                </label>

                                <input type="text"
                                       name="employeeNumber"
                                       required
                                       maxlength="50">

                            </div>


                            <div class="form-group">

                                <label>
                                    Qualification
                                </label>

                                <input type="text"
                                       name="qualification"
                                       maxlength="255"
                                       placeholder="e.g. B.Tech, M.Sc, MBA">

                            </div>


                            <div class="form-group">

                                <label>
                                    Specialization
                                </label>

                                <input type="text"
                                       name="specialization"
                                       maxlength="255"
                                       placeholder="e.g. Java, Mathematics">

                            </div>


                            <div class="form-group">

                                <label>
                                    Joining Date
                                </label>

                                <input type="date"
                                       name="joiningDate">

                            </div>


                            <div class="form-group">

                                <label>
                                    Status
                                </label>

                                <select name="status">

                                    <option value="ACTIVE">
                                        Active
                                    </option>

                                    <option value="INACTIVE">
                                        Inactive
                                    </option>

                                    <option value="ON_LEAVE">
                                        On Leave
                                    </option>

                                    <option value="LEFT">
                                        Left
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="form-group">

                            <label>
                                Bio
                            </label>

                            <textarea name="bio"
                                      rows="5"
                                      maxlength="5000"
                                      placeholder="Short professional biography..."></textarea>

                        </div>

                    </div>


                    <!-- ADDRESS -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Address
                                </h3>

                                <p>
                                    Teacher contact address.
                                </p>

                            </div>

                        </div>


                        <div class="form-grid">


                            <div class="form-group form-full">

                                <label>
                                    Address Line 1
                                </label>

                                <input type="text"
                                       name="addressLine1"
                                       maxlength="255">

                            </div>


                            <div class="form-group form-full">

                                <label>
                                    Address Line 2
                                </label>

                                <input type="text"
                                       name="addressLine2"
                                       maxlength="255">

                            </div>


                            <div class="form-group">

                                <label>
                                    City
                                </label>

                                <input type="text"
                                       name="city"
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label>
                                    State
                                </label>

                                <input type="text"
                                       name="state"
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label>
                                    Postal Code
                                </label>

                                <input type="text"
                                       name="postalCode"
                                       maxlength="20">

                            </div>


                            <div class="form-group">

                                <label>
                                    Country
                                </label>

                                <input type="text"
                                       name="country"
                                       maxlength="100"
                                       value="India">

                            </div>

                        </div>

                    </div>


                    <!-- ACTIONS -->

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/teachers"
                           class="btn btn-secondary">

                            Cancel

                        </a>

                        <button type="submit"
                                class="btn btn-primary">

                            Create Teacher

                        </button>

                    </div>


                </form>

            </div>

        </main>

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>