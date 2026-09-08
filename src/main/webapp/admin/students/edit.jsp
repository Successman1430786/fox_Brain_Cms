<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.foxbrain.model.Student" %>

<%
    if (session.getAttribute("loggedInUser") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Student student =
        (Student) request.getAttribute("student");

    if (student == null) {
        response.sendRedirect(
            request.getContextPath() + "/admin/students"
        );
        return;
    }

    String errorMessage =
        (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
        content="width=device-width, initial-scale=1.0">

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

        .container {
            max-width: 1100px;
            margin: 30px auto;
            padding: 0 20px;
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
        }

        .back-link {
            text-decoration: none;
            color: #333;
            background: #e9edf3;
            padding: 10px 16px;
            border-radius: 6px;
        }

        .back-link:hover {
            background: #dfe4eb;
        }

        .error {
            background: #fde8e8;
            color: #b42318;
            border: 1px solid #f5b5b5;
            padding: 14px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        }

        .card h2 {
            margin-top: 0;
            margin-bottom: 20px;
            font-size: 20px;
            border-bottom: 1px solid #eee;
            padding-bottom: 12px;
        }

        .grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .full {
            grid-column: 1 / -1;
        }

        label {
            font-weight: 600;
            margin-bottom: 7px;
        }

        input,
        select {
            width: 100%;
            padding: 11px 12px;
            border: 1px solid #cfd5dd;
            border-radius: 6px;
            font-size: 14px;
            background: white;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #4f46e5;
        }

        .readonly-box {
            background: #f1f3f5;
            color: #555;
        }

        .help-text {
            margin-top: 5px;
            font-size: 12px;
            color: #6b7280;
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            gap: 12px;
            margin-top: 10px;
        }

        .btn {
            border: none;
            border-radius: 6px;
            padding: 11px 20px;
            font-size: 14px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-cancel {
            background: #e9edf3;
            color: #333;
        }

        .btn-primary {
            background: #4f46e5;
            color: white;
        }

        .btn-primary:hover {
            background: #4338ca;
        }

        @media (max-width: 700px) {

            .grid {
                grid-template-columns: 1fr;
            }

            .full {
                grid-column: auto;
            }

            .page-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
        }

    </style>

</head>

<body>

<div class="container">

    <!-- PAGE HEADER -->

    <div class="page-header">

        <h1>Edit Student</h1>

        <a
            class="back-link"
            href="<%= request.getContextPath() %>/admin/students">

            ← Back to Students

        </a>

    </div>


    <!-- ERROR -->

    <% if (errorMessage != null &&
           !errorMessage.isEmpty()) { %>

        <div class="error">

            <strong>Error:</strong>

            <%= errorMessage %>

        </div>

    <% } %>


    <!-- =====================================================
         STUDENT ACCOUNT REFERENCE
         ===================================================== -->

    <div class="card">

        <h2>Student Account</h2>

        <div class="grid">

            <div class="form-group">

                <label>Student User ID</label>

                <input
                    type="text"
                    class="readonly-box"
                    value="<%= student.getUserId() %>"
                    readonly>

                <div class="help-text">
                    This account is permanently linked to this
                    student profile.
                </div>

            </div>


            <div class="form-group">

                <label>Student ID</label>

                <input
                    type="text"
                    class="readonly-box"
                    value="<%= student.getId() %>"
                    readonly>

            </div>

        </div>

    </div>


    <!-- =====================================================
         STUDENT INFORMATION
         ===================================================== -->

    <div class="card">

        <h2>Student Information</h2>

        <form
            method="post"
            action="<%= request.getContextPath() %>/admin/students">

            <input
                type="hidden"
                name="action"
                value="update">

            <input
                type="hidden"
                name="id"
                value="<%= student.getId() %>">

            <input
                type="hidden"
                name="userId"
                value="<%= student.getUserId() %>">


            <div class="grid">


                <!-- ADMISSION NUMBER -->

                <div class="form-group">

                    <label for="admissionNumber">
                        Admission Number
                    </label>

                    <input
                        type="text"
                        id="admissionNumber"
                        name="admissionNumber"
                        maxlength="50"
                        value="<%= student.getAdmissionNumber() != null
                            ? student.getAdmissionNumber()
                            : "" %>"
                        required>

                </div>


                <!-- ADMISSION DATE -->

                <div class="form-group">

                    <label for="admissionDate">
                        Admission Date
                    </label>

                    <input
                        type="date"
                        id="admissionDate"
                        name="admissionDate"
                        value="<%= student.getAdmissionDate() != null
                            ? student.getAdmissionDate()
                            : "" %>">

                </div>


                <!-- DATE OF BIRTH -->

                <div class="form-group">

                    <label for="dateOfBirth">
                        Date of Birth
                    </label>

                    <input
                        type="date"
                        id="dateOfBirth"
                        name="dateOfBirth"
                        value="<%= student.getDateOfBirth() != null
                            ? student.getDateOfBirth()
                            : "" %>">

                </div>


                <!-- GENDER -->

                <div class="form-group">

                    <label for="gender">
                        Gender
                    </label>

                    <select
                        id="gender"
                        name="gender">

                        <option value="">
                            Select Gender
                        </option>

                        <option value="Male"
                            <%= "Male".equals(student.getGender())
                                ? "selected"
                                : "" %>>
                            Male
                        </option>

                        <option value="Female"
                            <%= "Female".equals(student.getGender())
                                ? "selected"
                                : "" %>>
                            Female
                        </option>

                        <option value="Other"
                            <%= "Other".equals(student.getGender())
                                ? "selected"
                                : "" %>>
                            Other
                        </option>

                    </select>

                </div>


                <!-- STATUS -->

                <div class="form-group">

                    <label for="status">
                        Status
                    </label>

                    <select
                        id="status"
                        name="status">

                        <option value="ACTIVE"
                            <%= "ACTIVE".equals(student.getStatus())
                                ? "selected"
                                : "" %>>
                            Active
                        </option>

                        <option value="INACTIVE"
                            <%= "INACTIVE".equals(student.getStatus())
                                ? "selected"
                                : "" %>>
                            Inactive
                        </option>

                        <option value="SUSPENDED"
                            <%= "SUSPENDED".equals(student.getStatus())
                                ? "selected"
                                : "" %>>
                            Suspended
                        </option>

                        <option value="GRADUATED"
                            <%= "GRADUATED".equals(student.getStatus())
                                ? "selected"
                                : "" %>>
                            Graduated
                        </option>

                    </select>

                </div>


                <!-- COUNTRY -->

                <div class="form-group">

                    <label for="country">
                        Country
                    </label>

                    <input
                        type="text"
                        id="country"
                        name="country"
                        maxlength="100"
                        value="<%= student.getCountry() != null
                            ? student.getCountry()
                            : "" %>">

                </div>


                <!-- ADDRESS LINE 1 -->

                <div class="form-group full">

                    <label for="addressLine1">
                        Address Line 1
                    </label>

                    <input
                        type="text"
                        id="addressLine1"
                        name="addressLine1"
                        maxlength="255"
                        value="<%= student.getAddressLine1() != null
                            ? student.getAddressLine1()
                            : "" %>">

                </div>


                <!-- ADDRESS LINE 2 -->

                <div class="form-group full">

                    <label for="addressLine2">
                        Address Line 2
                    </label>

                    <input
                        type="text"
                        id="addressLine2"
                        name="addressLine2"
                        maxlength="255"
                        value="<%= student.getAddressLine2() != null
                            ? student.getAddressLine2()
                            : "" %>">

                </div>


                <!-- CITY -->

                <div class="form-group">

                    <label for="city">
                        City
                    </label>

                    <input
                        type="text"
                        id="city"
                        name="city"
                        maxlength="100"
                        value="<%= student.getCity() != null
                            ? student.getCity()
                            : "" %>">

                </div>


                <!-- STATE -->

                <div class="form-group">

                    <label for="state">
                        State
                    </label>

                    <input
                        type="text"
                        id="state"
                        name="state"
                        maxlength="100"
                        value="<%= student.getState() != null
                            ? student.getState()
                            : "" %>">

                </div>


                <!-- POSTAL CODE -->

                <div class="form-group">

                    <label for="postalCode">
                        Postal Code
                    </label>

                    <input
                        type="text"
                        id="postalCode"
                        name="postalCode"
                        maxlength="20"
                        value="<%= student.getPostalCode() != null
                            ? student.getPostalCode()
                            : "" %>">

                </div>

            </div>


            <!-- ACTIONS -->

            <div class="actions">

                <a
                    href="<%= request.getContextPath() %>/admin/students"
                    class="btn btn-cancel">

                    Cancel

                </a>

                <button
                    type="submit"
                    class="btn btn-primary">

                    Update Student

                </button>

            </div>

        </form>

    </div>

</div>

</body>

</html>