<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.setAttribute("pageTitle", "Add Course Category");

    String errorMessage =
        (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Course Category | FoxBrain Admin</title>

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
                        <h2>Add Course Category</h2>

                        <p>
                            Create a new category for organizing courses.
                        </p>
                    </div>

                    <div class="page-header-actions">

                        <a class="btn btn-secondary"
                           href="<%= request.getContextPath() %>/admin/course-categories">
                            ← Back
                        </a>

                    </div>

                </div>

                <% if (errorMessage != null) { %>

                    <div class="alert alert-danger">
                        <%= errorMessage %>
                    </div>

                <% } %>

                <div class="card form-card">

                    <form method="post"
                          action="<%= request.getContextPath() %>/admin/course-categories">

                        <input type="hidden"
                               name="action"
                               value="create">

                        <div class="form-grid">

                            <div class="form-group">

                                <label for="name">
                                    Category Name
                                    <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="name"
                                       name="name"
                                       maxlength="150"
                                       placeholder="e.g. Programming"
                                       required>

                            </div>

                            <div class="form-group">

                                <label for="slug">
                                    Slug
                                    <span class="required">*</span>
                                </label>

                                <input type="text"
                                       id="slug"
                                       name="slug"
                                       maxlength="180"
                                       placeholder="e.g. programming"
                                       required>

                                <small>
                                    Use lowercase words separated by hyphens.
                                </small>

                            </div>

                            <div class="form-group form-group-full">

                                <label for="description">
                                    Description
                                </label>

                                <textarea id="description"
                                          name="description"
                                          rows="5"
                                          placeholder="Enter category description..."></textarea>

                            </div>

                            <div class="form-group">

                                <label for="displayOrder">
                                    Display Order
                                </label>

                                <input type="number"
                                       id="displayOrder"
                                       name="displayOrder"
                                       value="0"
                                       min="0">

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

                                </select>

                            </div>

                        </div>

                        <div class="form-actions">

                            <a class="btn btn-secondary"
                               href="<%= request.getContextPath() %>/admin/course-categories">
                                Cancel
                            </a>

                            <button type="submit"
                                    class="btn btn-primary">
                                Create Category
                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </main>

        <%@ include file="/includes/admin-footer.jsp" %>

    </div>

</div>

</body>
</html>