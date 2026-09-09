<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Question" %>
<%@ page import="com.foxbrain.model.QuestionOption" %>

<%
    Question question =
            (Question) request.getAttribute("question");

    List<QuestionOption> options =
            (List<QuestionOption>) request.getAttribute("options");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Question Options - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>

            <h1>Question Options</h1>

            <p>
                <%= question == null
                        ? ""
                        : question.getQuestionText() %>
            </p>

        </div>

    </div>

    <div class="card">

        <h2>Add Option</h2>

        <form method="post"
              action="<%= request.getContextPath() %>/admin/question-bank">

            <input type="hidden"
                   name="action"
                   value="addOption">

            <input type="hidden"
                   name="questionId"
                   value="<%= question.getId() %>">

            <div class="form-grid">

                <div class="form-group">

                    <label>Option Text *</label>

                    <input type="text"
                           name="optionText"
                           maxlength="1000"
                           required>

                </div>

                <div class="form-group">

                    <label>Order *</label>

                    <input type="number"
                           name="optionOrder"
                           min="1"
                           value="<%= options == null ? 1 : options.size() + 1 %>"
                           required>

                </div>

                <div class="form-group">

                    <label>
                        <input type="checkbox"
                               name="isCorrect">
                        Correct Answer
                    </label>

                </div>

            </div>

            <button type="submit"
                    class="btn btn-primary">
                Add Option
            </button>

        </form>

    </div>

    <div class="card">

        <h2>Existing Options</h2>

        <table class="admin-table">

            <thead>

            <tr>
                <th>ID</th>
                <th>Order</th>
                <th>Option</th>
                <th>Correct</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody>

            <% if (options == null || options.isEmpty()) { %>

                <tr>
                    <td colspan="5">
                        No options found.
                    </td>
                </tr>

            <% } else { %>

                <% for (QuestionOption option : options) { %>

                    <tr>

                        <td>
                            <%= option.getId() %>
                        </td>

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

                        <td>

                            <a class="btn btn-sm btn-danger"
                               href="<%= request.getContextPath() %>/admin/question-bank?action=options&questionId=<%= question.getId() %>">
                                Refresh
                            </a>

                        </td>

                    </tr>

                <% } %>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>