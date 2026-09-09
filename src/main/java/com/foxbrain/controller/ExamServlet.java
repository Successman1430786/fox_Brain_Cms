package com.foxbrain.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.Exam;
import com.foxbrain.service.ExamService;

@WebServlet("/admin/exams")
public class ExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamService examService;

    @Override
    public void init() throws ServletException {
        examService = new ExamService();
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

        switch (action) {

            case "list":
                listExams(request, response);
                break;

            case "view":
                viewExam(request, response);
                break;

            case "edit":
                editExam(request, response);
                break;

            case "delete":
                deleteExam(request, response);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/exams?action=list");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {

            createExam(request, response);

        } else if ("update".equalsIgnoreCase(action)) {

            updateExam(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/exams?action=list");
        }
    }

    private void listExams(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "exams",
                examService.getAllExams());

        request.getRequestDispatcher(
                "/admin/exams/index.jsp")
                .forward(request, response);
    }

    private void viewExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long id = parseLong(request.getParameter("id"));

        if (id == null) {
            redirectWithError(request, response,
                    "Invalid exam ID.");
            return;
        }

        Exam exam = examService.getExamById(id);

        if (exam == null) {
            redirectWithError(request, response,
                    "Exam not found.");
            return;
        }

        request.setAttribute("exam", exam);

        request.getRequestDispatcher(
                "/admin/exams/view.jsp")
                .forward(request, response);
    }

    private void editExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long id = parseLong(request.getParameter("id"));

        if (id == null) {
            redirectWithError(request, response,
                    "Invalid exam ID.");
            return;
        }

        Exam exam = examService.getExamById(id);

        if (exam == null) {
            redirectWithError(request, response,
                    "Exam not found.");
            return;
        }

        request.setAttribute("exam", exam);

        request.getRequestDispatcher(
                "/admin/exams/edit.jsp")
                .forward(request, response);
    }

    private void createExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Exam exam = buildExamFromRequest(request);

            boolean success =
                    examService.createExam(exam);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/exams?action=list&success=created");

            } else {

                redirectWithError(
                        request,
                        response,
                        "Unable to create exam. Please check the entered data.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            redirectWithError(
                    request,
                    response,
                    "Invalid exam information.");
        }
    }

    private void updateExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Long id =
                    parseLong(request.getParameter("id"));

            if (id == null) {
                redirectWithError(
                        request,
                        response,
                        "Invalid exam ID.");
                return;
            }

            Exam exam =
                    buildExamFromRequest(request);

            exam.setId(id);

            boolean success =
                    examService.updateExam(exam);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/exams?action=list&success=updated");

            } else {

                redirectWithError(
                        request,
                        response,
                        "Unable to update exam.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            redirectWithError(
                    request,
                    response,
                    "Invalid exam information.");
        }
    }

    private void deleteExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long id =
                parseLong(request.getParameter("id"));

        if (id == null) {
            redirectWithError(
                    request,
                    response,
                    "Invalid exam ID.");
            return;
        }

        boolean success =
                examService.deleteExam(id);

        if (success) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/exams?action=list&success=deleted");

        } else {

            redirectWithError(
                    request,
                    response,
                    "Unable to delete exam.");
        }
    }

    private Exam buildExamFromRequest(
            HttpServletRequest request) {

        Exam exam = new Exam();

        exam.setBatchId(
                parseLongValue(
                        request.getParameter("batchId")));

        exam.setTitle(
                trim(request.getParameter("title")));

        exam.setExamType(
                trim(request.getParameter("examType")));

        exam.setExamMode(
                trim(request.getParameter("examMode")));

        String examDate =
                trim(request.getParameter("examDate"));

        if (!examDate.isEmpty()) {
            exam.setExamDate(
                    Date.valueOf(examDate));
        }

        String startTime =
                trim(request.getParameter("startTime"));

        if (!startTime.isEmpty()) {
            exam.setStartTime(
                    Time.valueOf(startTime));
        }

        String endTime =
                trim(request.getParameter("endTime"));

        if (!endTime.isEmpty()) {
            exam.setEndTime(
                    Time.valueOf(endTime));
        }

        String duration =
                trim(request.getParameter("durationMinutes"));

        if (!duration.isEmpty()) {
            exam.setDurationMinutes(
                    Integer.parseInt(duration));
        }

        String totalMarks =
                trim(request.getParameter("totalMarks"));

        if (!totalMarks.isEmpty()) {
            exam.setTotalMarks(
                    new BigDecimal(totalMarks));
        }

        String passingMarks =
                trim(request.getParameter("passingMarks"));

        if (!passingMarks.isEmpty()) {
            exam.setPassingMarks(
                    new BigDecimal(passingMarks));
        }

        exam.setRoomName(
                trim(request.getParameter("roomName")));

        exam.setInstructions(
                trim(request.getParameter("instructions")));

        exam.setAllowNavigation(
                request.getParameter("allowNavigation") != null);

        exam.setShuffleQuestions(
                request.getParameter("shuffleQuestions") != null);

        exam.setShuffleOptions(
                request.getParameter("shuffleOptions") != null);

        exam.setStatus(
                trim(request.getParameter("status")));

        return exam;
    }

    private Long parseLong(String value) {

        try {

            if (value == null || value.trim().isEmpty()) {
                return null;
            }

            return Long.parseLong(value.trim());

        } catch (NumberFormatException e) {

            return null;
        }
    }

    private long parseLongValue(String value) {

        Long parsed = parseLong(value);

        return parsed == null ? 0L : parsed;
    }

    private String trim(String value) {

        return value == null ? "" : value.trim();
    }

    private void redirectWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exams?action=list&error="
                + java.net.URLEncoder.encode(
                        message,
                        java.nio.charset.StandardCharsets.UTF_8));
    }
}