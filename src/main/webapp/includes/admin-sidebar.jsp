```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String contextPath = request.getContextPath();
    String currentURI = request.getRequestURI();

    boolean dashboardActive =
        currentURI.contains("/admin/dashboard.jsp");

    boolean studentsActive =
        currentURI.contains("/admin/students");

    boolean teachersActive =
        currentURI.contains("/admin/teachers");

    boolean categoriesActive =
        currentURI.contains("/admin/course-categories");

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
    	    currentURI.contains("/admin/exam-results");

    boolean feesActive =
        currentURI.contains("/admin/fees");

    boolean paymentsActive =
        currentURI.contains("/admin/payments");

    boolean certificatesActive =
        currentURI.contains("/admin/certificates");

    boolean announcementsActive =
        currentURI.contains("/admin/announcements");

    boolean notificationsActive =
        currentURI.contains("/admin/notifications");

    boolean enquiriesActive =
        currentURI.contains("/admin/enquiries");

    boolean reportsActive =
        currentURI.contains("/admin/reports");

    boolean settingsActive =
        currentURI.contains("/admin/settings");
%>

<aside class="admin-sidebar" id="adminSidebar">

    <!-- =====================================================
         SIDEBAR BRAND
         ===================================================== -->

    <div class="sidebar-brand">

        <a href="<%= contextPath %>/admin/dashboard.jsp"
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


    <!-- =====================================================
         SIDEBAR NAVIGATION
         ===================================================== -->

    <nav class="sidebar-nav">


        <!-- =================================================
             MAIN
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                MAIN
            </div>

            <a href="<%= contextPath %>/admin/dashboard.jsp"
               class="sidebar-link <%= dashboardActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🏠
                </span>

                <span class="sidebar-link-text">
                    Dashboard
                </span>

            </a>

        </div>


        <!-- =================================================
             ACADEMIC
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                ACADEMIC
            </div>


            <!-- Students -->

            <a href="<%= contextPath %>/admin/students"
               class="sidebar-link <%= studentsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    👨‍🎓
                </span>

                <span class="sidebar-link-text">
                    Students
                </span>

            </a>


            <!-- Teachers -->

            <a href="<%= contextPath %>/admin/teachers"
               class="sidebar-link <%= teachersActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    👨‍🏫
                </span>

                <span class="sidebar-link-text">
                    Teachers
                </span>

            </a>


            <!-- Course Categories -->

            <a href="<%= contextPath %>/admin/course-categories"
               class="sidebar-link <%= categoriesActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📚
                </span>

                <span class="sidebar-link-text">
                    Course Categories
                </span>

            </a>


            <!-- Courses -->

            <a href="<%= contextPath %>/admin/courses"
   class="sidebar-link <%= coursesActive ? "active" : "" %>">

    <span class="sidebar-icon">📖</span>
    <span class="sidebar-link-text">Courses</span>

</a>

            <!-- Batches -->

            <a href="<%= contextPath %>/admin/batches"
               class="sidebar-link <%= batchesActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🗂️
                </span>

                <span class="sidebar-link-text">
                    Batches
                </span>

            </a>


            <!-- Attendance -->

            <a href="<%= contextPath %>/admin/attendance"
               class="sidebar-link <%= attendanceActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📝
                </span>

                <span class="sidebar-link-text">
                    Attendance
                </span>

            </a>


            <!-- Assignments -->

            <a href="<%= contextPath %>/admin/assignments"
               class="sidebar-link <%= assignmentsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📋
                </span>

                <span class="sidebar-link-text">
                    Assignments
                </span>

            </a>


            <!-- Exams -->

            <a href="<%= contextPath %>/admin/exams"
               class="sidebar-link <%= examsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🧾
                </span>

                <span class="sidebar-link-text">
                    Exams
                </span>

            </a>


            <!-- Results -->
<!-- Results -->

<a href="<%= contextPath %>/admin/exam-results"
   class="sidebar-link <%= resultsActive ? "active" : "" %>">

    <span class="sidebar-icon">
        📊
    </span>

    <span class="sidebar-link-text">
        Results
    </span>

</a>

        </div>


        <!-- =================================================
             ADMISSIONS
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                ADMISSIONS
            </div>


            <!-- Admissions -->

            <a href="<%= contextPath %>/admin/admissions"
               class="sidebar-link <%= admissionsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📨
                </span>

                <span class="sidebar-link-text">
                    Admissions
                </span>

            </a>


            <!-- Enrollments -->

            <a href="<%= contextPath %>/admin/enrollments"
               class="sidebar-link <%= enrollmentsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🎓
                </span>

                <span class="sidebar-link-text">
                    Enrollments
                </span>

            </a>

        </div>


        <!-- =================================================
             LEARNING
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                LEARNING
            </div>


            <!-- Certificates -->

            <a href="<%= contextPath %>/admin/certificates"
               class="sidebar-link <%= certificatesActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🏆
                </span>

                <span class="sidebar-link-text">
                    Certificates
                </span>

            </a>

        </div>


        <!-- =================================================
             FINANCE
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                FINANCE
            </div>


            <!-- Fees -->

            <a href="<%= contextPath %>/admin/fees"
               class="sidebar-link <%= feesActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    💰
                </span>

                <span class="sidebar-link-text">
                    Fees
                </span>

            </a>


            <!-- Payments -->

            <a href="<%= contextPath %>/admin/payments"
               class="sidebar-link <%= paymentsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    💳
                </span>

                <span class="sidebar-link-text">
                    Payments
                </span>

            </a>

        </div>


        <!-- =================================================
             MANAGEMENT
             ================================================= -->

        <div class="sidebar-section">

            <div class="sidebar-section-title">
                MANAGEMENT
            </div>


            <!-- Announcements -->

            <a href="<%= contextPath %>/admin/announcements"
               class="sidebar-link <%= announcementsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📢
                </span>

                <span class="sidebar-link-text">
                    Announcements
                </span>

            </a>


            <!-- Notifications -->

            <a href="<%= contextPath %>/admin/notifications"
               class="sidebar-link <%= notificationsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    🔔
                </span>

                <span class="sidebar-link-text">
                    Notifications
                </span>

            </a>


            <!-- Enquiries -->

            <a href="<%= contextPath %>/admin/enquiries"
               class="sidebar-link <%= enquiriesActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    💬
                </span>

                <span class="sidebar-link-text">
                    Enquiries
                </span>

            </a>


            <!-- Reports -->

            <a href="<%= contextPath %>/admin/reports"
               class="sidebar-link <%= reportsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    📈
                </span>

                <span class="sidebar-link-text">
                    Reports
                </span>

            </a>


            <!-- Settings -->

            <a href="<%= contextPath %>/admin/settings"
               class="sidebar-link <%= settingsActive ? "active" : "" %>">

                <span class="sidebar-icon">
                    ⚙️
                </span>

                <span class="sidebar-link-text">
                    Settings
                </span>

            </a>

        </div>

    </nav>


    <!-- =====================================================
         SIDEBAR FOOTER
         ===================================================== -->

    <div class="sidebar-footer">

        <a href="<%= contextPath %>/logout"
           class="sidebar-link sidebar-logout">

            <span class="sidebar-icon">
                🚪
            </span>

            <span class="sidebar-link-text">
                Logout
            </span>

        </a>

    </div>

</aside>
