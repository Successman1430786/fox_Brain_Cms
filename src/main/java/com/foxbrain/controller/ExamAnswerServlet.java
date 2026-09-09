package com.foxbrain.controller;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.ExamAnswer;
import com.foxbrain.model.ExamAttempt;
import com.foxbrain.service.ExamAnswerService;
import com.foxbrain.service.ExamAttemptService;

@WebServlet("/exam/answers")
public class ExamAnswerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamAnswerService answerService;
    private ExamAttemptService attemptService;

    @Override
    public void init() throws ServletException {

        answerService =
                new ExamAnswerService();

        attemptService =
                new ExamAttemptService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("save".equalsIgnoreCase(action)) {

            saveAnswer(request, response);

        } else if ("evaluate".equalsIgnoreCase(action)) {

            evaluateAnswer(request, response);

        } else if ("autoEvaluate".equalsIgnoreCase(action)) {

            autoEvaluate(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/student/dashboard.jsp");
        }
    }

    private void saveAnswer(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long attemptId =
                    Long.parseLong(
                            request.getParameter(
                                    "attemptId"));

            long examQuestionId =
                    Long.parseLong(
                            request.getParameter(
                                    "examQuestionId"));

            ExamAttempt attempt =
                    attemptService.getAttemptById(
                            attemptId);

            if (attempt == null ||
                !"IN_PROGRESS".equalsIgnoreCase(
                        attempt.getStatus())) {

                sendError(
                        request,
                        response,
                        "This exam attempt is no longer active.");
                return;
            }

            ExamAnswer answer =
                    new ExamAnswer();

            answer.setAttemptId(
                    attemptId);

            answer.setExamQuestionId(
                    examQuestionId);

            String selectedOption =
                    request.getParameter(
                            "selectedOptionId");

            if (selectedOption != null &&
                !selectedOption.trim().isEmpty()) {

                answer.setSelectedOptionId(
                        Long.parseLong(
                                selectedOption));
            }

            answer.setAnswerText(
                    trim(
                            request.getParameter(
                                    "answerText")));

            boolean success =
                    answerService.saveAnswer(
                            answer);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/student/exam-attempt?action=view"
                        + "&attemptId="
                        + attemptId
                        + "&saved=true");

            } else {

                sendError(
                        request,
                        response,
                        "Unable to save answer.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            sendError(
                    request,
                    response,
                    "Invalid answer.");
        }
    }

    private void evaluateAnswer(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long answerId =
                    Long.parseLong(
                            request.getParameter(
                                    "answerId"));

            BigDecimal marks =
                    new BigDecimal(
                            request.getParameter(
                                    "marksObtained"));

            String correctValue =
                    request.getParameter(
                            "isCorrect");

            Boolean correct = null;

            if (correctValue != null &&
                !correctValue.trim().isEmpty()) {

                correct =
                        Boolean.parseBoolean(
                                correctValue);
            }

            String remarks =
                    trim(
                            request.getParameter(
                                    "teacherRemarks"));

            boolean success =
                    answerService.evaluateAnswer(
                            answerId,
                            marks,
                            correct,
                            remarks);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/exams/evaluation.jsp?answerId="
                    + answerId
                    + "&success="
                    + (success ? "evaluated" : "false"));

        } catch (Exception e) {

            e.printStackTrace();

            sendError(
                    request,
                    response,
                    "Unable to evaluate answer.");
        }
    }

    private void autoEvaluate(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long answerId =
                    Long.parseLong(
                            request.getParameter(
                                    "answerId"));

            ExamAnswer answer =
                    answerService.getAnswerById(
                            answerId);

            if (answer == null) {

                sendError(
                        request,
                        response,
                        "Answer not found.");
                return;
            }

            boolean success =
                    answerService.autoEvaluateAnswer(
                            answer);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/exams/evaluation.jsp?answerId="
                    + answerId
                    + "&success="
                    + (success ? "evaluated" : "false"));

        } catch (Exception e) {

            e.printStackTrace();

            sendError(
                    request,
                    response,
                    "Unable to auto-evaluate answer.");
        }
    }

    private String trim(String value) {

        return value == null ? "" : value.trim();
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