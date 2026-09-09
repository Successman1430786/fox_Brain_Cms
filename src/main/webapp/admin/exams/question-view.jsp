<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    Question q =
            (Question) request.getAttribute("question");

    if (q == null) {
        response.sendRedirect(
            request.getContextPath()
            + "/admin/question-bank?action=list"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <title>Question - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>
            <h1>Question #<%= q.getId() %></h1>
        </div>

        <div>

            <a href="<%= request.getContextPath() %>/admin/question-bank?action=edit&id=<%= q.getId() %>"
               class="btn btn-primary">
                Edit
            </a>

            <a href="<%= request.getContextPath() %>/admin/question-bank?action=options&questionId=<%= q.getId() %>"
               class="btn btn-secondary">
                Manage Options
            </a>

        </div>

    </div>

    <div class="card">

        <h2>Question</h2>

        <p>
            <%= q.getQuestionText() %>
        </p>

        <hr>

        <p>
            <strong>Type:</strong>
            <%= q.getQuestionType() %>
        </p>

        <p>
            <strong>Difficulty:</strong>
            <%= q.getDifficulty() %>
        </p>

        <p>
            <strong>Marks:</strong>
            <%= q.getDefaultMarks() %>
        </p>

        <p>
            <strong>Negative Marks:</strong>
            <%= q.getNegativeMarks() %>
        </p>

        <p>
            <strong>Status:</strong>
            <%= q.getStatus() %>
        </p>

    </div>

    <div class="card">

        <h2>Explanation</h2>

        <p>
            <%= q.getExplanation() == null ||
                q.getExplanation().isEmpty()
                ? "No explanation available."
                : q.getExplanation() %>
        </p>

    </div>

</div>

</body>
</html>