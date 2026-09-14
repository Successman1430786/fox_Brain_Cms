<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    String contextPath = request.getContextPath();

    Question question =
            (Question) request.getAttribute("question");

    boolean editing =
            question != null;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>
        <%= editing ? "Edit Question" : "Add Question" %>
        - FoxBrain
    </title>

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
            max-width: 900px;
            border-radius: 10px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-top: 15px;
            margin-bottom: 6px;
        }

        input, select, textarea {
            width: 100%;
            box-sizing: border-box;
            padding: 10px;
        }

        textarea {
            min-height: 150px;
        }

        button {
            margin-top: 20px;
            padding: 11px 20px;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>
        <%= editing ? "Edit Question" : "Add Question" %>
    </h1>

    <div class="box">

        <form method="post"
              action="<%= contextPath %>/admin/questions">

            <input type="hidden"
                   name="action"
                   value="<%= editing ? "update" : "create" %>">

            <% if (editing) { %>

                <input type="hidden"
                       name="id"
                       value="<%= question.getId() %>">

            <% } %>

            <label>Course ID</label>

            <input type="number"
                   name="courseId"
                   min="1"
                   value="<%= editing && question.getCourseId() != null
                            ? question.getCourseId()
                            : "" %>">

            <label>Question Text *</label>

            <textarea name="questionText"
                      required><%= editing
                        ? question.getQuestionText()
                        : "" %></textarea>

            <label>Question Type *</label>

            <select name="questionType">

                <option value="MCQ"
                    <%= editing && "MCQ".equals(question.getQuestionType())
                            ? "selected" : "" %>>
                    MCQ
                </option>

                <option value="TRUE_FALSE"
                    <%= editing && "TRUE_FALSE".equals(question.getQuestionType())
                            ? "selected" : "" %>>
                    True / False
                </option>

                <option value="SHORT_ANSWER"
                    <%= editing && "SHORT_ANSWER".equals(question.getQuestionType())
                            ? "selected" : "" %>>
                    Short Answer
                </option>

                <option value="LONG_ANSWER"
                    <%= editing && "LONG_ANSWER".equals(question.getQuestionType())
                            ? "selected" : "" %>>
                    Long Answer
                </option>

                <option value="CODING"
                    <%= editing && "CODING".equals(question.getQuestionType())
                            ? "selected" : "" %>>
                    Coding
                </option>

            </select>

            <label>Difficulty</label>

            <select name="difficulty">

                <option value="EASY"
                    <%= editing && "EASY".equals(question.getDifficulty())
                            ? "selected" : "" %>>
                    Easy
                </option>

                <option value="MEDIUM"
                    <%= !editing ||
                        "MEDIUM".equals(question.getDifficulty())
                            ? "selected" : "" %>>
                    Medium
                </option>

                <option value="HARD"
                    <%= editing && "HARD".equals(question.getDifficulty())
                            ? "selected" : "" %>>
                    Hard
                </option>

            </select>

            <label>Default Marks</label>

            <input type="number"
                   name="defaultMarks"
                   step="0.01"
                   min="0.01"
                   value="<%= editing
                            ? question.getDefaultMarks()
                            : "1" %>">

            <label>Negative Marks</label>

            <input type="number"
                   name="negativeMarks"
                   step="0.01"
                   min="0"
                   value="<%= editing
                            ? question.getNegativeMarks()
                            : "0" %>">

            <label>Explanation</label>

            <textarea name="explanation"><%= editing &&
                    question.getExplanation() != null
                        ? question.getExplanation()
                        : "" %></textarea>

            <button type="submit">

                <%= editing
                        ? "Update Question"
                        : "Create Question" %>

            </button>

        </form>

    </div>

</div>

</body>
</html>