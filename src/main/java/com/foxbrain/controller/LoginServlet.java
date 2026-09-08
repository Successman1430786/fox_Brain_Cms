package com.foxbrain.controller;

import java.io.IOException;

import com.foxbrain.model.User;
import com.foxbrain.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    // Show login page
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
    }

    // Process login
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get form values
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Remove accidental spaces from username
        if (username != null) {
            username = username.trim();
        }

        // Validate input
        if (username == null ||
                username.isEmpty() ||
                password == null ||
                password.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Username and password are required."
            );

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);

            return;
        }

        try {

            // Authenticate user
            User user = userService.authenticate(
                    username,
                    password
            );

            // Login failed
            if (user == null) {

                request.setAttribute(
                        "error",
                        "Invalid username or password."
                );

                request.getRequestDispatcher(
                        "/login.jsp"
                ).forward(request, response);

                return;
            }

            // Create session
            HttpSession session = request.getSession(true);

            // Store authenticated user
            session.setAttribute("loggedInUser", user);

            // Store useful user information
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("roleId", user.getRoleId());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());

            // Prevent session fixation
            request.changeSessionId();

            // Redirect according to role
            String role = getUserRole(user);

            if ("ADMIN".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/dashboard.jsp"
                );

            } else if ("TEACHER".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/teacher/dashboard.jsp"
                );

            } else if ("STUDENT".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/student/dashboard.jsp"
                );

            } else if ("PARENT".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/parent/dashboard.jsp"
                );

            } else {

                // Unknown role
                session.invalidate();

                request.setAttribute(
                        "error",
                        "Your account role is not configured."
                );

                request.getRequestDispatcher(
                        "/login.jsp"
                ).forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "An unexpected error occurred. Please try again."
            );

            request.getRequestDispatcher(
                    "/login.jsp"
            ).forward(request, response);
        }
    }

    /**
     * Gets the role name.
     *
     * Current User model stores roleId, so this method
     * will be connected to RoleDAO later.
     */
    private String getUserRole(User user) {

        /*
         * Temporary role handling.
         *
         * We will replace this with RoleDAO lookup
         * so the role comes directly from the database.
         */

        if (user.getRoleId() == 1) {
            return "ADMIN";
        }

        if (user.getRoleId() == 2) {
            return "TEACHER";
        }

        if (user.getRoleId() == 3) {
            return "STUDENT";
        }

        if (user.getRoleId() == 4) {
            return "PARENT";
        }

        return null;
    }
}