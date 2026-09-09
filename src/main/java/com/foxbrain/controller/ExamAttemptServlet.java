package com.foxbrain.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.ExamAttempt;
import com.foxbrain.service.ExamAttemptService;

@WebServlet(
        value = "/student/exam-attempt",
        initParams = {
                @WebInitParam(
                        name = "studentSessionAttribute",
                        value = "studentId")
        }
)
public class ExamAttemptServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamAttemptService attemptService;

    @Override
    public void init() throws ServletException {
        attemptService =
                new ExamAttemptService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if (action == null ||
            action.trim().isEmpty()) {

            action = "start";
        }

        Long studentId =
                getStudentId(request);

        if (studentId == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");
            return;
        }

        switch (action) {

            case "start":
                start(request, response, studentId);
                break;

            case "view":
                view(request, response, studentId);
                break;

            case "submit":
                submit(request, response, studentId, false);
                break;

            case "autoSubmit":
                submit(request, response, studentId, true);
                break;

            case "abandon":
                abandon(request, response, studentId);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                        + "/student/dashboard.jsp");
        }
    }

    private void start(
            HttpServletRequest request,
            HttpServletResponse response,
            long studentId)
            throws ServletException, IOException {

        Long examId =
                parseLong(
                        request.getParameter(
                                "examId"));

        if (examId == null) {
            sendError(
                    request,
                    response,
                    "Invalid exam.");
            return;
        }

        ExamAttempt attempt =
                attemptService.startAttempt(
                        examId,
                        studentId);

        if (attempt == null) {

            sendError(
                    request,
                    response,
                    "This exam cannot be started.");
            return;
        }

        request.setAttribute(
                "attempt",
                attempt);

        request.getRequestDispatcher(
                "/student/exam/start.jsp")
                .forward(request, response);
    }

    private void view(
            HttpServletRequest request,
            HttpServletResponse response,
            long studentId)
            throws ServletException, IOException {

        Long attemptId =
                parseLong(
                        request.getParameter(
                                "attemptId"));

        if (attemptId == null) {
            sendError(
                    request,
                    response,
                    "Invalid attempt.");
            return;
        }

        ExamAttempt attempt =
                attemptService.getAttemptById(
                        attemptId);

        if (attempt == null ||
            attempt.getStudentId() != studentId) {

            sendError(
                    request,
                    response,
                    "Attempt not found.");
            return;
        }

        request.setAttribute(
                "attempt",
                attempt);

        request.getRequestDispatcher(
                "/student/exam/take-exam.jsp")
                .forward(request, response);
    }

    private void submit(
            HttpServletRequest request,
            HttpServletResponse response,
            long studentId,
            boolean autoSubmit)
            throws IOException {

        Long attemptId =
                parseLong(
                        request.getParameter(
                                "attemptId"));

        if (attemptId == null) {
            sendError(
                    request,
                    response,
                    "Invalid attempt.");
            return;
        }

        ExamAttempt attempt =
                attemptService.getAttemptById(
                        attemptId);

        if (attempt == null ||
            attempt.getStudentId() != studentId) {

            sendError(
                    request,
                    response,
                    "Invalid attempt.");
            return;
        }

        boolean success;

        if (autoSubmit) {

            success =
                    attemptService.autoSubmitAttempt(
                            attemptId);

        } else {

            success =
                    attemptService.submitAttempt(
                            attemptId);
        }

        if (success) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/student/exam-attempt?action=view&attemptId="
                    + attemptId
                    + "&submitted=true");

        } else {

            sendError(
                    request,
                    response,
                    "Unable to submit exam.");
        }
    }

    private void abandon(
            HttpServletRequest request,
            HttpServletResponse response,
            long studentId)
            throws IOException {

        Long attemptId =
                parseLong(
                        request.getParameter(
                                "attemptId"));

        if (attemptId == null) {
            sendError(
                    request,
                    response,
                    "Invalid attempt.");
            return;
        }

        ExamAttempt attempt =
                attemptService.getAttemptById(
                        attemptId);

        if (attempt == null ||
            attempt.getStudentId() != studentId) {

            sendError(
                    request,
                    response,
                    "Invalid attempt.");
            return;
        }

        boolean success =
                attemptService.abandonAttempt(
                        attemptId);

        response.sendRedirect(
                request.getContextPath()
                + "/student/dashboard.jsp?success="
                + (success ? "abandoned" : "false"));
    }

    private Long getStudentId(
            HttpServletRequest request) {

        Object value =
                request.getSession()
                        .getAttribute(
                                "studentId");

        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        return parseLong(
                value.toString());
    }

    private Long parseLong(String value) {

        try {

            if (value == null ||
                value.trim().isEmpty()) {

                return null;
            }

            return Long.parseLong(value.trim());

        } catch (NumberFormatException e) {

            return null;
        }
    }

    private void sendError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/student/dashboard.jsp?error="
                + java.net.URLEncoder.encode(
                        message,
                        java.nio.charset.StandardCharsets.UTF_8));
    }
}