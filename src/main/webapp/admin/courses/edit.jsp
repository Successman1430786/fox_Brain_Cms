<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Course" %>
<%@ page import="com.foxbrain.model.CourseCategory" %>

<%
    request.setAttribute("pageTitle", "Edit Course");

    Course course =
        (Course) request.getAttribute("course");

    List<CourseCategory> categories =
        (List<CourseCategory>) request.getAttribute("categories");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    if (course == null) {
        response.sendError(404, "Course not found.");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Edit Course | FoxBrain Admin</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<div class="admin-layout">

    <%@ include file="/includes/admin-sidebar.jsp" %>

    <div class="admin-main-area">

        <%@ include file="/includes/admin-header.jsp" %>

        <main class="admin-content">

            <div class="page-container">

                <div class="page-header">

                    <div>
                        <h2>Edit Course</h2>

                        <p>
                            Update course information.
                        </p>
                    </div>

                </div>


                <% if (errorMessage != null) { %>

                    <div class="alert alert-danger">
                        <%= errorMessage %>
                    </div>

                <% } %>


                <div class="card form-card">

                    <form method="post"
                          action="<%= request.getContextPath() %>/admin/courses">

                        <input type="hidden"
                               name="action"
                               value="update">

                        <input type="hidden"
                               name="id"
                               value="<%= course.getId() %>">


                        <div class="form-grid">


                            <div class="form-group">

                                <label for="categoryId">
                                    Category
                                </label>

                                <select id="categoryId"
                                        name="categoryId">

                                    <option value="">
                                        -- No Category --
                                    </option>

                                    <% if (categories != null) {
                                           for (CourseCategory category : categories) {

                                               if ("ACTIVE".equals(
                                                       category.getStatus()) ||
                                                   (course.getCategoryId() != null &&
                                                    course.getCategoryId().equals(
                                                        category.getId()))) {
                                    %>

                                        <option value="<%= category.getId() %>"
                                            <%= course.getCategoryId() != null &&
                                                course.getCategoryId().equals(
                                                    category.getId())
                                                ? "selected"
                                                : "" %>>

                                            <%= category.getName() %>

                                        </option>

                                    <%
                                               }
                                           }
                                       }
                                    %>

                                </select>

                            </div>


                            <div class="form-group">

                                <label for="name">
                                    Course Name
                                    <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="name"
                                       name="name"
                                       maxlength="200"
                                       required
                                       value="<%= course.getName() != null ? course.getName() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="slug">
                                    Slug
                                    <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="slug"
                                       name="slug"
                                       maxlength="220"
                                       required
                                       value="<%= course.getSlug() != null ? course.getSlug() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="shortDescription">
                                    Short Description
                                </label>

                                <input type="text"
                                       id="shortDescription"
                                       name="shortDescription"
                                       maxlength="500"
                                       value="<%= course.getShortDescription() != null ? course.getShortDescription() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="durationValue">
                                    Duration
                                </label>

                                <input type="number"
                                       id="durationValue"
                                       name="durationValue"
                                       min="1"
                                       value="<%= course.getDurationValue() != null ? course.getDurationValue() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="durationUnit">
                                    Duration Unit
                                </label>

                                <select id="durationUnit"
                                        name="durationUnit">

                                    <option value="">
                                        -- Select Unit --
                                    </option>

                                    <option value="DAYS"
                                        <%= "DAYS".equals(course.getDurationUnit())
                                            ? "selected" : "" %>>
                                        Days
                                    </option>

                                    <option value="WEEKS"
                                        <%= "WEEKS".equals(course.getDurationUnit())
                                            ? "selected" : "" %>>
                                        Weeks
                                    </option>

                                    <option value="MONTHS"
                                        <%= "MONTHS".equals(course.getDurationUnit())
                                            ? "selected" : "" %>>
                                        Months
                                    </option>

                                    <option value="YEARS"
                                        <%= "YEARS".equals(course.getDurationUnit())
                                            ? "selected" : "" %>>
                                        Years
                                    </option>

                                </select>

                            </div>


                            <div class="form-group">

                                <label for="fee">
                                    Course Fee
                                </label>

                                <input type="number"
                                       id="fee"
                                       name="fee"
                                       min="0"
                                       step="0.01"
                                       value="<%= course.getFee() != null ? course.getFee() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="currencyCode">
                                    Currency
                                </label>

                                <input type="text"
                                       id="currencyCode"
                                       name="currencyCode"
                                       maxlength="3"
                                       value="<%= course.getCurrencyCode() != null ? course.getCurrencyCode() : "INR" %>">

                            </div>


                            <div class="form-group">

                                <label for="maxStudents">
                                    Maximum Students
                                </label>

                                <input type="number"
                                       id="maxStudents"
                                       name="maxStudents"
                                       min="1"
                                       value="<%= course.getMaxStudents() != null ? course.getMaxStudents() : "" %>">

                            </div>


                            <div class="form-group">

                                <label for="displayOrder">
                                    Display Order
                                </label>

                                <input type="number"
                                       id="displayOrder"
                                       name="displayOrder"
                                       min="0"
                                       value="<%= course.getDisplayOrder() %>">

                            </div>


                            <div class="form-group">

                                <label for="status">
                                    Status
                                    <span class="required">*</span>
                                </label>

                                <select id="status"
                                        name="status"
                                        required>

                                    <option value="DRAFT"
                                        <%= "DRAFT".equals(course.getStatus())
                                            ? "selected" : "" %>>
                                        Draft
                                    </option>

                                    <option value="ACTIVE"
                                        <%= "ACTIVE".equals(course.getStatus())
                                            ? "selected" : "" %>>
                                        Active
                                    </option>

                                    <option value="INACTIVE"
                                        <%= "INACTIVE".equals(course.getStatus())
                                            ? "selected" : "" %>>
                                        Inactive
                                    </option>

                                    <option value="ARCHIVED"
                                        <%= "ARCHIVED".equals(course.getStatus())
                                            ? "selected" : "" %>>
                                        Archived
                                    </option>

                                </select>

                            </div>


                            <div class="form-group">

                                <label>
                                    Featured
                                </label>

                                <label style="display:flex;align-items:center;gap:8px;min-height:44px;">

                                    <input type="checkbox"
                                           name="featured"
                                           value="true"
                                           <%= course.isFeatured()
                                               ? "checked"
                                               : "" %>>

                                    Show as featured course

                                </label>

                            </div>


                            <div class="form-group form-group-full">

                                <label for="imageUrl">
                                    Image URL
                                </label>

                                <input type="url"
                                       id="imageUrl"
                                       name="imageUrl"
                                       maxlength="500"
                                       value="<%= course.getImageUrl() != null ? course.getImageUrl() : "" %>">

                            </div>


                            <div class="form-group form-group-full">

                                <label for="description">
                                    Description
                                </label>

                                <textarea id="description"
                                          name="description"><%= course.getDescription() != null ? course.getDescription() : "" %></textarea>

                            </div>


                            <div class="form-group form-group-full">

                                <label for="syllabus">
                                    Syllabus
                                </label>

                                <textarea id="syllabus"
                                          name="syllabus"><%= course.getSyllabus() != null ? course.getSyllabus() : "" %></textarea>

                            </div>

                        </div>


                        <div class="form-actions">

                            <a href="<%= request.getContextPath() %>/admin/courses"
                               class="btn btn-secondary">

                                Cancel

                            </a>

                            <button type="submit"
                                    class="btn btn-primary">

                                💾 Update Course

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </main>

    </div>

</div>

<script src="<%= request.getContextPath() %>/assets/js/admin.js"></script>

</body>
</html>