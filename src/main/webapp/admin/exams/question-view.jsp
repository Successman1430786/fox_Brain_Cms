<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    String contextPath = request.getContextPath();

    Question question =
            (Question) request.getAttribute("question");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Question - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
        }

        .content {
            padding: 30px;
        }

        .box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            max-width: 900px;
        }

        .meta {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
            margin-bottom: 25px;
        }

        .item {
            background: #f8fafc;
            padding: 15px;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Question Details</h1>

    <div class="box">

        <% if (question != null) { %>

            <div class="meta">

                <div class="item">
                    <strong>ID</strong><br>
                    <%= question.getId() %>
                </div>

                <div class="item">
                    <strong>Type</strong><br>
                    <%= question.getQuestionType() %>
                </div>

                <div class="item">
                    <strong>Difficulty</strong><br>
                    <%= question.getDifficulty() %>
                </div>

                <div class="item">
                    <strong>Marks</strong><br>
                    <%= question.getDefaultMarks() %>
                </div>

                <div class="item">
                    <strong>Negative Marks</strong><br>
                    <%= question.getNegativeMarks() %>
                </div>

                <div class="item">
                    <strong>Status</strong><br>
                    <%= question.getStatus() %>
                </div>

            </div>

            <h3>Question</h3>

            <p>
                <%= question.getQuestionText() %>
            </p>

            <h3>Explanation</h3>

            <p>
                <%= question.getExplanation() != null
                        ? question.getExplanation()
                        : "No explanation." %>
            </p>

        <% } else { %>

            <p>Question not found.</p>

        <% } %>

        <br>

        <a href="<%= contextPath %>/admin/questions">
            Back to Question Bank
        </a>

    </div>

</div>

</body>
</html>