package com.foxbrain.filter;

import java.io.IOException;

import com.foxbrain.model.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
        "/admin/*",
        "/student/*",
        "/teacher/*",
        "/parent/*"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        User loggedInUser = null;

        if (session != null) {
            loggedInUser =
                    (User) session.getAttribute("loggedInUser");
        }

        // No authenticated user
        if (loggedInUser == null) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        // Prevent browser cache of protected pages
        httpResponse.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        httpResponse.setHeader(
                "Pragma",
                "no-cache"
        );

        httpResponse.setDateHeader(
                "Expires",
                0
        );

        // Check whether user can access this portal
        if (!hasPortalAccess(
                httpRequest,
                loggedInUser)) {

            httpResponse.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You do not have permission to access this page."
            );

            return;
        }

        // User is authenticated and authorized
        chain.doFilter(request, response);
    }

    private boolean hasPortalAccess(
            HttpServletRequest request,
            User user) {

        String uri = request.getRequestURI();

        String contextPath =
                request.getContextPath();

        String path =
                uri.substring(contextPath.length());

        long roleId = user.getRoleId();

        // ADMIN
        if (path.startsWith("/admin/")) {
            return roleId == 1;
        }

        // TEACHER
        if (path.startsWith("/teacher/")) {
            return roleId == 2;
        }

        // STUDENT
        if (path.startsWith("/student/")) {
            return roleId == 3;
        }

        // PARENT
        if (path.startsWith("/parent/")) {
            return roleId == 4;
        }

        return false;
    }
}