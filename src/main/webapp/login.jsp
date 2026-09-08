<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Login | FoxBrain Institute</title>

    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #f4f7fb;
        }

        .login-container {
            width: 100%;
            max-width: 420px;
            padding: 20px;
        }

        .login-card {
            background: #ffffff;
            padding: 40px;
            border-radius: 14px;
            box-shadow: 0 10px 35px rgba(0, 0, 0, 0.08);
        }

        .logo {
            text-align: center;
            margin-bottom: 25px;
        }

        .logo h1 {
            font-size: 30px;
            color: #172554;
            margin-bottom: 6px;
        }

        .logo p {
            color: #64748b;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 7px;
            font-size: 14px;
            font-weight: 600;
            color: #334155;
        }

        .form-group input {
            width: 100%;
            padding: 13px 14px;
            border: 1px solid #cbd5e1;
            border-radius: 8px;
            font-size: 15px;
            outline: none;
        }

        .form-group input:focus {
            border-color: #2563eb;
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.10);
        }

        .login-btn {
            width: 100%;
            padding: 13px;
            border: none;
            border-radius: 8px;
            background: #2563eb;
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
        }

        .login-btn:hover {
            background: #1d4ed8;
        }

        .error-message {
            background: #fef2f2;
            color: #b91c1c;
            border: 1px solid #fecaca;
            padding: 11px;
            border-radius: 8px;
            margin-bottom: 18px;
            font-size: 14px;
        }

        .success-message {
            background: #f0fdf4;
            color: #15803d;
            border: 1px solid #bbf7d0;
            padding: 11px;
            border-radius: 8px;
            margin-bottom: 18px;
            font-size: 14px;
        }

        .footer {
            text-align: center;
            margin-top: 22px;
            color: #94a3b8;
            font-size: 13px;
        }
    </style>
</head>

<body>

<div class="login-container">

    <div class="login-card">

        <div class="logo">
            <h1>FoxBrain Institute</h1>
            <p>Management & Learning Portal</p>
        </div>

        <% 
            String error = (String) request.getAttribute("error");
            String logout = request.getParameter("logout");
        %>

        <% if (error != null) { %>

            <div class="error-message">
                <%= error %>
            </div>

        <% } %>

        <% if ("true".equals(logout)) { %>

            <div class="success-message">
                You have been logged out successfully.
            </div>

        <% } %>

        <form action="<%= request.getContextPath() %>/login"
              method="post">

            <div class="form-group">

                <label for="username">
                    Username
                </label>

                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="Enter your username"
                    autocomplete="username"
                    required>

            </div>

            <div class="form-group">

                <label for="password">
                    Password
                </label>

                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your password"
                    autocomplete="current-password"
                    required>

            </div>

            <button
                type="submit"
                class="login-btn">
                Login
            </button>

        </form>

        <div class="footer">
            © FoxBrain Institute
        </div>

    </div>

</div>

</body>
</html>