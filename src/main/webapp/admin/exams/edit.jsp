<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    Exam exam = (Exam) request.getAttribute("exam");

    if (exam == null) {
        response.sendRedirect(
            request.getContextPath() + "/admin/exams?action=list"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <title>Edit Exam - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">

</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">

        <div>
            <h1>Edit Exam</h1>
            <p>Update examination information.</p>
        </div>

        <a href="<%= request.getContextPath() %>/admin/exams?action=view&id=<%= exam.getId() %>"
           class="btn btn-secondary">
            Back
        </a>

    </div>

    <div class="card">

        <form method="post"
              action="<%= request.getContextPath() %>/admin/exams">

            <input type="hidden"
                   name="action"
                   value="update">

            <input type="hidden"
                   name="id"
                   value="<%= exam.getId() %>">

            <div class="form-grid">

                <div class="form-group">

                    <label>Batch ID *</label>

                    <input type="number"
                           name="batchId"
                           min="1"
                           required
                           value="<%= exam.getBatchId() %>">

                </div>

                <div class="form-group">

                    <label>Exam Title *</label>

                    <input type="text"
                           name="title"
                           maxlength="255"
                           required
                           value="<%= exam.getTitle() %>">

                </div>

                <div class="form-group">

                    <label>Exam Type *</label>

                    <select name="examType" required>

                        <option value="QUIZ"
                            <%= "QUIZ".equals(exam.getExamType()) ? "selected" : "" %>>
                            Quiz
                        </option>

                        <option value="MIDTERM"
                            <%= "MIDTERM".equals(exam.getExamType()) ? "selected" : "" %>>
                            Midterm
                        </option>

                        <option value="FINAL"
                            <%= "FINAL".equals(exam.getExamType()) ? "selected" : "" %>>
                            Final
                        </option>

                        <option value="PRACTICAL"
                            <%= "PRACTICAL".equals(exam.getExamType()) ? "selected" : "" %>>
                            Practical
                        </option>

                        <option value="PROJECT"
                            <%= "PROJECT".equals(exam.getExamType()) ? "selected" : "" %>>
                            Project
                        </option>

                        <option value="OTHER"
                            <%= "OTHER".equals(exam.getExamType()) ? "selected" : "" %>>
                            Other
                        </option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Exam Mode *</label>

                    <select name="examMode"
                            id="examMode"
                            required
                            onchange="toggleRoom()">

                        <option value="ONLINE"
                            <%= "ONLINE".equals(exam.getExamMode()) ? "selected" : "" %>>
                            Online
                        </option>

                        <option value="OFFLINE"
                            <%= "OFFLINE".equals(exam.getExamMode()) ? "selected" : "" %>>
                            Offline
                        </option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Exam Date</label>

                    <input type="date"
                           name="examDate"
                           value="<%= exam.getExamDate() == null ? "" : exam.getExamDate() %>">

                </div>

                <div class="form-group">

                    <label>Start Time</label>

                    <input type="time"
                           name="startTime"
                           value="<%= exam.getStartTime() == null ? "" : exam.getStartTime() %>">

                </div>

                <div class="form-group">

                    <label>End Time</label>

                    <input type="time"
                           name="endTime"
                           value="<%= exam.getEndTime() == null ? "" : exam.getEndTime() %>">

                </div>

                <div class="form-group">

                    <label>Duration</label>

                    <input type="number"
                           name="durationMinutes"
                           min="1"
                           value="<%= exam.getDurationMinutes() == null ? "" : exam.getDurationMinutes() %>">

                </div>

                <div class="form-group">

                    <label>Total Marks *</label>

                    <input type="number"
                           name="totalMarks"
                           min="0.01"
                           step="0.01"
                           required
                           value="<%= exam.getTotalMarks() %>">

                </div>

                <div class="form-group">

                    <label>Passing Marks</label>

                    <input type="number"
                           name="passingMarks"
                           min="0"
                           step="0.01"
                           value="<%= exam.getPassingMarks() == null ? "" : exam.getPassingMarks() %>">

                </div>

                <div class="form-group">

                    <label>Room Name</label>

                    <input type="text"
                           name="roomName"
                           id="roomName"
                           maxlength="100"
                           value="<%= exam.getRoomName() == null ? "" : exam.getRoomName() %>">

                </div>

                <div class="form-group">

                    <label>Status</label>

                    <select name="status">

                        <option value="DRAFT"
                            <%= "DRAFT".equals(exam.getStatus()) ? "selected" : "" %>>
                            Draft
                        </option>

                        <option value="SCHEDULED"
                            <%= "SCHEDULED".equals(exam.getStatus()) ? "selected" : "" %>>
                            Scheduled
                        </option>

                        <option value="COMPLETED"
                            <%= "COMPLETED".equals(exam.getStatus()) ? "selected" : "" %>>
                            Completed
                        </option>

                        <option value="CANCELLED"
                            <%= "CANCELLED".equals(exam.getStatus()) ? "selected" : "" %>>
                            Cancelled
                        </option>

                    </select>

                </div>

            </div>

            <div class="form-group">

                <label>Instructions</label>

                <textarea name="instructions"
                          rows="6"><%= exam.getInstructions() == null ? "" : exam.getInstructions() %></textarea>

            </div>

            <div class="form-group">

                <label>
                    <input type="checkbox"
                           name="allowNavigation"
                           <%= exam.isAllowNavigation() ? "checked" : "" %>>
                    Allow navigation
                </label>

                <br>

                <label>
                    <input type="checkbox"
                           name="shuffleQuestions"
                           <%= exam.isShuffleQuestions() ? "checked" : "" %>>
                    Shuffle questions
                </label>

                <br>

                <label>
                    <input type="checkbox"
                           name="shuffleOptions"
                           <%= exam.isShuffleOptions() ? "checked" : "" %>>
                    Shuffle options
                </label>

            </div>

            <button type="submit"
                    class="btn btn-primary">
                Update Exam
            </button>

        </form>

    </div>

</div>

<script>
function toggleRoom() {

    const mode =
        document.getElementById("examMode").value;

    document.getElementById("roomName").required =
        mode === "OFFLINE";
}

toggleRoom();
</script>

</body>
</html>