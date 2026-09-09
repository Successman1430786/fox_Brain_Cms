<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.foxbrain.model.ExamQuestion" %>

<%
    Long examId =
            (Long) request.getAttribute("examId");

    List<ExamQuestion> examQuestions =
            (List<ExamQuestion>)
                    request.getAttribute("examQuestions");

    String success =
            request.getParameter("success");
%>

<!DOCTYPE html>
<html>
<head>

    <title>Exam Questions - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>

            <h1>Exam Questions</h1>

            <p>
                Configure questions assigned to Exam #<%= examId %>.
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/admin/exams?action=view&id=<%= examId %>"
           class="btn btn-secondary">
            Back to Exam
        </a>

    </div>

    <% if (success != null) { %>

        <div class="alert alert-success">
            Question operation: <%= success %>
        </div>

    <% } %>

    <div class="card">

        <h2>Add Question</h2>

        <form method="post"
              action="<%= request.getContextPath() %>/admin/exam-questions">

            <input type="hidden"
                   name="action"
                   value="add">

            <input type="hidden"
                   name="examId"
                   value="<%= examId %>">

            <div class="form-grid">

                <div class="form-group">

                    <label>Question ID *</label>

                    <input type="number"
                           name="questionId"
                           min="1"
                           required>

                    <small>
                        Enter an existing Question Bank ID.
                    </small>

                </div>

                <div class="form-group">

                    <label>Question Order</label>

                    <input type="number"
                           name="questionOrder"
                           min="1"
                           placeholder="Auto">

                </div>

                <div class="form-group">

                    <label>Marks *</label>

                    <input type="number"
                           name="marks"
                           min="0.01"
                           step="0.01"
                           required>

                </div>

                <div class="form-group">

                    <label>Negative Marks</label>

                    <input type="number"
                           name="negativeMarks"
                           min="0"
                           step="0.01"
                           value="0">

                </div>

                <div class="form-group">

                    <label>Section</label>

                    <input type="text"
                           name="sectionName"
                           maxlength="100"
                           placeholder="e.g. Section A">

                </div>

                <div class="form-group">

                    <label>
                        <input type="checkbox"
                               name="isRequired"
                               checked>
                        Required Question
                    </label>

                </div>

            </div>

            <button type="submit"
                    class="btn btn-primary">
                Add Question
            </button>

        </form>

    </div>

    <div class="card">

        <h2>Assigned Questions</h2>

        <table class="admin-table">

            <thead>

            <tr>
                <th>Order</th>
                <th>Question</th>
                <th>Type</th>
                <th>Marks</th>
                <th>Negative</th>
                <th>Section</th>
                <th>Required</th>
                <th>Actions</th>
            </tr>

            </thead>

            <tbody>

            <% if (examQuestions == null ||
                   examQuestions.isEmpty()) { %>

                <tr>
                    <td colspan="8"
                        style="text-align:center;">
                        No questions assigned yet.
                    </td>
                </tr>

            <% } else { %>

                <% for (ExamQuestion eq : examQuestions) { %>

                    <tr>

                        <td>
                            <%= eq.getQuestionOrder() %>
                        </td>

                        <td>

                            <%= eq.getQuestion() == null
                                    ? "Question #" + eq.getQuestionId()
                                    : eq.getQuestion().getQuestionText() %>

                        </td>

                        <td>

                            <%= eq.getQuestion() == null
                                    ? "-"
                                    : eq.getQuestion().getQuestionType() %>

                        </td>

                        <td>
                            <%= eq.getMarks() %>
                        </td>

                        <td>
                            <%= eq.getNegativeMarks() %>
                        </td>

                        <td>
                            <%= eq.getSectionName() == null
                                    ? "-"
                                    : eq.getSectionName() %>
                        </td>

                        <td>
                            <%= eq.isRequired() ? "YES" : "NO" %>
                        </td>

                        <td>

                            <a href="<%= request.getContextPath() %>/admin/exam-questions?action=remove&id=<%= eq.getId() %>&examId=<%= examId %>"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Remove this question from the exam?');">
                                Remove
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