<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String sidebarCurrentURI = request.getRequestURI();
    String sidebarContextPath = request.getContextPath();

    boolean dashboardActive =
            sidebarCurrentURI.contains("/admin/dashboard");

    boolean studentsActive =
            sidebarCurrentURI.contains("/admin/students");

    boolean teachersActive =
            sidebarCurrentURI.contains("/admin/teachers");

    boolean categoriesActive =
            sidebarCurrentURI.contains("/admin/course-categories");

    boolean coursesActive =
            sidebarCurrentURI.contains("/admin/courses");

    boolean batchesActive =
            sidebarCurrentURI.contains("/admin/batches");

    boolean admissionsActive =
            sidebarCurrentURI.contains("/admin/admissions");

    boolean enrollmentsActive =
            sidebarCurrentURI.contains("/admin/enrollments");

    boolean attendanceActive =
            sidebarCurrentURI.contains("/admin/attendance");

    boolean assignmentsActive =
            sidebarCurrentURI.contains("/admin/assignments");

    boolean examsActive =
            sidebarCurrentURI.contains("/admin/exams");

    boolean resultsActive =
            sidebarCurrentURI.contains("/admin/exam-results");

    boolean feesActive =
            sidebarCurrentURI.contains("/admin/fees");

    boolean paymentsActive =
            sidebarCurrentURI.contains("/admin/payments");

    boolean certificatesActive =
            sidebarCurrentURI.contains("/admin/certificates");

    boolean announcementsActive =
            sidebarCurrentURI.contains("/admin/announcements");

    boolean notificationsActive =
            sidebarCurrentURI.contains("/admin/notifications");

    boolean enquiriesActive =
            sidebarCurrentURI.contains("/admin/enquiries");

    boolean reportsActive =
            sidebarCurrentURI.contains("/admin/reports");

    boolean settingsActive =
            sidebarCurrentURI.contains("/admin/settings");
%>

<style>

    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
        background: #f5f7fb;
    }

    .admin-sidebar {
        position: fixed;
        left: 0;
        top: 0;
        bottom: 0;

        width: 260px;

        background: #111827;
        color: white;

        display: flex;
        flex-direction: column;

        overflow-y: auto;

        z-index: 1000;

        box-shadow: 4px 0 15px rgba(0, 0, 0, 0.12);
    }

    .sidebar-brand {
        height: 75px;

        display: flex;
        align-items: center;

        padding: 15px 20px;

        border-bottom: 1px solid #273244;
    }

    .sidebar-brand-link {
        display: flex;
        align-items: center;

        gap: 12px;

        width: 100%;

        color: white;
        text-decoration: none;
    }

    .sidebar-logo {
        width: 42px;
        height: 42px;

        border-radius: 10px;

        background: #2563eb;

        display: flex;
        align-items: center;
        justify-content: center;

        font-size: 23px;
    }

    .sidebar-brand-text {
        display: flex;
        flex-direction: column;
    }

    .sidebar-brand-text strong {
        font-size: 20px;
        line-height: 1.2;
    }

    .sidebar-brand-text span {
        color: #9ca3af;
        font-size: 12px;
        margin-top: 3px;
    }

    .sidebar-nav {
        flex: 1;

        padding: 15px 12px;
    }

    .sidebar-section {
        margin-bottom: 20px;
    }

    .sidebar-section-title {
        color: #6b7280;

        font-size: 11px;
        font-weight: bold;

        letter-spacing: 1px;

        padding: 8px 12px;
    }

    .sidebar-link {
        display: flex;
        align-items: center;

        gap: 12px;

        width: 100%;

        padding: 11px 13px;

        margin-bottom: 4px;

        border-radius: 8px;

        color: #d1d5db;

        text-decoration: none;

        font-size: 14px;

        transition: 0.2s ease;
    }

    .sidebar-link:hover {
        background: #1f2937;
        color: white;
    }

    .sidebar-link.active {
        background: #2563eb;
        color: white;

        font-weight: 600;

        box-shadow: 0 4px 10px rgba(37, 99, 235, 0.25);
    }

    .sidebar-icon {
        width: 25px;

        text-align: center;

        font-size: 17px;

        flex-shrink: 0;
    }

    .sidebar-link-text {
        white-space: nowrap;
    }

    .sidebar-footer {
        padding: 12px;

        border-top: 1px solid #273244;
    }

    .sidebar-logout {
        color: #fca5a5;
    }

    .sidebar-logout:hover {
        background: #3f1d1d;
        color: #fecaca;
    }


    /* MAIN CONTENT */

    .admin-main {
        margin-left: 260px;

        min-height: 100vh;

        padding: 30px;
    }


    /* MOBILE */

    @media (max-width: 900px) {

        .admin-sidebar {
            width: 220px;
        }

        .admin-main {
            margin-left: 220px;
        }
    }

    @media (max-width: 700px) {

        .admin-sidebar {
            width: 70px;
        }

        .sidebar-brand-text,
        .sidebar-section-title,
        .sidebar-link-text {
            display: none;
        }

        .sidebar-brand {
            justify-content: center;
            padding: 10px;
        }

        .sidebar-brand-link {
            justify-content: center;
        }

        .sidebar-link {
            justify-content: center;
            padding: 12px;
        }

        .sidebar-icon {
            font-size: 19px;
        }

        .admin-main {
            margin-left: 70px;
            padding: 20px;
        }
    }

</style>


