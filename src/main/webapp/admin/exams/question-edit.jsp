<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Question" %>

<%
    Question question =
            (Question) request.getAttribute("question");

    boolean editing =
            question != null && question.getId() > 0;
%>

<!DOCTYPE html>
<html>
<head>

    <title>
        <%= editing ? "Edit Question" : "Add Question" %>
        - FoxBrain
    </title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>

            <h1>
                <%= editing ? "Edit Question" : "Add Question" %>
            </h1>

            <p>
                Manage questions for the FoxBrain question bank.
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/admin/question-bank?action=list"
           class="btn btn-secondary">
            Back
        </a>

    </div>

    <div class="card">

        <form method="post"
              action="<%= request.getContextPath() %>/admin/question-bank">

            <input type="hidden"
                   name="action"
                   value="<%= editing ? "update" : "create" %>">

            <% if (editing) { %>

                <input type="hidden"
                       name="id"
                       value="<%= question.getId() %>">

            <% } %>

            <div class="form-grid">

                <div class="form-group">

                    <label>Course ID</label>

                    <input type="number"
                           name="courseId"
                           min="1"
                           value="<%= editing && question.getCourseId() != null
                                    ? question.getCourseId()
                                    : "" %>">

                </div>

                <div class="form-group">

                    <label>Question Type *</label>

                    <select name="questionType"
                            id="questionType"
                            required
                            onchange="showQuestionOptions()">

                        <option value="">Select Type</option>

                        <option value="MCQ"
                            <%= editing && "MCQ".equals(question.getQuestionType()) ? "selected" : "" %>>
                            Multiple Choice
                        </option>

                        <option value="TRUE_FALSE"
                            <%= editing && "TRUE_FALSE".equals(question.getQuestionType()) ? "selected" : "" %>>
                            True / False
                        </option>

                        <option value="SHORT_ANSWER"
                            <%= editing && "SHORT_ANSWER".equals(question.getQuestionType()) ? "selected" : "" %>>
                            Short Answer
                        </option>

                        <option value="LONG_ANSWER"
                            <%= editing && "LONG_ANSWER".equals(question.getQuestionType()) ? "selected" : "" %>>
                            Long Answer
                        </option>

                        <option value="CODING"
                            <%= editing && "CODING".equals(question.getQuestionType()) ? "selected" : "" %>>
                            Coding
                        </option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Difficulty *</label>

                    <select name="difficulty" required>

                        <option value="EASY"
                            <%= editing && "EASY".equals(question.getDifficulty()) ? "selected" : "" %>>
                            Easy
                        </option>

                        <option value="MEDIUM"
                            <%= !editing || "MEDIUM".equals(question.getDifficulty()) ? "selected" : "" %>>
                            Medium
                        </option>

                        <option value="HARD"
                            <%= editing && "HARD".equals(question.getDifficulty()) ? "selected" : "" %>>
                            Hard
                        </option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Default Marks *</label>

                    <input type="number"
                           name="defaultMarks"
                           min="0.01"
                           step="0.01"
                           required
                           value="<%= editing ? question.getDefaultMarks() : "1.00" %>">

                </div>

                <div class="form-group">

                    <label>Negative Marks</label>

                    <input type="number"
                           name="negativeMarks"
                           min="0"
                           step="0.01"
                           value="<%= editing ? question.getNegativeMarks() : "0.00" %>">

                </div>

                <div class="form-group">

                    <label>Status</label>

                    <select name="status">

                        <option value="ACTIVE"
                            <%= !editing || "ACTIVE".equals(question.getStatus())
                                ? "selected" : "" %>>
                            Active
                        </option>

                        <option value="INACTIVE"
                            <%= editing && "INACTIVE".equals(question.getStatus())
                                ? "selected" : "" %>>
                            Inactive
                        </option>

                    </select>

                </div>

            </div>

            <div class="form-group">

                <label>Question *</label>

                <textarea name="questionText"
                          rows="8"
                          required
                          placeholder="Enter question..."><%= editing ? question.getQuestionText() : "" %></textarea>

            </div>

            <div class="form-group">

                <label>Explanation</label>

                <textarea name="explanation"
                          rows="5"
                          placeholder="Explain the correct answer..."><%= editing && question.getExplanation() != null
                                ? question.getExplanation()
                                : "" %></textarea>

            </div>

            <div id="optionNotice"
                 class="alert alert-info">

                MCQ and True/False questions require answer options.
                Save the question first, then manage options.

            </div>

            <button type="submit"
                    class="btn btn-primary">

                <%= editing ? "Update Question" : "Create Question" %>

            </button>

        </form>

    </div>

</div>

<script>

function showQuestionOptions() {

    const type =
        document.getElementById("questionType").value;

    const notice =
        document.getElementById("optionNotice");

    if (type === "MCQ" || type === "TRUE_FALSE") {

        notice.style.display = "block";

    } else {

        notice.style.display = "none";
    }
}

showQuestionOptions();

</script>

</body>
</html>