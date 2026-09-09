<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Question Bank - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>
            <h1>Question Bank</h1>
            <p>Create and manage reusable examination questions.</p>
        </div>

        <a href="<%= request.getContextPath() %>/admin/exams/question-edit.jsp"
           class="btn btn-primary">
            + Add Question
        </a>

    </div>

    <% if (success != null) { %>

        <div class="alert alert-success">
            Operation completed: <%= success %>
        </div>

    <% } %>

    <% if (error != null) { %>

        <div class="alert alert-danger">
            <%= error %>
        </div>

    <% } %>

    <div class="card">

        <div class="table-responsive">

            <table class="admin-table">

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Question</th>
                    <th>Type</th>
                    <th>Difficulty</th>
                    <th>Marks</th>
                    <th>Negative</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>

                </thead>

                <tbody>

                <% if (questions == null || questions.isEmpty()) { %>

                    <tr>
                        <td colspan="8" style="text-align:center;">
                            No questions found.
                        </td>
                    </tr>

                <% } else { %>

                    <% for (Question q : questions) { %>

                        <tr>

                            <td><%= q.getId() %></td>

                            <td>
                                <%= q.getQuestionText() %>
                            </td>

                            <td>
                                <%= q.getQuestionType() %>
                            </td>

                            <td>
                                <%= q.getDifficulty() %>
                            </td>

                            <td>
                                <%= q.getDefaultMarks() %>
                            </td>

                            <td>
                                <%= q.getNegativeMarks() %>
                            </td>

                            <td>
                                <%= q.getStatus() %>
                            </td>

                            <td>

                                <a class="btn btn-sm"
                                   href="<%= request.getContextPath() %>/admin/question-bank?action=view&id=<%= q.getId() %>">
                                    View
                                </a>

                                <a class="btn btn-sm"
                                   href="<%= request.getContextPath() %>/admin/question-bank?action=edit&id=<%= q.getId() %>">
                                    Edit
                                </a>

                                <a class="btn btn-sm"
                                   href="<%= request.getContextPath() %>/admin/question-bank?action=options&questionId=<%= q.getId() %>">
                                    Options
                                </a>

                                <a class="btn btn-sm btn-danger"
                                   href="<%= request.getContextPath() %>/admin/question-bank?action=delete&id=<%= q.getId() %>"
                                   onclick="return confirm('Deactivate this question?');">
                                    Delete
                                </a>

                            </td>

                        </tr>

                    <% } %>

                <% } %>

                </tbody>

            </table>

        </div>

    </div>

</div>

</body>
</html>