<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Create Exam - FoxBrain</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f6fa;
            color: #1f2937;
        }

        .exam-content {
            margin-left: 260px;
            padding: 30px;
            min-height: 100vh;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .page-header h1 {
            margin: 0;
            font-size: 28px;
            color: #111827;
        }

        .page-header p {
            margin: 6px 0 0;
            color: #6b7280;
        }

        .form-box {
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            max-width: 1100px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
        }

        .form-title {
            margin-top: 0;
            margin-bottom: 25px;
            font-size: 20px;
            color: #111827;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 20px;
        }

        .full {
            grid-column: 1 / -1;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-weight: 600;
            color: #374151;
        }

        .required {
            color: #dc2626;
        }

        input,
        select,
        textarea {
            width: 100%;
            padding: 11px 12px;
            border: 1px solid #d1d5db;
            border-radius: 7px;
            font-size: 14px;
            background: #ffffff;
            color: #111827;
            outline: none;
        }

        input:focus,
        select:focus,
        textarea:focus {
            border-color: #2563eb;
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.10);
        }

        textarea {
            min-height: 130px;
            resize: vertical;
        }

        .help-text {
            margin-top: 5px;
            font-size: 12px;
            color: #6b7280;
        }

        .settings-box {
            margin-top: 5px;
            padding: 18px;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            background: #f9fafb;
        }

        .checks {
            display: flex;
            flex-wrap: wrap;
            gap: 20px 30px;
        }

        .check-item {
            display: flex;
            align-items: center;
            gap: 8px;
            font-weight: normal;
            margin: 0;
            cursor: pointer;
        }

        .check-item input {
            width: auto;
            margin: 0;
        }

        .actions {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-top: 30px;
            padding-top: 22px;
            border-top: 1px solid #e5e7eb;
        }

        .btn {
            display: inline-block;
            padding: 11px 20px;
            border: none;
            border-radius: 7px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
        }

        .btn-primary {
            background: #2563eb;
            color: #ffffff;
        }

        .btn-primary:hover {
            background: #1d4ed8;
        }

        .btn-secondary {
            background: #e5e7eb;
            color: #374151;
        }

        .btn-secondary:hover {
            background: #d1d5db;
        }

        @media (max-width: 900px) {
            .exam-content {
                margin-left: 220px;
            }

            .grid {
                grid-template-columns: 1fr;
            }

            .full {
                grid-column: auto;
            }
        }

        @media (max-width: 700px) {
            .exam-content {
                margin-left: 70px;
                padding: 20px;
            }

            .page-header {
                display: block;
            }

            .form-box {
                padding: 20px;
            }

            .checks {
                flex-direction: column;
                gap: 12px;
            }
        }
    </style>
</head>

