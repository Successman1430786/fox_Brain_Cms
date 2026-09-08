<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Student" %>

<%
    request.setAttribute("pageTitle", "Edit Student");

    Student student =
        (Student) request.getAttribute("student");

    String errorMessage =
        (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Edit Student | FoxBrain Admin</title>

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

                        <h2>Edit Student</h2>

                        <p>
                            Update student information.
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


                <% if (student == null) { %>

                    <div class="alert error">
                        Student information could not be loaded.
                    </div>

                <% } else { %>


                    <form method="post"
                          action="<%= request.getContextPath() %>/admin/students?action=update">


                        <input type="hidden"
                               name="id"
                               value="<%= student.getId() %>">


                        <!-- SYSTEM INFORMATION -->

                        <div class="card">

                            <div class="card-header">

                                <div>

                                    <h3>Student Information</h3>

                                    <p>
                                        System-generated information.
                                    </p>

                                </div>

                            </div>


                            <div class="grid">

                                <div class="form-group">

                                    <label>
                                        Student ID
                                    </label>

                                    <div class="readonly-box">
                                        <%= student.getId() %>
                                    </div>

                                </div>


                                <div class="form-group">

                                    <label>
                                        User ID
                                    </label>

                                    <div class="readonly-box">
                                        <%= student.getUserId() %>
                                    </div>

                                </div>

                            </div>

                        </div>


                        <!-- STUDENT DETAILS -->

                        <div class="card">

                            <div class="card-header">

                                <div>

                                    <h3>Student Details</h3>

                                    <p>
                                        Update admission and personal details.
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
                                           value="<%= student.getAdmissionNumber() != null
                                                ? student.getAdmissionNumber()
                                                : "" %>">

                                </div>


                                <div class="form-group">

                                    <label for="admissionDate">
                                        Admission Date
                                    </label>

                                    <input type="date"
                                           id="admissionDate"
                                           name="admissionDate"
                                           value="<%= student.getAdmissionDate() != null
                                                ? student.getAdmissionDate()
                                                : "" %>">

                                </div>


                                <div class="form-group">

                                    <label for="dateOfBirth">
                                        Date of Birth
                                    </label>

                                    <input type="date"
                                           id="dateOfBirth"
                                           name="dateOfBirth"
                                           value="<%= student.getDateOfBirth() != null
                                                ? student.getDateOfBirth()
                                                : "" %>">

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

                                        <option value="Male"
                                            <%= "Male".equals(student.getGender())
                                                ? "selected"
                                                : "" %>>
                                            Male
                                        </option>

                                        <option value="Female"
                                            <%= "Female".equals(student.getGender())
                                                ? "selected"
                                                : "" %>>
                                            Female
                                        </option>

                                        <option value="Other"
                                            <%= "Other".equals(student.getGender())
                                                ? "selected"
                                                : "" %>>
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

                                        <option value="ACTIVE"
                                            <%= "ACTIVE".equals(student.getStatus())
                                                ? "selected"
                                                : "" %>>
                                            Active
                                        </option>

                                        <option value="INACTIVE"
                                            <%= "INACTIVE".equals(student.getStatus())
                                                ? "selected"
                                                : "" %>>
                                            Inactive
                                        </option>

                                        <option value="SUSPENDED"
                                            <%= "SUSPENDED".equals(student.getStatus())
                                                ? "selected"
                                                : "" %>>
                                            Suspended
                                        </option>

                                        <option value="GRADUATED"
                                            <%= "GRADUATED".equals(student.getStatus())
                                                ? "selected"
                                                : "" %>>
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
                                           value="<%= student.getCountry() != null
                                                ? student.getCountry()
                                                : "" %>">

                                </div>


                                <div class="form-group full">

                                    <label for="addressLine1">
                                        Address Line 1
                                    </label>

                                    <input type="text"
                                           id="addressLine1"
                                           name="addressLine1"
                                           maxlength="255"
                                           value="<%= student.getAddressLine1() != null
                                                ? student.getAddressLine1()
                                                : "" %>">

                                </div>


                                <div class="form-group full">

                                    <label for="addressLine2">
                                        Address Line 2
                                    </label>

                                    <input type="text"
                                           id="addressLine2"
                                           name="addressLine2"
                                           maxlength="255"
                                           value="<%= student.getAddressLine2() != null
                                                ? student.getAddressLine2()
                                                : "" %>">

                                </div>


                                <div class="form-group">

                                    <label for="city">
                                        City
                                    </label>

                                    <input type="text"
                                           id="city"
                                           name="city"
                                           maxlength="100"
                                           value="<%= student.getCity() != null
                                                ? student.getCity()
                                                : "" %>">

                                </div>


                                <div class="form-group">

                                    <label for="state">
                                        State
                                    </label>

                                    <input type="text"
                                           id="state"
                                           name="state"
                                           maxlength="100"
                                           value="<%= student.getState() != null
                                                ? student.getState()
                                                : "" %>">

                                </div>


                                <div class="form-group">

                                    <label for="postalCode">
                                        Postal Code
                                    </label>

                                    <input type="text"
                                           id="postalCode"
                                           name="postalCode"
                                           maxlength="20"
                                           value="<%= student.getPostalCode() != null
                                                ? student.getPostalCode()
                                                : "" %>">

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
                                Save Changes
                            </button>

                        </div>


                    </form>

                <% } %>

            </div>

        </main>

    </div>

</div>


<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>

</html>