<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String currentURI = request.getRequestURI();

    String contextPath = request.getContextPath();

    boolean dashboardActive =
        currentURI.endsWith("/admin/dashboard.jsp");

    boolean studentsActive =
        currentURI.contains("/admin/students");

    boolean teachersActive =
        currentURI.contains("/admin/teachers");

    boolean coursesActive =
        currentURI.contains("/admin/courses");

    boolean batchesActive =
        currentURI.contains("/admin/batches");

    boolean admissionsActive =
        currentURI.contains("/admin/admissions");

    boolean enrollmentsActive =
        currentURI.contains("/admin/enrollments");

    boolean attendanceActive =
        currentURI.contains("/admin/attendance");

    boolean assignmentsActive =
        currentURI.contains("/admin/assignments");

    boolean examsActive =
        currentURI.contains("/admin/exams");

    boolean resultsActive =
        currentURI.contains("/admin/results");

    boolean feesActive =
        currentURI.contains("/admin/fees");

    boolean paymentsActive =
        currentURI.contains("/admin/payments");

    boolean certificatesActive =
        currentURI.contains("/admin/certificates");

    boolean announcementsActive =
        currentURI.contains("/admin/announcements");

    boolean reportsActive =
        currentURI.contains("/admin/reports");

    boolean settingsActive =
        currentURI.contains("/admin/settings");
%>

<aside class="admin-sidebar" id="adminSidebar">

    <!-- BRAND -->

    <div class="sidebar-brand">

        <div class="brand-logo">
            🦊
        </div>

        <div class="brand-text">
            <div class="brand-name">
                FoxBrain
            </div>

            <div class="brand-subtitle">
                Institute Admin
            </div>
        </div>

    </div>


    <!-- NAVIGATION -->

    <nav class="sidebar-navigation">

        <div class="nav-section-title">
            MAIN
        </div>


        <!-- DASHBOARD -->

        <a
            href="<%= contextPath %>/admin/dashboard.jsp"
            class="sidebar-link <%= dashboardActive ? "active" : "" %>">

            <span class="sidebar-icon">▣</span>

            <span class="sidebar-label">
                Dashboard
            </span>

        </a>


        <div class="nav-section-title">
            ACADEMIC
        </div>


        <!-- STUDENTS -->

        <a
            href="<%= contextPath %>/admin/students"
            class="sidebar-link <%= studentsActive ? "active" : "" %>">

            <span class="sidebar-icon">👨‍🎓</span>

            <span class="sidebar-label">
                Students
            </span>

        </a>


        <!-- TEACHERS -->

        <a
            href="<%= contextPath %>/admin/teachers"
            class="sidebar-link <%= teachersActive ? "active" : "" %>">

            <span class="sidebar-icon">👨‍🏫</span>

            <span class="sidebar-label">
                Teachers
            </span>

        </a>


        <!-- COURSES -->

        <a
            href="<%= contextPath %>/admin/courses"
            class="sidebar-link <%= coursesActive ? "active" : "" %>">

            <span class="sidebar-icon">📚</span>

            <span class="sidebar-label">
                Courses
            </span>

        </a>


        <!-- BATCHES -->

        <a
            href="<%= contextPath %>/admin/batches"
            class="sidebar-link <%= batchesActive ? "active" : "" %>">

            <span class="sidebar-icon">🗂</span>

            <span class="sidebar-label">
                Batches
            </span>

        </a>


        <div class="nav-section-title">
            ADMISSIONS
        </div>


        <!-- ADMISSIONS -->

        <a
            href="<%= contextPath %>/admin/admissions"
            class="sidebar-link <%= admissionsActive ? "active" : "" %>">

            <span class="sidebar-icon">📝</span>

            <span class="sidebar-label">
                Admissions
            </span>

        </a>


        <!-- ENROLLMENTS -->

        <a
            href="<%= contextPath %>/admin/enrollments"
            class="sidebar-link <%= enrollmentsActive ? "active" : "" %>">

            <span class="sidebar-icon">📋</span>

            <span class="sidebar-label">
                Enrollments
            </span>

        </a>


        <div class="nav-section-title">
            LEARNING
        </div>


        <!-- ATTENDANCE -->

        <a
            href="<%= contextPath %>/admin/attendance"
            class="sidebar-link <%= attendanceActive ? "active" : "" %>">

            <span class="sidebar-icon">📅</span>

            <span class="sidebar-label">
                Attendance
            </span>

        </a>


        <!-- ASSIGNMENTS -->

        <a
            href="<%= contextPath %>/admin/assignments"
            class="sidebar-link <%= assignmentsActive ? "active" : "" %>">

            <span class="sidebar-icon">📖</span>

            <span class="sidebar-label">
                Assignments
            </span>

        </a>


        <!-- EXAMS -->

        <a
            href="<%= contextPath %>/admin/exams"
            class="sidebar-link <%= examsActive ? "active" : "" %>">

            <span class="sidebar-icon">📝</span>

            <span class="sidebar-label">
                Exams
            </span>

        </a>


        <!-- RESULTS -->

        <a
            href="<%= contextPath %>/admin/results"
            class="sidebar-link <%= resultsActive ? "active" : "" %>">

            <span class="sidebar-icon">🏆</span>

            <span class="sidebar-label">
                Results
            </span>

        </a>


        <div class="nav-section-title">
            FINANCE
        </div>


        <!-- FEES -->

        <a
            href="<%= contextPath %>/admin/fees"
            class="sidebar-link <%= feesActive ? "active" : "" %>">

            <span class="sidebar-icon">💰</span>

            <span class="sidebar-label">
                Fees
            </span>

        </a>


        <!-- PAYMENTS -->

        <a
            href="<%= contextPath %>/admin/payments"
            class="sidebar-link <%= paymentsActive ? "active" : "" %>">

            <span class="sidebar-icon">💳</span>

            <span class="sidebar-label">
                Payments
            </span>

        </a>


        <div class="nav-section-title">
            MANAGEMENT
        </div>


        <!-- CERTIFICATES -->

        <a
            href="<%= contextPath %>/admin/certificates"
            class="sidebar-link <%= certificatesActive ? "active" : "" %>">

            <span class="sidebar-icon">🎓</span>

            <span class="sidebar-label">
                Certificates
            </span>

        </a>


        <!-- ANNOUNCEMENTS -->

        <a
            href="<%= contextPath %>/admin/announcements"
            class="sidebar-link <%= announcementsActive ? "active" : "" %>">

            <span class="sidebar-icon">📢</span>

            <span class="sidebar-label">
                Announcements
            </span>

        </a>


        <!-- REPORTS -->

        <a
            href="<%= contextPath %>/admin/reports"
            class="sidebar-link <%= reportsActive ? "active" : "" %>">

            <span class="sidebar-icon">📊</span>

            <span class="sidebar-label">
                Reports
            </span>

        </a>


        <!-- SETTINGS -->

        <a
            href="<%= contextPath %>/admin/settings"
            class="sidebar-link <%= settingsActive ? "active" : "" %>">

            <span class="sidebar-icon">⚙</span>

            <span class="sidebar-label">
                Settings
            </span>

        </a>

    </nav>


    <!-- SIDEBAR FOOTER -->

    <div class="sidebar-footer">

        <a
            href="<%= contextPath %>/logout"
            class="sidebar-link logout-link">

            <span class="sidebar-icon">↪</span>

            <span class="sidebar-label">
                Logout
            </span>

        </a>

    </div>

</aside>