<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Question" %>
<%@ page import="com.foxbrain.model.QuestionOption" %>

<%
    String contextPath = request.getContextPath();

    Question question =
            (Question) request.getAttribute("question");

    List<QuestionOption> options =
            (List<QuestionOption>)
                    request.getAttribute("options");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Question Options - FoxBrain</title>

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
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 10px;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
        }

        button {
            margin-top: 12px;
            padding: 10px 18px;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Question Options</h1>

    <% if (question != null) { %>

        <p>
            <strong>
                <%= question.getQuestionText() %>
            </strong>
        </p>

        <div class="box">

            <h3>Add Option</h3>

            <form method="post"
                  action="<%= contextPath %>/admin/question-options">

                <input type="hidden"
                       name="action"
                       value="add">

                <input type="hidden"
                       name="questionId"
                       value="<%= question.getId() %>">

                <label>Option Text</label>

                <input type="text"
                       name="optionText"
                       maxlength="1000"
                       required>

                <label>Order</label>

                <input type="number"
                       name="optionOrder"
                       value="1"
                       min="1">

                <label>
                    <input type="checkbox"
                           name="isCorrect"
                           style="width:auto;">
                    Correct Answer
                </label>

                <br>

                <button type="submit">
                    Add Option
                </button>

            </form>

        </div>

        <div class="box">

            <h3>Options</h3>

            <table>

                <thead>
                <tr>
                    <th>Order</th>
                    <th>Option</th>
                    <th>Correct</th>
                </tr>
                </thead>

                <tbody>

                <% if (options != null) {

                    for (QuestionOption option : options) {
                %>

                <tr>

                    <td>
                        <%= option.getOptionOrder() %>
                    </td>

                    <td>
                        <%= option.getOptionText() %>
                    </td>

                    <td>
                        <%= option.isCorrect()
                                ? "YES"
                                : "NO" %>
                    </td>

                </tr>

                <%
                    }
                }
                %>

                </tbody>

            </table>

        </div>

    <% } %>

</div>

</body>
</html>