<aside class="admin-sidebar" id="adminSidebar">

    <!-- BRAND -->

    <div class="sidebar-brand">

        <a href="<%= sidebarContextPath %>/admin/dashboard.jsp"
           class="sidebar-brand-link">

            <div class="sidebar-logo">
                🦊
            </div>

            <div class="sidebar-brand-text">
                <strong>FoxBrain</strong>
                <span>Institute Admin</span>
            </div>

        </a>

    </div>


    <!-- NAVIGATION -->

    <nav class="sidebar-nav">


        <!-- MAIN -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                MAIN
            </div>

            <a href="<%= sidebarContextPath %>/admin/dashboard.jsp"
               class="sidebar-link <%= dashboardActive ? "active" : "" %>">

                <span class="sidebar-icon">🏠</span>
                <span class="sidebar-link-text">Dashboard</span>

            </a>

        </div>


        <!-- ACADEMIC -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                ACADEMIC
            </div>


            <a href="<%= sidebarContextPath %>/admin/students"
               class="sidebar-link <%= studentsActive ? "active" : "" %>">

                <span class="sidebar-icon">👨‍🎓</span>
                <span class="sidebar-link-text">Students</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/teachers"
               class="sidebar-link <%= teachersActive ? "active" : "" %>">

                <span class="sidebar-icon">👨‍🏫</span>
                <span class="sidebar-link-text">Teachers</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/course-categories"
               class="sidebar-link <%= categoriesActive ? "active" : "" %>">

                <span class="sidebar-icon">📚</span>
                <span class="sidebar-link-text">Course Categories</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/courses"
               class="sidebar-link <%= coursesActive ? "active" : "" %>">

                <span class="sidebar-icon">📖</span>
                <span class="sidebar-link-text">Courses</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/batches"
               class="sidebar-link <%= batchesActive ? "active" : "" %>">

                <span class="sidebar-icon">🗂️</span>
                <span class="sidebar-link-text">Batches</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/attendance"
               class="sidebar-link <%= attendanceActive ? "active" : "" %>">

                <span class="sidebar-icon">📝</span>
                <span class="sidebar-link-text">Attendance</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/assignments"
               class="sidebar-link <%= assignmentsActive ? "active" : "" %>">

                <span class="sidebar-icon">📋</span>
                <span class="sidebar-link-text">Assignments</span>

            </a>


            <!-- EXAMS -->

            <a href="<%= sidebarContextPath %>/admin/exams"
               class="sidebar-link <%= examsActive ? "active" : "" %>">

                <span class="sidebar-icon">📝</span>
                <span class="sidebar-link-text">Exams</span>

            </a>


            <!-- RESULTS -->

            <a href="<%= sidebarContextPath %>/admin/exam-results"
               class="sidebar-link <%= resultsActive ? "active" : "" %>">

                <span class="sidebar-icon">📊</span>
                <span class="sidebar-link-text">Results</span>

            </a>

        </div>


        <!-- ADMISSIONS -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                ADMISSIONS
            </div>


            <a href="<%= sidebarContextPath %>/admin/admissions"
               class="sidebar-link <%= admissionsActive ? "active" : "" %>">

                <span class="sidebar-icon">📨</span>
                <span class="sidebar-link-text">Admissions</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/enrollments"
               class="sidebar-link <%= enrollmentsActive ? "active" : "" %>">

                <span class="sidebar-icon">🎓</span>
                <span class="sidebar-link-text">Enrollments</span>

            </a>

        </div>


        <!-- LEARNING -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                LEARNING
            </div>

            <a href="<%= sidebarContextPath %>/admin/certificates"
               class="sidebar-link <%= certificatesActive ? "active" : "" %>">

                <span class="sidebar-icon">🏆</span>
                <span class="sidebar-link-text">Certificates</span>

            </a>

        </div>


        <!-- FINANCE -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                FINANCE
            </div>


            <a href="<%= sidebarContextPath %>/admin/fees"
               class="sidebar-link <%= feesActive ? "active" : "" %>">

                <span class="sidebar-icon">💰</span>
                <span class="sidebar-link-text">Fees</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/payments"
               class="sidebar-link <%= paymentsActive ? "active" : "" %>">

                <span class="sidebar-icon">💳</span>
                <span class="sidebar-link-text">Payments</span>

            </a>

        </div>


        <!-- MANAGEMENT -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                MANAGEMENT
            </div>


            <a href="<%= sidebarContextPath %>/admin/announcements"
               class="sidebar-link <%= announcementsActive ? "active" : "" %>">

                <span class="sidebar-icon">📢</span>
                <span class="sidebar-link-text">Announcements</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/notifications"
               class="sidebar-link <%= notificationsActive ? "active" : "" %>">

                <span class="sidebar-icon">🔔</span>
                <span class="sidebar-link-text">Notifications</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/enquiries"
               class="sidebar-link <%= enquiriesActive ? "active" : "" %>">

                <span class="sidebar-icon">💬</span>
                <span class="sidebar-link-text">Enquiries</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/reports"
               class="sidebar-link <%= reportsActive ? "active" : "" %>">

                <span class="sidebar-icon">📈</span>
                <span class="sidebar-link-text">Reports</span>

            </a>


            <a href="<%= sidebarContextPath %>/admin/settings"
               class="sidebar-link <%= settingsActive ? "active" : "" %>">

                <span class="sidebar-icon">⚙️</span>
                <span class="sidebar-link-text">Settings</span>

            </a>

        </div>

    </nav>


    <!-- FOOTER -->

    <div class="sidebar-footer">

        <a href="<%= sidebarContextPath %>/logout"
           class="sidebar-link sidebar-logout">

            <span class="sidebar-icon">🚪</span>
            <span class="sidebar-link-text">Logout</span>

        </a>

    </div>

</aside>