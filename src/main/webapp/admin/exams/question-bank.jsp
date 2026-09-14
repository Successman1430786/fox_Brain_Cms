<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    String contextPath = request.getContextPath();

    List<Question> questions =
            (List<Question>)
                    request.getAttribute("questions");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Question Bank - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
        }

        .content {
            padding: 30px;
        }

        .top {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .btn {
            background: #2563eb;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 6px;
        }

        .box {
            background: white;
            padding: 20px;
            border-radius: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background: #f1f5f9;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <div class="top">

        <div>
            <h1>Question Bank</h1>
            <p>Manage reusable exam questions.</p>
        </div>

        <a class="btn"
           href="<%= contextPath %>/admin/exams/question-edit.jsp">
            + Add Question
        </a>

    </div>

    <div class="box">

        <table>

            <thead>

            <tr>
                <th>ID</th>
                <th>Question</th>
                <th>Type</th>
                <th>Difficulty</th>
                <th>Marks</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody>

            <% if (questions != null &&
                   !questions.isEmpty()) {

                for (Question q : questions) {
            %>

            <tr>

                <td>
                    <%= q.getId() %>
                </td>

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
                    <%= q.getStatus() %>
                </td>

                <td>

                    <a href="<%= contextPath %>/admin/questions?action=view&id=<%= q.getId() %>">
                        View
                    </a>

                    |

                    <a href="<%= contextPath %>/admin/exams/question-edit.jsp?id=<%= q.getId() %>">
                        Edit
                    </a>

                </td>

            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="7">
                    No questions found.
                </td>
            </tr>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>