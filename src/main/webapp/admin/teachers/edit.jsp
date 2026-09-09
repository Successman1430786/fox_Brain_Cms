<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.foxbrain.model.Teacher" %>

<%
    request.setAttribute(
        "pageTitle",
        "Edit Teacher"
    );

    Teacher teacher =
        (Teacher) request.getAttribute(
            "teacher"
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
        FoxBrain - Edit Teacher
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
                            Edit Teacher
                        </h2>

                        <p>
                            Update teacher professional information.
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


                <% if (teacher != null) { %>


                <form method="post"
                      action="<%= request.getContextPath() %>/admin/teachers?action=update">


                    <input type="hidden"
                           name="id"
                           value="<%= teacher.getId() %>">


                    <!-- IDs -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Account Reference
                                </h3>

                                <p>
                                    System-generated identifiers.
                                </p>

                            </div>

                        </div>


                        <div class="form-grid">


                            <div class="form-group">

                                <label>
                                    Teacher ID
                                </label>

                                <input type="text"
                                       value="<%= teacher.getId() %>"
                                       readonly>

                            </div>


                            <div class="form-group">

                                <label>
                                    User ID
                                </label>

                                <input type="text"
                                       value="<%= teacher.getUserId() %>"
                                       readonly>

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
                                       maxlength="50"
                                       value="<%= teacher.getEmployeeNumber() != null
                                           ? teacher.getEmployeeNumber()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Qualification
                                </label>

                                <input type="text"
                                       name="qualification"
                                       maxlength="255"
                                       value="<%= teacher.getQualification() != null
                                           ? teacher.getQualification()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Specialization
                                </label>

                                <input type="text"
                                       name="specialization"
                                       maxlength="255"
                                       value="<%= teacher.getSpecialization() != null
                                           ? teacher.getSpecialization()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Joining Date
                                </label>

                                <input type="date"
                                       name="joiningDate"
                                       value="<%= teacher.getJoiningDate() != null
                                           ? teacher.getJoiningDate()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Status
                                </label>

                                <select name="status">

                                    <option value="ACTIVE"
                                        <%= "ACTIVE".equals(
                                            teacher.getStatus()
                                        ) ? "selected" : "" %>>
                                        Active
                                    </option>

                                    <option value="INACTIVE"
                                        <%= "INACTIVE".equals(
                                            teacher.getStatus()
                                        ) ? "selected" : "" %>>
                                        Inactive
                                    </option>

                                    <option value="ON_LEAVE"
                                        <%= "ON_LEAVE".equals(
                                            teacher.getStatus()
                                        ) ? "selected" : "" %>>
                                        On Leave
                                    </option>

                                    <option value="LEFT"
                                        <%= "LEFT".equals(
                                            teacher.getStatus()
                                        ) ? "selected" : "" %>>
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
                                      maxlength="5000"><%= teacher.getBio() != null
                                          ? teacher.getBio()
                                          : "" %></textarea>

                        </div>

                    </div>


                    <!-- ADDRESS -->

                    <div class="card">

                        <div class="card-header">

                            <div>

                                <h3>
                                    Address
                                </h3>

                            </div>

                        </div>


                        <div class="form-grid">


                            <div class="form-group form-full">

                                <label>
                                    Address Line 1
                                </label>

                                <input type="text"
                                       name="addressLine1"
                                       maxlength="255"
                                       value="<%= teacher.getAddressLine1() != null
                                           ? teacher.getAddressLine1()
                                           : "" %>">

                            </div>


                            <div class="form-group form-full">

                                <label>
                                    Address Line 2
                                </label>

                                <input type="text"
                                       name="addressLine2"
                                       maxlength="255"
                                       value="<%= teacher.getAddressLine2() != null
                                           ? teacher.getAddressLine2()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    City
                                </label>

                                <input type="text"
                                       name="city"
                                       maxlength="100"
                                       value="<%= teacher.getCity() != null
                                           ? teacher.getCity()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    State
                                </label>

                                <input type="text"
                                       name="state"
                                       maxlength="100"
                                       value="<%= teacher.getState() != null
                                           ? teacher.getState()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Postal Code
                                </label>

                                <input type="text"
                                       name="postalCode"
                                       maxlength="20"
                                       value="<%= teacher.getPostalCode() != null
                                           ? teacher.getPostalCode()
                                           : "" %>">

                            </div>


                            <div class="form-group">

                                <label>
                                    Country
                                </label>

                                <input type="text"
                                       name="country"
                                       maxlength="100"
                                       value="<%= teacher.getCountry() != null
                                           ? teacher.getCountry()
                                           : "" %>">

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

                            Update Teacher

                        </button>

                    </div>


                </form>


                <% } else { %>

                    <div class="alert error">
                        Teacher information could not be loaded.
                    </div>

                <% } %>


            </div>

        </main>

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>