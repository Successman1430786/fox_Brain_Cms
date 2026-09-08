<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.foxbrain.model.User" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {
        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Admin Dashboard | FoxBrain Institute</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        body {
            background: #f5f7fb;
            color: #1e293b;
        }

        .layout {
            display: flex;
            min-height: 100vh;
        }

        /* SIDEBAR */

        .sidebar {
            width: 250px;
            background: #172554;
            color: white;
            padding: 25px 15px;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            overflow-y: auto;
        }

        .brand {
            text-align: center;
            margin-bottom: 30px;
        }

        .brand h1 {
            font-size: 24px;
        }

        .brand p {
            font-size: 12px;
            margin-top: 5px;
            opacity: 0.7;
        }

        .menu-title {
            font-size: 11px;
            text-transform: uppercase;
            opacity: 0.55;
            margin: 20px 10px 8px;
        }

        .menu a {
            display: block;
            color: #e2e8f0;
            text-decoration: none;
            padding: 11px 12px;
            border-radius: 7px;
            margin-bottom: 4px;
            font-size: 14px;
        }

        .menu a:hover,
        .menu a.active {
            background: #2563eb;
            color: white;
        }

        /* MAIN */

        .main {
            margin-left: 250px;
            width: calc(100% - 250px);
        }

        .topbar {
            height: 70px;
            background: white;
            border-bottom: 1px solid #e2e8f0;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
        }

        .topbar h2 {
            font-size: 20px;
        }

        .user-area {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-name {
            font-size: 14px;
            font-weight: 600;
        }

        .logout {
            text-decoration: none;
            color: #dc2626;
            font-size: 14px;
            font-weight: 600;
        }

        .content {
            padding: 30px;
        }

        .welcome {
            margin-bottom: 25px;
        }

        .welcome h1 {
            font-size: 25px;
            margin-bottom: 6px;
        }

        .welcome p {
            color: #64748b;
            font-size: 14px;
        }

        /* CARDS */

        .cards {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(210px, 1fr));
            gap: 20px;
        }

        .card {
            background: white;
            padding: 22px;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
        }

        .card-title {
            color: #64748b;
            font-size: 13px;
            margin-bottom: 12px;
        }

        .card-value {
            font-size: 28px;
            font-weight: 700;
            color: #172554;
        }

        /* QUICK ACTIONS */

        .section {
            margin-top: 30px;
        }

        .section h2 {
            font-size: 19px;
            margin-bottom: 15px;
        }

        .actions {
            display: grid;
            grid-template-columns:
                repeat(auto-fit, minmax(180px, 1fr));
            gap: 15px;
        }

        .action {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            padding: 18px;
            text-decoration: none;
            color: #1e293b;
        }

        .action:hover {
            border-color: #2563eb;
        }

        .action strong {
            display: block;
            margin-bottom: 5px;
        }

        .action span {
            font-size: 12px;
            color: #64748b;
        }

        /* MOBILE */

        @media (max-width: 800px) {

            .sidebar {
                width: 210px;
            }

            .main {
                margin-left: 210px;
                width: calc(100% - 210px);
            }

            .topbar {
                padding: 0 18px;
            }

            .content {
                padding: 20px;
            }
        }

    </style>

</head>

<body>

<div class="layout">

    <!-- SIDEBAR -->

    <aside class="sidebar">

        <div class="brand">
            <h1>FoxBrain</h1>
            <p>Institute Management</p>
        </div>

        <div class="menu">

            <div class="menu-title">
                Main
            </div>

            <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
               class="active">
                Dashboard
            </a>

            <div class="menu-title">
                Academic
            </div>

          <a href="<%= request.getContextPath() %>/admin/students">
    Students
</a>

<a href="<%= request.getContextPath() %>/admin/teachers">
    Teachers
</a>

<a href="<%= request.getContextPath() %>/admin/courses">
    Courses
