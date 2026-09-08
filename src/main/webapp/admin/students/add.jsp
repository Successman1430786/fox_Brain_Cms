<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("loggedInUser") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
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

    <title>Add Student - FoxBrain Admin</title>

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

        .required {
            color: #d92d20;
        }

        input,
        select,
        textarea {
            width: 100%;
            padding: 11px 12px;
            border: 1px solid #cfd5dd;
            border-radius: 6px;
            font-size: 14px;
            background: white;
        }

        textarea {
            min-height: 90px;
            resize: vertical;
        }

        input:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #4f46e5;
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

        <h1>Add Student</h1>

        <a class="back-link"
           href="<%= request.getContextPath() %>/admin/students">
            ← Back to Students
        </a>

    </div>


    <!-- ERROR MESSAGE -->
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>

        <div class="error">
            <strong>Error:</strong>
            <%= errorMessage %>
        </div>

    <% } %>


    <!-- =====================================================
         STUDENT ACCOUNT
         ===================================================== -->

    <div class="card">

        <h2>Student Account</h2>

        <div class="grid">

            <div class="form-group">

                <label for="username">
                    Username <span class="required">*</span>
                </label>

                <input
                    type="text"
                    id="username"
                    name="username"
                    form="studentForm"
                    maxlength="100"
                    required
                    placeholder="Enter username">

                <div class="help-text">
                    This username will be used for student login.
                </div>

            </div>


            <div class="form-group">

                <label for="email">
                    Email <span class="required">*</span>
                </label>

                <input
                    type="email"
                    id="email"
                    name="email"
                    form="studentForm"
                    maxlength="150"
                    required
                    placeholder="student@example.com">

            </div>


            <div class="form-group">

                <label for="password">
                    Password <span class="required">*</span>
                </label>

                <input
                    type="password"
                    id="password"
                    name="password"
                    form="studentForm"
                    required
                    placeholder="Enter temporary password">

                <div class="help-text">
                    The password will be securely hashed before storage.
                </div>

            </div>


            <div class="form-group">

                <label for="phone">
                    Phone
                </label>

                <input
                    type="tel"
                    id="phone"
                    name="phone"
                    form="studentForm"
                    maxlength="30"
                    placeholder="Enter phone number">

            </div>


            <div class="form-group">

                <label for="firstName">
                    First Name <span class="required">*</span>
                </label>

                <input
                    type="text"
                    id="firstName"
                    name="firstName"
                    form="studentForm"
                    maxlength="100"
                    required
                    placeholder="Enter first name">

            </div>


            <div class="form-group">

                <label for="lastName">
                    Last Name
                </label>

                <input
                    type="text"
                    id="lastName"
                    name="lastName"
                    form="studentForm"
                    maxlength="100"
                    placeholder="Enter last name">

            </div>

        </div>

    </div>


    <!-- =====================================================
         STUDENT INFORMATION
         ===================================================== -->

    <div class="card">

        <h2>Student Information</h2>

        <form
            id="studentForm"
            method="post"
            action="<%= request.getContextPath() %>/admin/students">

            <input
                type="hidden"
                name="action"
                value="create">


            <div class="grid">

                <!-- Admission Number -->

                <div class="form-group">

                    <label for="admissionNumber">
                        Admission Number
                        <span class="required">*</span>
                    </label>

                    <input
                        type="text"
                        id="admissionNumber"
                        name="admissionNumber"
                        maxlength="50"
                        required
                        placeholder="e.g. FB2026001">

                </div>


                <!-- Admission Date -->

                <div class="form-group">

                    <label for="admissionDate">
                        Admission Date
                    </label>

                    <input
                        type="date"
                        id="admissionDate"
                        name="admissionDate">

                </div>


                <!-- Date of Birth -->

                <div class="form-group">

                    <label for="dateOfBirth">
                        Date of Birth
                    </label>

                    <input
                        type="date"
                        id="dateOfBirth"
                        name="dateOfBirth">

                </div>


                <!-- Gender -->

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

                        <option value="Male">
                            Male
                        </option>

                        <option value="Female">
                            Female
                        </option>

                        <option value="Other">
                            Other
                        </option>

                    </select>

                </div>


                <!-- Status -->

                <div class="form-group">

                    <label for="status">
                        Status
                    </label>

                    <select
                        id="status"
                        name="status">

                        <option value="ACTIVE">
                            Active
                        </option>

                        <option value="INACTIVE">
                            Inactive
                        </option>

                        <option value="SUSPENDED">
                            Suspended
                        </option>

                        <option value="GRADUATED">
                            Graduated
                        </option>

                    </select>

                </div>


                <!-- Country -->

                <div class="form-group">

                    <label for="country">
                        Country
                    </label>

                    <input
                        type="text"
                        id="country"
                        name="country"
                        maxlength="100"
                        value="India"
                        placeholder="Enter country">

                </div>


                <!-- Address Line 1 -->

                <div class="form-group full">

                    <label for="addressLine1">
                        Address Line 1
                    </label>

                    <input
                        type="text"
                        id="addressLine1"
                        name="addressLine1"
                        maxlength="255"
                        placeholder="House number, street, area">

                </div>


                <!-- Address Line 2 -->

                <div class="form-group full">

                    <label for="addressLine2">
                        Address Line 2
                    </label>

                    <input
                        type="text"
                        id="addressLine2"
                        name="addressLine2"
                        maxlength="255"
                        placeholder="Landmark, apartment, etc.">

                </div>


                <!-- City -->

                <div class="form-group">

                    <label for="city">
                        City
                    </label>

                    <input
                        type="text"
                        id="city"
                        name="city"
                        maxlength="100"
                        placeholder="Enter city">

                </div>


                <!-- State -->

                <div class="form-group">

                    <label for="state">
                        State
                    </label>

                    <input
                        type="text"
                        id="state"
                        name="state"
                        maxlength="100"
                        placeholder="Enter state">

                </div>


                <!-- Postal Code -->

                <div class="form-group">

                    <label for="postalCode">
                        Postal Code
                    </label>

                    <input
                        type="text"
                        id="postalCode"
                        name="postalCode"
                        maxlength="20"
                        placeholder="Enter postal code">

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
                    Create Student
                </button>

            </div>

        </form>

    </div>

</div>

</body>
</html>