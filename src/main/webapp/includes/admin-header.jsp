<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
String headerContextPath = request.getContextPath();

    String firstName = (String) session.getAttribute("firstName");
    String lastName = (String) session.getAttribute("lastName");
    String username = (String) session.getAttribute("username");

    if (firstName == null || firstName.trim().isEmpty()) {
        firstName = "Admin";
    }

    if (lastName == null) {
        lastName = "";
    }

    String fullName = (firstName + " " + lastName).trim();

    String pageTitle = (String) request.getAttribute("pageTitle");

    if (pageTitle == null || pageTitle.trim().isEmpty()) {
        pageTitle = "Dashboard";
    }
%>

<header class="admin-header">

    <!-- Mobile Sidebar Button -->
    <button type="button"
            class="sidebar-toggle"
            id="sidebarToggle"
            aria-label="Toggle sidebar">
        ☰
    </button>

    <!-- Page Title -->
    <div class="header-title">
        <h1><%= pageTitle %></h1>
    </div>

    <!-- Header Actions -->
    <div class="header-actions">

        <!-- Search -->
        <div class="header-search">
            <span class="search-icon">⌕</span>
            <input type="text"
                   id="adminSearch"
                   placeholder="Search...">
        </div>

        <!-- Notifications -->
        <button type="button"
                class="header-icon-button"
                title="Notifications">
            🔔
            <span class="notification-badge">0</span>
        </button>

        <!-- Admin Profile -->
        <div class="admin-profile">

            <div class="admin-avatar">
                <%= firstName.substring(0, 1).toUpperCase() %>
            </div>

            <div class="admin-profile-info">
                <strong><%= fullName %></strong>
                <span>@<%= username != null ? username : "admin" %></span>
            </div>

            <button type="button"
                    class="profile-menu-button"
                    id="profileMenuButton">
                ▾
            </button>

            <!-- Profile Dropdown -->
            <div class="profile-dropdown" id="profileDropdown">

                <a href="<%= headerContextPath %>/admin/settings">
                    ⚙ Settings
                </a>

                <a href="<%= headerContextPath %>/logout"
                   class="logout-link">
                    🚪 Logout
                </a>

            </div>

        </div>

    </div>

</header>