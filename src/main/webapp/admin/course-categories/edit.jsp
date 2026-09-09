<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.foxbrain.model.CourseCategory" %>

<%
    request.setAttribute("pageTitle", "Edit Course Category");

    CourseCategory category =
        (CourseCategory) request.getAttribute("category");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    if (category == null) {
        response.sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "Category not found."
        );
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Edit Course Category | FoxBrain Admin</title>

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
                        <h2>Edit Course Category</h2>

                        <p>
                            Update the selected course category.
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
                               value="update">

                        <input type="hidden"
                               name="id"
                               value="<%= category.getId() %>">

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
                                       value="<%= category.getName() %>"
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
                                       value="<%= category.getSlug() %>"
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
                                          placeholder="Enter category description..."><%= category.getDescription() != null ? category.getDescription() : "" %></textarea>

                            </div>

                            <div class="form-group">

                                <label for="displayOrder">
                                    Display Order
                                </label>

                                <input type="number"
                                       id="displayOrder"
                                       name="displayOrder"
                                       min="0"
                                       value="<%= category.getDisplayOrder() %>">

                            </div>

                            <div class="form-group">

                                <label for="status">
                                    Status
                                </label>

                                <select id="status"
                                        name="status">

                                    <option value="ACTIVE"
                                        <%= "ACTIVE".equals(category.getStatus())
                                            ? "selected" : "" %>>
                                        Active
                                    </option>

                                    <option value="INACTIVE"
                                        <%= "INACTIVE".equals(category.getStatus())
                                            ? "selected" : "" %>>
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
                                Update Category
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