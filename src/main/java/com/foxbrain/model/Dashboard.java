package com.foxbrain.model;

import java.util.List;

public class Dashboard {

    private long totalStudents;
    private long totalTeachers;
    private long totalCourses;
    private long totalBatches;

    private long pendingAdmissions;
    private long todayAttendance;
    private long pendingFees;
    private long upcomingExams;

    private List<Student> recentStudents;

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalBatches() {
        return totalBatches;
    }

    public void setTotalBatches(long totalBatches) {
        this.totalBatches = totalBatches;
    }

    public long getPendingAdmissions() {
        return pendingAdmissions;
    }

    public void setPendingAdmissions(long pendingAdmissions) {
        this.pendingAdmissions = pendingAdmissions;
    }

    public long getTodayAttendance() {
        return todayAttendance;
    }

    public void setTodayAttendance(long todayAttendance) {
        this.todayAttendance = todayAttendance;
    }

    public long getPendingFees() {
        return pendingFees;
    }

    public void setPendingFees(long pendingFees) {
        this.pendingFees = pendingFees;
    }

    public long getUpcomingExams() {
        return upcomingExams;
    }

    public void setUpcomingExams(long upcomingExams) {
        this.upcomingExams = upcomingExams;
    }

    public List<Student> getRecentStudents() {
        return recentStudents;
    }

    public void setRecentStudents(List<Student> recentStudents) {
        this.recentStudents = recentStudents;
    }
}