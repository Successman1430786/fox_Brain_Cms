<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.Exam" %>
<%@ page import="com.foxbrain.model.ExamQuestion" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    String contextPath = request.getContextPath();

    Exam exam =
            (Exam) request.getAttribute("exam");

    List<ExamQuestion> examQuestions =
            (List<ExamQuestion>)
                    request.getAttribute("examQuestions");

    List<Question> questions =
            (List<Question>)
                    request.getAttribute("questions");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exam Questions - FoxBrain</title>

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
            margin-bottom: 25px;
            border-radius: 10px;
        }

        select, input {
            padding: 9px;
            width: 100%;
            box-sizing: border-box;
        }

        .grid {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr;
            gap: 15px;
        }

        button {
            padding: 10px 18px;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f1f5f9;
        }

        .delete {
            color: #dc2626;
            text-decoration: none;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>
        Questions:
        <%= exam != null ? exam.getTitle() : "" %>
    </h1>

    <div class="box">

        <h3>Add Question</h3>

        <form method="post"
              action="<%= contextPath %>/admin/exam-questions">

            <input type="hidden"
                   name="action"
                   value="add">

            <input type="hidden"
                   name="examId"
                   value="<%= exam.getId() %>">

            <div class="grid">

                <div>
                    <label>Question</label>

                    <select name="questionId"
                            required>

                        <option value="">
                            Select Question
                        </option>

                        <% if (questions != null) {

                            for (Question q : questions) {
                        %>

                        <option value="<%= q.getId() %>">

                            <%= q.getId() %> -
                            <%= q.getQuestionText() %>

                        </option>

                        <%
                            }
                        }
                        %>

                    </select>
                </div>

                <div>
                    <label>Marks</label>

                    <input type="number"
                           name="marks"
                           step="0.01"
                           min="0.01"
                           value="1"
                           required>
                </div>

                <div>
                    <label>Negative Marks</label>

                    <input type="number"
                           name="negativeMarks"
                           step="0.01"
                           min="0"
                           value="0">
                </div>

                <div>
                    <label>Section</label>

                    <input type="text"
                           name="sectionName">
                </div>

                <div>
                    <label>
                        <input type="checkbox"
                               name="isRequired"
                               checked>
                        Required Question
                    </label>
                </div>

            </div>

            <br>

            <button type="submit">
                Add Question
            </button>

        </form>

    </div>

    <div class="box">

        <h3>Exam Question List</h3>

        <table>

            <thead>
            <tr>
                <th>Order</th>
                <th>Question</th>
                <th>Type</th>
                <th>Marks</th>
                <th>Negative</th>
                <th>Required</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>

            <% if (examQuestions != null &&
                   !examQuestions.isEmpty()) {

                for (ExamQuestion eq : examQuestions) {
            %>

            <tr>

                <td>
                    <%= eq.getQuestionOrder() %>
                </td>

                <td>

                    <%
                        Question q = eq.getQuestion();
                    %>

                    <%= q != null
                            ? q.getQuestionText()
                            : eq.getQuestionId() %>

                </td>

                <td>
                    <%= q != null
                            ? q.getQuestionType()
                            : "-" %>
                </td>

                <td>
                    <%= eq.getMarks() %>
                </td>

                <td>
                    <%= eq.getNegativeMarks() %>
                </td>

                <td>
                    <%= eq.isRequired()
                            ? "Yes"
                            : "No" %>
                </td>

                <td>

                    <a class="delete"
                       href="<%= contextPath %>/admin/exam-questions?action=delete&id=<%= eq.getId() %>&examId=<%= exam.getId() %>"
                       onclick="return confirm('Remove this question?');">
                        Remove
                    </a>

                </td>

            </tr>

            <%
                }

            } else {
            %>

            <tr>
                <td colspan="7">
                    No questions added yet.
                </td>
            </tr>

            <% } %>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>