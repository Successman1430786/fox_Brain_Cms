<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Admission" %>
<%@ page import="com.foxbrain.model.Course" %>
<%@ page import="com.foxbrain.model.Batch" %>

<%
    request.setAttribute("pageTitle", "Edit Admission");

    Admission admission =
            (Admission) request.getAttribute("admission");

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

    <title>Edit Admission - FoxBrain Admin</title>

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
                    <h2>Edit Admission</h2>
                    <p>
                        Update admission application details.
                    </p>
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
                           value="update">

                    <input type="hidden"
                           name="id"
                           value="<%= admission.getId() %>">

                    <div class="form-grid">

                        <div class="form-group">

                            <label>
                                Application Number *
                            </label>

                            <input type="text"
                                   name="applicationNumber"
                                   maxlength="80"
                                   value="<%= admission.getApplicationNumber() %>"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Application Date
                            </label>

                            <input type="date"
                                   name="applicationDate"
                                   value="<%= admission.getApplicationDate() != null
                                           ? admission.getApplicationDate()
                                           : "" %>">

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

                                        boolean selected =
                                                course.getId()
                                                == admission.getCourseId();
                                %>

                                <option value="<%= course.getId() %>"
                                    <%= selected ? "selected" : "" %>>

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

                                        boolean selected =
                                                admission.getBatchId() != null
                                                && admission.getBatchId()
                                                    == batch.getId();
                                %>

                                <option value="<%= batch.getId() %>"
                                    <%= selected ? "selected" : "" %>>

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
                                   value="<%= admission.getFirstName() %>"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Last Name
                            </label>

                            <input type="text"
                                   name="lastName"
                                   maxlength="100"
                                   value="<%= admission.getLastName() != null
                                           ? admission.getLastName()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Email
                            </label>

                            <input type="email"
                                   name="email"
                                   maxlength="150"
                                   value="<%= admission.getEmail() != null
                                           ? admission.getEmail()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Phone *
                            </label>

                            <input type="text"
                                   name="phone"
                                   maxlength="30"
                                   value="<%= admission.getPhone() %>"
                                   required>

                        </div>

                        <div class="form-group">

                            <label>
                                Date of Birth
                            </label>

                            <input type="date"
                                   name="dateOfBirth"
                                   value="<%= admission.getDateOfBirth() != null
                                           ? admission.getDateOfBirth()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Gender
                            </label>

                            <select name="gender">

                                <option value="">
                                    Select Gender
                                </option>

                                <option value="MALE"
                                    <%= "MALE".equals(admission.getGender())
                                            ? "selected" : "" %>>
                                    Male
                                </option>

                                <option value="FEMALE"
                                    <%= "FEMALE".equals(admission.getGender())
                                            ? "selected" : "" %>>
                                    Female
                                </option>

                                <option value="OTHER"
                                    <%= "OTHER".equals(admission.getGender())
                                            ? "selected" : "" %>>
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
                                   value="<%= admission.getQualification() != null
                                           ? admission.getQualification()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Source
                            </label>

                            <input type="text"
                                   name="source"
                                   maxlength="100"
                                   value="<%= admission.getSource() != null
                                           ? admission.getSource()
                                           : "" %>">

                        </div>

                        <div class="form-group form-group-full">

                            <label>
                                Address Line 1
                            </label>

                            <input type="text"
                                   name="addressLine1"
                                   maxlength="255"
                                   value="<%= admission.getAddressLine1() != null
                                           ? admission.getAddressLine1()
                                           : "" %>">

                        </div>

                        <div class="form-group form-group-full">

                            <label>
                                Address Line 2
                            </label>

                            <input type="text"
                                   name="addressLine2"
                                   maxlength="255"
                                   value="<%= admission.getAddressLine2() != null
                                           ? admission.getAddressLine2()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                City
                            </label>

                            <input type="text"
                                   name="city"
                                   maxlength="100"
                                   value="<%= admission.getCity() != null
                                           ? admission.getCity()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                State
                            </label>

                            <input type="text"
                                   name="state"
                                   maxlength="100"
                                   value="<%= admission.getState() != null
                                           ? admission.getState()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Postal Code
                            </label>

                            <input type="text"
                                   name="postalCode"
                                   maxlength="20"
                                   value="<%= admission.getPostalCode() != null
                                           ? admission.getPostalCode()
                                           : "" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Country
                            </label>

                            <input type="text"
                                   name="country"
                                   maxlength="100"
                                   value="<%= admission.getCountry() != null
                                           ? admission.getCountry()
                                           : "India" %>">

                        </div>

                        <div class="form-group">

                            <label>
                                Status
                            </label>

                            <select name="status">

                                <option value="APPLIED"
                                    <%= "APPLIED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Applied
                                </option>

                                <option value="UNDER_REVIEW"
                                    <%= "UNDER_REVIEW".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Under Review
                                </option>

                                <option value="APPROVED"
                                    <%= "APPROVED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Approved
                                </option>

                                <option value="REJECTED"
                                    <%= "REJECTED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Rejected
                                </option>

                                <option value="WAITLISTED"
                                    <%= "WAITLISTED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Waitlisted
                                </option>

                                <option value="CANCELLED"
                                    <%= "CANCELLED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Cancelled
                                </option>

                                <option value="CONVERTED"
                                    <%= "CONVERTED".equals(admission.getStatus())
                                            ? "selected" : "" %>>
                                    Converted
                                </option>

                            </select>

                        </div>

                        <div class="form-group form-group-full">

                            <label>
                                Notes
                            </label>

                            <textarea name="notes"
                                      rows="5"><%= admission.getNotes() != null
                                              ? admission.getNotes()
                                              : "" %></textarea>

                        </div>

                    </div>

                    <div class="form-actions">

                        <a href="<%= request.getContextPath() %>/admin/admissions"
                           class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">
                            Update Admission
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