</a>

            <a href="${pageContext.request.contextPath}/admin/batches/">
                Batches
            </a>

            <a href="${pageContext.request.contextPath}/admin/enrollments/">
                Enrollments
            </a>

            <a href="${pageContext.request.contextPath}/admin/attendance/">
                Attendance
            </a>

            <a href="${pageContext.request.contextPath}/admin/assignments/">
                Assignments
            </a>

            <a href="${pageContext.request.contextPath}/admin/exams/">
                Exams
            </a>

            <a href="${pageContext.request.contextPath}/admin/results/">
                Results
            </a>

            <div class="menu-title">
                Finance
            </div>

            <a href="${pageContext.request.contextPath}/admin/fees/">
                Fees
            </a>

            <a href="${pageContext.request.contextPath}/admin/payments/">
                Payments
            </a>

            <div class="menu-title">
                Communication
            </div>

            <a href="${pageContext.request.contextPath}/admin/announcements/">
                Announcements
            </a>

            <a href="${pageContext.request.contextPath}/admin/notifications/">
                Notifications
            </a>

            <a href="${pageContext.request.contextPath}/admin/enquiries/">
                Enquiries
            </a>

            <div class="menu-title">
                System
            </div>

            <a href="${pageContext.request.contextPath}/admin/certificates/">
                Certificates
            </a>

            <a href="${pageContext.request.contextPath}/admin/reports/">
                Reports
            </a>

            <a href="${pageContext.request.contextPath}/admin/settings/">
                Settings
            </a>

            <a href="${pageContext.request.contextPath}/admin/website/">
                Website Content
            </a>

        </div>

    </aside>


    <!-- MAIN -->

    <main class="main">

        <header class="topbar">

            <h2>Admin Dashboard</h2>

            <div class="user-area">

                <span class="user-name">
                    Welcome,
                    <%= loggedInUser.getFirstName() %>
                </span>

                <a class="logout"
                   href="${pageContext.request.contextPath}/logout">
                    Logout
                </a>

            </div>

        </header>


        <section class="content">

            <div class="welcome">

                <h1>
                    Welcome to FoxBrain Institute
                </h1>

                <p>
                    Manage students, teachers, courses,
                    academics, finance and institute operations.
                </p>

            </div>


            <!-- SUMMARY CARDS -->

            <div class="cards">

                <div class="card">
                    <div class="card-title">
                        Students
                    </div>

                    <div class="card-value">
                        —
                    </div>
                </div>


                <div class="card">
                    <div class="card-title">
                        Teachers
                    </div>

                    <div class="card-value">
                        —
                    </div>
                </div>


                <div class="card">
                    <div class="card-title">
                        Courses
                    </div>

                    <div class="card-value">
                        —
                    </div>
                </div>


                <div class="card">
                    <div class="card-title">
                        Active Batches
                    </div>

                    <div class="card-value">
                        —
                    </div>
                </div>

            </div>


            <!-- QUICK ACTIONS -->

            <div class="section">

                <h2>
                    Quick Actions
                </h2>

                <div class="actions">

                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/students/">

                        <strong>
                            Manage Students
                        </strong>

                        <span>
                            Add and manage student records
                        </span>

                    </a>


                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/teachers/">

                        <strong>
                            Manage Teachers
                        </strong>

                        <span>
                            Manage faculty and assignments
                        </span>

                    </a>


                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/courses/">

                        <strong>
                            Manage Courses
                        </strong>

                        <span>
                            Configure institute courses
                        </span>

                    </a>


                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/batches/">

                        <strong>
                            Manage Batches
                        </strong>

                        <span>
                            Create and manage batches
                        </span>

                    </a>


                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/enquiries/">

                        <strong>
                            View Enquiries
                        </strong>

                        <span>
                            Manage website enquiries
                        </span>

                    </a>


                    <a class="action"
                       href="${pageContext.request.contextPath}/admin/announcements/">

                        <strong>
                            Announcements
                        </strong>

                        <span>
                            Publish institute announcements
                        </span>

                    </a>

                </div>

            </div>

        </section>

    </main>

</div>

</body>

</html>