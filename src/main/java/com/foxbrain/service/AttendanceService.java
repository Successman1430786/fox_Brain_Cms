package com.foxbrain.service;

import com.foxbrain.dao.AttendanceDAO;
import com.foxbrain.model.Attendance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO;
    private final EnrollmentService enrollmentService;
    private final TeacherService teacherService;

    private static final Set<String> VALID_STATUSES =
            new HashSet<>(Arrays.asList(
                    "PRESENT",
                    "ABSENT",
                    "LATE",
                    "EXCUSED"
            ));

    public AttendanceService() {

        this.attendanceDAO = new AttendanceDAO();
        this.enrollmentService = new EnrollmentService();
        this.teacherService = new TeacherService();
    }

    public List<Attendance> getAll()
            throws SQLException {

        return attendanceDAO.findAll();
    }

    public List<Attendance> getByDate(LocalDate date)
            throws SQLException {

        if (date == null) {
            throw new IllegalArgumentException(
                    "Attendance date is required."
            );
        }

        return attendanceDAO.findByDate(date);
    }

    public List<Attendance> getByStatus(String status)
            throws SQLException {

        if (status == null || status.trim().isEmpty()) {
            return attendanceDAO.findAll();
        }

        status = status.trim().toUpperCase();

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid attendance status."
            );
        }

        return attendanceDAO.findByStatus(status);
    }

    public Attendance getById(long id)
            throws SQLException {

        if (id <= 0) {
            return null;
        }

        return attendanceDAO.findById(id);
    }

    public long create(Attendance attendance)
            throws SQLException {

        validate(attendance);

        if (attendanceDAO.existsByEnrollmentAndDate(
                attendance.getEnrollmentId(),
                attendance.getAttendanceDate())) {

            throw new IllegalArgumentException(
                    "Attendance already exists for this enrollment and date."
            );
        }

        return attendanceDAO.insert(attendance);
    }

    public void update(Attendance attendance)
            throws SQLException {

        if (attendance == null || attendance.getId() <= 0) {

            throw new IllegalArgumentException(
                    "Invalid attendance ID."
            );
        }

        validate(attendance);

        if (attendanceDAO.existsByEnrollmentAndDateExceptId(
                attendance.getEnrollmentId(),
                attendance.getAttendanceDate(),
                attendance.getId())) {

            throw new IllegalArgumentException(
                    "Attendance already exists for this enrollment and date."
            );
        }

        attendanceDAO.update(attendance);
    }

    public void delete(long id)
            throws SQLException {

        if (id <= 0) {

            throw new IllegalArgumentException(
                    "Invalid attendance ID."
            );
        }

        attendanceDAO.delete(id);
    }

    private void validate(Attendance attendance)
            throws SQLException {

        if (attendance == null) {

            throw new IllegalArgumentException(
                    "Attendance data is required."
            );
        }

        if (attendance.getEnrollmentId() <= 0) {

            throw new IllegalArgumentException(
                    "Please select an enrollment."
            );
        }

        if (enrollmentService.getById(
                attendance.getEnrollmentId()) == null) {

            throw new IllegalArgumentException(
                    "Selected enrollment does not exist."
            );
        }

        if (attendance.getAttendanceDate() == null) {

            attendance.setAttendanceDate(
                    LocalDate.now()
            );
        }

        String status = attendance.getStatus();

        if (status == null || status.trim().isEmpty()) {

            status = "PRESENT";

        } else {

            status = status.trim().toUpperCase();
        }

        if (!VALID_STATUSES.contains(status)) {

            throw new IllegalArgumentException(
                    "Invalid attendance status."
            );
        }

        attendance.setStatus(status);

        if (attendance.getCheckInTime() != null
                && attendance.getCheckOutTime() != null
                && attendance.getCheckOutTime()
                    .isBefore(attendance.getCheckInTime())) {

            throw new IllegalArgumentException(
                    "Check-out time cannot be before check-in time."
            );
        }

        if (attendance.getRemarks() != null
                && attendance.getRemarks().trim().length() > 500) {

            throw new IllegalArgumentException(
                    "Remarks must not exceed 500 characters."
            );
        }

        if (attendance.getMarkedByTeacherId() != null
                && attendance.getMarkedByTeacherId() > 0) {

            if (teacherService.getById(
                    attendance.getMarkedByTeacherId()) == null) {

                throw new IllegalArgumentException(
                        "Selected teacher does not exist."
                );
            }

        } else {

            attendance.setMarkedByTeacherId(null);
        }
    }
}