<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Exam - FoxBrain</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/admin.css">
</head>

<body>

<%@ include file="/includes/admin-sidebar.jsp" %>
<%@ include file="/includes/admin-header.jsp" %>

<div class="admin-content">

    <div class="page-header">
        <div>
            <h1>Create Exam</h1>
            <p>Create an online or offline examination.</p>
        </div>

        <a href="<%= request.getContextPath() %>/admin/exams?action=list"
           class="btn btn-secondary">
            Back
        </a>
    </div>

    <div class="card">

        <form method="post"
              action="<%= request.getContextPath() %>/admin/exams">

            <input type="hidden"
                   name="action"
                   value="create">

            <div class="form-grid">

                <div class="form-group">

                    <label>Batch ID *</label>

                    <input type="number"
                           name="batchId"
                           min="1"
                           required>

                    <small>Enter the batch ID for this exam.</small>

                </div>

                <div class="form-group">

                    <label>Exam Title *</label>

                    <input type="text"
                           name="title"
                           maxlength="255"
                           required
                           placeholder="e.g. Java Mid Term Examination">

                </div>

                <div class="form-group">

                    <label>Exam Type *</label>

                    <select name="examType" required>

                        <option value="">Select Type</option>
                        <option value="QUIZ">Quiz</option>
                        <option value="MIDTERM">Midterm</option>
                        <option value="FINAL">Final</option>
                        <option value="PRACTICAL">Practical</option>
                        <option value="PROJECT">Project</option>
                        <option value="OTHER">Other</option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Exam Mode *</label>

                    <select name="examMode"
                            id="examMode"
                            required
                            onchange="toggleRoom()">

                        <option value="">Select Mode</option>
                        <option value="ONLINE">Online</option>
                        <option value="OFFLINE">Offline</option>

                    </select>

                </div>

                <div class="form-group">

                    <label>Exam Date</label>

                    <input type="date"
                           name="examDate">

                </div>

                <div class="form-group">

                    <label>Start Time</label>

                    <input type="time"
                           name="startTime">

                </div>

                <div class="form-group">

                    <label>End Time</label>

                    <input type="time"
                           name="endTime">

                </div>

                <div class="form-group">

                    <label>Duration (Minutes)</label>

                    <input type="number"
                           name="durationMinutes"
                           min="1"
                           placeholder="e.g. 120">

                </div>

                <div class="form-group">

                    <label>Total Marks *</label>

                    <input type="number"
                           name="totalMarks"
                           min="0.01"
                           step="0.01"
                           required>

                </div>

                <div class="form-group">

                    <label>Passing Marks</label>

                    <input type="number"
                           name="passingMarks"
                           min="0"
                           step="0.01">

                </div>

                <div class="form-group">

                    <label>Room Name</label>

                    <input type="text"
                           name="roomName"
                           id="roomName"
                           maxlength="100"
                           placeholder="e.g. Lab 2">

                </div>

                <div class="form-group">

                    <label>Status *</label>

                    <select name="status" required>

                        <option value="DRAFT">Draft</option>
                        <option value="SCHEDULED">Scheduled</option>
                        <option value="COMPLETED">Completed</option>
                        <option value="CANCELLED">Cancelled</option>

                    </select>

                </div>

            </div>

            <div class="form-group">

                <label>Instructions</label>

                <textarea name="instructions"
                          rows="6"
                          placeholder="Enter exam instructions..."></textarea>

            </div>

            <div class="form-group">

                <label>Online Exam Settings</label>

                <div>
                    <label>
                        <input type="checkbox"
                               name="allowNavigation"
                               checked>
                        Allow question navigation
                    </label>
                </div>

                <div>
                    <label>
                        <input type="checkbox"
                               name="shuffleQuestions">
                        Shuffle questions
                    </label>
                </div>

                <div>
                    <label>
                        <input type="checkbox"
                               name="shuffleOptions">
                        Shuffle options
                    </label>
                </div>

            </div>

            <button type="submit"
                    class="btn btn-primary">
                Create Exam
            </button>

            <a href="<%= request.getContextPath() %>/admin/exams?action=list"
               class="btn btn-secondary">
                Cancel
            </a>

        </form>

    </div>

</div>

<script>
function toggleRoom() {

    const mode =
        document.getElementById("examMode").value;

    const room =
        document.getElementById("roomName");

    if (mode === "OFFLINE") {
        room.required = true;
    } else {
        room.required = false;
    }
}
</script>

</body>
</html>