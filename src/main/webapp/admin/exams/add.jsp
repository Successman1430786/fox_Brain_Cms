<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Exam - FoxBrain</title>

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
            margin-top: 10px;
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
            text-decoration: none;
        }
    </style>
</head>

<body>


<%@ include file="/includes/admin-header.jsp" %>

<div class="content">

    <h1>Create Exam</h1>

    <div class="form-box">

        <form method="post"
              action="<%= contextPath %>/admin/exams">

            <input type="hidden"
                   name="action"
                   value="create">

            <div class="grid">

                <div>
                    <label>Batch ID *</label>
                    <input type="number"
                           name="batchId"
                           min="1"
                           required>
                </div>

                <div>
                    <label>Exam Title *</label>
                    <input type="text"
                           name="title"
                           maxlength="255"
                           required>
                </div>

                <div>
                    <label>Exam Type *</label>

                    <select name="examType" required>

                        <option value="">Select type</option>
                        <option value="QUIZ">Quiz</option>
                        <option value="MIDTERM">Midterm</option>
                        <option value="FINAL">Final</option>
                        <option value="PRACTICAL">Practical</option>
                        <option value="PROJECT">Project</option>
                        <option value="OTHER">Other</option>

                    </select>
                </div>

                <div>
                    <label>Exam Mode *</label>

                    <select name="examMode" required>

                        <option value="">Select mode</option>
                        <option value="ONLINE">Online</option>
                        <option value="OFFLINE">Offline</option>

                    </select>
                </div>

                <div>
                    <label>Exam Date</label>

                    <input type="date"
                           name="examDate">
                </div>

                <div>
                    <label>Room</label>

                    <input type="text"
                           name="roomName"
                           maxlength="100">
                </div>

                <div>
                    <label>Start Time</label>

                    <input type="time"
                           name="startTime">
                </div>

                <div>
                    <label>End Time</label>

                    <input type="time"
                           name="endTime">
                </div>

                <div>
                    <label>Duration (minutes)</label>

                    <input type="number"
                           name="durationMinutes"
                           min="1">
                </div>

                <div>
                    <label>Total Marks *</label>

                    <input type="number"
                           name="totalMarks"
                           min="0.01"
                           step="0.01"
                           required>
                </div>

                <div>
                    <label>Passing Marks</label>

                    <input type="number"
                           name="passingMarks"
                           min="0"
                           step="0.01">
                </div>

                <div>
                    <label>Status</label>

                    <select name="status">

                        <option value="DRAFT">
                            Draft
                        </option>

                        <option value="SCHEDULED">
                            Scheduled
                        </option>

                        <option value="COMPLETED">
                            Completed
                        </option>

                        <option value="CANCELLED">
                            Cancelled
                        </option>

                    </select>
                </div>

                <div class="full">

                    <label>Instructions</label>

                    <textarea name="instructions"></textarea>

                </div>

                <div class="full">

                    <div class="checks">

                        <label>
                            <input type="checkbox"
                                   name="allowNavigation"
                                   checked>
                            Allow question navigation
                        </label>

                        <label>
                            <input type="checkbox"
                                   name="shuffleQuestions">
                            Shuffle questions
                        </label>

                        <label>
                            <input type="checkbox"
                                   name="shuffleOptions">
                            Shuffle options
                        </label>

                    </div>

                </div>

            </div>

            <div class="actions">

                <button type="submit">
                    Create Exam
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