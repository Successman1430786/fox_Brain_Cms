<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.setAttribute("pageTitle", "Add Student");

    String errorMessage =
        (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Student | FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <!-- SIDEBAR -->
    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <!-- HEADER -->
        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="container">

                <!-- PAGE HEADER -->

                <div class="page-header">

                    <div>
                        <h2>Add Student</h2>

                        <p>
                            Create a student and their login account.
                        </p>
                    </div>

                    <a href="<%= request.getContextPath() %>/admin/students"
                       class="btn btn-cancel">
                        ← Back to Students
                    </a>

                </div>


                <!-- ERROR -->

                <% if (errorMessage != null) { %>

                    <div class="alert error">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <form method="post"
                      action="<%= request.getContextPath() %>/admin/students?action=create"
                      id="studentForm">


                    <!-- ACCOUNT INFORMATION -->

                    <div class="card">

                        <div class="card-header">

                            <div>
                                <h3>Student Account</h3>

                                <p>
                                    Login information for the student.
                                </p>
                            </div>

                        </div>


                        <div class="grid">


                            <div class="form-group">

                                <label for="username">
                                    Username <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="username"
                                       name="username"
                                       required
                                       maxlength="100"
                                       placeholder="Enter username">

                            </div>


                            <div class="form-group">

                                <label for="email">
                                    Email <span class="required">*</span>
                                </label>

                                <input type="email"
                                       id="email"
                                       name="email"
                                       required
                                       maxlength="150"
                                       placeholder="student@example.com">

                            </div>


                            <div class="form-group">

                                <label for="password">
                                    Password <span class="required">*</span>
                                </label>

                                <input type="password"
                                       id="password"
                                       name="password"
                                       required
                                       minlength="6"
                                       maxlength="100"
                                       placeholder="Enter password">

                            </div>


                            <div class="form-group">

                                <label for="phone">
                                    Phone
                                </label>

                                <input type="text"
                                       id="phone"
                                       name="phone"
                                       maxlength="30"
                                       placeholder="Enter phone number">

                            </div>


                            <div class="form-group">

                                <label for="firstName">
                                    First Name <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="firstName"
                                       name="firstName"
                                       required
                                       maxlength="100"
                                       placeholder="First name">

                            </div>


                            <div class="form-group">

                                <label for="lastName">
                                    Last Name
                                </label>

                                <input type="text"
                                       id="lastName"
                                       name="lastName"
                                       maxlength="100"
                                       placeholder="Last name">

                            </div>

                        </div>

                    </div>


                    <!-- STUDENT INFORMATION -->

                    <div class="card">

                        <div class="card-header">

                            <div>
                                <h3>Student Information</h3>

                                <p>
                                    Academic and personal information.
                                </p>
                            </div>

                        </div>


                        <div class="grid">


                            <div class="form-group">

                                <label for="admissionNumber">
                                    Admission Number
                                    <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="admissionNumber"
                                       name="admissionNumber"
                                       required
                                       maxlength="50"
                                       placeholder="e.g. FB2026001">

                            </div>


                            <div class="form-group">

                                <label for="admissionDate">
                                    Admission Date
                                </label>

                                <input type="date"
                                       id="admissionDate"
                                       name="admissionDate">

                            </div>


                            <div class="form-group">

                                <label for="dateOfBirth">
                                    Date of Birth
                                </label>

                                <input type="date"
                                       id="dateOfBirth"
                                       name="dateOfBirth">

                            </div>


                            <div class="form-group">

                                <label for="gender">
                                    Gender
                                </label>

                                <select id="gender"
                                        name="gender">

                                    <option value="">
                                        Select Gender
                                    </option>

                                    <option value="Male">
                                        Male
                                    </option>

                                    <option value="Female">
                                        Female
                                    </option>

                                    <option value="Other">
                                        Other
                                    </option>

                                </select>

                            </div>


                            <div class="form-group">

                                <label for="status">
                                    Status
                                </label>

                                <select id="status"
                                        name="status">

                                    <option value="ACTIVE">
                                        Active
                                    </option>

                                    <option value="INACTIVE">
                                        Inactive
                                    </option>

                                    <option value="SUSPENDED">
                                        Suspended
                                    </option>

                                    <option value="GRADUATED">
                                        Graduated
                                    </option>

                                </select>

                            </div>


                            <div class="form-group">

                                <label for="country">
                                    Country
                                </label>

                                <input type="text"
                                       id="country"
                                       name="country"
                                       maxlength="100"
                                       value="India">

                            </div>


                            <div class="form-group full">

                                <label for="addressLine1">
                                    Address Line 1
                                </label>

                                <input type="text"
                                       id="addressLine1"
                                       name="addressLine1"
                                       maxlength="255"
                                       placeholder="House / Street / Area">

                            </div>


                            <div class="form-group full">

                                <label for="addressLine2">
                                    Address Line 2
                                </label>

                                <input type="text"
                                       id="addressLine2"
                                       name="addressLine2"
                                       maxlength="255"
                                       placeholder="Optional">

                            </div>


                            <div class="form-group">

                                <label for="city">
                                    City
                                </label>

                                <input type="text"
                                       id="city"
                                       name="city"
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label for="state">
                                    State
                                </label>

                                <input type="text"
                                       id="state"
                                       name="state"
                                       maxlength="100">

                            </div>


                            <div class="form-group">

                                <label for="postalCode">
                                    Postal Code
                                </label>

                                <input type="text"
                                       id="postalCode"
                                       name="postalCode"
                                       maxlength="20">

                            </div>


                        </div>

                    </div>


                    <!-- ACTIONS -->

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/students"
                           class="btn btn-cancel">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Create Student
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