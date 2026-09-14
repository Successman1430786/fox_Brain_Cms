<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.foxbrain.model.Exam" %>

<%
    String contextPath = request.getContextPath();

    Exam exam =
            (Exam) request.getAttribute("exam");

    if (exam == null) {
        response.sendRedirect(
                contextPath + "/admin/exams"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Exam - FoxBrain</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f6fa;
        }

        .content {
            padding: 30px;
        }

        .form-box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            max-width: 1000px;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        .full {
            grid-column: 1 / -1;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        textarea {
            min-height: 120px;
        }

        .checks {
            display: flex;
            gap: 25px;
        }

        .checks label {
            font-weight: normal;
        }

        .checks input {
            width: auto;
        }

        .actions {
            margin-top: 25px;
        }

        button {
            padding: 11px 20px;
            border: none;
            border-radius: 6px;
            background: #2563eb;
            color: white;
            cursor: pointer;
        }

        .cancel {
            margin-left: 10px;
        }
    </style>
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Edit Exam</h1>

    <div class="form-box">

        <form method="post"
              action="<%= contextPath %>/admin/exams">

            <input type="hidden"
                   name="action"
                   value="update">

            <input type="hidden"
                   name="id"
                   value="<%= exam.getId() %>">

            <div class="grid">

                <div>
                    <label>Batch ID</label>

                    <input type="number"
                           name="batchId"
                           value="<%= exam.getBatchId() %>"
                           required>
                </div>

                <div>
                    <label>Exam Title</label>

                    <input type="text"
                           name="title"
                           value="<%= exam.getTitle() %>"
                           required>
                </div>

                <div>
                    <label>Exam Type</label>

                    <select name="examType">

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

                <div>
                    <label>Exam Mode</label>

                    <select name="examMode">

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

                <div>
                    <label>Exam Date</label>

                    <input type="date"
                           name="examDate"
                           value="<%= exam.getExamDate() != null ? exam.getExamDate() : "" %>">
                </div>

                <div>
                    <label>Room</label>

                    <input type="text"
                           name="roomName"
                           value="<%= exam.getRoomName() != null ? exam.getRoomName() : "" %>">
                </div>

                <div>
                    <label>Start Time</label>

                    <input type="time"
                           name="startTime"
                           value="<%= exam.getStartTime() != null ? exam.getStartTime().toString().substring(0,5) : "" %>">
                </div>

                <div>
                    <label>End Time</label>

                    <input type="time"
                           name="endTime"
                           value="<%= exam.getEndTime() != null ? exam.getEndTime().toString().substring(0,5) : "" %>">
                </div>

                <div>
                    <label>Duration</label>

                    <input type="number"
                           name="durationMinutes"
                           value="<%= exam.getDurationMinutes() %>">
                </div>

                <div>
                    <label>Total Marks</label>

                    <input type="number"
                           name="totalMarks"
                           step="0.01"
                           value="<%= exam.getTotalMarks() %>"
                           required>
                </div>

                <div>
                    <label>Passing Marks</label>

                    <input type="number"
                           name="passingMarks"
                           step="0.01"
                           value="<%= exam.getPassingMarks() %>">
                </div>

                <div>
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

                <div class="full">

                    <label>Instructions</label>

                    <textarea name="instructions"><%= exam.getInstructions() != null ? exam.getInstructions() : "" %></textarea>

                </div>

                <div class="full">

                    <div class="checks">

                        <label>
                            <input type="checkbox"
                                   name="allowNavigation"
                                   <%= exam.isAllowNavigation() ? "checked" : "" %>>
                            Allow Navigation
                        </label>

                        <label>
                            <input type="checkbox"
                                   name="shuffleQuestions"
                                   <%= exam.isShuffleQuestions() ? "checked" : "" %>>
                            Shuffle Questions
                        </label>

                        <label>
                            <input type="checkbox"
                                   name="shuffleOptions"
                                   <%= exam.isShuffleOptions() ? "checked" : "" %>>
                            Shuffle Options
                        </label>

                    </div>

                </div>

            </div>

            <div class="actions">

                <button type="submit">
                    Update Exam
                </button>

                <a class="cancel"
                   href="<%= contextPath %>/admin/exams">
                    Cancel
                </a>

            </div>

        </form>

    </div>

</div>

</body>
</html>