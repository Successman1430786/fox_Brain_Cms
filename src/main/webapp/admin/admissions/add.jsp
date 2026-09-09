<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Course" %>
<%@ page import="com.foxbrain.model.Batch" %>

<%
    request.setAttribute("pageTitle", "New Admission");

    List<Course> courses =
            (List<Course>) request.getAttribute("courses");

    List<Batch> batches =
            (List<Batch>) request.getAttribute("batches");

    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>New Admission - FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="page-header">

                <div>
                    <h2>New Admission</h2>
                    <p>Create a new admission application.</p>
                </div>

                <a href="<%= request.getContextPath() %>/admin/admissions"
                   class="btn btn-secondary">
                    ← Back
                </a>

            </div>

            <% if (error != null) { %>

                <div class="alert alert-danger">
                    <%= error %>
                </div>

            <% } %>

            <div class="content-card">

                <form method="post"
                      action="<%= request.getContextPath() %>/admin/admissions">

                    <input type="hidden"
                           name="action"
                           value="create">

                    <div class="form-grid">

                        <div class="form-group">

                            <label>
                                Application Number *
                            </label>

                            <input type="text"
                                   name="applicationNumber"
                                   maxlength="80"
                                   placeholder="APP-2026-0001"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Application Date
                            </label>

                            <input type="date"
                                   name="applicationDate">

                        </div>

                        <div class="form-group">

                            <label>
                                Course *
                            </label>

                            <select name="courseId"
                                    required>

                                <option value="">
                                    Select Course
                                </option>

                                <% if (courses != null) {
                                    for (Course course : courses) {
                                %>

                                <option value="<%= course.getId() %>">
                                    <%= course.getName() %>
                                </option>

                                <% }
                                } %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label>
                                Preferred Batch
                            </label>

                            <select name="batchId">

                                <option value="">
                                    Select Batch
                                </option>

                                <% if (batches != null) {
                                    for (Batch batch : batches) {
                                %>

                                <option value="<%= batch.getId() %>">
                                    <%= batch.getBatchCode() %>
                                    -
                                    <%= batch.getName() %>
                                </option>

                                <% }
                                } %>

                            </select>

                        </div>

                        <div class="form-group">

                            <label>
                                First Name *
                            </label>

                            <input type="text"
                                   name="firstName"
                                   maxlength="100"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Last Name
                            </label>

                            <input type="text"
                                   name="lastName"
                                   maxlength="100">

                        </div>

                        <div class="form-group">

                            <label>
                                Email
                            </label>

                            <input type="email"
                                   name="email"
                                   maxlength="150">

                        </div>

                        <div class="form-group">

                            <label>
                                Phone *
                            </label>

                            <input type="text"
                                   name="phone"
                                   maxlength="30"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Date of Birth
                            </label>

                            <input type="date"
                                   name="dateOfBirth">

                        </div>

                        <div class="form-group">

                            <label>
                                Gender
                            </label>

                            <select name="gender">

                                <option value="">
                                    Select Gender
                                </option>

                                <option value="MALE">
                                    Male
                                </option>

                                <option value="FEMALE">
                                    Female
                                </option>

                                <option value="OTHER">
                                    Other
                                </option>

                            </select>

                        </div>

                        <div class="form-group">

                            <label>
                                Qualification
                            </label>

                            <input type="text"
                                   name="qualification"
                                   maxlength="255"
                                   placeholder="12th / Diploma / Graduation">

                        </div>

                        <div class="form-group">

                            <label>
                                Source
                            </label>

                            <input type="text"
                                   name="source"
                                   maxlength="100"
                                   placeholder="Website / Referral / Walk-in">

                        </div>

                        <div class="form-group form-group-full">

                            <label>
                                Address Line 1
                            </label>

                            <input type="text"
                                   name="addressLine1"
                                   maxlength="255">

                        </div>

                        <div class="form-group form-group-full">

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

                        <div class="form-group">

                            <label>
                                Status
                            </label>

                            <select name="status">

                                <option value="APPLIED">
                                    Applied
                                </option>

                                <option value="UNDER_REVIEW">
                                    Under Review
                                </option>

                                <option value="APPROVED">
                                    Approved
                                </option>

                                <option value="REJECTED">
                                    Rejected
                                </option>

                                <option value="WAITLISTED">
                                    Waitlisted
                                </option>

                                <option value="CANCELLED">
                                    Cancelled
                                </option>

                                <option value="CONVERTED">
                                    Converted
                                </option>

                            </select>

                        </div>

                        <div class="form-group form-group-full">

                            <label>
                                Notes
                            </label>

                            <textarea name="notes"
                                      rows="5"
                                      placeholder="Admission remarks..."></textarea>

                        </div>

                    </div>

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/admissions"
                           class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Create Admission
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