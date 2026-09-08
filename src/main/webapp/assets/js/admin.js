document.addEventListener("DOMContentLoaded", function () {

    // =========================================
    // SIDEBAR TOGGLE
    // =========================================

    const sidebarToggle = document.getElementById("sidebarToggle");
    const sidebar = document.querySelector(".admin-sidebar");

    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener("click", function () {
            sidebar.classList.toggle("open");
        });
    }

    // =========================================
    // PROFILE DROPDOWN
    // =========================================

    const profileButton =
        document.getElementById("profileMenuButton");

    const profileDropdown =
        document.getElementById("profileDropdown");

    if (profileButton && profileDropdown) {

        profileButton.addEventListener("click", function (event) {

            event.stopPropagation();

            profileDropdown.classList.toggle("show");

        });

        document.addEventListener("click", function () {
            profileDropdown.classList.remove("show");
        });
    }

});