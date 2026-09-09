package com.foxbrain.controller;

import com.foxbrain.model.Attendance;
import com.foxbrain.model.Enrollment;
import com.foxbrain.model.Teacher;
import com.foxbrain.service.AttendanceService;
import com.foxbrain.service.EnrollmentService;
import com.foxbrain.service.TeacherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/admin/attendance")
public class AttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AttendanceService attendanceService;
    private EnrollmentService enrollmentService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {

        attendanceService = new AttendanceService();
        enrollmentService = new EnrollmentService();
        teacherService = new TeacherService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        try {

            switch (action) {

                case "add":
                    showAddForm(request, response);
                    break;

                case "edit":
                    showEditForm(request, response);
                    break;

                case "delete":
                    deleteAttendance(request, response);
                    break;

                case "list":
                default:
                    listAttendance(request, response);
                    break;
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Database error in Attendance module.",
                    e
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        try {

            switch (action) {

                case "create":
                    createAttendance(request, response);
                    break;

                case "update":
                    updateAttendance(request, response);
                    break;

                default:
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/admin/attendance"
                    );
                    break;
            }

        } catch (SQLException e) {

            throw new ServletException(
                    "Database error in Attendance module.",
                    e
            );
        }
    }

    private void listAttendance(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String dateParameter =
                request.getParameter("date");

        String status =
                request.getParameter("status");

        List<Attendance> attendanceList;

        if (dateParameter != null
                && !dateParameter.trim().isEmpty()) {

            LocalDate date =
                    LocalDate.parse(dateParameter);

            attendanceList =
                    attendanceService.getByDate(date);

            request.setAttribute(
                    "selectedDate",
                    date
            );

        } else if (status != null
                && !status.trim().isEmpty()) {

            attendanceList =
                    attendanceService.getByStatus(status);

            request.setAttribute(
                    "selectedStatus",
                    status.toUpperCase()
            );

        } else {

            attendanceList =
                    attendanceService.getAll();
        }

        request.setAttribute(
                "attendanceList",
                attendanceList
        );

        request.getRequestDispatcher(
                "/admin/attendance/list.jsp"
        ).forward(request, response);
    }

    private void showAddForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        loadFormData(request);

        Attendance attendance =
                new Attendance();

        attendance.setAttendanceDate(
                LocalDate.now()
        );

        attendance.setStatus(
                "PRESENT"
        );

        request.setAttribute(
                "attendance",
                attendance
        );

        request.getRequestDispatcher(
                "/admin/attendance/add.jsp"
        ).forward(request, response);
    }

    private void showEditForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        long id = parseLong(
                request.getParameter("id"),
                "Invalid attendance ID."
        );

        Attendance attendance =
                attendanceService.getById(id);

        if (attendance == null) {

            request.getSession().setAttribute(
                    "error",
                    "Attendance record not found."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/attendance"
            );

            return;
        }

        loadFormData(request);

        request.setAttribute(
                "attendance",
                attendance
        );

        request.getRequestDispatcher(
                "/admin/attendance/edit.jsp"
        ).forward(request, response);
    }

    private void createAttendance(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        try {

            Attendance attendance =
                    buildAttendanceFromRequest(request);

            attendanceService.create(attendance);

            request.getSession().setAttribute(
                    "success",
                    "Attendance marked successfully."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/attendance"
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            loadFormData(request);

            Attendance attendance =
                    buildAttendanceWithoutValidation(request);

            request.setAttribute(
                    "attendance",
                    attendance
            );

            request.getRequestDispatcher(
                    "/admin/attendance/add.jsp"
            ).forward(request, response);
        }
    }

    private void updateAttendance(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        try {

            Attendance attendance =
                    buildAttendanceFromRequest(request);

            attendance.setId(
                    parseLong(
                            request.getParameter("id"),
                            "Invalid attendance ID."
                    )
            );

            attendanceService.update(attendance);

            request.getSession().setAttribute(
                    "success",
                    "Attendance updated successfully."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/attendance"
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            loadFormData(request);

            Attendance attendance =
                    buildAttendanceWithoutValidation(request);

            attendance.setId(
                    parseLongSafely(
                            request.getParameter("id")
                    )
            );

            request.setAttribute(
                    "attendance",
                    attendance
            );

            request.getRequestDispatcher(
                    "/admin/attendance/edit.jsp"
            ).forward(request, response);
        }
    }

    private void deleteAttendance(
            HttpServletRequest request,
            HttpServletResponse response)
            throws SQLException, IOException {

        long id = parseLong(
                request.getParameter("id"),
                "Invalid attendance ID."
        );

        attendanceService.delete(id);

        request.getSession().setAttribute(
                "success",
                "Attendance deleted successfully."
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/attendance"
        );
    }

    private Attendance buildAttendanceFromRequest(
            HttpServletRequest request) {

        Attendance attendance =
                buildAttendanceWithoutValidation(request);

        return attendance;
    }

    private Attendance buildAttendanceWithoutValidation(
            HttpServletRequest request) {

        Attendance attendance =
                new Attendance();

        String enrollmentId =
                request.getParameter("enrollmentId");

        if (enrollmentId != null
                && !enrollmentId.trim().isEmpty()) {

            attendance.setEnrollmentId(
                    parseLongSafely(enrollmentId)
            );
        }

        String attendanceDate =
                request.getParameter("attendanceDate");

        if (attendanceDate != null
                && !attendanceDate.trim().isEmpty()) {

            try {

                attendance.setAttendanceDate(
                        LocalDate.parse(
                                attendanceDate
                        )
                );

            } catch (Exception ignored) {
            }
        }

        attendance.setStatus(
                clean(request.getParameter("status"))
        );

        String checkIn =
                request.getParameter("checkInTime");

        if (checkIn != null
                && !checkIn.trim().isEmpty()) {

            try {

                attendance.setCheckInTime(
                        LocalTime.parse(checkIn)
                );

            } catch (Exception ignored) {
            }
        }

        String checkOut =
                request.getParameter("checkOutTime");

        if (checkOut != null
                && !checkOut.trim().isEmpty()) {

            try {

                attendance.setCheckOutTime(
                        LocalTime.parse(checkOut)
                );

            } catch (Exception ignored) {
            }
        }

        attendance.setRemarks(
                clean(request.getParameter("remarks"))
        );

        String teacherId =
                request.getParameter("markedByTeacherId");

        if (teacherId != null
                && !teacherId.trim().isEmpty()) {

            long id = parseLongSafely(teacherId);

            if (id > 0) {
                attendance.setMarkedByTeacherId(id);
            }
        }

        return attendance;
    }

    private void loadFormData(
            HttpServletRequest request)
            throws SQLException {

        List<Enrollment> enrollments =
                enrollmentService.getAll();

        List<Teacher> teachers =
                teacherService.getAll();

        request.setAttribute(
                "enrollments",
                enrollments
        );

        request.setAttribute(
                "teachers",
                teachers
        );
    }

    private long parseLong(
            String value,
            String message) {

        if (value == null
                || value.trim().isEmpty()) {

            throw new IllegalArgumentException(message);
        }

        try {

            long result =
                    Long.parseLong(value.trim());

            if (result <= 0) {
                throw new IllegalArgumentException(message);
            }

            return result;

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(message);
        }
    }

    private long parseLongSafely(String value) {

        if (value == null
                || value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String clean(String value) {

        return value == null
                ? null
                : value.trim();
    }
}