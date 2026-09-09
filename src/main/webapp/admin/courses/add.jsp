<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.CourseCategory" %>

<%
    request.setAttribute("pageTitle", "Add Course");

    List<CourseCategory> categories =
        (List<CourseCategory>) request.getAttribute("categories");

    String errorMessage =
        (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Add Course | FoxBrain Admin</title>

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
                        <h2>Add Course</h2>

                        <p>
                            Create a new course for FoxBrain Institute.
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
                               value="create">


                        <div class="form-grid">


                            <div class="form-group">

                                <label for="categoryId">
                                    Category
                                </label>

                                <select id="categoryId"
                                        name="categoryId">

                                    <option value="">
                                        -- Select Category --
                                    </option>

                                    <% if (categories != null) {
                                           for (CourseCategory category : categories) {

                                               if ("ACTIVE".equals(
                                                       category.getStatus())) {
                                    %>

                                        <option value="<%= category.getId() %>">
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
                                       placeholder="e.g. Full Stack Java Development">

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
                                       placeholder="e.g. full-stack-java-development">

                                <small>
                                    Use a unique URL-friendly value.
                                </small>

                            </div>


                            <div class="form-group">

                                <label for="shortDescription">
                                    Short Description
                                </label>

                                <input type="text"
                                       id="shortDescription"
                                       name="shortDescription"
                                       maxlength="500"
                                       placeholder="Brief course description">

                            </div>


                            <div class="form-group">

                                <label for="durationValue">
                                    Duration
                                </label>

                                <input type="number"
                                       id="durationValue"
                                       name="durationValue"
                                       min="1"
                                       placeholder="e.g. 6">

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

                                    <option value="DAYS">
                                        Days
                                    </option>

                                    <option value="WEEKS">
                                        Weeks
                                    </option>

                                    <option value="MONTHS">
                                        Months
                                    </option>

                                    <option value="YEARS">
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
                                       placeholder="e.g. 25000">

                            </div>


                            <div class="form-group">

                                <label for="currencyCode">
                                    Currency
                                </label>

                                <input type="text"
                                       id="currencyCode"
                                       name="currencyCode"
                                       value="INR"
                                       maxlength="3"
                                       placeholder="INR">

                            </div>


                            <div class="form-group">

                                <label for="maxStudents">
                                    Maximum Students
                                </label>

                                <input type="number"
                                       id="maxStudents"
                                       name="maxStudents"
                                       min="1"
                                       placeholder="Leave empty for unlimited">

                            </div>


                            <div class="form-group">

                                <label for="displayOrder">
                                    Display Order
                                </label>

                                <input type="number"
                                       id="displayOrder"
                                       name="displayOrder"
                                       min="0"
                                       value="0">

                            </div>


                            <div class="form-group">

                                <label for="status">
                                    Status
                                    <span class="required">*</span>
                                </label>

                                <select id="status"
                                        name="status"
                                        required>

                                    <option value="DRAFT">
                                        Draft
                                    </option>

                                    <option value="ACTIVE">
                                        Active
                                    </option>

                                    <option value="INACTIVE">
                                        Inactive
                                    </option>

                                    <option value="ARCHIVED">
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
                                           value="true">

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
                                       placeholder="https://example.com/course-image.jpg">

                            </div>


                            <div class="form-group form-group-full">

                                <label for="description">
                                    Description
                                </label>

                                <textarea id="description"
                                          name="description"
                                          placeholder="Enter complete course description"></textarea>

                            </div>


                            <div class="form-group form-group-full">

                                <label for="syllabus">
                                    Syllabus
                                </label>

                                <textarea id="syllabus"
                                          name="syllabus"
                                          placeholder="Enter course syllabus, modules and topics"></textarea>

                            </div>

                        </div>


                        <div class="form-actions">

                            <a href="<%= request.getContextPath() %>/admin/courses"
                               class="btn btn-secondary">

                                Cancel

                            </a>

                            <button type="submit"
                                    class="btn btn-primary">

                                💾 Create Course

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