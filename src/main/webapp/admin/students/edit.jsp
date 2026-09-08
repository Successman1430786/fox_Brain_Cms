<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.foxbrain.model.Student" %>

<%
    // Security check
    if (session.getAttribute("loggedInUser") == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );
        return;
    }

    Student student =
        (Student) request.getAttribute("student");

    String errorMessage =
        (String) request.getAttribute("errorMessage");

    if (student == null) {
        response.sendRedirect(
            request.getContextPath() + "/admin/students"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Edit Student - FoxBrain Admin</title>

    <style>

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f7fb;
            color: #222;
        }

        .header {
            height: 65px;
            background: #1e293b;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
        }

        .header h2 {
            margin: 0;
        }

        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .page-title {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .page-title h1 {
            margin: 0;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            margin-bottom: 25px;
        }

        .section-title {
            margin-top: 0;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e5e7eb;
            color: #1e293b;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full {
            grid-column: 1 / -1;
        }

        label {
            font-weight: bold;
            margin-bottom: 7px;
            font-size: 14px;
        }

        input,
        select {
            padding: 11px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 14px;
            font-family: Arial, sans-serif;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #2563eb;
        }

        .required {
            color: #dc2626;
        }

        .alert {
            background: #fee2e2;
            color: #991b1b;
            padding: 12px 16px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        .btn {
            display: inline-block;
            padding: 11px 18px;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }

        .btn-primary {
            background: #2563eb;
            color: white;
        }

        .btn-secondary {
            background: #64748b;
            color: white;
        }

        @media (max-width: 700px) {

            .form-grid {
                grid-template-columns: 1fr;
            }

            .form-group.full {
                grid-column: auto;
            }

            .container {
                padding: 0 10px;
            }

            .card {
                padding: 20px;
            }

        }

    </style>

</head>

<body>

    <!-- HEADER -->

    <div class="header">

        <h2>FoxBrain Admin</h2>

        <a
            href="<%= request.getContextPath() %>/admin/dashboard.jsp"
            class="btn"
            style="color:white;"
        >
            Dashboard
        </a>

    </div>


    <div class="container">

        <!-- PAGE TITLE -->

        <div class="page-title">

            <h1>Edit Student</h1>

            <a
                href="<%= request.getContextPath() %>/admin/students"
                class="btn btn-secondary"
            >
                ← Back to Students
            </a>

        </div>


        <!-- ERROR -->

        <% if (errorMessage != null &&
               !errorMessage.trim().isEmpty()) { %>

            <div class="alert">
                <%= errorMessage %>
            </div>

        <% } %>


        <form
            method="post"
            action="<%= request.getContextPath() %>/admin/students"
        >

            <input
                type="hidden"
                name="action"
                value="update"
            >

            <input
                type="hidden"
                name="id"
                value="<%= student.getId() %>"
            >


            <!-- ACCOUNT INFORMATION -->

            <div class="card">

                <h2 class="section-title">
                    Student Account
                </h2>

                <div class="form-grid">

                    <div class="form-group">

                        <label>
                            Student ID
                        </label>

                        <input
                            type="text"
                            value="<%= student.getId() %>"
                            readonly
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            User ID
                            <span class="required">*</span>
                        </label>

                        <input
                            type="number"
                            name="userId"
                            min="1"
                            required
                            value="<%= student.getUserId() %>"
                        >

                    </div>

                </div>

            </div>


            <!-- STUDENT INFORMATION -->

            <div class="card">

                <h2 class="section-title">
                    Student Information
                </h2>

                <div class="form-grid">

                    <div class="form-group">

                        <label>
                            Admission Number
                            <span class="required">*</span>
                        </label>

                        <input
                            type="text"
                            name="admissionNumber"
                            maxlength="50"
                            required
                            value="<%= student.getAdmissionNumber() != null
                                ? student.getAdmissionNumber()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            Admission Date
                        </label>

                        <input
                            type="date"
                            name="admissionDate"
                            value="<%= student.getAdmissionDate() != null
                                ? student.getAdmissionDate()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            Date of Birth
                        </label>

                        <input
                            type="date"
                            name="dateOfBirth"
                            value="<%= student.getDateOfBirth() != null
                                ? student.getDateOfBirth()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            Gender
                        </label>

                        <select name="gender">

                            <option value="">
                                Select Gender
                            </option>

                            <option value="Male"
                                <%= "Male".equals(student.getGender())
                                    ? "selected" : "" %>>
                                Male
                            </option>

                            <option value="Female"
                                <%= "Female".equals(student.getGender())
                                    ? "selected" : "" %>>
                                Female
                            </option>

                            <option value="Other"
                                <%= "Other".equals(student.getGender())
                                    ? "selected" : "" %>>
                                Other
                            </option>

                        </select>

                    </div>


                    <div class="form-group">

                        <label>
                            Status
                        </label>

                        <select name="status">

                            <option value="ACTIVE"
                                <%= "ACTIVE".equals(student.getStatus())
                                    ? "selected" : "" %>>
                                Active
                            </option>

                            <option value="INACTIVE"
                                <%= "INACTIVE".equals(student.getStatus())
                                    ? "selected" : "" %>>
                                Inactive
                            </option>

                            <option value="SUSPENDED"
                                <%= "SUSPENDED".equals(student.getStatus())
                                    ? "selected" : "" %>>
                                Suspended
                            </option>

                            <option value="GRADUATED"
                                <%= "GRADUATED".equals(student.getStatus())
                                    ? "selected" : "" %>>
                                Graduated
                            </option>

                        </select>

                    </div>

                </div>

            </div>


            <!-- ADDRESS -->

            <div class="card">

                <h2 class="section-title">
                    Address Information
                </h2>

                <div class="form-grid">

                    <div class="form-group full">

                        <label>
                            Address Line 1
                        </label>

                        <input
                            type="text"
                            name="addressLine1"
                            maxlength="255"
                            value="<%= student.getAddressLine1() != null
                                ? student.getAddressLine1()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group full">

                        <label>
                            Address Line 2
                        </label>

                        <input
                            type="text"
                            name="addressLine2"
                            maxlength="255"
                            value="<%= student.getAddressLine2() != null
                                ? student.getAddressLine2()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            City
                        </label>

                        <input
                            type="text"
                            name="city"
                            maxlength="100"
                            value="<%= student.getCity() != null
                                ? student.getCity()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            State
                        </label>

                        <input
                            type="text"
                            name="state"
                            maxlength="100"
                            value="<%= student.getState() != null
                                ? student.getState()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            Postal Code
                        </label>

                        <input
                            type="text"
                            name="postalCode"
                            maxlength="20"
                            value="<%= student.getPostalCode() != null
                                ? student.getPostalCode()
                                : "" %>"
                        >

                    </div>


                    <div class="form-group">

                        <label>
                            Country
                        </label>

                        <input
                            type="text"
                            name="country"
                            maxlength="100"
                            value="<%= student.getCountry() != null
                                ? student.getCountry()
                                : "" %>"
                        >

                    </div>

                </div>

            </div>


            <!-- ACTIONS -->

            <div class="card">

                <div class="actions">

                    <a
                        href="<%= request.getContextPath() %>/admin/students"
                        class="btn btn-secondary"
                    >
                        Cancel
                    </a>

                    <button
                        type="submit"
                        class="btn btn-primary"
                    >
                        Update Student
                    </button>

                </div>

            </div>

        </form>

    </div>

</body>
</html>