<body>

    <!-- Admin Sidebar -->
    <%@ include file="/includes/admin-sidebar.jsp" %>

    <main class="exam-content">

        <div class="page-header">
            <div>
                <h1>Create Exam</h1>
                <p>Create and schedule a new examination.</p>
            </div>
        </div>

        <div class="form-box">

            <h2 class="form-title">Exam Information</h2>

            <form method="post"
                  action="<%= contextPath %>/admin/exams">

                <input type="hidden"
                       name="action"
                       value="create">

                <div class="grid">

                    <!-- Batch -->
                    <div class="form-group">
                        <label for="batchId">
                            Batch ID <span class="required">*</span>
                        </label>

                        <input type="number"
                               id="batchId"
                               name="batchId"
                               min="1"
                               required>

                        <span class="help-text">
                            Enter the batch ID for this exam.
                        </span>
                    </div>

                    <!-- Exam Title -->
                    <div class="form-group">
                        <label for="title">
                            Exam Title <span class="required">*</span>
                        </label>

                        <input type="text"
                               id="title"
                               name="title"
                               maxlength="255"
                               placeholder="e.g. Java Programming Final Exam"
                               required>
                    </div>

                    <!-- Exam Type -->
                    <div class="form-group">
                        <label for="examType">
                            Exam Type <span class="required">*</span>
                        </label>

                        <select id="examType"
                                name="examType"
                                required>

                            <option value="">Select type</option>
                            <option value="QUIZ">Quiz</option>
                            <option value="MIDTERM">Midterm</option>
                            <option value="FINAL">Final</option>
                            <option value="PRACTICAL">Practical</option>
                            <option value="PROJECT">Project</option>
                            <option value="OTHER">Other</option>

                        </select>
                    </div>

                    <!-- Exam Mode -->
                    <div class="form-group">
                        <label for="examMode">
                            Exam Mode <span class="required">*</span>
                        </label>

                        <select id="examMode"
                                name="examMode"
                                required>

                            <option value="">Select mode</option>
                            <option value="ONLINE">Online</option>
                            <option value="OFFLINE">Offline</option>

                        </select>
                    </div>

                    <!-- Exam Date -->
                    <div class="form-group">
                        <label for="examDate">
                            Exam Date
                        </label>

                        <input type="date"
                               id="examDate"
                               name="examDate">
                    </div>

                    <!-- Room -->
                    <div class="form-group">
                        <label for="roomName">
                            Room
                        </label>

                        <input type="text"
                               id="roomName"
                               name="roomName"
                               maxlength="100"
                               placeholder="e.g. Lab 1 / Room 204">
                    </div>

                    <!-- Start Time -->
                    <div class="form-group">
                        <label for="startTime">
                            Start Time
                        </label>

                        <input type="time"
                               id="startTime"
                               name="startTime">
                    </div>

                    <!-- End Time -->
                    <div class="form-group">
                        <label for="endTime">
                            End Time
                        </label>

                        <input type="time"
                               id="endTime"
                               name="endTime">
                    </div>

                    <!-- Duration -->
                    <div class="form-group">
                        <label for="durationMinutes">
                            Duration (minutes)
                        </label>

                        <input type="number"
                               id="durationMinutes"
                               name="durationMinutes"
                               min="1"
                               placeholder="e.g. 120">
                    </div>

                    <!-- Total Marks -->
                    <div class="form-group">
                        <label for="totalMarks">
                            Total Marks <span class="required">*</span>
                        </label>

                        <input type="number"
                               id="totalMarks"
                               name="totalMarks"
                               min="0.01"
                               step="0.01"
                               placeholder="e.g. 100"
                               required>
                    </div>

                    <!-- Passing Marks -->
                    <div class="form-group">
                        <label for="passingMarks">
                            Passing Marks
                        </label>

                        <input type="number"
                               id="passingMarks"
                               name="passingMarks"
                               min="0"
                               step="0.01"
                               placeholder="e.g. 40">
                    </div>

                    <!-- Status -->
                    <div class="form-group">
                        <label for="status">
                            Status
                        </label>

                        <select id="status"
                                name="status">

                            <option value="DRAFT" selected>
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

                    <!-- Instructions -->
                    <div class="form-group full">

                        <label for="instructions">
                            Instructions
                        </label>

                        <textarea id="instructions"
                                  name="instructions"
                                  placeholder="Enter instructions for students..."></textarea>

                    </div>

                    <!-- Exam Settings -->
                    <div class="form-group full">

                        <label>
                            Exam Settings
                        </label>

                        <div class="settings-box">

                            <div class="checks">

                                <label class="check-item">
                                    <input type="checkbox"
                                           name="allowNavigation"
                                           value="true"
                                           checked>

                                    <span>
                                        Allow question navigation
                                    </span>
                                </label>

                                <label class="check-item">
                                    <input type="checkbox"
                                           name="shuffleQuestions"
                                           value="true">

                                    <span>
                                        Shuffle questions
                                    </span>
                                </label>

                                <label class="check-item">
                                    <input type="checkbox"
                                           name="shuffleOptions"
                                           value="true">

                                    <span>
                                        Shuffle options
                                    </span>
                                </label>

                            </div>

                        </div>

                    </div>

                </div>

                <!-- Actions -->
                <div class="actions">

                    <button type="submit"
                            class="btn btn-primary">
                        Create Exam
                    </button>

                    <a href="<%= contextPath %>/admin/exams"
                       class="btn btn-secondary">
                        Cancel
                    </a>

                </div>

            </form>

        </div>

    </main>

</body>
</html>